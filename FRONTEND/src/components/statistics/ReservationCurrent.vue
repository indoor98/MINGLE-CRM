<template>
  <div class="q-pa-md">
    <div class="row col-12">
      <div class="col-6 q-pa-sm">
        <q-card
          class="full-width justify-center card-item"
          style="align-content: center"
        >
          <ReservationCntByRoom class="full-width" />
        </q-card>
      </div>
      <div class="col-6 q-pa-sm">
        <q-card
          class="full-width justify-center card-item"
          style="align-content: center"
        >
          <ReservationCntMonthlyThisYear class="full-width" />
        </q-card>
      </div>
    </div>
    <div class="row col-12 q-pa-sm">
      <div class="col-12 q-mt-sm row">
        <q-card class="q-pa-md full-width">
          <ReservationCntByAgeGender />
        </q-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import ECharts from "vue-echarts";
import "echarts";
import ReservationCntMonthlyThisYear from "./ReservationCntMonthlyThisYear.vue";
import ReservationCntByRoom from "./ReservationCntByRoom.vue";
import ReservationCntByAgeGender from "./RoomReservationCntByAgeGender.vue";
import { api as axios } from "src/boot/axios";

const revisitRate = ref(null);

const fetchRevisitRate = async () => {
  try {
    const response = await axios.get(
      "/api/v1/statistics/customers/revisit-rate"
    );
    revisitRate.value = response.data.overallRevisitRate.toFixed(2);
  } catch {
    console.log("잘못된 요청 어쩌구");
  }
};

onMounted(() => {
  fetchRevisitRate();
});
</script>

<style scoped>
.card-item {
  min-height: 20rem !important;
  border-radius: 5px;
}
</style>
