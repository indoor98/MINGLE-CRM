<template>
  <q-page>
    <div class="q-pa-md">
      <q-tabs v-model="selectedTab" dense>
        <q-tab name="all" label="모든 리스트" />
        <q-tab name="requested" label="승인 요청된 바우처" />
        <q-tab name="histories" label="바우처 히스토리 리스트" />
      </q-tabs>
      <q-separator />
      <div v-if="selectedTab === 'all'">
        <VoucherList />
      </div>
      <div v-else-if="selectedTab === 'requested'">
        <RequestedVoucherList />
      </div>
      <div v-else-if="selectedTab === 'histories'">
        <VoucherHistoryList />
      </div>
    </div>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import VoucherList from "./VoucherList.vue";
import RequestedVoucherList from "./RequestedVoucherList.vue";
import VoucherHistoryList from "./VoucherHistoryList.vue";

const selectedTab = ref("all");
</script>

<style></style>
