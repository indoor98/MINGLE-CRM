<template>
  <div class="q-pa-md">
    <q-table
      flat
      bordered
      title="발송 내역"
      :rows="rows"
      :columns="columns"
      row-key="eventId"
      :pagination="{ page: currentPage, rowsPerPage: rowsPerPage }"
      hide-pagination
      @row-click="onRowClick"
    />

    <div class="row justify-center q-mt-md">
      <q-pagination
        v-model="currentPage"
        color="grey-8"
        :max="pagesNumber"
        size="sm"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const currentPage = ref(0);
const rows = ref([]);
const rowsPerPage = 3;
const pagesNumber = ref(5);

const router = useRouter();

const columns = [
  {
    name: "desc",
    required: true,
    label: "이벤트 ID",
    align: "center",
    field: (row) => `${row.eventId}`,
    format: (val) => `${val}`,
    sortable: true,
    style: "max-width: 10px;",
  },
  {
    name: "employeeId",
    align: "center",
    label: "직원 ID",
    field: "employeeId",
    sortable: true,
  },
  {
    name: "employeeName",
    label: "직원 이름",
    field: "employeeName",
    align: "center",
    sortable: true,
  },
  {
    name: "eventTitle",
    label: "이메일 제목",
    align: "center",
    field: "emailTitle",
  },
  {
    name: "eventContent",
    label: "이메일 내용",
    align: "center",
    field: "emailContent",
  },
  {
    name: "sentDate",
    label: "발송 시간",
    align: "center",
    field: (row) => `${row.sentDate}`,
    format: (val) => `${val.slice(0, 10) + " " + val.slice(11, 16)}`,
  },
  {
    name: "sendCount",
    label: "발신자 수",
    align: "center",
    field: "sendCount",
  },
  { name: "readCount", label: "열람 수", align: "center", field: "readCount" },
];

const onRowClick = (event, row) => {
  console.log(event, row);
  router.push({ name: "emailDetailPage", params: { eventId: row.eventId } });
};

const getEventByPage = async (pageNo) => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/events/${currentPage.value}`,
      {
        withCredentials: true,
      }
    );
    rows.value = response.data.data;
    pagesNumber.value = Math.ceil(response.data.total / rowsPerPage);
  } catch (error) {
    console.log(error);
  }
};

// Fetch data when currentPage changes
watch(currentPage, (newPage) => {
  getEventByPage(newPage);
});

// Initial data fetch
onMounted(() => {
  getEventByPage(currentPage.value);
});
</script>
