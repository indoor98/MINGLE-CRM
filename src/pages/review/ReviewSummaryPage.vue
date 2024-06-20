<template>
  <q-card class="my-card q-mt-xl" flat bordered>
    <q-item>
      <q-item-section> 전체 평균 평점 </q-item-section>

      <q-separator vertical />

      <q-item-section> 전체 리뷰 수 </q-item-section>

      <q-separator vertical />

      <q-item-section> 평점 비율 </q-item-section>
    </q-item>

    <q-separator />

    <q-card-section horizontal>
      <q-card-section> 검색 필터 들어갈 곳 </q-card-section>

      <q-separator vertical />

      <q-card-section class="col-4"> 리뷰 요약 내용 들어갈 곳 </q-card-section>
    </q-card-section>
  </q-card>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { api as axios } from "src/boot/axios";

const seletedSummaryTap = ref("positive");
const positiveReviewSummary = ref("");
const negativeReviewSummary = ref("");
const params = ref({
  startDate: "2020-01-01T00:00:00",
  endDate: "2025-01-01T23:59:59",
});

const getHotelReviewsAverageRatings = async () => {
  try {
    console.log("현재 시간 : ", Date.get);
    const response = await axios
      .get("/api/hotel/rating/average", {
        params: {
          startDate: "2020-01-01T00:00:00",
          endDate: "2025-01-01T23:59:59",
        },
      })
      .then(() => {
        console.log(response.data);
      });
  } catch (error) {
    console.log(error);
  }
};

const getHotelReviewSummary = async (summaryType, targetRef) => {
  try {
    const response = await axios.get(
      "http://localhost:8080/api/hotel/review/summary",
      {
        params: { summaryType },
      }
    );
    targetRef.value = response.data.data.summary;
  } catch (error) {
    console.error(`Error fetching ${summaryType} review summary:`, error);
  }
};

onMounted(() => {
  getHotelReviewSummary("POSITIVE", positiveReviewSummary);
  getHotelReviewSummary("NEGATIVE", negativeReviewSummary);
  getHotelReviewsAverageRatings();
});
</script>
