<template>
  <div class="q-pa-md">
    <q-table
      flat
      bordered
      title="발송 내역"
      :rows="rows"
      :columns="columns"
      row-key="name"
      v-model:pagination="pagination"
      hide-pagination
    />

    <div class="row justify-center q-mt-md">
      <q-pagination
        v-model="pagination.page"
        color="grey-8"
        :max="pagesNumber"
        size="sm"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import axios from "axios";

const pagination = ref({
  sortBy: "desc",
  descending: false,
  page: 0,
  rowsPerPage: 3,
  // rowsNumber: xx if getting data from a server
});

const columns = [
  {
    name: "desc",
    required: true,
    label: "이벤트 ID",
    align: "left",
    field: (row) => row.name,
    format: (val) => `${val}`,
    sortable: true,
  },
  {
    name: "calories",
    align: "center",
    label: "직원 ID",
    field: "calories",
    sortable: true,
  },
  { name: "fat", label: "직원 이름", field: "fat", sortable: true },
  { name: "carbs", label: "이메일 제목", field: "carbs" },
  { name: "protein", label: "이메일 내용", field: "protein" },
  { name: "protein", label: "발송 시간", field: "protein" },
  { name: "protein", label: "발신자 수", field: "protein" },
  { name: "protein", label: "열람 수", field: "protein" },
];

const getEventByPage = async (pageNo) => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/events/${pagination.value.page}`,
      {
        withCredentials: true,
      }
    );
    return console.log(response.data.data);
  } catch (error) {
    console.log(error);
  }
};
const events = ref("");

events.value = getEventByPage(pagination.value.page);
console.log(events);

const rows = [
  {
    name: "Frozen Yogurt",
    calories: 159,
    fat: 6.0,
    carbs: 24,
    protein: 4.0,
  },
  {
    name: "Ice cream sandwich",
    calories: 237,
    fat: 9.0,
    carbs: 37,
    protein: 4.3,
  },
  {
    name: "Eclair",
    calories: 262,
    fat: 16.0,
    carbs: 23,
    protein: 6.0,
  },
  {
    name: "Cupcake",
    calories: 305,
    fat: 3.7,
    carbs: 67,
    protein: 4.3,
  },
  {
    name: "Gingerbread",
    calories: 356,
    fat: 16.0,
    carbs: 49,
    protein: 3.9,
  },
  {
    name: "Jelly bean",
    calories: 375,
    fat: 0.0,
    carbs: 94,
    protein: 0.0,
  },
  {
    name: "Lollipop",
    calories: 392,
    fat: 0.2,
    carbs: 98,
    protein: 0,
  },
  {
    name: "Honeycomb",
    calories: 408,
    fat: 3.2,
    carbs: 87,
    protein: 6.5,
  },
  {
    name: "Donut",
    calories: 452,
    fat: 25.0,
    carbs: 51,
    protein: 4.9,
  },
  {
    name: "KitKat",
    calories: 518,
    fat: 26.0,
    carbs: 65,
    protein: 7,
  },
];

const pagesNumber = computed(() =>
  Math.ceil(rows.length / pagination.value.rowsPerPage)
);

// onMounted(() => {
//   rows = getEventByPage(pagenation.page);
// });
</script>
