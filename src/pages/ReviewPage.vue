<template>
  <q-page padding>
    <section class="q-pa-md">
      <div class="row q-pa-xs q-gutter-md">
        <q-btn flat style="color: black; font-size: 24px" label="호텔 리뷰" />
        <q-btn flat style="color: black; font-size: 24px" label="호텔 리뷰" />
      </div>
    </section>

    <section class="row q-gutter-md q-pa-md flex flex-center">
      <!-- First Scroll Area -->
      <q-input
        filled
        bottom-slots
        v-model="text"
        label="Label"
        counter
        maxlength="12"
        :dense="dense"
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
        <template v-slot:hint> Field hint </template>
      </q-input>

      <div class="col-4 q-pa-md scroll" style="max-height: 200px">
        <div v-for="n in 100" :key="`second-${n}`" class="q-py-xs">
          Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua.
        </div>
      </div>
      <div class="col-4 q-pa-md scroll" style="max-height: 200px">
        <div v-for="n in 100" :key="`second-${n}`" class="q-py-xs">
          Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua.
        </div>
      </div>
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
                <div calss="col">
                  <div class="q-mb-sm">{{ review.roomType }}</div>
                  <div class="q-mb-sm">{{ review.customerName }}</div>
                  <div>{{ review.createdTime.substring(0, 10) }}</div>
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
              {{ review.createdTime }}
            </q-card-section>
          </q-card>
        </div>
      </div>
    </section>
    <section class="flex flex-center q-mt-xl">
      <q-pagination
        v-model="current"
        max="5"
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
import axios from "axios"; // axios 모듈을 기본 내보내기로 임포트

const current = ref(1);
const reviews = ref([]);

const getHotelReviews = async () => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/hotel/reviews/${current.value - 1}`
    );
    reviews.value = response.data.data;
  } catch (error) {
    console.log(error);
  }
};

// 페이지네이션 값이 변경될 때마다 getHotelReviews 함수 호출
watch(current, () => {
  getHotelReviews();
});

// 컴포넌트가 마운트될 때 getHotelReviews 함수 호출
onMounted(() => {
  getHotelReviews();
});
</script>

<style lang="scss" scoped></style>
