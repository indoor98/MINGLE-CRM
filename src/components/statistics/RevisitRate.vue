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
import { ref, onMounted, watch } from "vue";
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

const data = ref(null);
const loaded = ref(false); // 데이터 로딩 상태

const overallChartData = ref({
  labels: ["Overall"],
  datasets: [
    {
      label: "Overall Revisit Rate",
      backgroundColor: "#42A5F5",
      data: [data.value ? data.value.overallRevisitRate : 0],
    },
  ],
});

const genderChartData = ref({
  labels: ["Male", "Female"],
  datasets: [
    {
      label: "Revisit Rate By Gender",
      backgroundColor: ["#FF6384", "#36A2EB"],
      data: [
        data.value ? data.value.revisitRateByGender.Male : 0,
        data.value ? data.value.revisitRateByGender.Female : 0,
      ],
    },
  ],
});

const gradeChartData = ref({
  labels: ["NEW", "VIP", "BASIC", "VVIP"],
  datasets: [
    {
      label: "Revisit Rate By Grade",
      backgroundColor: ["#FFCE56", "#FF6384", "#36A2EB", "#4BC0C0"],
      data: [
        data.value ? data.value.revisitRateByGrade.NEW : 0,
        data.value ? data.value.revisitRateByGrade.VIP : 0,
        data.value ? data.value.revisitRateByGrade.BASIC : 0,
        data.value ? data.value.revisitRateByGrade.VVIP : 0,
      ],
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

// 데이터가 변경될 때마다 차트 데이터 업데이트
watch(data, (newValue, oldValue) => {
  if (newValue) {
    overallChartData.value.datasets[0].data = [newValue.overallRevisitRate];
    genderChartData.value.datasets[0].data = [
      newValue.revisitRateByGender.Male,
      newValue.revisitRateByGender.Female,
    ];
    gradeChartData.value.datasets[0].data = [
      newValue.revisitRateByGrade.NEW,
      newValue.revisitRateByGrade.VIP,
      newValue.revisitRateByGrade.BASIC,
      newValue.revisitRateByGrade.VVIP,
    ];
  }
});

onMounted(async () => {
  try {
    const response = await axios.get(
      "http://localhost:8080/api/v1/statistics/revisit-rate"
    );

    data.value = response.data;

    loaded.value = true; // 데이터 로딩이 완료되면 true로 설정
  } catch (error) {
    console.error("Error fetching data:", error);
  }
});
</script>

<style></style>
