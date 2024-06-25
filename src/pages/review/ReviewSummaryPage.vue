<template>
  <q-page>
    <div class="q-pa-md">
      <q-tabs
        v-model="selectedTab"
        narrow-indicator
        densealign="justify"
        class="text-primary bg-accent"
      >
        <q-tab name="hotelSummary" label="호텔 리뷰 요약" />
        <q-tab name="diningSummary" label="다이닝 리뷰 요약" />
      </q-tabs>

      <div v-if="selectedTab === 'hotelSummary'">
        <HotelReviewSummaryPage />
      </div>
      <div v-else-if="selectedTab === 'diningSummary'">
        <DiningReviewSummaryPage />
      </div>
    </div>
  </q-page>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { api as axios } from "src/boot/axios";
import { date } from "quasar";
import DiningReviewSummaryPage from "./DiningReviewSummaryPage.vue";
import HotelReviewSummaryPage from "./HotelReviewSummaryPage.vue";

const selectedTab = ref("hotelSummary");

onMounted(() => {
  // getHotelReviewsAverageRatings(, endDate.value);
  // getHotelReviewsNumber();
  // const converted = Date().toISOString().toString().slice(0, 11) + "00:00:00";
});
</script>
