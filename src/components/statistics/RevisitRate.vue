<template>
  <q-page class="q-pa-md">
    <h2>고객 등급별 재방문률</h2>
    <Bar v-if="loaded" :data="chartData" :options="chartOptions" />
    <h2>고객 성별별 재방문률</h2>
    <Doughnut
      v-if="loaded"
      :data="genderChartData"
      :options="genderChartOptions"
    />
  </q-page>
</template>

<script setup>
import { api as axios } from "src/boot/axios";
import { ref, onMounted } from "vue";
import { Bar, Doughnut } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
  DoughnutController,
  ArcElement,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
  DoughnutController,
  ArcElement
);

const chartData = ref({
  labels: ["NEW", "VIP", "BASIC", "VVIP", "Overall"],
  datasets: [
    {
      label: "Revisit Rate By Grade",
      backgroundColor: ["#FFCE56", "#FF6384", "#36A2EB", "#4BC0C0", "#42A5F5"],
      data: [0, 0, 0, 0, 0], // 초기값 설정
    },
  ],
});

const genderChartData = ref({
  labels: ["Male", "Female"],
  datasets: [
    {
      label: "Revisit Rate By Gender",
      backgroundColor: ["#FF6384", "#36A2EB"],
      data: [0, 0], // 초기값 설정
    },
  ],
});

const chartOptions = ref({
  responsive: true,
  plugins: {
    legend: {
      position: "top",
    },
    title: {
      display: true,
      text: "Revisit Rate Statistics",
    },
    datalabels: {
      anchor: "end",
      align: "top",
      formatter: (value) => value.toFixed(2),
      font: {
        weight: "bold",
      },
    },
  },
  scales: {
    x: {
      grid: {
        display: true,
        borderColor: "rgba(0, 0, 0, 0.1)",
        drawBorder: true,
      },
    },
    y: {
      beginAtZero: false,
    },
  },
});

const genderChartOptions = ref({
  responsive: true,
  plugins: {
    legend: {
      position: "top",
    },
    title: {
      display: true,
      text: "Revisit Rate by Gender",
    },
    datalabels: {
      align: "top",
      formatter: (value) => value.toFixed(2),
      font: {
        weight: "bold",
      },
    },
  },
});

const loaded = ref(false); // 데이터 로딩 상태

onMounted(async () => {
  try {
    const response = await axios.get(
      "/api/v1/statistics/customers/revisit-rate"
    );

    const responseData = response.data;

    chartData.value.datasets[0].data = [
      responseData.revisitRateByGrade.NEW,
      responseData.revisitRateByGrade.VIP,
      responseData.revisitRateByGrade.BASIC,
      responseData.revisitRateByGrade.VVIP,
      responseData.overallRevisitRate,
    ];

    genderChartData.value.datasets[0].data = [
      responseData.revisitRateByGender.Male,
      responseData.revisitRateByGender.Female,
    ];

    loaded.value = true; // 데이터 로딩이 완료되면 true로 설정
  } catch (error) {
    console.error("Error fetching data:", error);
  }
});
</script>

<style></style>
