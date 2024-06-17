<template>
  <q-page>
    <div class="q-pa-md">
      <q-tabs v-model="selectedTab">
        <q-tab name="requested" label="승인 요청된 바우처" />
        <q-tab name="confirmed" label="검토 완료된 바우처" />
        <q-tab name="all-test" label="모든 바우처 히스토리" />
      </q-tabs>
      <q-separator />
      <div v-if="selectedTab === 'requested'">
        <RequestedVoucherHistoryList />
      </div>
      <div v-else-if="selectedTab === 'approved'">
        <VoucherHistoryList selected="approved" />
      </div>
      <div v-else-if="selectedTab === 'rejected'">
        <VoucherHistoryList selected="rejected" />
      </div>
      <div v-else-if="selectedTab === 'confirmed'">
        <q-tabs v-model="selectedTab2">
          <q-tab name="approved" label="승인 완료된 바우처" />
          <q-tab name="rejected" label="승인 거절된 바우처" />
        </q-tabs>
        <q-separator />
        <div v-if="selectedTab2 === 'approved'">
          <VoucherHistoryList selected="approved" />
        </div>
        <div v-else-if="selectedTab2 === 'rejected'">
          <VoucherHistoryList selected="rejected" />
        </div>
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
const selectedTab2 = ref("approved");
</script>

<style></style>
