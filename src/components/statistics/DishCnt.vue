<template>
  <div class="q-pa-md">
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
  CategoryScale,
  LinearScale,
} from "chart.js";
import { api as axios } from "src/boot/axios";

ChartJS.register(Title, Tooltip, Legend, CategoryScale, LinearScale);

const chartData = ref({
  labels: [],
  datasets: [
    {
      label: "Dish Counts",
      backgroundColor: [
        "#FF6384", // Red
        "#36A2EB", // Blue
        "#FFCE56", // Yellow
        "#4BC0C0", // Cyan
        "#9966FF", // Purple
        "#FF9F40", // Orange
        "#2ECC71", // Green
        "#FFD700", // Gold
        "#CD5C5C", // Indian Red
        "#FF69B4", // Hot Pink
        "#48D1CC", // Medium Turquoise
        "#87CEEB", // Sky Blue
        "#9370DB", // Medium Purple
        "#32CD32", // Lime Green
        "#FF6347", // Tomato
        "#8A2BE2", // Blue Violet
        "#4682B4", // Steel Blue
        "#FF7F50", // Coral
        "#00BFFF", // Deep Sky Blue
        "#9ACD32", // Yellow Green
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
      display: false,
    },
    title: {
      display: true,
      text: "Dish Counts by Name",
    },
    datalabels: {
      anchor: "end",
      align: "top",
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
  } catch (error) {
    console.error("Error fetching dish name statistics:", error);
  }
};

onMounted(() => {
  fetchDishNameStatistics();
});
</script>

<style scoped></style>
