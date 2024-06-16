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
          <q-item-label>Request Date</q-item-label>
          <q-item-section>{{ voucherDetails.requestDate }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Authorized</q-item-label>
          <q-item-section>{{ voucherDetails.isAuth }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Authorization Date</q-item-label>
          <q-item-section>{{ voucherDetails.authDate }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Converted</q-item-label>
          <q-item-section>{{ voucherDetails.isConvertedYn }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Conversion Date</q-item-label>
          <q-item-section>{{ voucherDetails.conversionDate }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Issuer ID</q-item-label>
          <q-item-section>{{ voucherDetails.issuerId }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Approver ID</q-item-label>
          <q-item-section>{{ voucherDetails.approverId }}</q-item-section>
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
import { ref, watch, onMounted } from 'vue';
import axios from 'axios';

const props = defineProps(['voucher']);
const voucherDetails = ref({});

const fetchVoucherDetail = async (customerId, voucherId) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/customers/${customerId}/voucher/${voucherId}`);
    voucherDetails.value = response.data.data;
  } catch (error) {
    console.error('Error fetching voucher detail:', error);
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
