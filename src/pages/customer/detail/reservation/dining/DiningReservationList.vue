<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <!-- 테이블로 다이닝 예약 목록 표시 -->
    <q-table
      flat
      bordered
      title=""
      :rows="reservations"
      :columns="reservationColumns"
      row-key="reservationId"
      v-model:pagination="reservationPagination"
    >
      <template v-slot:body="props">
        <q-tr :props="props" @click="showReservationDetail(props)">
          <q-td v-for="col in reservationColumns" :key="col.name" :props="props">
            {{ props.row[col.field] }}
          </q-td>
        </q-tr>
      </template>

    </q-table>

    <!-- 예약 상세 정보 다이얼로그 -->
    <q-dialog v-model="showDialog" persistent>
      <dining-reservation-detail :reservation="selectedReservation" @close="closeReservationDetail" />
    </q-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';
import DiningReservationDetail from './DiningReservationDetail.vue';

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

const showDialog = ref(false);
const selectedReservation = ref(null);

// 예약 목록 가져오기
const fetchReservations = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/customers/${customerId}/dish/reservations`);
    reservations.value = response.data.map((reservation,index) => ({
      reservationId: index+1,
      reservationDate: new Date(reservation.reservationDate).toLocaleDateString(),
      visitDate: new Date(reservation.visitDate).toLocaleDateString(),
      totalPrice: reservation.totalPrice,
      dishes: reservation.dishes.map(dish => `${dish.name} ($${dish.price})`).join(', ')
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

// 예약 상세 정보 보기
const showReservationDetail = (props) => {
  selectedReservation.value = props.row;
  showDialog.value = true;
};

// 예약 상세 정보 닫기
const closeReservationDetail = () => {
  showDialog.value = false;
  selectedReservation.value = null;
};

onMounted(() => {
  fetchReservations();
});

const reservationColumns = [
  {name: 'reservationId', label: '#', align: 'left', field: 'reservationId'},
  {name: 'reservationDate', label: '예약 날짜', align: 'left', field: 'reservationDate'},
  {name: 'visitDate', label: '방문 날짜', align: 'center', field: 'visitDate'},
  {name: 'totalPrice', label: '총 가격', align: 'center', field: 'totalPrice'},
  {name: 'dishes', label: '음식', align: 'center', field: 'dishes'}
];
</script>

<style scoped>
/* 필요한 스타일을 추가할 수 있습니다. */
</style>
