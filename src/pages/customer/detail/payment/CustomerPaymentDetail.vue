<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <q-card class="q-mt-md">
      <q-card-section class="q-pa-md">
        <div class="text-h6">Payment Details</div>
      </q-card-section>

      <q-separator />

      <q-card-section>
        <q-list>
          <q-item>
            <q-item-section>Customer Name</q-item-section>
            <q-item-section>{{ payment.customerName }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Number</q-item-section>
            <q-item-section>{{ payment.number }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Type</q-item-section>
            <q-item-section>{{ payment.type }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Amount Before Discount</q-item-section>
            <q-item-section>{{ payment.amountBeforeDiscount }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Discount Amount</q-item-section>
            <q-item-section>{{ payment.discountAmount }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Payment Amount</q-item-section>
            <q-item-section>{{ payment.paymentAmount }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Payment Date</q-item-section>
            <q-item-section>{{ payment.paymentDate }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Is Refunded</q-item-section>
            <q-item-section>{{ payment.isRefunded }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Refund Date</q-item-section>
            <q-item-section>{{ payment.refundDate }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Created Reward</q-item-section>
            <q-item-section>{{ payment.createdReward }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Payment Spot</q-item-section>
            <q-item-section>{{ payment.paymentSpot }}</q-item-section>
          </q-item>
        </q-list>
      </q-card-section>
    </q-card>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const props = defineProps(['payment']);
</script>

<style scoped>
/* 필요한 스타일을 추가할 수 있습니다. */
</style>
