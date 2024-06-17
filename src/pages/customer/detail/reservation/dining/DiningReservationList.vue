<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />
    <SearchInput
      v-model="search"
      label="검색어를 입력해주세요"
      :search-fields="['reservationId', 'reservationDate', 'visitDate', 'totalPrice', 'dishes']"
      @search="handleSearch"
    />
    <q-table
      flat
      bordered
      title=""
      :rows="filteredReservations"
      :columns="reservationColumns"
      row-key="reservationId"
      v-model:pagination="reservationPagination"
    >
      <template v-slot:body="props">
        <q-tr :props="props" @click="showReservationDetail(props.row)">
          <q-td v-for="col in reservationColumns" :key="col.name" :props="props">
            <template v-if="col.name === 'totalPrice'">
              {{ formatPrice(props.row[col.field]) }}
            </template>
            <template v-else>
              {{ props.row[col.field] }}
            </template>
          </q-td>
        </q-tr>
      </template>
    </q-table>

    <q-dialog v-model="showDialog" persistent>
      <DiningReservationDetail :reservation="selectedReservation" @close="closeReservationDetail" />
    </q-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';
import DiningReservationDetail from './DiningReservationDetail.vue';
import SearchInput from 'src/components/SearchInput.vue';
import { formatPrice } from '/src/utils/utils';
import Fuse from 'fuse.js';

const route = useRoute();
const customerId = route.params.id;
const reservations = ref([]);
const search = ref('');
const reservationPagination = ref({
  rowsPerPage: 10,
  page: 1,
  pagesNumber: 1,
  isFirstPage: true,
  isLastPage: true
});
const showDialog = ref(false);
const selectedReservation = ref(null);
let fuse;

const fetchReservations = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/customers/${customerId}/dish/reservations`);
    reservations.value = response.data.map((reservation) => ({
      reservationId: reservation.reservationId,
      reservationDate: new Date(reservation.reservationDate).toLocaleDateString(),
      visitDate: new Date(reservation.visitDate).toLocaleDateString(),
      totalPrice: reservation.totalPrice,
      dishes: reservation.dishes.map(dish => `${dish.name} ($${dish.price})`).join(', ')
    }));

    fuse = new Fuse(reservations.value, {
      keys: ['reservationId', 'reservationDate', 'visitDate', 'totalPrice', 'dishes'],
      threshold: 0.3
    });

    reservationPagination.value.pagesNumber = Math.ceil(reservations.value.length / reservationPagination.value.rowsPerPage);
    reservationPagination.value.isFirstPage = reservationPagination.value.page === 1;
    reservationPagination.value.isLastPage = reservationPagination.value.page === reservationPagination.value.pagesNumber;
  } catch (error) {
    console.error('Error fetching reservations:', error);
  }
};

const showReservationDetail = (row) => {
  selectedReservation.value = row;
  showDialog.value = true;
};

const closeReservationDetail = () => {
  showDialog.value = false;
  selectedReservation.value = null;
};

const filteredReservations = computed(() => {
  if (!search.value) {
    return reservations.value;
  }
  return fuse.search(search.value).map(result => result.item);
});

const handleSearch = (searchTerm) => {
  search.value = searchTerm;
};

onMounted(() => {
  fetchReservations();
});

const reservationColumns = [
  { name: 'reservationId', label: '#', align: 'left', field: 'reservationId' },
  { name: 'reservationDate', label: '예약 날짜', align: 'left', field: 'reservationDate' },
  { name: 'visitDate', label: '방문 날짜', align: 'center', field: 'visitDate' },
  { name: 'totalPrice', label: '총 가격', align: 'center', field: 'totalPrice' },
  { name: 'dishes', label: '음식', align: 'center', field: 'dishes' }
];
</script>

<style scoped>
/* 필요한 스타일을 추가할 수 있습니다. */
</style>
