<!-- VoucherList.vue -->
<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <!-- SearchInput 컴포넌트 사용 -->
    <SearchInput
      v-model="search"
      label="검색어를 입력해주세요"
      :searchFields="['voucherId', 'requestDate', 'isAuth', 'authDate', 'isConvertedYn', 'conversionDate', 'issuerId', 'approverId', 'customerId', 'amount']"
      @search="handleSearch"
    />

    <!-- 테이블로 고객 바우처 목록 표시 -->
    <q-table
      flat
      bordered
      title=""
      :rows="filteredVouchers"
      :columns="voucherColumns"
      row-key="voucherId"
      v-model:pagination="voucherPagination"
    >
      <template v-slot:body="props">
        <q-tr :props="props" @click="showVoucherDetail(props.row)">
          <q-td v-for="col in voucherColumns" :key="col.name" :props="props">
            <template v-if="col.name === 'amount'">
              {{ formatPrice(props.row[col.field]) }}
            </template>
            <template v-else>
              {{ props.row[col.field] }}
            </template>
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
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';
import Fuse from 'fuse.js';
import CustomerVoucherDetail from './CustomerVoucherDetail.vue';
import SearchInput from 'src/components/SearchInput.vue'; // SearchInput 컴포넌트 임포트
import { formatPrice } from 'src/utils/utils.js'; // 유틸리티 함수 불러오기

const route = useRoute();
const customerId = route.params.id;
const vouchers = ref([]);
const search = ref('');
const voucherPagination = ref({
  rowsPerPage: 10,
  page: 1,
  pagesNumber: 1,
  isFirstPage: true,
  isLastPage: true
});
const showDialog = ref(false);
const selectedVoucher = ref({});
let fuse; // fuse.js 인스턴스

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

    // fuse.js 설정
    fuse = new Fuse(vouchers.value, {
      keys: ['voucherId', 'requestDate', 'isAuth', 'authDate', 'isConvertedYn', 'conversionDate', 'issuerId', 'approverId', 'customerId', 'amount'],
      threshold: 0.3 // 유사도 설정 (0.0 - 1.0, 낮을수록 엄격)
    });

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

const filteredVouchers = computed(() => {
  if (!search.value) {
    return vouchers.value;
  }
  return fuse.search(search.value).map(result => result.item);
});

const handleSearch = (searchTerm, searchFields) => {
  search.value = searchTerm;
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
