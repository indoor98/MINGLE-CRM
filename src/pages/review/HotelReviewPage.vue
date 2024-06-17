<template>
  <q-page padding>
    <section class="row q-col-gutter-xl flex flex-center q-pa-xs">
      <q-input v-model="startDate" mask="date" :rules="['date']" label="시작일">
        <template v-slot:append>
          <q-icon name="event" class="cursor-pointer">
            <q-popup-proxy
              cover
              transition-show="scale"
              transition-hide="scale"
            >
              <q-date v-model="startDate">
                <div class="row items-center justify-end">
                  <q-btn v-close-popup label="Close" color="primary" flat />
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
                  <q-btn v-close-popup label="Close" color="primary" flat />
                </div>
              </q-date>
            </q-popup-proxy>
          </q-icon>
        </template>
      </q-input>
      <q-select
        class="q-mb-md"
        v-model="hotel"
        :options="hotelOptions"
        label="호텔 지점"
      />
      <q-select
        class="q-mb-md"
        v-model="roomType"
        :options="roomTypeOptions"
        label="룸 타입"
      />
      <!-- <q-input
        bottom-slots
        v-model="customerName"
        label="작성자"
        maxlength="20"
      >
        <template v-slot:append>
          <q-icon
            v-if="text !== ''"
            name="close"
            @click="text = ''"
            class="cursor-pointer"
          />
          <q-icon name="search" />
        </template>
      </q-input> -->

      <q-input
        bottom-slots
        v-model="customerName"
        label="Label"
        counter
        maxlength="12"
        :dense="dense"
      >
        <template v-slot:after>
          <q-btn
            round
            dense
            flat
            icon="search"
            @click="
              () => {
                getHotelReviews();
              }
            "
          />
        </template>
      </q-input>
    </section>

    <section class="row q-gutter-md q-pa-md flex flex-center">
      <!-- First Scroll Area -->

      <q-card class="my-card">
        <q-card-section class="bg-accent">
          <div class="text-h6">최근 리뷰 요약</div>
        </q-card-section>

        <div v-if="seletedSummaryTap === 'positive'">
          <q-card-section>
            {{ positiveReviewSummary }}
          </q-card-section>
        </div>
        <div v-if="seletedSummaryTap === 'negative'">
          <q-card-section>
            {{ negativeReviewSummary }}
          </q-card-section>
        </div>
        <q-separator dark />

        <q-card-actions>
          <q-btn
            flat
            @click="
              () => {
                seletedSummaryTap = 'positive';
              }
            "
          >
            긍정적인 리뷰 요약</q-btn
          >
          <q-btn
            flat
            @click="
              () => {
                console.log(seletedSummaryTap);
                seletedSummaryTap = 'negative';
              }
            "
            >부정적인 리뷰 요약</q-btn
          >
        </q-card-actions>
      </q-card>
    </section>

    <section class="q-mt-xl">
      <div class="row q-col-gutter-xl">
        <div
          v-for="(review, index) in reviews"
          :key="index"
          class="col-12 col-sm-6 col-md-4 col-lg-4"
        >
          <q-card class="my-card">
            <q-card-section>
              <div class="row">
                <div class="q-mb-sm">{{ review.hotel }}</div>
                <q-space></q-space>
                <div class="q-mb-sm">{{ review.roomType }}</div>
                <q-space></q-space>
                <div>{{ review.createdTime.substring(0, 10) }}</div>
              </div>
              <div class="row">
                <div calss="col">
                  <div class="q-mb-sm">{{ review.customerName }}</div>
                </div>
                <div class="col q-px-lg">
                  <div>친절도</div>
                  <q-rating
                    size="15px"
                    v-model="review.kindnessRating"
                    :max="5"
                    color="primary"
                  />
                  <div>청결도</div>
                  <q-rating
                    size="15px"
                    v-model="review.cleanlinessRating"
                    :max="5"
                    color="primary"
                  />
                </div>
                <div class="col">
                  <div>편의성</div>
                  <q-rating
                    size="15px"
                    v-model="review.convenienceRating"
                    :max="5"
                    color="primary"
                  />
                  <div>위치 만족도</div>
                  <q-rating
                    size="15px"
                    v-model="review.locationRating"
                    :max="5"
                    color="primary"
                  />
                </div>
              </div>
            </q-card-section>

            <q-card-section class="scroll" style="max-height: 300px">
              {{ review.comment }}
            </q-card-section>
          </q-card>
        </div>
      </div>
    </section>
    <section class="flex flex-center q-mt-xl">
      <q-pagination
        v-model="pagination.page"
        :max="pagination.pagesNumber"
        :max-pages="6"
        :boundary-numbers="false"
        direction-links
        flat
        color="grey"
        active-color="primary"
      />
    </section>
  </q-page>
</template>
<script setup>
import { ref, watch, onMounted } from "vue";
import { api as axios } from "src/boot/axios";
// import axios from "axios"; // axios 모듈을 기본 내보내기로 임포트

const current = ref(1);
const reviews = ref([]);
const hotel = ref("선택 안함");
const hotelOptions = ref(["선택 안함", "SEOUL", "SOKCHO", "BUSAN"]);
const roomType = ref("선택 안함");
const roomTypeOptions = ref([
  "선택 안함",
  "SUPERIOR",
  "DELUXE_DOUBLE",
  "DELUXE_TWIN",
  "PREMIER_SUITE",
  "EXECUTIVE_SUITE",
  "RESIDENTIAL_SUITE",
  "PLAZA_SUITE",
  "PRESIDENTIAL_SUITE",
  "ROYAL_SUITE",
]);
const startDate = ref("");
const endDate = ref("");

const positiveReviewSummary = ref("");
const negativeReviewSummary = ref("");
const seletedSummaryTap = ref("positive");

const customerName = ref("");

const pagination = ref({
  sortBy: "desc",
  descending: false,
  page: 1,
  rowsPerPage: 9,
  pagesNumber: 0,
});

const dateToLocalDateTime = (beforeDate) => {
  return (
    beforeDate.substring(0, 4) +
    "-" +
    beforeDate.substring(5, 7) +
    "-" +
    beforeDate.substring(8, 10)
  );
};

const getHotelReviews = async () => {
  try {
    const searchCondition = ref({});

    if (hotel.value !== "선택 안함") {
      searchCondition.value.hotel = hotel.value;
    }
    if (roomType.value !== "선택 안함") {
      searchCondition.value.roomType = roomType.value;
    }
    if (startDate.value !== "" && endDate.value !== "") {
      // 2021-11-08T11:44:30.327959
      searchCondition.value.startDate =
        dateToLocalDateTime(startDate.value) + "T00:00:00";
      searchCondition.value.endDate =
        dateToLocalDateTime(endDate.value) + "T23:59:59";
    }
    if (customerName.value !== "") {
      searchCondition.value.customerName = customerName;
    }

    console.log(searchCondition.value);

    const response = await axios.post(
      `http://localhost:8080/api/hotel/reviews/${pagination.value.page - 1}`,
      searchCondition.value,
      { withCredentials: true }
    );
    reviews.value = response.data.data;
    console.log(reviews);
  } catch (error) {
    console.log(error);
  }
};

const getHotelPositiveReviewSummary = async () => {
  const response = await axios.get(
    "http://localhost:8080/api/hotel/review/summary",
    {
      params: {
        summaryType: "POSITIVE",
      },
    }
  );
  positiveReviewSummary.value = response.data.data.summary;
};

const getHotelNegativeReviewSummary = async () => {
  const response = await axios.get(
    "http://localhost:8080/api/hotel/review/summary",
    {
      params: {
        summaryType: "NEGATIVE",
      },
    }
  );
  negativeReviewSummary.value = response.data.data.summary;
};

const getPagesNumber = async () => {
  const response = await axios.get(
    "http://localhost:8080/api/hotel/review/pagesnumber"
  );

  pagination.value.pagesNumber = Math.ceil(
    response.data.data / pagination.value.rowsPerPage
  );
  console.log("page : ", pagination.value.pagesNumber);
};

// 페이지네이션 값이 변경될 때마다 getHotelReviews 함수 호출
watch(
  () => pagination.value.page,
  () => {
    getHotelReviews();
  }
);

// 컴포넌트가 마운트될 때 getHotelReviews 함수 호출
onMounted(() => {
  getHotelReviews();
  getHotelPositiveReviewSummary();
  getHotelNegativeReviewSummary();
  getPagesNumber();
});
</script>

<style lang="scss" scoped></style>
