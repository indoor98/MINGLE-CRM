<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <q-card class="q-mt-md" v-if="loadedPayment">
      <q-card-section class="q-pa-md">
        <div class="text-h6">Payment Details</div>
      </q-card-section>

      <q-separator />

      <q-card-section>
        <q-list>
          <q-item>
            <q-item-section>Customer Name</q-item-section>
            <q-item-section>{{ loadedPayment.customerName }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Number</q-item-section>
            <q-item-section>{{ loadedPayment.number }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Type</q-item-section>
            <q-item-section>{{ loadedPayment.type }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Amount Before Discount</q-item-section>
            <q-item-section>{{ loadedPayment.amountBeforeDiscount }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Discount Amount</q-item-section>
            <q-item-section>{{ loadedPayment.discountAmount }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Payment Amount</q-item-section>
            <q-item-section>{{ loadedPayment.paymentAmount }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Payment Date</q-item-section>
            <q-item-section>{{ loadedPayment.paymentDate }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Is Refunded</q-item-section>
            <q-item-section>{{ loadedPayment.isRefunded }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Refund Date</q-item-section>
            <q-item-section>{{ loadedPayment.refundDate }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Created Reward</q-item-section>
            <q-item-section>{{ loadedPayment.createdReward }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Payment Spot</q-item-section>
            <q-item-section>{{ loadedPayment.paymentSpot }}</q-item-section>
          </q-item>
        </q-list>
      </q-card-section>
    </q-card>

    <div v-else class="q-mt-md text-h6 text-center">Loading...</div>

  </div>
</template>

<script setup>
import { ref, onMounted, defineProps } from 'vue';
import axios from 'axios';
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
/* 필요한 스타일 추가 */
</style>
