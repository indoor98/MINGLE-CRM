<template>
  <q-page>
    <div class="q-pa-md">
      <q-tabs
        v-model="selectedTab"
        narrow-indicator
        densealign="justify"
        class="text-primary"
      >
        <q-tab name="hotel" label="호텔 리뷰" />
        <q-tab name="dining" label="다이닝 리뷰" />
      </q-tabs>

      <div v-if="selectedTab === 'hotel'">
        <HotelReviewPage />
      </div>
      <div v-else-if="selectedTab === 'dining'">
        <DiningReviewPage />
      </div>
    </div>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import DiningReviewPage from "./DiningReviewPage.vue";
import HotelReviewPage from "./HotelReviewPage.vue";

const selectedTab = ref("hotel");
</script>
