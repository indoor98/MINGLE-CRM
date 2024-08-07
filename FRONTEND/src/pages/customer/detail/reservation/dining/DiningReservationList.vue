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
        <q-tr :props="props" @click="showReservationDetail(props.row)" class="q-table-row">
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
      <template v-slot:no-data>
        <q-tr>
          <q-td :colspan="reservationColumns.length" class="text-center">
            다이닝 예약이 없습니다.
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
import { api as axios } from "src/boot/axios";
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
    const response = await axios.get(`/api/v1/customers/${customerId}/dish/reservations`);
    reservations.value = response.data.map((reservation, index) => ({
      reservationId: index + 1,
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
.q-table-row {
  cursor: pointer; /* 마우스를 올리면 클릭할 수 있는 것처럼 보이도록 */
}
</style>
