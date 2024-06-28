<template>
  <q-card class="my-card">
    <q-card-section class="row justify-center q-mt-lg q-pa-xs">
      <div class="col-12 col-md-3 q-pa-sm">
        <q-input
          v-model="searchName"
          clearable
          filled
          color="purple-12"
          label="고객명"
          dense
          placeholder="고객명을 입력하세요"
        />
      </div>
      <div class="col-12 col-md-3 q-pa-sm">
        <q-input
          v-model="searchEmail"
          clearable
          filled
          color="purple-12"
          label="고객 이메일"
          dense
          placeholder="이메일을 입력하세요"
        />
      </div>

      <div class="col-12 col-md-3 q-pa-sm">
        <q-select
          v-model="selectedGrade"
          filled
          color="purple-12"
          label="등급"
          :options="gradeOptions"
          emit-value
          map-options
          dense
          placeholder="선택"
        />
      </div>

      <div class="col-12 col-md-3 q-pa-sm">
        <q-select
          v-model="selectedGender"
          filled
          color="purple-12"
          label="성별"
          :options="genderOptions"
          emit-value
          map-options
          dense
          placeholder="선택"
        />
      </div>
    </q-card-section>
    <q-card-section>
      <div class="row flex" style="justify-content: right">
        <q-btn
          color="primary"
          label="검색하기"
          @click="executeSearch"
          dense
          style="font-size: 20px"
        />
      </div>
    </q-card-section>
    <q-card-section class="row flex" style="justify-content: space-between">
      <div class="text-h4">고객 목록</div>
      <q-btn label="선택 완료" color="primary" @click="createEmailList"
    /></q-card-section>
    <q-card-section class="q-pa-none">
      <div class="q-table-container">
        <q-table
          flat
          bordered
          :rows="customers"
          :columns="columns"
          row-key="id"
          v-model:pagination="pagination"
          v-model:selected="selected"
          @request="onRequest"
          hide-pagination
          selection="multiple"
        >
        </q-table>
      </div>
    </q-card-section>

    <div class="q-pa-lg flex flex-center">
      <q-pagination
        v-model="pagination.page"
        :max="maxPage"
        @update:model-value="onPageChange"
        input
        input-class="text-orange-10"
      />
    </div>
  </q-card>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import { api as axios } from "src/boot/axios";
import { list } from "postcss";

const selected = ref([]);
const searchName = ref("");
const customers = ref([]);
const searchEmail = ref("");
const pagination = ref({
  page: 1,
  rowsPerPage: 10,
  rowsNumber: 0,
});
const selectedGrade = ref(null);
const selectedGender = ref(null);

const emit = defineEmits(["selected-emails"]);

const createEmailList = () => {
  const emails = [];
  selected.value.forEach((val) => {
    console.log(val.email);
    emails.push(val.email);
  });
  emit("selected-emails", emails);
};

const fetchCustomers = async (page = 1) => {
  try {
    const params = {
      page: page - 1,
      size: pagination.value.rowsPerPage,
      name: searchName.value || null,
      email: searchEmail.value || null,
      grade: selectedGrade.value || null,
      gender: selectedGender.value || null,
    };

    const response = await axios.get("/api/v1/customers/search", { params });
    customers.value = response.data.content;
    pagination.value.page = response.data.number + 1;
    pagination.value.rowsPerPage = response.data.size;
    pagination.value.rowsNumber = response.data.totalElements;
  } catch (error) {
    console.error("Error fetching customers:", error);
  }
};

const maxPage = computed(() =>
  Math.ceil(pagination.value.rowsNumber / pagination.value.rowsPerPage)
);

onMounted(() => {
  fetchCustomers();
});

const executeSearch = () => {
  pagination.value.page = 1; // 검색을 다시 시작할 때 페이지를 1로 초기화
  fetchCustomers();
};

const onRequest = (params) => {
  const { page, rowsPerPage } = params.pagination;
  pagination.value.page = page;
  pagination.value.rowsPerPage = rowsPerPage;
  fetchCustomers(page);
};

const onPageChange = (page) => {
  pagination.value.page = page;
  fetchCustomers(page);
};

const columns = [
  {
    name: "name",
    label: "고객 이름",
    align: "left",
    field: "name",
    sortable: true,
  },
  {
    name: "email",
    label: "고객 이메일",
    align: "left",
    field: "email",
    sortable: true,
  },
  {
    name: "grade",
    label: "등급",
    align: "center",
    field: "grade",
    sortable: true,
  },
  {
    name: "phone",
    label: "전화번호",
    align: "center",
    field: "phone",
    sortable: true,
  },
  {
    name: "employeeName",
    label: "담당 직원 이름",
    align: "center",
    field: "employeeName",
  },
  { name: "gender", label: "성별", align: "center", field: "gender" },
  { name: "birth", label: "생년월일", align: "center", field: "birth" },
];

const gradeOptions = [
  { label: "선택 안 함", value: "" },
  { label: "NEW", value: "NEW" },
  { label: "BASIC", value: "BASIC" },
  { label: "VIP", value: "VIP" },
  { label: "VVIP", value: "VVIP" },
];

const genderOptions = [
  { label: "선택 안 함", value: "" },
  { label: "여성", value: "Female" },
  { label: "남성", value: "Male" },
];
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  min-height: 100vh;
}

.my-card {
  width: 100%;
  max-width: 900px;
  margin: 16px auto; /* 위 아래 카드 간격 설정 */
}

.q-table-container {
  max-width: 100%;
  overflow-x: auto; /* 가로 스크롤바 설정 */
}

.full-width {
  width: 100%;
}

.q-mt-md {
  margin-top: 16px;
}

.back-to-top-btn {
  position: fixed;
  bottom: 30px;
  right: 60px; /* 버튼을 왼쪽으로 이동 */
  transform: translateX(-70%); /* 정확한 가운데 정렬 */
  width: 9px; /* 버튼 크기 조정 */
  height: 9px; /* 버튼 크기 조정 */
}

.q-pa-lg {
  padding: 16px;
}

.flex-center {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
