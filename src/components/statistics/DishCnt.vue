<template>
  <div class="q-pa-md">
    <Bar
      v-if="loaded"
      ref="barChart"
      :data="chartData"
      :options="chartOptions"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from "vue";
import { Bar } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  CategoryScale,
  LinearScale,
} from "chart.js";
import { api as axios } from "src/boot/axios";

ChartJS.register(Title, Tooltip, Legend, CategoryScale, LinearScale);

const chartData = ref({
  labels: [],
  datasets: [
    {
      label: "판매량",
      backgroundColor: [
        "#FF6384",
        "#36A2EB",
        "#FFCE56",
        "#4BC0C0",
        "#9966FF",
        "#FF9F40",
        "#2ECC71",
        "#FFD700",
        "#CD5C5C",
        "#FF69B4",
        "#48D1CC",
        "#87CEEB",
        "#9370DB",
        "#32CD32",
        "#FF6347",
        "#8A2BE2",
        "#4682B4",
        "#FF7F50",
        "#00BFFF",
        "#9ACD32",
      ],
      data: [],
    },
  ],
});

const chartOptions = ref({
  indexAxis: "y",
  responsive: true,
  plugins: {
    legend: {
      display: true,
    },
    title: {
      display: true,
      text: "메뉴별 판매량 통계",
    },
    datalabels: {
      anchor: "end",
      align: "right",
      formatter: (value) => value,
      font: {
        weight: "bold",
      },
    },
  },
  scales: {
    x: {
      ticks: {
        beginAtZero: true,
      },
    },
    y: {
      ticks: {
        reverse: true,
      },
    },
  },
});

const loaded = ref(false);

const fetchDishNameStatistics = async () => {
  try {
    const response = await axios.get(
      "/api/v1/statistics/purchase/statistics/dishname"
    );
    const data = response.data;

    const labels = data.map((item) => item.dishName);
    const counts = data.map((item) => item.count);

    chartData.value.labels = labels;
    chartData.value.datasets[0].data = counts;

    loaded.value = true;

    // 차트가 로드된 후 리사이즈
    await nextTick();
    if (barChart.value) {
      barChart.value.resize();
    }

    // 차트가 로드되었음을 부모 컴포넌트에 알림
    emit("chart-loaded");
  } catch (error) {
    console.error("Error fetching dish name statistics:", error);
  }
};

const barChart = ref(null);

onMounted(() => {
  fetchDishNameStatistics();
});
</script>

<style scoped></style>
