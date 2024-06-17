<template>
  <div>
    <h2 class="text-h6">승인 요청된 바우처 목록</h2>
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
          <template v-slot:body-cell-approve="props">
            <q-td :props="props">
              <q-btn
                label="승인"
                color="primary"
                @click="approveVoucher(props.row.voucherId)"
              ></q-btn>
              <q-btn
                label="거절"
                color="secondary"
                @click="rejectVoucher(props.row.voucherId)"
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
import { ref, onMounted } from "vue";
import { api as axios } from "src/boot/axios";
import { Notify, Dialog } from "quasar";

const vouchers = ref([]);
const errorMessage = ref("");
const loading = ref(true);

const columns = ref([
  {
    name: "voucherId",
    label: "바우처 ID",
    align: "center",
    field: "voucherId",
    sortable: true,
  },
  // {
  //   name: "customerId",
  //   label: "고객 ID",
  //   align: "center",
  //   field: "customerId",
  //   sortable: true,
  // },
  {
    name: "customerName",
    label: "고객 이름",
    align: "center",
    field: "customerName",
    sortable: true,
  },
  {
    name: "creatorName",
    label: "발급 직원 이름",
    align: "center",
    field: "creatorName",
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
  { name: "approve", label: "승인/거절", align: "center" },
]);

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

const fetchVouchers = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8080/api/v1/vouchers/requested"
    );
    vouchers.value = response.data.data;
    console.log(vouchers.value);
    errorMessage.value = "";
  } catch (error) {
    console.error("승인 요청된 바우처 목록을 불러오는 중 에러 발생:", error);
    errorMessage.value =
      "승인 요청된 바우처 목록을 불러오는 중 에러가 발생했습니다.";
  } finally {
    loading.value = false;
  }
};

const approveVoucher = async (voucherId) => {
  Dialog.create({
    title: "확인",
    message: `${voucherId}번 바우처 발급을 승인하시겠습니까?`,
    ok: "예",
    cancel: "아니오",
  }).onOk(async () => {
    try {
      await axios.post(
        `http://localhost:8080/api/v1/vouchers/approval/${voucherId}`
      );
      Notify.create({
        type: "positive",
        message: "바우처가 성공적으로 승인되었습니다.",
      });
      // Refresh the voucher list after approval
      fetchVouchers();
    } catch (error) {
      console.error("바우처 승인 중 에러 발생:", error);
      Notify.create({
        type: "negative",
        message: "바우처 승인 중 에러가 발생했습니다.",
      });
    }
  });
};

const rejectVoucher = async (voucherId) => {
  Dialog.create({
    title: "거절 사유 입력",
    message: "거절 사유를 입력해주세요:",
    prompt: {
      model: "",
      type: "textarea",
    },
    ok: "확인",
    cancel: "취소",
  }).onOk(async (reason) => {
    try {
      await axios.post(
        `http://localhost:8080/api/v1/vouchers/rejection/${voucherId}`,
        { reason }
      );
      Notify.create({
        type: "positive",
        message: "바우처가 성공적으로 거절되었습니다.",
      });
      // Refresh the voucher list after rejection
      fetchVouchers();
    } catch (error) {
      console.error("바우처 거절 중 에러 발생:", error);
      Notify.create({
        type: "negative",
        message: "바우처 거절 중 에러가 발생했습니다.",
      });
    }
  });
};

onMounted(() => {
  fetchVouchers();
});
</script>

<style scoped></style>
