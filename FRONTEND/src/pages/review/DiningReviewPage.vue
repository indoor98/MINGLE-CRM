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
        v-model="restaurant"
        :options="restaurantOptions"
        label="식당"
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
                getDiningReviews();
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
                <div class="q-mb-sm">{{ review.restaurant }}</div>
                <q-space></q-space>
                <div>{{ review.createdTime.substring(0, 10) }}</div>
              </div>
              <div class="row">
                <div calss="col">
                  <div class="q-mb-sm">{{ review.customerName }}</div>
                </div>
                <div class="col q-px-lg">
                  <div>맛</div>
                  <q-rating
                    size="15px"
                    v-model="review.tasteRating"
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
                  <div>친절도</div>
                  <q-rating
                    size="15px"
                    v-model="review.kindnessRating"
                    :max="5"
                    color="primary"
                    readonly
                  />
                  <div>분위기</div>
                  <q-rating
                    size="15px"
                    v-model="review.atmosphereRating"
                    :max="5"
                    color="primary"
                    readonly
                  />
                </div>
              </div>
            </q-card-section>

            <q-card-section
              class="scroll"
              style="min-height: 100px; max-height: 100px"
            >
              {{ review.review }}
            </q-card-section>
          </q-card>
        </div>
      </div>
    </section>
    <section class="flex flex-center q-mt-xl">
      <q-pagination
        v-model="pagination.page"
        :max="pagination.pagesNumber"
        :max-pages="1"
        :boundary-numbers="true"
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
import { api as axios } from "/src/boot/axios"; // axios 모듈을 기본 내보내기로 임포트

const reviews = ref([]);
const restaurant = ref("선택 안함");
const restaurantOptions = ref(["선택 안함", "담소정", "도림"]);
const startDate = ref("");
const endDate = ref("");

const pagination = ref({
  sortBy: "desc",
  descending: false,
  page: 1,
  rowsPerPage: 9,
  pagesNumber: 0,
});

const customerName = ref("");

const dateToLocalDateTime = (date) => {
  return (
    date.substring(0, 4) +
    "-" +
    date.substring(5, 7) +
    "-" +
    date.substring(8, 10)
  );
};

const getDiningReviews = async () => {
  try {
    const searchCondition = ref({});

    if (restaurant.value !== "선택 안함") {
      searchCondition.value.restaurant = restaurant.value;
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

    const response = await axios.get(
      `/api/dining/review/${pagination.value.page - 1}`,
      {params: searchCondition.value}
    );

    reviews.value = response.data.data.reviews;
    pagination.value.pagesNumber = response.data.data.metaData.pagesNumber;
  } catch (error) {
    console.log(error);
  }
};


// 페이지네이션 값이 변경될 때마다 getHotelReviews 함수 호출
watch(
  () => pagination.value.page,
  () => {
    getDiningReviews();
  }
);

// 컴포넌트가 마운트될 때 getHotelReviews 함수 호출
onMounted(() => {
  getDiningReviews();
});
</script>

<style lang="scss" scoped></style>
