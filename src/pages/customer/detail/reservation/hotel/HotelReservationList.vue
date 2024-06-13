<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <!-- 테이블로 호텔 예약 목록 표시 -->
    <q-table
      flat
      bordered
      title="Hotel Reservations"
      :rows="reservations"
      :columns="reservationColumns"
      row-key="reservationId"
      v-model:pagination="reservationPagination"
    >
      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td v-for="col in reservationColumns" :key="col.name" :props="props">
            {{ props.row[col.field] }}
          </q-td>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';

const route = useRoute();
const customerId = route.params.id;
const reservations = ref([]);
const reservationPagination = ref({
  rowsPerPage: 10,
  page: 1,
  pagesNumber: 1,
  isFirstPage: true,
  isLastPage: true
});

const fetchReservations = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/customers/${customerId}/hotel/reservations`);
    reservations.value = response.data.map((reservation, index) => ({
      reservationId: index + 1,
      reservationDate: new Date(reservation.reservationDate).toLocaleDateString(),
      name: reservation.name,
      phoneNumber: reservation.phoneNumber,
      hotelName: reservation.hotelName,
      reservationType: reservation.reservationType,
      memo: reservation.memo
    }));

    // 페이지네이션 설정
    reservationPagination.value.pagesNumber = Math.ceil(reservations.value.length / reservationPagination.value.rowsPerPage);
    reservationPagination.value.isFirstPage = reservationPagination.value.page === 1;
    reservationPagination.value.isLastPage = reservationPagination.value.page === reservationPagination.value.pagesNumber;

    console.log(reservations.value); // 데이터 확인용 콘솔 출력
  } catch (error) {
    console.error('Error fetching reservations:', error);
  }
};

onMounted(() => {
  fetchReservations();
});

const reservationColumns = [
  { name: 'reservationId', label: '#', align: 'left', field: 'reservationId' },
  { name: 'reservationDate', label: 'Reservation Date', align: 'left', field: 'reservationDate' },
  { name: 'name', label: 'Name', align: 'center', field: 'name' },
  { name: 'phoneNumber', label: 'Phone Number', align: 'center', field: 'phoneNumber' },
  { name: 'hotelName', label: 'Hotel Name', align: 'center', field: 'hotelName' },
  { name: 'reservationType', label: 'Reservation Type', align: 'center', field: 'reservationType' },
  { name: 'memo', label: 'Memo', align: 'center', field: 'memo' }
];
</script>

<style scoped>
/* 필요한 스타일을 추가할 수 있습니다. */
</style>
