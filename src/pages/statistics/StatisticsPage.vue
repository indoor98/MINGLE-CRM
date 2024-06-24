<template>
  <div id="statistics-page">
    <h1>Statistics Page</h1>
    <!-- <BarChart /> -->
    <!-- <DoughnutChart /> -->
    <!-- <ReuseChart :updatedata="updatedata" /> -->
    <RevisitRate />
    <ReservationCntByYear />
    <ReservationCntMonthly />
    <ReservationCntWeekly />
    <VisitedCustomers />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import BarChart from "../../components/statistics/BarChart.vue";
import DoughnutChart from "../../components/statistics/DoughnutChart.vue";
import ReuseChart from "../../components/statistics/ReuseChart.vue";
import RevisitRate from "../../components/statistics/RevisitRate.vue";
import ReservationCntByYear from "../../components/statistics/ReservationCntByYear.vue";
import ReservationCntMonthly from "../../components/statistics/ReservationCntMonthly.vue";
import ReservationCntWeekly from "../../components/statistics/ReservationCntWeekly.vue";
import VisitedCustomers from "../../components/statistics/VisitedCustomersByPeriod.vue";

// const updatedata = ref([10, 20, 30, 40, 50]);
</script>
