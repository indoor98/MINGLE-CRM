<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <!-- 리워드 정보 표시 -->
    <q-card class="q-mt-md">
      <q-card-section class="q-pa-md">
        <div class="q-gutter-md">
          <strong>
            <span>잔여 리워드 금액:</span>
            {{ formatAmount(rewardInfo.amount) }}
          </strong>
        </div>
      </q-card-section>
      <q-separator />
      <q-card-section class="q-pa-md">
        <q-table
          :rows="rewardHistoryInfo"
          :columns="historyColumns"
          row-key="id"
          flat
          bordered
        />
      </q-card-section>
    </q-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { api as axios } from "src/boot/axios";
import { useRoute } from "vue-router";

const route = useRoute();
const customerId = route.params.id;
const rewardInfo = ref({
  customerId: null,
  amount: null,
});

const rewardHistoryInfo = ref([]);

const historyColumns = [
  {
    name: "amount",
    label: "금액",
    align: "left",
    field: (row) => formatAmount(row.amount),
  },
  {
    name: "type",
    label: "지급/사용",
    align: "left",
    field: (row) => row.type,
  },
  {
    name: "reason",
    label: "사유",
    align: "left",
    field: (row) => row.reason,
  },
  {
    name: "date",
    label: "일시",
    align: "left",
    field: (row) => formatDate(row.date),
  },
];

const fetchRewardInfo = async () => {
  try {
    const response = await axios.get(`/api/v1/customers/${customerId}/rewards`);
    rewardInfo.value.customerId = response.data.data.customerId;
    rewardInfo.value.amount = response.data.data.amount;
  } catch (error) {
    console.error("Error fetching reward info:", error);
  }
};

const fetchRewardHistoryInfo = async () => {
  try {
    const response = await axios.get(`/api/v1/rewards/history/${customerId}`);
    rewardHistoryInfo.value = response.data.data;
  } catch (error) {
    console.error("Error fetching reward history info:", error);
  }
};

// Function to format amount with thousand separators
function formatAmount(amount) {
  if (typeof amount !== "number") {
    return amount;
  }
  return amount.toLocaleString("en-US"); // Example: Formats with comma separators (,)
}

// Function to format date
function formatDate(date) {
  const options = {
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  };
  return new Date(date).toLocaleDateString("ko-KR", options);
}

onMounted(() => {
  fetchRewardInfo();
  fetchRewardHistoryInfo();
});
</script>

<style scoped>
.q-mt-md {
  margin-top: 16px;
}

.q-pa-md {
  padding: 16px;
}

.q-my-md {
  margin: 16px 0;
}

.q-gutter-md {
  display: flex;
  flex-direction: column;
  gap: 16px;
}
</style>
