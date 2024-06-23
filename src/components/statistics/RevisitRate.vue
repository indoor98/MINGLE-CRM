<template>
  <div>
    <h2>Overall Revisit Rate</h2>
    <Bar v-if="loaded" :data="overallChartData" :options="chartOptions" />
    <h2>Revisit Rate By Gender</h2>
    <Doughnut v-if="loaded" :data="genderChartData" :options="chartOptions" />
    <h2>Revisit Rate By Grade</h2>
    <Bar v-if="loaded" :data="gradeChartData" :options="chartOptions" />
  </div>
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

const overallChartData = ref({
  labels: ["Overall"],
  datasets: [
    {
      label: "Overall Revisit Rate",
      backgroundColor: "#42A5F5",
      data: [0], // 초기값 설정
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

const gradeChartData = ref({
  labels: ["NEW", "VIP", "BASIC", "VVIP"],
  datasets: [
    {
      label: "Revisit Rate By Grade",
      backgroundColor: ["#FFCE56", "#FF6384", "#36A2EB", "#4BC0C0"],
      data: [0, 0, 0, 0], // 초기값 설정
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
  },
});

const loaded = ref(false); // 데이터 로딩 상태

onMounted(async () => {
  try {
    const response = await axios.get(
      "http://localhost:8080/api/v1/statistics/customers/revisit-rate"
    );

    const responseData = response.data;

    overallChartData.value.datasets[0].data = [responseData.overallRevisitRate];
    genderChartData.value.datasets[0].data = [
      responseData.revisitRateByGender.Male,
      responseData.revisitRateByGender.Female,
    ];
    gradeChartData.value.datasets[0].data = [
      responseData.revisitRateByGrade.NEW,
      responseData.revisitRateByGrade.VIP,
      responseData.revisitRateByGrade.BASIC,
      responseData.revisitRateByGrade.VVIP,
    ];

    loaded.value = true; // 데이터 로딩이 완료되면 true로 설정
  } catch (error) {
    console.error("Error fetching data:", error);
  }
});
</script>

<style></style>
