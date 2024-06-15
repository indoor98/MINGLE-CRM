<template>
  <q-page>
    <div class="q-pa-md">
      <q-tabs v-model="selectedTab" dense>
        <q-tab name="not-requested" label="승인 요청전 바우처" />
        <q-tab name="requested" label="승인 요청한 바우처" />
      </q-tabs>
      <q-separator />
      <div v-if="selectedTab === 'not-requested'">
        <VoucherList />
      </div>
      <div v-else-if="selectedTab === 'requested'">
        <RequestedVoucherHistoryList auth="marketer" />
      </div>
    </div>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import RequestedVoucherHistoryList from "./RequestedVoucherHistoryList.vue";
import VoucherList from "./VoucherList.vue";
import VoucherHistoryList from "../../components/VoucherHistoryList.vue";

const selectedTab = ref("not-requested");
</script>

<style></style>
