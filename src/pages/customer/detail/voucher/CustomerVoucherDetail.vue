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
            <th>바우처 ID</th>
            <td>{{ voucher.voucherId }}</td>
          </tr>
          <tr>
            <th>상태</th>
            <td>{{ voucher.status }}</td>
          </tr>
          <!-- <tr>
            <th>고객 ID</th>
            <td>{{ voucher.customerId }}</td>
          </tr> -->
          <!-- <tr>
            <th>고객 이름</th>
            <td>{{ voucher.customerName }}</td>
          </tr> -->
          <!-- <tr>
            <th>고객 등급</th>
            <td>{{ voucher.customerGrade }}</td>
          </tr> -->
          <!-- <tr>
            <th>고객 이메일</th>
            <td>{{ voucher.customerEmail }}</td>
          </tr> -->
          <tr>
            <th>요청 일시</th>
            <td>{{ voucher.requestDate }}</td>
          </tr>
          <tr>
            <th>요청 이유</th>
            <td>{{ voucher.createdReason }}</td>
          </tr>
          <tr v-if="voucher.creatorId">
            <th>요청 직원 ID</th>
            <td>{{ voucher.creatorId }}</td>
          </tr>
          <tr v-if="voucher.creatorId">
            <th>요청 직원 이름</th>
            <td>{{ voucher.creatorName }}</td>
          </tr>
          <tr v-if="voucher.confirmDate">
            <th>승인/거절 일시</th>
            <td>{{ voucher.confirmDate }}</td>
          </tr>
          <tr v-if="voucher.confirmerId">
            <th>확정 매니저 ID</th>
            <td>{{ voucher.confirmerId }}</td>
          </tr>
          <tr v-if="voucher.confirmerId">
            <th>확정 매니저 이름</th>
            <td>{{ voucher.confirmerName }}</td>
          </tr>
          <tr>
            <th>바우처 금액</th>
            <td>{{ voucher.amount }}</td>
          </tr>
          <tr v-if="voucher.rejectedReason">
            <th>거절 사유</th>
            <td>{{ voucher.rejectedReason }}</td>
          </tr>
          <tr v-if="voucher.sendOrCancelDate">
            <th>이메일 전송/발급 취소 일시</th>
            <td>{{ voucher.sendOrCancelDate }}</td>
          </tr>
          <tr v-if="voucher.voucherCode">
            <th>바우처 코드</th>
            <td>{{ voucher.voucherCode }}</td>
          </tr>
          <tr v-if="voucher.conversionDate">
            <th>리워드 전환 일시</th>
            <td>{{ voucher.conversionDate }}</td>
          </tr>
          <tr v-if="voucher.startDate && voucher.endDate">
            <th>바우처 유효 기간</th>
            <td>{{ voucher.startDate }} ~ {{ voucher.endDate }}</td>
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
