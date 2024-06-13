<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <!-- 테이블로 결제 목록 표시 -->
    <q-table
      flat
      bordered
      title="Payments"
      :rows="payments"
      :columns="paymentColumns"
      row-key="paymentId"
      v-model:pagination="paymentPagination"
    >
      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td v-for="col in paymentColumns" :key="col.name" :props="props">
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
const payments = ref([]);
const paymentPagination = ref({
  rowsPerPage: 10,
  page: 1,
  pagesNumber: 1,
  isFirstPage: true,
  isLastPage: true
});

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

onMounted(() => {
  fetchPayments();
});

const paymentColumns = [
  { name: 'paymentId', label: '#', align: 'left', field: 'paymentId' },
  { name: 'customerName', label: 'Customer Name', align: 'left', field: 'customerName' },
  { name: 'number', label: 'Number', align: 'center', field: 'number' },
  { name: 'type', label: 'Type', align: 'center', field: 'type' },
  { name: 'amountBeforeDiscount', label: 'Amount Before Discount', align: 'center', field: 'amountBeforeDiscount' },
  { name: 'discountAmount', label: 'Discount Amount', align: 'center', field: 'discountAmount' },
  { name: 'paymentAmount', label: 'Payment Amount', align: 'center', field: 'paymentAmount' },
  { name: 'paymentDate', label: 'Payment Date', align: 'center', field: 'paymentDate' },
  { name: 'isRefunded', label: 'Refunded', align: 'center', field: 'isRefunded' },
  { name: 'refundDate', label: 'Refund Date', align: 'center', field: 'refundDate' },
  { name: 'createdReward', label: 'Created Reward', align: 'center', field: 'createdReward' },
  { name: 'paymentSpot', label: 'Payment Spot', align: 'center', field: 'paymentSpot' }
];
</script>
