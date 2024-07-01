<template>
  <q-card class="custom-card">
    <q-card-section>
      <div class="text-h6">바우처 상세 정보</div>
    </q-card-section>

    <q-separator />

    <q-card-section>
      <table class="voucher-table">
        <tbody>
          <tr>
            <th>Voucher ID</th>
            <td>{{ voucherDetails.voucherId }}</td>
          </tr>
          <tr>
            <th>Status</th>
            <td>{{ voucherDetails.status }}</td>
          </tr>
          <tr>
            <th>요청 일자</th>
            <td>{{ voucherDetails.requestDate }}</td>
          </tr>
          <tr>
            <th>매니저 승인/거절 일자</th>
            <td>{{ voucherDetails.confirmDate }}</td>
          </tr>
          <tr>
            <th>전송/취소 일자</th>
            <td>{{ voucherDetails.sendOrCancelDate }}</td>
          </tr>
          <tr>
            <th>리워드 전환 일자</th>
            <td>{{ voucherDetails.convertsionDate }}</td>
          </tr>
          <tr>
            <th>생성 마케터 ID</th>
            <td>{{ voucherDetails.creatorId }}</td>
          </tr>
          <tr>
            <th>승인 매니저 ID</th>
            <td>{{ voucherDetails.confirmerId }}</td>
          </tr>
          <tr>
            <th>Customer ID</th>
            <td>{{ voucherDetails.customerId }}</td>
          </tr>
          <tr>
            <th>Amount</th>
            <td>{{ voucherDetails.amount }}</td>
          </tr>
        </tbody>
      </table>
    </q-card-section>

    <q-card-actions align="right">
      <q-btn flat label="Close" color="primary" @click="$emit('close')" />
    </q-card-actions>
  </q-card>
</template>

<script setup>
import { ref, watch } from "vue";
import { api as axios } from "src/boot/axios";
import { defineProps } from "vue";

const props = defineProps(["voucher"]);
const voucherDetails = ref({});

const fetchVoucherDetail = async (customerId, voucherId) => {
  try {
    const response = await axios.get(
      `https://httpstest.mingle-crm.com/api/v1/vouchers/histories/${voucherId}`
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
  width: 100%;
  max-width: 800px;
  margin: auto;
}

.text-h6 {
  font-size: 1.25rem;
  font-weight: bold;
}

.voucher-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 16px;
}

.voucher-table th,
.voucher-table td {
  padding: 12px;
  border: 1px solid #e0e0e0;
  text-align: left;
}

.voucher-table th {
  background-color: #f5f5f5;
  font-weight: bold;
}
</style>
