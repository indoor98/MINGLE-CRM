<template>
  <q-page>
    <div class="q-pa-md">
      <q-tabs v-model="selectedTab">
        <q-tab name="dining" label="다이닝 통계" />
      </q-tabs>
      <q-separator />
      <div class="q-pa-md">
        <div class="row col-12">
          <div class="col-12 q-pl-sm q-mb-sm">
            <q-card
              class="row full-width justify-center card-item"
              style="align-content: center; height: 35rem"
            >
              <!-- DishCnt 컴포넌트에서 chart-loaded 이벤트를 handleChartLoaded 메서드와 연결 -->
              <DishCnt class="full-width" @chart-loaded="handleChartLoaded" />
            </q-card>
          </div>
        </div>
        <div class="row col-12">
          <div class="col-12 q-pr-sm q-mt-sm row">
            <q-card class="q-pa-md full-width" style="align-content: center">
              <DiningReservationByPeriod />
            </q-card>
          </div>
        </div>
      </div>
    </div>
  </q-page>
</template>

<script setup>
import { ref, onMounted } from "vue";
import DiningReservationByPeriod from "src/components/statistics/DiningReservationByPeriod.vue";
import { api as axios } from "src/boot/axios";
import DishCnt from "src/components/statistics/DishCnt.vue";

const selectedTab = ref("dining");

const handleChartLoaded = () => {
  console.log("Chart has been loaded and resized");
};

onMounted(() => {
  // 필요한 경우 다른 초기화 작업
});
</script>

<style scoped>
.card-item {
  border-radius: 5px;
}
</style>
