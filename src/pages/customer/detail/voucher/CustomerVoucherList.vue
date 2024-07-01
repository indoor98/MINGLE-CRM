<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <!-- SearchInput 컴포넌트 사용 -->
    <SearchInput
      v-model="search"
      label="검색어를 입력해주세요"
      :searchFields="[
        'voucherId',
        'requestDate',
        'isAuth',
        'authDate',
        'isConvertedYn',
        'conversionDate',
        'issuerId',
        'approverId',
        'customerId',
        'amount',
      ]"
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
        <q-tr
          :props="props"
          @click="showVoucherDetail(props.row)"
          class="q-table-row"
        >
          <q-td v-for="col in voucherColumns" :key="col.name" :props="props">
            <template v-if="col.name === 'amount'">
              {{ formatPrice(props.row[col.field]) }}
            </template>
            <template v-else-if="col.name === 'requestDate'">
              {{ toDate(props.row[col.field]) }}
            </template>
            <template v-else>
              {{ props.row[col.field] }}
            </template>
          </q-td>
        </q-tr>
      </template>
      <template v-slot:no-data>
        <q-tr>
          <q-td :colspan="voucherColumns.length" class="text-center">
            바우처 내역이 없습니다.
          </q-td>
        </q-tr>
      </template>
    </q-table>

    <!-- 바우처 상세 정보 다이얼로그 -->
    <q-dialog v-model="showDialog" persistent>
      <customer-voucher-detail
        :voucher="selectedVoucher"
        @close="showDialog = false"
      />
    </q-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { api as axios } from "src/boot/axios";
import { useRoute } from "vue-router";
import Fuse from "fuse.js";
import CustomerVoucherDetail from "./CustomerVoucherDetail.vue";
import SearchInput from "src/components/SearchInput.vue"; // SearchInput 컴포넌트 임포트
import { formatPrice, toDate } from "src/utils/utils.js"; // 유틸리티 함수 불러오기

const route = useRoute();
const customerId = route.params.id;
const vouchers = ref([]);
const search = ref("");
const voucherPagination = ref({
  rowsPerPage: 10,
  page: 1,
  pagesNumber: 1,
  isFirstPage: true,
  isLastPage: true,
});
const showDialog = ref(false);
const selectedVoucher = ref({});
let fuse; // fuse.js 인스턴스

const fetchVouchers = async () => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/v1/vouchers/customer/${customerId}`
    );
    // vouchers.value = response.data.data.map((voucher, index) => ({
    //   voucherId: voucher.voucherId,
    //   idx: index + 1,
    //   requestDate: new Date(voucher.requestDate).toLocaleDateString(),
    //   isAuth: voucher.isAuth ? "Yes" : "No",
    //   authDate: voucher.authDate
    //     ? new Date(voucher.authDate).toLocaleDateString()
    //     : "-",
    //   conversionDate: voucher.conversionDate
    //     ? new Date(voucher.conversionDate).toLocaleDateString()
    //     : null,
    //   creatorName: voucher.creatorName,
    //   approverId: voucher.approverId,
    //   customerId: voucher.customerId,
    //   amount: voucher.amount,
    //   status: voucher.status,
    //   createdReason: voucher.createdReason,
    // })
    // );
    vouchers.value = response.data.data;

    // fuse.js 설정
    fuse = new Fuse(vouchers.value, {
      keys: [
        "voucherId",
        "requestDate",
        "isAuth",
        "authDate",
        "isConvertedYn",
        "conversionDate",
        "issuerId",
        "approverId",
        "customerId",
        "amount",
      ],
      threshold: 0.3, // 유사도 설정 (0.0 - 1.0, 낮을수록 엄격)
    });
  } catch (error) {
    console.error("Error fetching vouchers:", error);
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
  return fuse.search(search.value).map((result) => result.item);
});

const handleSearch = (searchTerm, searchFields) => {
  search.value = searchTerm;
};

onMounted(() => {
  fetchVouchers();
});

const voucherColumns = [
  {
    name: "voucherId",
    label: "바우처 ID",
    align: "center",
    field: "voucherId",
    sortable: true,
  },
  {
    name: "requestDate",
    label: "요청 일자",
    align: "center",
    field: "requestDate",
    sortable: true,
  },
  {
    name: "creatorName",
    label: "요청 직원 이름",
    align: "center",
    field: "creatorName",
    sortable: true,
  },
  {
    name: "createdReason",
    label: "발급 사유",
    align: "center",
    field: "createdReason",
    sortable: true,
  },
  {
    name: "amount",
    label: "금액",
    align: "center",
    field: "amount",
    sortable: true,
  },
  {
    name: "status",
    label: "상태",
    align: "center",
    field: "status",
    sortable: true,
  },
];
</script>

<style scoped>
.q-table-row {
  cursor: pointer; /* 마우스를 올리면 클릭할 수 있는 것처럼 보이도록 */
}
</style>
