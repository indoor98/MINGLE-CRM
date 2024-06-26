<template>
  <div>
    <h2 class="text-h6">취소된 바우처 목록</h2>

    <q-card class="my-card">
      <q-card-section class="row justify-center q-pa-xs">
        <div class="col q-pa-sm">
          <q-input
            v-model="searchCustomerName"
            clearable
            filled
            color="purple-12"
            label="고객명"
            dense
            placeholder="고객명을 입력하세요"
          />
        </div>
        <div class="col q-pa-sm">
          <q-input
            v-model="searchConfirmerName"
            clearable
            filled
            color="purple-12"
            label="검토 매니저명"
            dense
            placeholder="매니저명을 입력하세요"
          />
        </div>

        <div class="col q-pa-sm">
          <q-input
            v-model="searchAmount"
            clearable
            filled
            color="purple-12"
            label="금액"
            dense
            placeholder="금액을 입력하세요"
          />
        </div>

        <div class="col q-pa-sm">
          <q-input
            v-model="searchCreatedReason"
            clearable
            filled
            color="purple-12"
            label="생성 사유"
            dense
            placeholder="생성 사유를 입력하세요"
          />
        </div>

        <div class="col q-pa-sm">
          <q-select
            v-model="selectedGrades"
            multiple
            filled
            color="purple-12"
            label="고객 등급"
            :options="gradeOptions"
            emit-value
            map-options
            dense
            placeholder="선택"
          />
        </div>

        <div class="col q-pa-sm">
          <q-btn
            color="primary"
            label="검색"
            @click="searchVouchers"
            dense
            class="full-width"
          />
        </div>
      </q-card-section>
    </q-card>

    <q-card class="q-mt-md">
      <q-card-section>
        <q-table
          :rows="vouchers"
          :columns="columns"
          row-key="id"
          :loading="loading"
          :pagination="{ rowsPerPage: 10 }"
          style="cursor: pointer"
        >
          <template v-slot:body="props">
            <q-tr :props="props" @click="showVoucherDetail(props.row)">
              <q-td v-for="col in columns" :key="col.name" :props="props">
                <span v-if="col.field === 'sendOrCancelDate'">
                  {{ toDate(props.row[col.field]) }}
                </span>
                <span v-else>
                  {{ props.row[col.field] }}
                </span>
              </q-td>
            </q-tr>
          </template>
          <template v-slot:no-data>
            <q-tr>
              <q-td :colspan="columns.length" class="text-center">
                바우처가 없습니다.
              </q-td>
            </q-tr>
          </template>
        </q-table>
      </q-card-section>
    </q-card>
    <q-card class="q-mt-md">
      <q-card-section v-if="errorMessage">
        <p style="color: red" class="text-center">{{ errorMessage }}</p>
      </q-card-section>
    </q-card>

    <q-dialog v-model="showDialog" persistent>
      <VoucherDetail :voucher="selectedVoucher" @close="closeVoucherDetail" />
    </q-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { api as axios } from "src/boot/axios";
import VoucherDetail from "components/voucher/VoucherHistoryDetail.vue";
import { useUserStore } from "src/stores/user-store";
const userStore = useUserStore();

const vouchers = ref([]);
const errorMessage = ref("");
const loading = ref(true);
const showDialog = ref(false);
const selectedVoucher = ref({});

const searchCustomerName = ref("");
const searchAmount = ref(null);
const searchCreatedReason = ref("");
const searchConfirmerName = ref("");
const selectedGrades = ref([]);

const gradeOptions = [
  { label: "선택 안 함", value: "" },
  { label: "NEW", value: "NEW" },
  { label: "BASIC", value: "BASIC" },
  { label: "VIP", value: "VIP" },
  { label: "VVIP", value: "VVIP" },
];

const columns = [
  {
    name: "voucherId",
    label: "바우처 ID",
    align: "center",
    field: "voucherId",
    sortable: true,
  },
  {
    name: "customerName",
    label: "고객 이름",
    align: "center",
    field: "customerName",
    sortable: true,
  },
  {
    name: "createdReason",
    label: "생성 사유",
    align: "center",
    field: "createdReason",
  },
  {
    name: "amount",
    label: "금액",
    align: "center",
    field: "amount",
    sortable: true,
  },
  {
    name: "sendOrCancelDate",
    label: "취소 일자",
    align: "center",
    field: "sendOrCancelDate",
    sortable: true,
  },
];

const toDate = (beforeDate) => {
  const date = new Date(beforeDate);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
};

const toTenWords = (beforeWord) => {
  const afterWord =
    beforeWord.length <= 10 ? beforeWord : beforeWord.substring(0, 10) + "...";
  return afterWord;
};

const fetchVouchers = async () => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/v1/vouchers/canceled-marketer`
    );
    vouchers.value = response.data.data;
    errorMessage.value = "";
  } catch (error) {
    console.error("취소된 바우처 목록을 불러오는 중 에러 발생:", error);
    errorMessage.value =
      "취소된 바우처 목록을 불러오는 중 에러가 발생했습니다.";
  } finally {
    loading.value = false;
  }
};

const showVoucherDetail = (voucher) => {
  selectedVoucher.value = voucher;
  showDialog.value = true;
};

const closeVoucherDetail = () => {
  showDialog.value = false;
  selectedVoucher.value = null;
};

const searchVouchers = async () => {
  try {
    const data = {
      customerName: searchCustomerName.value || null,
      creatorName: userStore.name || null,
      confirmerName: searchConfirmerName.value || null,
      amount: searchAmount.value ? Number(searchAmount.value) : null,
      createdReason: searchCreatedReason.value || null,
      customerGrades:
        selectedGrades.value.length > 0 ? selectedGrades.value : null,
      status: ["CANCELED"],
    };

    const response = await axios.post(
      "http://localhost:8080/api/v1/vouchers/search",
      data
    );
    vouchers.value = response.data.data;
  } catch (error) {
    console.error("Error fetching vouchers:", error);
  }
};

onMounted(() => {
  fetchVouchers();
});
</script>
