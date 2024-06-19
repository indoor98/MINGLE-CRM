<template>
  <Bar :data="chartData" :options="chartOptions" />
</template>

<script setup>
import { ref } from "vue";
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

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
);

const chartData = ref({
  labels: ["January", "February", "March", "April", "May"],
  datasets: [
    {
      label: "Sales",
      backgroundColor: "#42A5F5",
      data: [40, 20, 30, 10, 50],
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
      text: "Monthly Sales",
    },
  },
});
</script>

<style></style>
