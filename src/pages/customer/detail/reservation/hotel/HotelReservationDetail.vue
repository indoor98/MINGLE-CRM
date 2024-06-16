<template>
  <q-card class="custom-card">
    <q-card-section>
      <div class="text-h6">Reservation Details</div>
    </q-card-section>

    <q-separator />

    <q-card-section>
      <q-list>
        <q-item>
          <q-item-label>Reservation ID</q-item-label>
          <q-item-section>{{ reservation.reservationId }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Reservation Date</q-item-label>
          <q-item-section>{{ reservation.reservationDate }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Customer Name</q-item-label>
          <q-item-section>{{ reservation.name }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Phone Number</q-item-label>
          <q-item-section>{{ reservation.phoneNumber }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Hotel Name</q-item-label>
          <q-item-section>{{ reservation.hotelName }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Room Type</q-item-label>
          <q-item-section>{{ reservation.reservationType }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Memo</q-item-label>
          <q-item-section>{{ reservation.memo }}</q-item-section>
        </q-item>
      </q-list>
    </q-card-section>

    <q-card-actions align="right">
      <q-btn flat label="Close" color="primary" @click="$emit('close')" />
    </q-card-actions>
  </q-card>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps(['reservation']);
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
