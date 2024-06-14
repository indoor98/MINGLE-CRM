<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <!-- 테이블로 결제 목록 표시 -->
    <q-table
      flat
      bordered
      title=""
      :rows="payments"
      :columns="paymentColumns"
      row-key="paymentId"
      v-model:pagination="paymentPagination"
    >
      <template v-slot:body="props">
        <q-tr :props="props" @click="showPaymentDetail(props.row)">
          <q-td v-for="col in paymentColumns" :key="col.name" :props="props">
            <template v-if="['amountBeforeDiscount', 'discountAmount', 'paymentAmount', 'createdReward'].includes(col.name)">
              {{ formatPrice(props.row[col.field]) }}
            </template>
            <template v-else>
              {{ props.row[col.field] }}
            </template>
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
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';
import CustomerPaymentDetail from './CustomerPaymentDetail.vue';
import { formatPrice } from 'src/utils/utils.js'; // 유틸리티 함수 불러오기

const route = useRoute();
const customerId = route.params.id;
const payments = ref([]);
const paymentPagination = ref({
  rowsPerPage: 10,
  page: 1,
  pagesNumber: 1,
  isFirstPage: true,
  isLastPage: true
});
const showDialog = ref(false);
const selectedPayment = ref({});

const fetchPayments = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/customers/${customerId}/payments`);
    payments.value = response.data.map((payment, index) => ({
      paymentId: index + 1,
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

onMounted(() => {
  fetchPayments();
});

const paymentColumns = [
  {name: 'paymentId', label: '#', align: 'left', field: 'paymentId', sortable: true},
  {name: 'customerName', label: '고객명', align: 'left', field: 'customerName', sortable: true},
  {name: 'number', label: '전화번호', align: 'center', field: 'number', sortable: true},
  {name: 'type', label: '결제 종류', align: 'center', field: 'type', sortable: true},
  {name: 'amountBeforeDiscount', label: '할인 전 금액', align: 'center', field: 'amountBeforeDiscount', sortable: true},
  {name: 'discountAmount', label: '할인 금액', align: 'center', field: 'discountAmount', sortable: true},
  {name: 'paymentAmount', label: '결제 금액', align: 'center', field: 'paymentAmount'},
  {name: 'paymentDate', label: '결제 날짜', align: 'center', field: 'paymentDate'},
  {name: 'isRefunded', label: '환불 여부', align: 'center', field: 'isRefunded'},
  {name: 'refundDate', label: '환불 날짜', align: 'center', field: 'refundDate'},
  {name: 'createdReward', label: '적립금', align: 'center', field: 'createdReward'},
  {name: 'paymentSpot', label: '결제 지점', align: 'center', field: 'paymentSpot'}
];
</script>

<style scoped>
/* 필요한 스타일을 추가할 수 있습니다. */
</style>
