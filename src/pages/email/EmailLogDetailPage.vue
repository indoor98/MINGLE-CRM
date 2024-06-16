<template>
  <div>
    <!-- <div class="q-pa-md row items-start q-gutter-md">
      <q-card class="my-card">
        <q-card-section>
          안녕하세요
        </q-card-section>
      </q-card>
    </div> -->

    <q-btn flat icon="arrow_back" @click="goBack"> 뒤로</q-btn>
    <q-table
      :rows="emailLogs"
      :columns="columns"
      row-key="id"
      :loading="loading"
      :pagination="pagination"
      hide-pagination
    >
      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td v-for="col in columns" :key="col.name" :props="props">
            {{ props.row[col.field] }}
          </q-td>
        </q-tr>
      </template>
      <template v-slot:no-data>
        <q-tr>
          <q-td :colspan="columns.length" class="text-center"
            >이메일 로그가 없습니다.</q-td
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
import { ref, defineEmits, onMounted, watch } from "vue";
import axios from "axios";

const pagesNumber = ref(0);
const pagination = ref({
  sortBy: "desc",
  descending: false,
  page: 1,
  rowsPerPage: 50,
});

const props = defineProps({
  eventId: {
    type: Number,
    required: true,
  },
});

const emit = defineEmits(["back"]);

const goBack = () => {
  emit("back");
};

const emailLogs = ref([]);
const errorMessage = ref("");
const loading = ref(true);
const currentPage = ref(1);
const columns = ref([
  {
    name: "customerId",
    label: "고객 ID",
    align: "center",
    field: "customerId",
  },
  {
    name: "customerName",
    label: "고객 이름",
    align: "center",
    field: "customerName",
  },
  {
    name: "isOpened",
    label: "열람 여부",
    align: "center",
    field: "isOpened",
  },
  {
    name: "openedDate",
    label: "열람 시간",
    align: "center",
    field: "openedDate",
  },
]);

const fetchEmailLogs = async (eventId) => {
  try {
    console.log("page : ", pagination.value.page);

    const response = await axios.get(
      `http://localhost:8080/api/emaillog/${eventId}/${
        pagination.value.page - 1
      }`
    );
    emailLogs.value = response.data.data;

    const pagesNumberResponse = await axios.get(
      `http://localhost:8080/api/emaillog/pagesnumber/${eventId}`
    );

    pagesNumber.value = Math.ceil(
      pagesNumberResponse.data.data / pagination.value.rowsPerPage
    );
  } catch (error) {
    console.log(error);
  } finally {
    loading.value = false;
  }
};

watch(
  () => pagination.value.page,
  (newPage) => {
    fetchEmailLogs(props.eventId);
  }
);

onMounted(() => {
  fetchEmailLogs(props.eventId);
});
</script>
<style lang="scss"></style>
