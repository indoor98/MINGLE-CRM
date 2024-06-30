<template>
  <q-page>
    <div class="q-pa-md">
      <q-tabs v-model="selectedTab">
        <q-tab name="approved" label="승인됨" />
        <q-tab name="rejected" label="거절됨" />
        <q-tab name="not-confirmed" label="검토 전" />
      </q-tabs>
      <q-separator />
      <div v-if="selectedTab === 'approved' && !showVoucherEmailSend">
        <ApprovedVouchers @send-voucher="showEmailSendPage" />
      </div>
      <div v-if="selectedTab === 'approved' && showVoucherEmailSend">
        <VoucherEmailSend
          :voucherId="selectedVoucherId"
          :customerEmail="selectedCustomerEmail"
          :voucherCode="selectedVoucherCode"
          @go-back="hideEmailSendPage"
        />
      </div>

      <div v-else-if="selectedTab === 'rejected'">
        <RejectedVouchers />
      </div>
      <div v-else-if="selectedTab === 'not-confirmed'">
        <NotConfirmedVouchers />
      </div>
    </div>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import ApprovedVouchers from "components/voucher/marketer/ApprovedVouchers.vue";
import RejectedVouchers from "components/voucher/marketer/RejectedVouchers.vue";
import NotConfirmedVouchers from "components/voucher/marketer/NotConfirmedVouchers.vue";
import VoucherEmailSend from "../../../components/voucher/marketer/SendVoucherEmail.vue";

const selectedTab = ref("approved");

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
