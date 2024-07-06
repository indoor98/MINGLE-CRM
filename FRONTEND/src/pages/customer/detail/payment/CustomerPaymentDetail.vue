<template>
  <q-card class="custom-card">
    <q-card-section>
      <div class="text-h6">결제 상세 정보</div>
    </q-card-section>

    <q-separator />

    <q-card-section v-if="paymentDetail">
      <table class="payment-table">
        <tbody>
        <tr>
          <th>고객명</th>
          <td>{{ paymentDetail.customerName }}</td>
        </tr>
        <tr>
          <th>전화번호</th>
          <td>{{ paymentDetail.number }}</td>
        </tr>
        <tr>
          <th>결제 종류</th>
          <td>{{ paymentDetail.type }}</td>
        </tr>
        <tr>
          <th>할인 전 금액</th>
          <td>{{ paymentDetail.amountBeforeDiscount }}</td>
        </tr>
        <tr>
          <th>할인 금액</th>
          <td>{{ paymentDetail.discountAmount }}</td>
        </tr>
        <tr>
          <th>결제 금액</th>
          <td>{{ paymentDetail.paymentAmount }}</td>
        </tr>
        <tr>
          <th>결제 날짜</th>
          <td>{{ paymentDetail.paymentDate }}</td>
        </tr>
        <tr>
          <th>환불 여부</th>
          <td>{{ paymentDetail.isRefunded }}</td>
        </tr>
        <tr>
          <th>환불 날짜</th>
          <td>{{ paymentDetail.refundDate }}</td>
        </tr>
        <tr>
          <th>리워드 생성 금액</th>
          <td>{{ paymentDetail.createdReward }}</td>
        </tr>
        <tr>
          <th>결제 지점</th>
          <td>{{ paymentDetail.paymentSpot }}</td>
        </tr>
        </tbody>
      </table>
    </q-card-section>

    <q-card-actions align="right">
      <q-btn flat label="닫기" color="primary" @click="emit('close')" />
    </q-card-actions>
  </q-card>
</template>

<script setup>
import { ref, onMounted, defineProps, defineEmits } from 'vue';
import { api as axios } from "src/boot/axios";
import { useRoute } from 'vue-router';

const props = defineProps(['payment']);
const emit = defineEmits(['close']);
const paymentDetail = ref(null);
const route = useRoute();

onMounted(async () => {
  const customerId = route.params.id;
  if (props.payment && props.payment.paymentId) {
    await fetchPayment(customerId, props.payment.paymentId);
  }
});

const fetchPayment = async (customerId, paymentId) => {
  try {
    const response = await axios.get(`/api/v1/customers/${customerId}/payments/${paymentId}`);
    paymentDetail.value = response.data;
  } catch (error) {
    console.error('결제 정보를 가져오는 중 에러 발생:', error);
  }
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
</style>
