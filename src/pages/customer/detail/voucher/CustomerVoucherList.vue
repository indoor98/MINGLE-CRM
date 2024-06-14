<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <!-- 테이블로 고객 바우처 목록 표시 -->
    <q-table
      flat
      bordered
      title=""
      :rows="vouchers"
      :columns="voucherColumns"
      row-key="voucherId"
      v-model:pagination="voucherPagination"
    >
      <template v-slot:body="props">
        <q-tr :props="props" @click="showVoucherDetail(props.row)">
          <q-td v-for="col in voucherColumns" :key="col.name" :props="props">
            {{ props.row[col.field] }}
          </q-td>
        </q-tr>
      </template>
    </q-table>

    <!-- 바우처 상세 정보 다이얼로그 -->
    <q-dialog v-model="showDialog" persistent>
      <customer-voucher-detail :voucher="selectedVoucher" @close="showDialog = false" />
    </q-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';
import CustomerVoucherDetail from './CustomerVoucherDetail.vue';

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
const showDialog = ref(false);
const selectedVoucher = ref({});

const fetchVouchers = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/customers/${customerId}/voucher`);
    vouchers.value = response.data.data.map((voucher) => ({
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
  } catch (error) {
    console.error('Error fetching vouchers:', error);
  }
};

const showVoucherDetail = (voucher) => {
  selectedVoucher.value = voucher;
  showDialog.value = true;
};

onMounted(() => {
  fetchVouchers();
});

const voucherColumns = [
  { name: 'voucherId', label: '바우처 ID', align: 'left', field: 'voucherId' },
  { name: 'requestDate', label: '요청 날짜', align: 'left', field: 'requestDate' },
  { name: 'isAuth', label: '인증 여부', align: 'center', field: 'isAuth' },
  { name: 'authDate', label: '인증 날짜', align: 'center', field: 'authDate' },
  { name: 'isConvertedYn', label: '전환 여부', align: 'center', field: 'isConvertedYn' },
  { name: 'conversionDate', label: '전환 날짜', align: 'center', field: 'conversionDate' },
  { name: 'issuerId', label: '발급자 ID', align: 'center', field: 'issuerId' },
  { name: 'approverId', label: '승인자 ID', align: 'center', field: 'approverId' },
  { name: 'customerId', label: '고객 ID', align: 'center', field: 'customerId' },
  { name: 'amount', label: '금액', align: 'center', field: 'amount' }
];
</script>

<style scoped>
/* 필요한 스타일을 추가할 수 있습니다. */
</style>
