<template>
  <q-card class="custom-card">
    <q-separator />

    <q-card-section>
      <q-list>
        <q-item>
          <q-item-label>Voucher ID</q-item-label>
          <q-item-section>{{ voucherDetails.voucherId }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Status</q-item-label>
          <q-item-section>{{ voucherDetails.status }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>요청 일자</q-item-label>
          <q-item-section>{{ voucherDetails.requestDate }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>매니저 승인/거절 일자</q-item-label>
          <q-item-section>{{ voucherDetails.confirmDate }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>전송/취소 일자</q-item-label>
          <q-item-section>{{ voucherDetails.sendOrCancelDate }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>리워드 전환 일자</q-item-label>
          <q-item-section>{{ voucherDetails.convertsionDate }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>생성 마케터 ID</q-item-label>
          <q-item-section>{{ voucherDetails.creatorId }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>승인 매니저 ID</q-item-label>
          <q-item-section>{{ voucherDetails.confirmerId }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Customer ID</q-item-label>
          <q-item-section>{{ voucherDetails.customerId }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Amount</q-item-label>
          <q-item-section>{{ voucherDetails.amount }}</q-item-section>
        </q-item>
      </q-list>
    </q-card-section>

    <q-card-actions align="right">
      <q-btn flat label="Close" color="primary" @click="$emit('close')" />
    </q-card-actions>
  </q-card>
</template>

<script setup>
import { ref, watch, onMounted } from "vue";
import axios from "axios";

const props = defineProps(["voucher"]);
const voucherDetails = ref({});

const fetchVoucherDetail = async (customerId, voucherId) => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/v1/vouchers/histories/${voucherId}`
    );
    voucherDetails.value = response.data.data;
  } catch (error) {
    console.error("Error fetching voucher detail:", error);
  }
};

watch(
  () => props.voucher,
  (newVoucher) => {
    if (newVoucher.customerId && newVoucher.voucherId) {
      fetchVoucherDetail(newVoucher.customerId, newVoucher.voucherId);
    }
  },
  { immediate: true }
);
</script>

<style scoped>
.custom-card {
  max-width: 600px;
  margin: auto;
}

.text-h6 {
  font-size: 1.25rem;
  font-weight: bold;
}

.q-item {
  padding: 10px 0;
}

.q-item-label {
  font-weight: bold;
}
</style>
