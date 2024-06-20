<template>
  <Doughnut :data="chartData" :options="chartOptions" />
</template>

<script setup>
import { ref } from "vue";
import { Doughnut } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  DoughnutController,
  CategoryScale,
  ArcElement,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  DoughnutController,
  CategoryScale,
  ArcElement
);

const chartData = ref({
  labels: ["January", "February", "March", "April", "May"],
  datasets: [
    {
      label: "Sales",
      backgroundColor: ["#FF6384", "#36A2EB", "#FFCE56", "#4BC0C0", "#9966FF"],
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
