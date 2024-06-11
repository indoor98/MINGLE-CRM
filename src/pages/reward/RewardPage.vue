<template>
  <q-page>
    <div class="q-pa-md">
      <q-tabs v-model="selectedTab" dense>
        <q-tab name="all" label="모든 리워드 리스트" />
        <q-tab name="histories" label="리워드 히스토리 리스트" />
      </q-tabs>
      <q-separator />
      <div v-if="selectedTab === 'all'">
        <RewardList />
      </div>
      <div v-else-if="selectedTab === 'histories'">
        <RewardHistoryList />
      </div>
    </div>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import RewardList from "./RewardList.vue";
import RewardHistoryList from "./RewardHistoryList.vue";

const selectedTab = ref("all");
</script>

<style></style>
