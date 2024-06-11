<template>
  <div>
    <h2 class="text-h6">리워드 목록</h2>
    <q-card class="q-mt-md">
      <q-card-section>
        <q-table
          :rows="rewards"
          :columns="columns"
          row-key="id"
          :loading="loading"
          :dense="true"
          class="q-table--dense"
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
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const rewards = ref([]);
const errorMessage = ref("");
const loading = ref(true);
const columns = ref([
  {
    name: "customerId",
    label: "회원 ID",
    align: "center",
    field: "customerId",
  },
  {
    name: "amount",
    label: "잔여 리워드",
    align: "center",
    field: "amount",
  },
]);

const fetchVouchers = async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/v1/rewards");
    rewards.value = response.data.data;
    errorMessage.value = "";
  } catch (error) {
    console.error("바우처 목록을 불러오는 중 에러 발생:", error);
    errorMessage.value = "바우처 목록을 불러오는 중 에러가 발생했습니다.";
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchVouchers();
});
</script>

<style scoped></style>
