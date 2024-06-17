<template>
  <div>
    <q-btn flat icon="arrow_back" @click="goBack"> 뒤로</q-btn>
    <h2 class="text-h6">{{ customerName }}님의 리워드 히스토리</h2>
    <q-card class="q-mt-md">
      <q-card-section>
        <q-table
          :rows="rewardHistories"
          :columns="columns"
          row-key="id"
          :loading="loading"
          :pagination="{ rowsPerPage: 10 }"
        >
          <template v-slot:no-data>
            <q-tr>
              <q-td :colspan="columns.length" class="text-center"
                >리워드 히스토리가 없습니다.</q-td
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
import { ref, onMounted, watch, defineProps, defineEmits } from "vue";
import axios from "axios";

const props = defineProps({
  customerName: {
    type: String,
    required: true,
  },
  customerId: {
    type: Number,
    required: true,
  },
});

const emit = defineEmits(["back"]);

const rewardHistories = ref([]);
const errorMessage = ref("");
const loading = ref(true);
const columns = ref([
  {
    name: "rewardHistoryId",
    label: "히스토리 ID",
    align: "center",
    field: "rewardHistoryId",
  },
  {
    name: "amount",
    label: "금액",
    align: "center",
    field: "amount",
  },
  {
    name: "type",
    label: "적립/사용 상태",
    align: "center",
    field: "type",
  },
  {
    name: "reason",
    label: "지급 사유",
    align: "center",
    field: "reason",
  },
  {
    name: "date",
    label: "적립/사용 날짜",
    align: "center",
    field: "date",
  },
  {
    name: "paymentId",
    label: "결제 ID",
    align: "center",
    field: "paymentId",
  },
  {
    name: "voucherId",
    label: "전환 바우처 ID",
    align: "center",
    field: "voucherId",
  },
]);

const fetchRewardHistories = async (customerId) => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/v1/rewards/history/${customerId}`
    );
    console.log(customerId);
    console.log(response);
    rewardHistories.value = response.data.data;
    errorMessage.value = "";
  } catch (error) {
    console.error("리워드 히스토리를 불러오는 중 에러 발생:", error);
    errorMessage.value = "리워드 히스토리를 불러오는 중 에러가 발생했습니다.";
  } finally {
    loading.value = false;
  }
};

const goBack = () => {
  emit("back");
};

watch(
  () => props.customerId,
  (newCustomerId) => {
    fetchRewardHistories(newCustomerId);
  }
);

onMounted(() => {
  fetchRewardHistories(props.customerId);
});
</script>

<style scoped></style>
