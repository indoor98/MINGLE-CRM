<template>
  <div id="statistics-page">
    <h1>Statistics Page</h1>
    <BarChart ref="bar" />
    <DoughnutChart ref="doughnut" />
    <ReuseChart :updatedata="updatedata" />
    <RevisitRate />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import BarChart from "../../components/BarChart.vue";
import DoughnutChart from "../../components/DoughnutChart.vue";
import ReuseChart from "../../components/ReuseChart.vue";
import RevisitRate from "../../components/statistics/RevisitRate.vue";

const updatedata = ref([10, 20, 30, 40, 50]);
</script>
