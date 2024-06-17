<template>
  <div>
    <q-table
      :rows="events"
      :columns="columns"
      row-key="id"
      :loading="loading"
      v-model:pagination="pagination"
      hide-pagination
    >
      <template v-slot:body="props">
        <q-tr :props="props" @click="rowClicked(props.row)">
          <q-td v-for="col in columns" :key="col.name" :props="props">
            {{ props.row[col.field] }}
          </q-td>
        </q-tr>
      </template>
      <template v-slot:no-data>
        <q-tr>
          <q-td :colspan="columns.length" class="text-center"
            >로그가 없습니다.</q-td
          >
        </q-tr>
      </template>
    </q-table>
    <div class="row justify-center q-mt-md">
      <q-pagination
        v-model="pagination.page"
        color="grey-8"
        :max="pagesNumber"
        :max-pages="6"
        :boundary-numbers="false"
        size="sm"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, defineEmits, computed } from "vue";
import axios from "axios";

const events = ref([]);
const errorMessage = ref("");
const loading = ref(true);
const pagesNumber = ref(0);
const pagination = ref({
  sortBy: "desc",
  descending: false,
  page: 1,
  rowsPerPage: 50,
});

const columns = ref([
  {
    name: "eventId",
    label: "이벤트 ID",
    align: "center",
    field: "eventId",
    sortable: true,
  },
  {
    name: "employeeId",
    label: "담당자 ID",
    align: "center",
    field: "employeeId",
    sortable: true,
  },
  {
    name: "employeeName",
    label: "담당자 명",
    align: "center",
    field: "employeeName",
    sortable: true,
  },
  {
    name: "emailTitle",
    label: "이메일 제목",
    align: "center",
    field: "emailTitle",
    sortable: true,
  },
  {
    name: "emailContent",
    label: "이메일 내용",
    align: "center",
    field: "emailContent",
    sortable: true,
  },
  {
    name: "sentDate",
    label: "발송 시간",
    align: "center",
    field: "sentDate",
    sortable: true,
  },
  {
    name: "sendCount",
    label: "총 수신자 수",
    align: "center",
    field: "sendCount",
    sortable: true,
  },
  {
    name: "readCount",
    label: "열람자 수",
    align: "center",
    field: "readCount",
    sortable: true,
  },
]);

const fetchRewards = async () => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/events/${pagination.value.page - 1}`
    );
    events.value = response.data.data;

    const pagesNumberResponse = await axios.get(
      "http://localhost:8080/api/event/pagesnumber"
    );
    pagesNumber.value = Math.ceil(
      pagesNumberResponse.data.data / pagination.value.rowsPerPage
    );
    console.log("event 불러오기 : " + response.data.data);
    errorMessage.value = "";
  } catch (error) {
    console.log("이벤트 목록 불러오기 실패 " + error);
    errorMessage.value = "이벤트 목록을 불러오는 중 실패하였습니다";
  } finally {
    loading.value = false;
  }
};

const emit = defineEmits(["row-click"]);
const rowClicked = (row) => {
  console.log("Clicked Row: " + row.eventId);
  emit("row-click", row.eventId);
};

onMounted(() => {
  fetchRewards();
});
</script>
