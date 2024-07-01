<template>
  <div>
    <Bar v-if="loaded" :data="chartData" :options="chartOptions" />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { Bar } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from "chart.js";
import { api as axios } from "src/boot/axios";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
);

const loaded = ref(false);
const chartData = ref({
  labels: ["20대", "30대", "40대", "50대", "60대 이상"],
  datasets: [
    {
      label: "여성 재방문율 (%)",
      backgroundColor: "rgba(255, 99, 132, 0.2)",
      borderColor: "rgba(255, 99, 132, 1)",
      borderWidth: 1,
      data: [], // Data will be populated after API calls
    },
    {
      label: "남성 재방문율 (%)",
      backgroundColor: "rgba(54, 162, 235, 0.2)",
      borderColor: "rgba(54, 162, 235, 1)",
      borderWidth: 1,
      data: [], // Data will be populated after API calls
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
      text: "연령 및 성별별 재방문율",
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
        display: false,
      },
    },
    y: {
      beginAtZero: true,
      min: 80, // y축의 최소값을 80으로 설정하여 낮은 값들을 배제
      max: 102, // 재방문율은 퍼센트로 표시되므로 최대값을 100으로 설정
      ticks: {
        stepSize: 2, // y축 눈금의 간격을 더 촘촘하게 설정
      },
    },
  },
});

const fetchData = async () => {
  try {
    const revisitResponse = await axios.get(
      "/api/v1/statistics/customers/revisit/age-gender"
    );
    const visitResponse = await axios.get(
      "/api/v1/statistics/customers/visit/age-gender"
    );

    const revisitData = revisitResponse.data.data;
    const visitData = visitResponse.data.data;

    const femaleRates = [
      (revisitData.twentyFemale / visitData.twentyFemale) * 100,
      (revisitData.thirtyFemale / visitData.thirtyFemale) * 100,
      (revisitData.fortyFemale / visitData.fortyFemale) * 100,
      (revisitData.fiftyFemale / visitData.fiftyFemale) * 100,
      (revisitData.overSixtyFemale / visitData.overSixtyFemale) * 100,
    ];

    const maleRates = [
      (revisitData.twentyMale / visitData.twentyMale) * 100,
      (revisitData.thirtyMale / visitData.thirtyMale) * 100,
      (revisitData.fortyMale / visitData.fortyMale) * 100,
      (revisitData.fiftyMale / visitData.fiftyMale) * 100,
      (revisitData.overSixtyMale / visitData.overSixtyMale) * 100,
    ];

    chartData.value.datasets[0].data = femaleRates;
    chartData.value.datasets[1].data = maleRates;
    loaded.value = true;
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

onMounted(() => {
  fetchData();
});
</script>

<style scoped>
/* Add any necessary styling here */
</style>
