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
          <template v-slot:body-cell-rejectedReason="props">
            <q-td :props="props">
              {{ toTenWords(props.row.rejectedReason) }}
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
import { ref, onMounted, defineProps, watch } from "vue";
import { api as axios } from "src/boot/axios";

const vouchers = ref([]);
const errorMessage = ref("");
const loading = ref(true);
const title = ref("");

const props = defineProps({
  selected: {
    type: String,
    required: true,
  },
});

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
    name: "confirmerName",
    label: "검토 매니저 이름",
    align: "center",
    field: "confirmerName",
    sortable: true,
  },
  {
    name: "voucherCode",
    label: "바우처 코드",
    align: "center",
    field: "voucherCode",
  },
  {
    name: "status",
    label: "상태",
    align: "center",
    field: "status",
    sortable: true,
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
    name: "confirmerName",
    label: "검토 매니저 이름",
    align: "center",
    field: "confirmerName",
    sortable: true,
  },
  {
    name: "rejectedReason",
    label: "거절 사유",
    align: "center",
    field: "rejectedReason",
  },
];

const allColumns = [
  ...defaultColumns,
  {
    name: "status",
    label: "상태",
    align: "center",
    field: "status",
    sortable: true,
  },
];

const toDate = (beforeDate) => {
  return (
    beforeDate.substring(0, 4) +
    "-" +
    beforeDate.substring(5, 7) +
    "-" +
    beforeDate.substring(8, 10)
  );
};

const toTenWords = (beforeWord) => {
  const afterWord =
    beforeWord.length <= 10 ? beforeWord : beforeWord.substring(0, 10) + "...";
  return afterWord;
};

const updateColumns = (selected) => {
  switch (selected) {
    case "approved":
      title.value = "승인된 바우처 목록";
      columns.value = approvedColumns;
      break;
    case "rejected":
      title.value = "승인 거절된 바우처 목록";
      columns.value = rejectedColumns;
      break;
    case "histories":
      title.value = "모든 바우처 히스토리 목록";
      columns.value = allColumns;
      break;
  }
};

const fetchVouchers = async () => {
  if (!props.selected) {
    errorMessage.value = "유효한 바우처 ID를 선택해주세요.";
    loading.value = false;
    return;
  }

  try {
    const response = await axios.get(
      `http://localhost:8080/api/v1/vouchers/${props.selected}`
    );
    vouchers.value = response.data.data;
    errorMessage.value = "";
  } catch (error) {
    console.error("승인된 바우처 목록을 불러오는 중 에러 발생:", error);
    errorMessage.value =
      "승인된 바우처 목록을 불러오는 중 에러가 발생했습니다.";
  } finally {
    loading.value = false;
  }
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
