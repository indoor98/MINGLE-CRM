<template>
  <q-page>
    <div class="q-pa-md">
      <q-tabs v-model="selectedTab">
        <q-tab name="approved" label="승인 완료" />
        <q-tab name="rejected" label="승인 거절" />
      </q-tabs>
      <q-separator />
      <div v-if="selectedTab === 'approved'">
        <ApprovedVouchers />
      </div>
      <div v-if="selectedTab === 'rejected'">
        <RejectedVouchers />
      </div>
    </div>
  </q-page>
</template>

<script setup>
import ApprovedVouchers from "src/components/voucher/manager/ApprovedVouchers.vue";
import RejectedVouchers from "src/components/voucher/manager/RejectedVouchers.vue";
import { ref } from "vue";

const selectedTab = ref("approved");
</script>

<style></style>
