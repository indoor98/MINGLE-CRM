<template>
  <div>
    <div class="flex justify-between items-center">
      <h2 class="text-h6">바우처 목록</h2>
      <q-btn color="primary" label="+ 바우처 생성" @click="showModal = true" />
    </div>
    <q-card class="q-mt-md">
      <q-card-section>
        <q-table
          :rows="vouchers"
          :columns="columns"
          row-key="id"
          :loading="loading"
          :pagination="{ rowsPerPage: 10 }"
        >
          <template v-slot:no-data>
            <q-tr>
              <q-td :colspan="columns.length" class="text-center"
                >바우처가 없습니다.</q-td
              >
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

    <!-- Modal -->
    <q-dialog v-model="showModal">
      <q-card>
        <q-card-section>
          <div class="text-h6">바우처 생성</div>
        </q-card-section>

        <q-card-section>
          <q-input v-model="voucher.customerId" label="회원 ID" />
          <q-input v-model="voucher.amount" label="금액" type="number" />
        </q-card-section>

        <q-card-actions align="right">
          <q-btn flat label="취소" color="primary" @click="showModal = false" />
          <q-btn flat label="생성" color="primary" @click="createVoucher" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const vouchers = ref([]);
const errorMessage = ref("");
const loading = ref(true);
const showModal = ref(false); // 모달 창 표시 여부
const voucher = ref({
  customerId: "",
  amount: 0,
});

const columns = ref([
  {
    name: "customerId",
    label: "회원 ID",
    align: "center",
    field: "customerId",
  },
  {
    name: "customerName",
    label: "회원명",
    align: "center",
    field: "customerName",
  },
  {
    name: "employeeId",
    label: "발급 직원 ID",
    align: "center",
    field: "employeeId",
  },
  {
    name: "employeeName",
    label: "발급 직원명",
    align: "center",
    field: "employeeName",
  },
  {
    name: "amount",
    label: "금액",
    align: "center",
    field: "amount",
  },
  {
    name: "voucherCode",
    label: "바우처 코드",
    align: "center",
    field: "voucherCode",
  },
]);

const fetchVouchers = async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/v1/vouchers");
    vouchers.value = response.data.data;
    errorMessage.value = "";
  } catch (error) {
    console.error("바우처 목록을 불러오는 중 에러 발생:", error);
    errorMessage.value = "바우처 목록을 불러오는 중 에러가 발생했습니다.";
  } finally {
    loading.value = false;
  }
};

const createVoucher = async () => {
  try {
    const response = await axios.post("http://localhost:8080/api/v1/vouchers", {
      customerId: voucher.value.customerId,
      amount: voucher.value.amount,
    });
    // 새로운 바우처 목록을 다시 불러옴
    fetchVouchers();
    showModal.value = false;
  } catch (error) {
    console.error("바우처 생성 중 에러 발생:", error);
    errorMessage.value = "바우처 생성 중 에러가 발생했습니다.";
  }
};

onMounted(() => {
  fetchVouchers();
});
</script>
<style scoped>
.flex {
  display: flex;
}
.justify-between {
  justify-content: space-between;
}
.items-center {
  align-items: center;
}
</style>
