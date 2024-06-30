<template>
  <q-page>
    <div class="q-pa-md">
      <q-tabs v-model="selectedTab">
        <q-tab name="revisit-current" label="재방문 현황" />
        <q-tab name="reservation-current" label="예약 현황" />
        <q-tab name="reservation-by-period" label="기간별 방문 조회" />
      </q-tabs>
      <q-separator />

      <div v-if="selectedTab === 'revisit-current'">
        <RevisitCurrent />
      </div>
      <div v-else-if="selectedTab === 'reservation-current'">
        <ReservationCurrent />
      </div>
      <div v-else-if="selectedTab === 'reservation-by-period'">
        <div class="row col-12">
          <VisitedCustomersByPeriod class="col-6" />
          <VisitedCustomersByPeriod class="col-6" />
        </div>
      </div>
    </div>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import RevisitCurrent from "src/components/statistics/RevisitCurrent.vue";
// import ReservationByPeriod from "src/components/statistics/ReservationByPeriod.vue";
import VisitedCustomersByPeriod from "src/components/statistics/VisitedCustomersByPeriod.vue";
import ReservationCurrent from "src/components/statistics/ReservationCurrent.vue";

const selectedTab = ref("revisit-current");
</script>

<style></style>
