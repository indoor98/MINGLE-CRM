<template>
  <q-card class="custom-card">
    <q-card-section class="q-pa-md">
      <div class="text-h6">바우처 상세</div>
    </q-card-section>
    <q-separator />
    <q-card-section>
      <table class="voucher-table">
        <tbody>
          <!-- 
            "voucherId": 12,
            "customerId": 11,
            "customerName": "gbricea",
            "employeeId": 23,
            "employeeName": "찜케터",
            "amount": 30,
            "createdDate": "2024-06-25T00:09:24.721307",
            "startDate": "2024-06-04T00:00:00",
            "endDate": "2024-06-26T23:59:59",
            "createdReason": "ㅇㅇㅇ"
            -->
          <tr>
            <th>바우처 ID</th>
            <td>{{ voucher.voucherId }}</td>
          </tr>
          <tr>
            <th>고객 ID</th>
            <td>{{ voucher.customerId }}</td>
          </tr>
          <tr>
            <th>고객 이름</th>
            <td>{{ voucher.customerName }}</td>
          </tr>
          <tr>
            <th>고객 이름</th>
            <td>{{ voucher.customerName }}</td>
          </tr>
          <tr>
            <th>바우처 금액</th>
            <td>{{ voucher.amount }}</td>
          </tr>
          <tr>
            <th>바우처 사용 유효 기간</th>
            <td>{{ voucher.startDate }} ~ {{ voucher.endDate }}</td>
          </tr>
        </tbody>
      </table>
    </q-card-section>

    <q-card-actions align="right">
      <q-btn flat label="닫기" color="primary" @click="closeModal" />
    </q-card-actions>
  </q-card>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

const props = defineProps(["voucher"]);
const emit = defineEmits(["close"]);

const closeModal = () => {
  emit("close");
};
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
