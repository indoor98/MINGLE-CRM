<template>
  <q-page class="container">
    <q-card class="my-card">
      <q-card-section class="row justify-center q-pa-xs">
        <div class="col-12 col-md-4 q-pa-sm">
          <q-input
            v-model="searchName"
            clearable
            filled
            color="purple-12"
            label="이름"
            dense
            placeholder="이름을 입력하세요"
          />
        </div>

        <div class="col-12 col-md-3 q-pa-sm">
          <q-select
            v-model="selectedRole"
            filled
            color="purple-12"
            label="역할"
            :options="roleOptions"
            emit-value
            map-options
            dense
            placeholder="선택"
          />
        </div>

        <div class="col-12 col-md-2 q-pa-sm">
          <q-btn
            color="primary"
            label="검색"
            @click="executeSearch"
            dense
            class="full-width"
          />
        </div>
      </q-card-section>
    </q-card>

    <q-card class="q-mt-md my-card">
      <q-card-section class="q-pa-none">
        <div class="q-table-container">
          <q-table
            flat
            bordered
            title="직원 요청 목록"
            :rows="requests"
            :columns="columns"
            row-key="email"
            v-model:pagination="pagination"
            @request="onRequest"
            hide-pagination
          >
            <template v-slot:body="props">
              <q-tr :props="props" class="q-table-row">
                <q-td v-for="col in columns" :key="col.name" :props="props">
                  <div>
                    {{ props.row[col.field] }}
                  </div>
                </q-td>
              </q-tr>
            </template>
          </q-table>
        </div>
      </q-card-section>
    </q-card>

    <div class="q-pa-lg flex flex-center">
      <q-pagination
        v-model="pagination.page"
        :max="maxPage"
        @update:model-value="onPageChange"
        input
        input-class="text-orange-10"
      />
    </div>

    <q-btn
      class="back-to-top-btn"
      fab
      icon="arrow_upward"
      @click="scrollToTop"
      title="맨 위로 이동"
    />
  </q-page>
</template>

<script setup>
import {ref, computed, onMounted} from "vue";
import {api as axios} from "src/boot/axios";

const searchName = ref("");
const requests = ref([]);
const pagination = ref({
  page: 1,
  rowsPerPage: 20,
  rowsNumber: 0
});
const selectedRole = ref(null);

const fetchRequests = async (page = 1) => {
  try {
    const params = {
      page: page - 1,
      size: pagination.value.rowsPerPage,
      name: searchName.value || null,
      role: selectedRole.value || null
    };

    const response = await axios.get('/api/v1/admin/registers/list', {params});
    requests.value = response.data.content;
    pagination.value.page = response.data.number + 1;
    pagination.value.rowsPerPage = response.data.size;
    pagination.value.rowsNumber = response.data.totalElements;
  } catch (error) {
    console.error("Error fetching requests:", error);
  }
};

const maxPage = computed(() => Math.ceil(pagination.value.rowsNumber / pagination.value.rowsPerPage));

onMounted(() => {
  fetchRequests();
});

const executeSearch = () => {
  pagination.value.page = 1; // 검색을 다시 시작할 때 페이지를 1로 초기화
  fetchRequests();
};

const onRequest = (params) => {
  const {page, rowsPerPage} = params.pagination;
  pagination.value.page = page;
  pagination.value.rowsPerPage = rowsPerPage;
  fetchRequests(page);
};

const onPageChange = (page) => {
  pagination.value.page = page;
  fetchRequests(page);
};

const columns = [
  {name: 'name', label: '이름', align: 'left', field: 'name', sortable: true},
  {name: 'email', label: '이메일', align: 'left', field: 'email', sortable: true},
  {name: 'requestedRole', label: '요청된 역할', align: 'center', field: 'requestedRole', sortable: true},
  {name: 'status', label: '상태', align: 'center', field: 'status', sortable: true}, // 추가된 status 컬럼
];

const roleOptions = [
  {label: '선택 안 함', value: ''},
  {label: '매니저', value: 'ROLE_MANAGER'},
  {label: '상담사', value: 'ROLE_CONSULTANT'},
  {label: '마케터', value: 'ROLE_MARKETER'},
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
  max-width: 1000px;
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
  width: 56px; /* 버튼 크기 조정 */
  height: 56px; /* 버튼 크기 조정 */
}

.q-pa-lg {
  padding: 16px;
}

.flex-center {
  display: flex;
  justify-content: center;
  align-items: center;
}

.q-table-row {
  cursor: pointer; /* 마우스를 올리면 클릭할 수 있는 것처럼 보이도록 */
}
</style>
