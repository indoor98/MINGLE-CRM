<template>
  <div>
    <h2 class="text-h6">승인된 바우처 목록</h2>

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
                <span
                  v-if="
                    col.field === 'requestDate' || col.field === 'confirmDate'
                  "
                >
                  {{ toDate(props.row[col.field]) }}
                </span>
                <span v-else-if="col.field === 'sendOrCancel'">
                  <q-btn
                    label="발송"
                    color="primary"
                    @click.stop="
                      sendVoucher(
                        props.row.voucherId,
                        props.row.customerEmail,
                        props.row.voucherCode
                      )
                    "
                  ></q-btn>
                  <q-btn
                    label="취소"
                    color="secondary"
                    @click.stop="cancelVoucher(props.row.voucherId)"
                  ></q-btn>
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
  // { label: "선택 안 함", value: "" },
  { label: "BROWN", value: "BROWN" },
  { label: "SILVER", value: "SILVER" },
  { label: "GOLD", value: "GOLD" },
  { label: "DIAMOND", value: "DIAMOND" },
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
    name: "customerGrade",
    label: "고객 등급",
    align: "center",
    field: "customerGrade",
    sortable: true,
  },
  {
    name: "createdReason",
    label: "생성 사유",
    align: "center",
    field: "createdReason",
  },
  {
    name: "requestDate",
    label: "요청 날짜",
    align: "center",
    field: "requestDate",
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
    name: "confirmDate",
    label: "승인 일자",
    align: "center",
    field: "confirmDate",
    sortable: true,
  },
  {
    name: "voucherCode",
    label: "바우처 코드",
    align: "center",
    field: "voucherCode",
  },
  {
    name: "confirmerName",
    label: "검토 매니저 이름",
    align: "center",
    field: "confirmerName",
    sortable: true,
  },
  {
    name: "sendOrCancel",
    label: "발송 / 발급취소",
    align: "center",
    field: "sendOrCancel",
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
      `http://localhost:8080/api/v1/vouchers/approved-marketer`
    );
    vouchers.value = response.data.data.sort(
      (a, b) => b.voucherId - a.voucherId
    );
    errorMessage.value = "";
  } catch (error) {
    console.error("승인된 바우처 목록을 불러오는 중 에러 발생:", error);
    errorMessage.value =
      "승인된 바우처 목록을 불러오는 중 에러가 발생했습니다.";
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
      status: ["APPROVED"],
    };

    const response = await axios.post(
      "http://localhost:8080/api/v1/vouchers/search",
      data
    );
    vouchers.value = response.data.data.sort(
      (a, b) => b.voucherId - a.voucherId
    );
  } catch (error) {
    console.error("Error fetching vouchers:", error);
  }
};

const emits = defineEmits(["send-voucher"]);

const sendVoucher = (voucherId, customerEmail, voucherCode) => {
  emits("send-voucher", voucherId, customerEmail, voucherCode);
};

const cancelVoucher = async (voucherId) => {
  Dialog.create({
    title: "취소",
    message: "바우처 발급을 취소하시겠습니까?",
    ok: "확인",
    cancel: "취소",
  }).onOk(async () => {
    try {
      await axios.post(
        `http://localhost:8080/api/v1/vouchers/cancel/${voucherId}`
      );
      Notify.create({
        type: "positive",
        message: "바우처가 성공적으로 발급 취소되었습니다.",
      });
      // Refresh the voucher list after rejection
      fetchVouchers();
    } catch (error) {
      console.error("바우처 발급 취소 중 에러 발생:", error);
      Notify.create({
        type: "negative",
        message: "바우처 발급 취소 중 에러가 발생했습니다.",
      });
    }
  });
};

onMounted(() => {
  fetchVouchers();
});
</script>
