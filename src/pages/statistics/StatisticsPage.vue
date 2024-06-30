<template>
  <div id="statistics-page">
    <h1>Statistics Page</h1>
    <ReservationCntMonthlyThisYear />
    <RevisitRate />
    <ReservationCntByYear />
    <ReservationCntMonthly />
    <ReservationCntWeekly />
    <VisitedCustomers />
  </div>
</template>

<script setup>
import RevisitRate from "../../components/statistics/RevisitRate.vue";
import ReservationCntByYear from "../../components/statistics/ReservationCntByYear.vue";
import ReservationCntMonthly from "../../components/statistics/ReservationCntMonthly.vue";
import ReservationCntWeekly from "../../components/statistics/ReservationCntWeekly.vue";
import VisitedCustomers from "../../components/statistics/VisitedCustomersByPeriod.vue";
import ReservationCntMonthlyThisYear from "../../components/statistics/ReservationCntMonthlyThisYear.vue";
</script>
