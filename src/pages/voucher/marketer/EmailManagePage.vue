<template>
  <q-page>
    <div class="q-pa-md">
      <q-tabs v-model="selectedTab">
        <q-tab name="sended" label="발송 완료" />
        <q-tab name="canceled" label="발송 취소" />
      </q-tabs>
      <q-separator />
      <div v-if="selectedTab === 'sended'">
        <sended-vouchers />
      </div>
      <div v-if="selectedTab === 'canceled'">
        <canceled-vouchers />
      </div>
    </div>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import CanceledVouchers from "src/components/voucher/marketer/CanceledVouchers.vue";
import SendedVouchers from "src/components/voucher/marketer/SendedVouchers.vue";

const selectedTab = ref("sended");
</script>

<style></style>
