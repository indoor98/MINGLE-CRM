<template>
  <q-page>
    <div class="q-pa-md">
      <q-tabs v-model="selectedTab" dense>
        <q-tab name="requested" label="승인 요청된 바우처" />
        <q-tab name="approved-test" label="승인 완료된 바우처" />
        <q-tab name="rejected-test" label="승인 거절된 바우처" />
        <q-tab name="all-test" label="모든 바우처 히스토리" />
      </q-tabs>
      <q-separator />
      <div v-if="selectedTab === 'requested'">
        <RequestedVoucherHistoryList />
      </div>
      <div v-else-if="selectedTab === 'approved-test'">
        <VoucherHistoryList selected="approved" />
      </div>
      <div v-else-if="selectedTab === 'rejected-test'">
        <VoucherHistoryList selected="rejected" />
      </div>
      <div v-else-if="selectedTab === 'all-test'">
        <VoucherHistoryList selected="histories" />
      </div>
    </div>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import RequestedVoucherHistoryList from "./RequestedVoucherHistoryList.vue";
import VoucherHistoryList from "../../components/VoucherHistoryList.vue";

const selectedTab = ref("requested");
</script>

<style></style>
