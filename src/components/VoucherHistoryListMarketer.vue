<template>
  <div>
    <h2 class="text-h6">{{ title }}</h2>
    <q-card class="q-mt-md">
      <q-card-section>
        <q-table
          :rows="vouchers"
          :columns="columns"
          row-key="id"
          :loading="loading"
          :pagination="{ rowsPerPage: 10 }"
        >
          <template v-slot:body-cell-createdReason="props">
            <q-td :props="props">
              {{ toTenWords(props.row.createdReason) }}
            </q-td>
          </template>
          <template v-slot:body-cell-requestDate="props">
            <q-td :props="props">
              {{ toDate(props.row.requestDate) }}
            </q-td>
          </template>
          <template v-slot:body-cell-confirmDate="props">
            <q-td :props="props">
              {{ toDate(props.row.confirmDate) }}
            </q-td>
          </template>
          <template v-slot:body-cell-sendOrCancel="props">
            <q-td :props="props">
              <q-btn
                label="발송"
                color="primary"
                @click="
                  sendVoucher(props.row.customerEmail, props.row.voucherCode)
                "
              ></q-btn>
              <q-btn
                label="취소"
                color="secondary"
                @click="cancelVoucher(props.row.voucherId)"
              ></q-btn>
            </q-td>
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
  </div>
</template>

<script setup>
import { ref, onMounted, defineEmits, watch } from "vue";
import { api as axios } from "src/boot/axios";
import { Notify, Dialog } from "quasar";

const props = defineProps({
  selected: {
    type: String,
    required: true,
  },
});

const emits = defineEmits(["send-voucher"]);

const vouchers = ref([]);
const errorMessage = ref("");
const loading = ref(true);
const title = ref("");

const columns = ref([]);

const defaultColumns = [
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
    name: "confirmerName",
    label: "검토 매니저 이름",
    align: "center",
    field: "confirmerName",
    sortable: true,
  },
];

const approvedColumns = [
  ...defaultColumns,
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
    name: "sendOrCancel",
    label: "발송 / 발급취소",
    align: "center",
    field: "sendOrCancel",
  },
];

const rejectedColumns = [
  ...defaultColumns,
  {
    name: "confirmDate",
    label: "거절 일자",
    align: "center",
    field: "confirmDate",
    sortable: true,
  },
  {
    name: "rejectedReason",
    label: "거절 사유",
    align: "center",
    field: "rejectedReason",
  },
];

const toTenWords = (beforeWord) => {
  const afterword =
    beforeWord.length <= 10 ? beforeWord : beforeWord.substring(0, 10) + "...";
  return afterword;
};

const toDate = (beforeDate) => {
  return (
    beforeDate.substring(0, 4) +
    "-" +
    beforeDate.substring(5, 7) +
    "-" +
    beforeDate.substring(8, 10)
  );
};

const updateColumns = (selected) => {
  switch (selected) {
    case "approved-marketer":
      title.value = "승인된 바우처 목록";
      columns.value = approvedColumns;
      break;
    case "rejected-marketer":
      title.value = "승인 거절된 바우처 목록";
      columns.value = rejectedColumns;
      break;
  }
};

const fetchVouchers = async () => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/v1/vouchers/${props.selected}`
    );
    vouchers.value = response.data.data;
    console.log(vouchers.value);
    errorMessage.value = "";
  } catch (error) {
    console.error("승인 요청한 바우처 목록을 불러오는 중 에러 발생:", error);
    errorMessage.value =
      "승인 요청한 바우처 목록을 불러오는 중 에러가 발생했습니다.";
  } finally {
    loading.value = false;
  }
};

const sendVoucher = (customerEmail, voucherCode) => {
  emits("send-voucher", customerEmail, voucherCode);
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
  updateColumns(props.selected);
  fetchVouchers();
});

watch(
  () => props.selected,
  (newSelected) => {
    if (newSelected) {
      loading.value = true;
      updateColumns(newSelected);
      fetchVouchers();
    }
  }
);
</script>

<style scoped></style>
