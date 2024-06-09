<template>
  <div>
    <h1>Reservation Page</h1>
    <p>Customer ID: {{ customerId }}</p>
    <p>Reservation ID: {{ reservationId }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const customerId = ref(route.params.customerId);
const reservationId = ref(route.params.reservationId);

console.log('Customer ID:', customerId.value);
console.log('Reservation ID:', reservationId.value);
</script>
