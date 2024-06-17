<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <!-- SearchInput 컴포넌트 사용 -->
    <SearchInput
      v-model="search"
      label="검색어를 입력해주세요"
      :searchFields="['customerName', 'number', 'type', 'paymentSpot']"
      @search="handleSearch"
    />

    <!-- 테이블로 결제 목록 표시 -->
    <q-table
      flat
      bordered
      title=""
      :rows="filteredPayments"
      :columns="paymentColumns"
      row-key="paymentId"
      v-model:pagination="paymentPagination"
    >
      <template v-slot:body="props">
        <q-tr :props="props" @click="showPaymentDetail(props.row)">
          <q-td v-for="col in paymentColumns" :key="col.name" :props="props">
            <span v-if="shouldHighlight(props.row[col.field])" class="highlighted-text">
              {{ props.row[col.field] }}
            </span>
            <span v-else>
              {{ props.row[col.field] }}
            </span>
          </q-td>
        </q-tr>
      </template>
    </q-table>

    <!-- 결제 상세 정보 다이얼로그 -->
    <q-dialog v-model="showDialog">
      <customer-payment-detail :payment="selectedPayment" />
    </q-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';
import Fuse from 'fuse.js';
import CustomerPaymentDetail from './CustomerPaymentDetail.vue';
import SearchInput from 'src/components/SearchInput.vue'; // SearchInput 컴포넌트 임포트
import { formatPrice } from 'src/utils/utils.js'; // 유틸리티 함수 불러오기

const route = useRoute();
const customerId = route.params.id;
const payments = ref([]);
const search = ref('');
const paymentPagination = ref({
  rowsPerPage: 10,
  page: 1,
  pagesNumber: 1,
  isFirstPage: true,
  isLastPage: true
});
const showDialog = ref(false);
const selectedPayment = ref({});
let fuse; // fuse.js 인스턴스

const fetchPayments = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/customers/${customerId}/payments`);
    payments.value = response.data.map((payment, index) => ({
      paymentId: payment.id,
      customerName: payment.customerName,
      number: payment.number,
      type: payment.type,
      amountBeforeDiscount: payment.amountBeforeDiscount,
      discountAmount: payment.discountAmount,
      paymentAmount: payment.paymentAmount,
      paymentDate: new Date(payment.paymentDate).toLocaleDateString(),
      isRefunded: payment.isRefunded ? 'Yes' : 'No',
      refundDate: payment.refundDate ? new Date(payment.refundDate).toLocaleDateString() : '-',
      createdReward: payment.createdReward,
      paymentSpot: payment.paymentSpot
    }));

    // fuse.js 설정
    fuse = new Fuse(payments.value, {
      keys: ['customerName', 'number', 'type', 'paymentSpot'],
      threshold: 0.5 // 유사도 설정 (0.0 - 1.0, 낮을수록 엄격)
    });

    // 페이지네이션 설정
    paymentPagination.value.pagesNumber = Math.ceil(payments.value.length / paymentPagination.value.rowsPerPage);
    paymentPagination.value.isFirstPage = paymentPagination.value.page === 1;
    paymentPagination.value.isLastPage = paymentPagination.value.page === paymentPagination.value.pagesNumber;

    console.log(payments.value); // 데이터 확인용 콘솔 출력
  } catch (error) {
    console.error('Error fetching payments:', error);
  }
};

const showPaymentDetail = (payment) => {
  selectedPayment.value = payment;
  showDialog.value = true;
};

const filteredPayments = computed(() => {
  if (!search.value) {
    return payments.value;
  }
  return fuse.search(search.value).map(result => result.item);
});

const handleSearch = (searchTerm) => {
  search.value = searchTerm;
};

const shouldHighlight = (value) => {
  if (!search.value) return false;
  return value.toString().toLowerCase().includes(search.value.toLowerCase());
};

onMounted(() => {
  fetchPayments();
});

const paymentColumns = [
  {name: 'paymentId', label: '#', align: 'left', field: 'paymentId', sortable: true},
  {name: 'customerName', label: '고객명', align: 'left', field: 'customerName', sortable: true},
  {name: 'number', label: '전화번호', align: 'center', field: 'number', sortable: true},
  {name: 'type', label: '결제 종류', align: 'center', field: 'type', sortable: true},
  {name: 'paymentAmount', label: '결제 금액', align: 'center', field: 'paymentAmount'},
  {name: 'paymentDate', label: '결제 날짜', align: 'center', field: 'paymentDate'},
  {name: 'isRefunded', label: '환불 여부', align: 'center', field: 'isRefunded'},
];
</script>

<style scoped>
.highlighted-text {
  color: red; /* 원하는 텍스트 색상으로 설정 */
}
</style>
