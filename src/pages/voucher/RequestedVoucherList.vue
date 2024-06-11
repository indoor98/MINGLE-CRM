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
          <template v-slot:body-cell-approve="props">
            <q-td :props="props">
              <q-btn
                v-if="!props.row.isAuth"
                label="승인"
                color="primary"
                @click="approveVoucher(props.row.voucherId)"
              ></q-btn>
              <span v-else>승인됨</span>
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
import axios from "axios";
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
  {
    name: "requestDate",
    label: "요청 날짜",
    align: "center",
    field: "requestDate",
    sortable: true,
  },
  // {
  //   name: "isAuth",
  //   label: "승인 여부",
  //   align: "center",
  //   field: "isAuth",
  //   sortable: true,
  // },
  {
    name: "issuerId",
    label: "발급 직원 ID",
    align: "center",
    field: "issuerId",
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
    name: "voucherCode",
    label: "바우처 코드",
    align: "center",
    field: "voucherCode",
  },
  { name: "approve", label: "승인", align: "center", sortable: true }, // 승인 컬럼 추가
]);

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
    message: "바우처 발급을 승인하시겠습니까?",
    ok: "예",
    cancel: "아니오",
  }).onOk(async () => {
    try {
      const response = await axios.post(
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

onMounted(() => {
  fetchVouchers();
});
</script>

<style scoped></style>
