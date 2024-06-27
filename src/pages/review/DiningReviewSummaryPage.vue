<template>
  <q-card class="my-card q-mt-xl" flat bordered>
    <q-item>
      <q-item-section class="row flex flex-center">
        <div class="q-pa-md" style="font-size: 25px">
          요약된 호텔 리뷰 총 평점
        </div>
        <q-rating
          size="50px"
          v-model="averageRating"
          :max="5"
          color="primary"
          readonly
          class="q-pa-md"
        />
        <div class="q-pa-md" style="font-size: 25px">
          {{ averageRating.toFixed(2) }} / 5
        </div>
      </q-item-section>

      <q-separator vertical />

      <q-item-section class="row flex flex-center">
        <div class="q-pb-md" style="font-size: 25px">요약된 리뷰 수</div>
        <q-icon class="q-pa-md" name="people" size="60px"></q-icon>
        <div style="font-size: 25px">
          {{ reviewsNumber }}
        </div>
      </q-item-section>
    </q-item>

    <q-separator />

    <q-card-section horizontal>
      <q-card-section class="flex flex-center">
        <q-input
          v-model="startDate"
          mask="date"
          :rules="['date']"
          label="리뷰 작성 시작일"
        >
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer">
              <q-popup-proxy
                cover
                transition-show="scale"
                transition-hide="scale"
              >
                <q-date v-model="startDate">
                  <div class="row items-center justify-end">
                    <q-btn
                      v-close-popup
                      label="선택하기"
                      color="primary"
                      flat
                    />
                  </div>
                </q-date>
              </q-popup-proxy>
            </q-icon>
          </template>
        </q-input>
        <q-input
          v-model="endDate"
          mask="date"
          :rules="['date']"
          label="리뷰 작성 종료일"
        >
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer">
              <q-popup-proxy
                cover
                transition-show="scale"
                transition-hide="scale"
              >
                <q-date v-model="endDate">
                  <div class="row items-center justify-end">
                    <q-btn
                      v-close-popup
                      label="선택하기"
                      color="primary"
                      flat
                    />
                  </div>
                </q-date>
              </q-popup-proxy>
            </q-icon>
          </template>
        </q-input>

        <q-select
          class="q-mx-xl"
          v-model="restaurant"
          :options="restaurantOptions"
          label="식당"
        />

        <q-btn-toggle
          v-model="summaryType"
          toggle-color="primary"
          :options="[
            { label: '긍정적인 리뷰 요약', value: 'POSITIVE' },
            { label: '부정적인 리뷰 요약', value: 'NEGATIVE' },
          ]"
          class="q-ma-md"
        />

        <q-btn
          class="align-right"
          @click="
            () => {
              getDiningReviewsAverageRatings(startDate, endDate);
              getDiningReviewsNumber(startDate, endDate);
              getDiningReviewsSummaryByPeriod(startDate, endDate);
            }
          "
          >조회</q-btn
        >
      </q-card-section>

      <q-separator vertical />

      <q-card-section
        class="col-9 scroll flex flex-center"
        style="font-size: 17px"
        :loading="loading"
      >
        {{ summary }}
      </q-card-section>
    </q-card-section>
  </q-card>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { api as axios } from "src/boot/axios";

const loading = ref(true);
const summary = ref("");
const averageRating = ref(0);
const reviewsNumber = ref(0);
const startDate = ref();
const endDate = ref();
const summaryType = ref("POSITIVE");

const restaurant = ref("선택 안함");
const restaurantOptions = ref([
  "선택 안함",
  "담소정",
  "하나미 스시",
  "Château d Étoiles",
  "Bella Vista",
]);

const getDiningReviewsAverageRatings = async (startDate, endDate) => {
  try {
    const start = new Date(startDate).toISOString();
    const end = new Date(endDate).toISOString();
    let restaurantParam = restaurant.value;

    if (restaurantParam === "선택 안함") {
      restaurantParam = "All";
    }

    const response = await axios.get("/api/dining/rating/average", {
      params: {
        startDate: start.slice(0, 11) + "00:00:00",
        endDate: end.slice(0, 11) + "23:59:59",
        restaurant: restaurantParam,
      },
    });
    averageRating.value = response.data.data;
  } catch (error) {
    console.log(error);
  }
};

const getDiningReviewsNumber = async (startDate, endDate) => {
  try {
    const start = new Date(startDate).toISOString();
    const end = new Date(endDate).toISOString();

    let restaurantParam = restaurant.value;

    if (restaurantParam === "선택 안함") {
      restaurantParam = "All";
    }
    const response = await axios.get("/api/dining/review/count", {
      params: {
        startDate: start.slice(0, 11) + "00:00:00",
        endDate: end.slice(0, 11) + "23:59:59",
        restaurant: restaurantParam,
      },
    });

    reviewsNumber.value = response.data.data;
  } catch (error) {
    console.log(error);
  }
};

const getDiningReviewsSummaryByPeriod = async (startDate, endDate) => {
  try {
    const start = new Date(startDate).toISOString();
    const end = new Date(endDate).toISOString();

    let restaurantParam = restaurant.value;

    if (restaurantParam === "선택 안함") {
      restaurantParam = "All";
    }

    const response = await axios.get("/api/dining/review/summary", {
      params: {
        startDate: start.slice(0, 11) + "00:00:00",
        endDate: end.slice(0, 11) + "23:59:59",
        summaryType: summaryType.value,
        restaurant: restaurantParam,
      },
    });
    if (response.data.data === null) {
      console.log("리뷰 요약 데이터가 없습니다");
      createDiningReviewsSummaryByPeriod(startDate, endDate);
    } else {
      summary.value = response.data.data.summary;
    }
  } catch (error) {
    console.log(error);
  }
};

const createDiningReviewsSummaryByPeriod = async (startDate, endDate) => {
  try {
    const start = new Date(startDate).toISOString();
    const end = new Date(endDate).toISOString();

    let restaurantParam = restaurant.value;
    if (restaurant.value === "선택 안함") {
      restaurantParam = "All";
    }

    const response = await axios.post("/api/dining/review/summary", {
      startDate: start.slice(0, 11) + "00:00:00",
      endDate: end.slice(0, 11) + "23:59:59",
      summaryType: summaryType.value,
      restaurant: restaurantParam,
    });
    if (response.data.data === null) {
      window.alert("리뷰 데이터가 없습니다! 다른 날짜를 선택해주세요");
      summary.value = "";
      return;
    }
    summary.value = response.data.data;
  } catch (error) {
    console.log(error);
  }
};

onMounted(() => {
  // getHotelReviewsAverageRatings(, endDate.value);
  // getHotelReviewsNumber();
  // const converted = Date().toISOString().toString().slice(0, 11) + "00:00:00";
});
</script>
