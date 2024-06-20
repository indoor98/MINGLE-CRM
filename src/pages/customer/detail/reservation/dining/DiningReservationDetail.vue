<template>
  <q-card class="custom-card">
    <q-card-section>
      <div class="text-h6">다이닝 상세 예약 정보</div>
    </q-card-section>

    <q-separator />

    <q-card-section>
      <table class="reservation-table">
        <tbody>
        <tr>
          <th>예약 ID</th>
          <td>{{ reservation.reservationId }}</td>
        </tr>
        <tr>
          <th>예약 날짜</th>
          <td>{{ reservation.reservationDate }}</td>
        </tr>
        <tr>
          <th>방문 날짜</th>
          <td>{{ reservation.visitDate }}</td>
        </tr>
        <tr>
          <th>총 소비 금액</th>
          <td>{{ reservation.totalPrice }}</td>
        </tr>
        <tr>
          <th>주문 요리(가격)</th>
          <td>{{ reservation.dishes }}</td>
        </tr>
        </tbody>
      </table>
    </q-card-section>

    <q-card-actions align="right">
      <q-btn flat label="닫기" color="primary" @click="$emit('close')" />
    </q-card-actions>
  </q-card>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  reservation: {
    type: Object,
    default: () => ({})
  }
});
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

.reservation-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 16px;
}

.reservation-table th,
.reservation-table td {
  padding: 12px;
  border: 1px solid #e0e0e0;
  text-align: left;
}

.reservation-table th {
  background-color: #f5f5f5;
  font-weight: bold;
}
</style>
