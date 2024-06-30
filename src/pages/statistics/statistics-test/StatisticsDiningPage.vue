<template>
  <div class="q-pa-md">
    <div class="row col-12">
      <!-- <div class="col-3 q-pr-sm q-mb-sm row">
        <q-card class="q-pa-md row full-width justify-center card-item">
          <q-circular-progress
            :value="revisitRate"
            size="300px"
            :thickness="0.1"
            color="blue-grey-9"
            track-color="grey-3"
            class="q-ma-md full-width"
            show-value
          >
            <span class="text-blue-grey-9 text-h3">{{ revisitRate }}%</span>
          </q-circular-progress>
          <div class="text-subtitle1 text-bold">전체 고객 재방문률</div>
        </q-card>
      </div> -->
      <div class="col-12 q-pl-sm q-mb-sm">
        <q-card
          class="row full-width justify-center card-item"
          style="align-content: center"
        >
          <!-- <RevisitRate :chartName="byGrade" class="full-width" /> -->
          <DishCnt class="full-width" />
        </q-card>
      </div>
    </div>
    <div class="row col-12">
      <div class="col-12 q-pr-sm q-mt-sm row">
        <q-card class="q-pa-md full-width" style="align-content: center">
          <!-- 여기 연령/성별별 통계로 바꾸기 -->
          <DiningReservationByPeriod />
        </q-card>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import RevisitRate from "src/components/statistics/RevisitRate.vue";
import DiningReservationByPeriod from "src/components/statistics/DiningReservationByPeriod.vue";
import { api as axios } from "src/boot/axios";
import DishCnt from "src/components/statistics/DishCnt.vue";

const revisitRate = ref(null);
const doughnut = "doughnut";
const bar = "bar";
const byGrade = "byGrade";

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
  /* height: 24.5rem !important; */
  border-radius: 5px;
}

.card-item-2 {
  /* height: 24.5rem !important; */
  border-radius: 5px;
  background: linear-gradient(
    145deg,
    rgb(252, 189, 138) 10%,
    rgb(255, 146, 73)
  );
}
</style>
