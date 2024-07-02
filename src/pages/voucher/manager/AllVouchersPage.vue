<template>
  <q-page>
    <div class="q-pa-md">
      <h2 class="text-h6">모든 바우처 목록</h2>

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
              v-model="searchCreatorName"
              clearable
              filled
              color="purple-12"
              label="요청 직원명"
              dense
              placeholder="직원명을 입력하세요"
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
            <q-select
              v-model="selectedStatus"
              multiple
              filled
              color="purple-12"
              label="바우처 상태"
              :options="statusOptions"
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
                  <span
                    v-if="
                      col.field === 'requestDate' || col.field === 'confirmDate'
                    "
                  >
                    {{ dateTimeToDate(props.row[col.field]) }}
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
        <VoucherHistoryDetail
          :voucher="selectedVoucher"
          @close="closeVoucherDetail"
        />
      </q-dialog>
    </div>
  </q-page>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { api as axios } from "src/boot/axios";
import VoucherHistoryDetail from "../../../components/voucher/VoucherHistoryDetail.vue";
import { dateTimeToDate } from "src/utils/utils.js";

const vouchers = ref([]);
const errorMessage = ref("");
const loading = ref(true);
const showDialog = ref(false);
const selectedVoucher = ref({});

const searchCustomerName = ref("");
const searchAmount = ref(null);
const searchCreatedReason = ref("");
const searchCreatorName = ref("");
const searchConfirmerName = ref("");
const selectedGrades = ref([]);
const selectedStatus = ref([]);

const gradeOptions = [
  // { label: "선택 안 함", value: "" },,
  { label: "BROWN", value: "BROWN" },
  { label: "SILVER", value: "SILVER" },
  { label: "GOLD", value: "GOLD" },
  { label: "DIAMOND", value: "DIAMOND" },
];

const statusOptions = [
  // { label: "선택 안 함", value: "" },,
  { label: "요청됨", value: "REQUESTED" },
  { label: "승인됨", value: "APPROVED" },
  { label: "거절됨", value: "REJECTED" },
  { label: "발송됨", value: "SENDED" },
  { label: "취소됨", value: "CANCELED" },
  { label: "전환됨", value: "CONVERTED" },
];

const props = defineProps({
  selected: {
    type: String,
    required: true,
  },
});

const columns = [
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
    name: "customerName",
    label: "고객 이름",
    align: "center",
    field: "customerName",
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

const toTenWords = (beforeWord) => {
  const afterWord =
    beforeWord.length <= 10 ? beforeWord : beforeWord.substring(0, 10) + "...";
  return afterWord;
};

const fetchVouchers = async () => {
  try {
    const response = await axios.get(
      `https://httpstest.mingle-crm.com/api/v1/vouchers/histories`
    );
    vouchers.value = response.data.data.sort(
      (a, b) => b.voucherId - a.voucherId
    );
    errorMessage.value = "";
  } catch (error) {
    console.error("모든된 바우처 목록을 불러오는 중 에러 발생:", error);
    errorMessage.value = "모든 바우처 목록을 불러오는 중 에러가 발생했습니다.";
  } finally {
    loading.value = false;
  }
};

const getStatusLabel = (status) => {
  switch (status) {
    case "REQUESTED":
      return "요청됨";
    case "APPROVED":
      return "승인됨";
    case "REJECTED":
      return "거절됨";
    case "SENDED":
      return "발송됨";
    case "CANCELED":
      return "취소됨";
    case "CONVERTED":
      return "전환됨";
    default:
      return status;
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
      creatorName: searchCreatorName.value || null,
      confirmerName: searchConfirmerName.value || null,
      amount: searchAmount.value ? Number(searchAmount.value) : null,
      createdReason: searchCreatedReason.value || null,
      customerGrades:
        selectedGrades.value.length > 0 ? selectedGrades.value : null,
      status: selectedStatus.value.length > 0 ? selectedStatus.value : null,
    };

    const response = await axios.post(
      "https://httpstest.mingle-crm.com/api/v1/vouchers/search",
      data
    );
    vouchers.value = response.data.data.sort(
      (a, b) => b.voucherId - a.voucherId
    );
  } catch (error) {
    console.error("Error fetching vouchers:", error);
    errorMessage.value = "바우처 검색 중 에러가 발생했습니다.";
  }
};

onMounted(fetchVouchers);
</script>
