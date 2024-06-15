<template>
  <div>
    <h2 class="text-h6">리워드 히스토리 목록</h2>
    <q-card class="q-mt-md">
      <q-card-section>
        <q-table
          :rows="rewards"
          :columns="columns"
          row-key="id"
          :loading="loading"
          :pagination="{ rowsPerPage: 10 }"
        >
          <template v-slot:body="props">
            <q-tr :props="props" @click="rowClicked(props.row)">
              <q-td v-for="col in columns" :key="col.name" :props="props">
                <template v-if="col.name === 'date'">
                  {{ toDate(props.row[col.field]) }}
                </template>
                <template v-else>
                  {{ props.row[col.field] }}
                </template>
              </q-td>
            </q-tr>
          </template>
          <template v-slot:no-data>
            <q-tr>
              <q-td :colspan="columns.length" class="text-center">
                리워드 히스토리가 없습니다.
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
import { ref, onMounted, defineEmits } from "vue";
import axios from "axios";

const rewards = ref([]);
const errorMessage = ref("");
const loading = ref(true);
const columns = ref([
  {
    name: "rewardHistoryId",
    label: "히스토리 ID",
    align: "center",
    field: "rewardHistoryId",
    sortable: true,
  },
  {
    name: "customerId",
    label: "회원 ID",
    align: "center",
    field: "customerId",
    sortable: true,
  },
  {
    name: "customerName",
    label: "회원 이름",
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
    name: "type",
    label: "지급/사용/전환",
    align: "center",
    field: "type",
    sortable: true,
  },
  {
    name: "reason",
    label: "사유",
    align: "center",
    field: "reason",
  },
  {
    name: "date",
    label: "날짜",
    align: "center",
    field: "date",
  },
  {
    name: "paymentId",
    label: "결제 id",
    align: "center",
    field: "paymentId",
  },
]);

const toDate = (beforeDate) => {
  return (
    beforeDate.substring(0, 4) +
    "-" +
    beforeDate.substring(5, 7) +
    "-" +
    beforeDate.substring(8, 10)
  );
};

const fetchRewards = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8080/api/v1/rewards/histories"
    );
    rewards.value = response.data.data;
    errorMessage.value = "";
  } catch (error) {
    console.error("리워드 히스토리 목록을 불러오는 중 에러 발생:", error);
    errorMessage.value =
      "리워드 히스토리 목록을 불러오는 중 에러가 발생했습니다.";
  } finally {
    loading.value = false;
  }
};

const emit = defineEmits(["row-click"]);
const rowClicked = (row) => {
  console.log("Clicked Row: " + row.customerId);
  emit("row-click", row.customerId);
};

onMounted(() => {
  fetchRewards();
});
</script>

<style scoped></style>
