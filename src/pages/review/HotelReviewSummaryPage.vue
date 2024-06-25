<template>
  <q-card class="my-card q-mt-xl" flat bordered>
    <q-item>
      <q-item-section class="row flex flex-center">
        <div class="q-pa-md" style="font-size: 25px">
          기간 내 사용자 리뷰 총 평점
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
        <div class="q-pb-md" style="font-size: 25px">기간 내 리뷰 수</div>
        <q-icon class="q-pa-md" name="people" size="60px"></q-icon>
        <div style="font-size: 25px">
          {{ reviewsNumber }}
        </div>
      </q-item-section>
    </q-item>

    <q-separator />

    <q-card-section horizontal>
      <q-card-section class="row flex flex-center">
        <q-input
          v-model="startDate"
          mask="date"
          :rules="['date']"
          label="시작일"
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
        <q-input v-model="endDate" mask="date" :rules="['date']" label="종료일">
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
          v-model="hotel"
          :options="hotelOptions"
          label="호텔 지점"
        />

        <q-btn-toggle
          v-model="summaryType"
          toggle-color="primary"
          :options="[
            { label: '긍정', value: 'POSITIVE' },
            { label: '부정', value: 'NEGATIVE' },
          ]"
          class="q-ma-md"
        />

        <q-btn
          class="align-right"
          @click="
            () => {
              getHotelReviewsAverageRatings(startDate, endDate);
              getHotelReviewsNumber(startDate, endDate);
              getHotelReviewsSummaryByPeriod(startDate, endDate);
            }
          "
          >조회</q-btn
        >
      </q-card-section>

      <q-separator vertical />

      <q-card-section
        class="col-9 scroll flex flex-center"
        style="font-size: 17px"
      >
        {{ summary }}
      </q-card-section>
    </q-card-section>
  </q-card>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { api as axios } from "src/boot/axios";

const summary = ref("");
const averageRating = ref(0);
const reviewsNumber = ref(0);
const startDate = ref();
const endDate = ref();
const summaryType = ref("POSITIVE");
const hotel = ref("선택 안함");
const hotelOptions = ref(["선택 안함", "SEOUL", "BUSAN", "SOKCHO"]);

const getHotelReviewsAverageRatings = async (startDate, endDate) => {
  try {
    const start = new Date(startDate).toISOString();
    const end = new Date(endDate).toISOString();
    const response = await axios.get("/api/hotel/rating/average", {
      params: {
        startDate: start.slice(0, 11) + "00:00:00",
        endDate: end.slice(0, 11) + "23:59:59",
      },
    });
    console.log(response.data);
    averageRating.value = response.data;
  } catch (error) {
    console.log(error);
  }
};

const getHotelReviewsNumber = async (startDate, endDate) => {
  try {
    const start = new Date(startDate).toISOString();
    const end = new Date(endDate).toISOString();
    const response = await axios.get("/api/hotel/review/count", {
      params: {
        startDate: start.slice(0, 11) + "00:00:00",
        endDate: end.slice(0, 11) + "23:59:59",
      },
    });
    console.log(response.data);
    reviewsNumber.value = response.data;
  } catch (error) {
    console.log(error);
  }
};

const getHotelReviewsSummaryByPeriod = async (startDate, endDate) => {
  try {
    const start = new Date(startDate).toISOString();
    const end = new Date(endDate).toISOString();
    let hotelParam = hotel.value;
    if (hotelParam === "선택 안함") {
      hotelParam = "All";
    }

    const response = await axios.get("/api/hotel/review/summary", {
      params: {
        startDate: start.slice(0, 11) + "00:00:00",
        endDate: end.slice(0, 11) + "23:59:59",
        summaryType: summaryType.value,
        hotel: hotelParam,
      },
    });
    if (response.data.data === null) {
      console.log("리뷰 요약 데이터가 없습니다");
      createHotelReviewsSummaryByPeriod(startDate, endDate);
    } else {
      summary.value = response.data.data.summary;
    }
  } catch (error) {
    console.log(error);
  }
};

const createHotelReviewsSummaryByPeriod = async (startDate, endDate) => {
  try {
    const start = new Date(startDate).toISOString();
    const end = new Date(endDate).toISOString();
    let hotelParam = hotel.value;
    if (hotelParam === "선택 안함") {
      hotelParam = "All";
    }

    const response = await axios.post("/api/hotel/review/summary", {
      startDate: start.slice(0, 11) + "00:00:00",
      endDate: end.slice(0, 11) + "23:59:59",
      summaryType: summaryType.value,
      hotel: hotelParam,
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
