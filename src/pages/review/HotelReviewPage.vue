<template>
  <q-page>
    <section class="row q-col-gutter-xl flex flex-center q-mt-md">
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

      <q-input
        bottom-slots
        v-model="customerName"
        label="고객 명"
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

    <section class="q-mt-xl">
      <div class="row q-col-gutter-xl">
        <div v-for="(review, index) in reviews" :key="index" class="col-12">
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
                    readonly
                  />
                  <div>청결도</div>
                  <q-rating
                    size="15px"
                    v-model="review.cleanlinessRating"
                    :max="5"
                    color="primary"
                    readonly
                  />
                </div>
                <div class="col">
                  <div>편의성</div>
                  <q-rating
                    size="15px"
                    v-model="review.convenienceRating"
                    :max="5"
                    color="primary"
                    readonly
                  />
                  <div>위치 만족도</div>
                  <q-rating
                    size="15px"
                    v-model="review.locationRating"
                    :max="5"
                    color="primary"
                    readonly
                  />
                </div>
              </div>
            </q-card-section>

            <q-card-section
              class="scroll"
              style="min-width: 100px; max-height: 100px"
            >
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
    getHotelReviewMetaData();
    reviews.value = response.data.data;
    console.log(reviews);
  } catch (error) {
    console.log(error);
  }
};

const getHotelReviewMetaData = async () => {
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

  const response = await axios.get(
    "http://localhost:8080/api/hotel/review/meta",
    {
      params: searchCondition.value,
    }
  );

  pagination.value.pagesNumber = response.data.data.pagesNumber;
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

  getHotelReviewMetaData();
});
</script>

<style lang="scss" scoped></style>
