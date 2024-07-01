<template>
  <!-- <div>고객 등급별 재방문률</div> -->
  <Bar
    v-if="loaded && chartName === 'byGrade'"
    :data="chartData"
    :options="chartOptions"
  />
  <!-- <div>고객 성별별 재방문률</div> -->
  <Doughnut
    v-if="loaded && chartShape === 'doughnut'"
    :data="genderChartData"
    :options="genderChartOptions"
  />
  <Bar
    v-if="loaded && chartShape === 'bar'"
    :data="genderChartData"
    :options="genderChartOptions"
  />
</template>

<script setup>
import { api as axios } from "src/boot/axios";
import { ref, onMounted, defineProps } from "vue";
import { Bar, Doughnut } from "vue-chartjs";
import Chart from "chart.js/auto";

const props = defineProps(["chartShape", "chartName"]);

const chartData = ref({
  labels: ["BROWN", "SILVER", "GOLD", "DIAMOND", "전체"],
  datasets: [
    {
      label: "재방문율 (%)",
      backgroundColor: ["#FF6666", "#FF9966", "#FFFF66", "#66FF66", "#66B2FF"],
      data: [0, 0, 0, 0, 0], // 초기값 설정
    },
  ],
});

const genderChartData = ref({
  labels: ["여성", "남성"],
  datasets: [
    {
      label: "Revisit Rate By Gender",
      backgroundColor: ["#FF6666", "#66B2FF"],
      data: [0, 0], // 초기값 설정
    },
  ],
});

const chartOptions = ref({
  // indexAxis: "y",
  responsive: true,
  plugins: {
    legend: {
      position: "top",
    },
    title: {
      display: true,
      text: "등급별 재방문율 통계",
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
      text: "성별별 재방문률",
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
      responseData.revisitRateByGrade.BROWN,
      responseData.revisitRateByGrade.SILVER,
      responseData.revisitRateByGrade.GOLD,
      responseData.revisitRateByGrade.DIAMOND,
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

<style scoped>
.card-item {
  height: 24.5rem !important;
  border-radius: 5px;
}

.card-item-2 {
  height: 24.5rem !important;
  border-radius: 5px;
  background: linear-gradient(
    145deg,
    rgb(252, 189, 138) 10%,
    rgb(255, 146, 73)
  );
}
</style>
