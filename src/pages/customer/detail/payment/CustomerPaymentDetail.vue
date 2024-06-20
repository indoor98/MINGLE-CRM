<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <q-card class="q-mt-md" v-if="loadedPayment">
      <q-card-section class="q-pa-md">
        <div class="text-h6">Payment Details</div>
      </q-card-section>

      <q-separator />

      <q-card-section>
        <table class="payment-table">
          <tbody>
          <tr>
            <th>고객명</th>
            <td>{{ loadedPayment.customerName }}</td>
          </tr>
          <tr>
            <th>전화번호</th>
            <td>{{ loadedPayment.number }}</td>
          </tr>
          <tr>
            <th>결제 종류</th>
            <td>{{ loadedPayment.type }}</td>
          </tr>
          <tr>
            <th>할인 전 금액</th>
            <td>{{ loadedPayment.amountBeforeDiscount }}</td>
          </tr>
          <tr>
            <th>할인 금액</th>
            <td>{{ loadedPayment.discountAmount }}</td>
          </tr>
          <tr>
            <th>결제 금액</th>
            <td>{{ loadedPayment.paymentAmount }}</td>
          </tr>
          <tr>
            <th>결제 날짜</th>
            <td>{{ loadedPayment.paymentDate }}</td>
          </tr>
          <tr>
            <th>환불 여부</th>
            <td>{{ loadedPayment.isRefunded }}</td>
          </tr>
          <tr>
            <th>환불 날짜</th>
            <td>{{ loadedPayment.refundDate }}</td>
          </tr>
          <tr>
            <th>리워드 생성 금액</th>
            <td>{{ loadedPayment.createdReward }}</td>
          </tr>
          <tr>
            <th>결제 지점</th>
            <td>{{ loadedPayment.paymentSpot }}</td>
          </tr>
          </tbody>
        </table>
      </q-card-section>
    </q-card>

    <div v-else class="q-mt-md text-h6 text-center">Loading...</div>
  </div>
</template>

<script setup>
import { ref, onMounted, defineProps } from 'vue';
import { api as axios } from "src/boot/axios";
import { useRoute } from 'vue-router';

const props = defineProps(['payment']);
const loadedPayment = ref(null);
const route = useRoute(); // Vue Router 사용

onMounted(async () => {
  const customerId = route.params.id;
  if (props.payment && props.payment.paymentId) {
    await fetchPayment(customerId, props.payment.paymentId);
  }
});

const fetchPayment = async (customerId, paymentId) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/customers/${customerId}/payments/${paymentId}`);
    loadedPayment.value = response.data;
  } catch (error) {
    console.error('결제 정보를 가져오는 중 에러 발생:', error);
  }
};
</script>

<style scoped>
.payment-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 16px;
}

.payment-table th,
.payment-table td {
  padding: 12px;
  border: 1px solid #e0e0e0;
  text-align: left;
}

.payment-table th {
  background-color: #f5f5f5;
  font-weight: bold;
}

.q-card-section {
  padding: 16px;
}
</style>
