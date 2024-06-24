<template>
  <q-page>
    <div class="q-pa-md">
      <q-tabs v-model="selectedTab">
        <q-tab name="not-requested" label="승인 요청전 바우처" />
        <q-tab name="requested" label="승인 요청한 바우처" />
        <q-tab name="sended" label="발송한 바우처" />
      </q-tabs>
      <q-separator />
      <div v-if="selectedTab === 'not-requested'">
        <VoucherList />
      </div>
      <div v-else-if="selectedTab === 'requested'">
        <q-tabs v-model="selectedTab2">
          <q-tab name="approved" label="승인 완료된 바우처" />
          <q-tab name="rejected" label="승인 거절된 바우처" />
          <q-tab name="requested" label="승인 검토 전 바우처" />
          <q-tab name="canceled" label="발송 취소한 바우처" />
        </q-tabs>
        <q-separator />
        <div v-if="selectedTab2 === 'approved' && !showVoucherEmailSend">
          <VoucherHistoryListMarketer
            @send-voucher="showEmailSendPage"
            selected="approved-marketer"
          />
        </div>
        <div v-else-if="selectedTab2 === 'approved' && showVoucherEmailSend">
          <VoucherEmailSend
            :voucherId="selectedVoucherId"
            :customerEmail="selectedCustomerEmail"
            :voucherCode="selectedVoucherCode"
            @go-back="hideEmailSendPage"
          />
        </div>
        <div v-else-if="selectedTab2 === 'rejected'">
          <VoucherHistoryListMarketer selected="rejected-marketer" />
        </div>
        <div v-else-if="selectedTab2 === 'requested'">
          <VoucherHistoryListMarketer selected="requested-marketer" />
        </div>
        <div v-else-if="selectedTab2 === 'canceled'">
          <VoucherHistoryListMarketer selected="canceled-marketer" />
        </div>
      </div>
      <div v-else-if="selectedTab === 'sended'">
        <VoucherHistoryListMarketer selected="sended-marketer" />
      </div>
    </div>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import VoucherList from "./VoucherList.vue";
import VoucherHistoryListMarketer from "../../components/VoucherHistoryListMarketer.vue";
import VoucherEmailSend from "./VoucherEmailSend.vue";

const selectedTab = ref("not-requested");
const selectedTab2 = ref("approved");
const showVoucherEmailSend = ref(false);
const selectedVoucherId = ref(null);
const selectedCustomerEmail = ref("");
const selectedVoucherCode = ref("");

const showEmailSendPage = (voucherId, customerEmail, voucherCode) => {
  selectedVoucherId.value = voucherId;
  selectedCustomerEmail.value = customerEmail;
  selectedVoucherCode.value = voucherCode;
  showVoucherEmailSend.value = true;
};

const hideEmailSendPage = () => {
  showVoucherEmailSend.value = false;
};
</script>

<style></style>
