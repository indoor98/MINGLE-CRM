vue
코드 복사
<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <!-- 테이블로 고객 바우처 목록 표시 -->
    <q-table
      flat
      bordered
      title="Customer Vouchers"
      :rows="vouchers"
      :columns="voucherColumns"
      row-key="voucherId"
      v-model:pagination="voucherPagination"
    >
      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td v-for="col in voucherColumns" :key="col.name" :props="props">
            {{ props.row[col.field] }}
          </q-td>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';

const route = useRoute();
const customerId = route.params.id;
const vouchers = ref([]);
const voucherPagination = ref({
  rowsPerPage: 10,
  page: 1,
  pagesNumber: 1,
  isFirstPage: true,
  isLastPage: true
});

const fetchVouchers = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/customers/${customerId}/voucher`);
    vouchers.value = response.data.data.map((voucher, index) => ({
      voucherId: voucher.voucherId,
      requestDate: new Date(voucher.requestDate).toLocaleDateString(),
      isAuth: voucher.isAuth ? 'Yes' : 'No',
      authDate: voucher.authDate ? new Date(voucher.authDate).toLocaleDateString() : '-',
      isConvertedYn: voucher.isConvertedYn ? 'Yes' : 'No',
      conversionDate: voucher.conversionDate ? new Date(voucher.conversionDate).toLocaleDateString() : '-',
      issuerId: voucher.issuerId,
      approverId: voucher.approverId,
      customerId: voucher.customerId,
      amount: voucher.amount
    }));

    // 페이지네이션 설정
    voucherPagination.value.pagesNumber = Math.ceil(response.data.data.length / voucherPagination.value.rowsPerPage);
    voucherPagination.value.isFirstPage = voucherPagination.value.page === 1;
    voucherPagination.value.isLastPage = voucherPagination.value.page === voucherPagination.value.pagesNumber;

    console.log(vouchers.value); // 데이터 확인용 콘솔 출력
  } catch (error) {
    console.error('Error fetching vouchers:', error);
  }
};

onMounted(() => {
  fetchVouchers();
});

const voucherColumns = [
  { name: 'voucherId', label: 'Voucher ID', align: 'left', field: 'voucherId' },
  { name: 'requestDate', label: 'Request Date', align: 'left', field: 'requestDate' },
  { name: 'isAuth', label: 'Authorized', align: 'center', field: 'isAuth' },
  { name: 'authDate', label: 'Authorization Date', align: 'center', field: 'authDate' },
  { name: 'isConvertedYn', label: 'Converted', align: 'center', field: 'isConvertedYn' },
  { name: 'conversionDate', label: 'Conversion Date', align: 'center', field: 'conversionDate' },
  { name: 'issuerId', label: 'Issuer ID', align: 'center', field: 'issuerId' },
  { name: 'approverId', label: 'Approver ID', align: 'center', field: 'approverId' },
  { name: 'customerId', label: 'Customer ID', align: 'center', field: 'customerId' },
  { name: 'amount', label: 'Amount', align: 'center', field: 'amount' }
];
</script>

<style scoped>
/* 필요한 스타일을 추가할 수 있습니다. */
</style>
