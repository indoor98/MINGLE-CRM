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
            label="고객명"
            dense
            placeholder="고객명을 입력하세요"
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
            title="고객 목록"
            :rows="customers"
            :columns="columns"
            row-key="id"
            v-model:pagination="pagination"
            @request="onRequest"
            hide-pagination
          >
            <template v-slot:body="props">
              <q-tr :props="props" @click="rowClicked(props.row)">
                <q-td v-for="col in columns" :key="col.name" :props="props">
                  <!-- 필드별로 적절한 마스킹 함수 적용 -->
                  {{
                    col.field === 'name' ? maskName(props.row[col.field]) :
                      col.field === 'phone' ? maskPhoneNumber(props.row[col.field]) :
                        col.field === 'birth' ? maskBirthdate(props.row[col.field]) :
                          props.row[col.field]
                  }}
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

    <!-- 맨 위로 가는 버튼 -->
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
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { api as axios } from "src/boot/axios";

const router = useRouter();
const searchName = ref('');
const customers = ref([]);
const pagination = ref({
  page: 1,
  rowsPerPage: 20,
  rowsNumber: 0
});
const selectedGrade = ref(null);
const selectedGender = ref(null);

const fetchCustomers = async (page = 1) => {
  try {
    const params = {
      page: page - 1,
      size: pagination.value.rowsPerPage,
      name: searchName.value || null,
      grade: selectedGrade.value || null,
      gender: selectedGender.value || null
    };

    const response = await axios.get('/api/v1/customers/search', { params });
    customers.value = response.data.content;
    pagination.value.page = response.data.number + 1;
    pagination.value.rowsPerPage = response.data.size;
    pagination.value.rowsNumber = response.data.totalElements;
  } catch (error) {
    console.error('Error fetching customers:', error);
  }
};

const maxPage = computed(() => Math.ceil(pagination.value.rowsNumber / pagination.value.rowsPerPage));

onMounted(() => {
  fetchCustomers();
});

const executeSearch = () => {
  pagination.value.page = 1; // 검색을 다시 시작할 때 페이지를 1로 초기화
  fetchCustomers();
};

const rowClicked = (row) => {
  if (row && row.id) {
    router.push({ path: `/customer-detail/${row.id}` });
  } else {
    console.error('Invalid row data:', row);
  }
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
  { name: 'id', label: '#', align: 'left', field: 'id' },
  { name: 'name', label: '이름', align: 'left', field: 'name', sortable: true },
  { name: 'grade', label: '등급', align: 'center', field: 'grade', sortable: true },
  { name: 'phone', label: '전화번호', align: 'center', field: 'phone', sortable: true },
  { name: 'employeeName', label: '직원 이름', align: 'center', field: 'employeeName' },
  { name: 'gender', label: '성별', align: 'center', field: 'gender' },
  { name: 'birth', label: '생년월일', align: 'center', field: 'birth' },
];

const gradeOptions = [
  { label: '선택 안 함', value: '' },
  { label: 'NEW', value: 'NEW' },
  { label: 'BASIC', value: 'BASIC' },
  { label: 'VIP', value: 'VIP' },
  { label: 'VVIP', value: 'VVIP' }
];

const genderOptions = [
  { label: '선택 안 함', value: '' },
  { label: '여성', value: 'Female' },
  { label: '남성', value: 'Male' }
];

const maskName = (name) => {
  if (!name) return '';

  const length = name.length;
  const visibleChars = Math.min(4, length); // 처음 두 글자와 마지막 두 글자를 노출
  const maskedChars = Math.max(0, length - visibleChars); // 마스킹할 문자 수

  const visiblePart = name.slice(0, 2); // 처음 두 글자
  const maskedPart = '*'.repeat(maskedChars); // 나머지 부분을 '*'로 마스킹

  const lastName = name.slice(-2); // 마지막 두 글자

  return `${visiblePart}${maskedPart}${lastName}`;
};

const maskPhoneNumber = (phoneNumber) => {
  if (!phoneNumber) return '';

  const visibleDigits = phoneNumber.slice(-4);
  const maskedDigits = '*'.repeat(Math.max(0, phoneNumber.length - 7)); // 처음 세 자리와 마지막 네 자리를 제외한 부분을 마스킹

  return `${phoneNumber.slice(0, 3)}${maskedDigits}${visibleDigits}`;
};

const maskBirthdate = (birthdate) => {
  if (!birthdate) return '';

  const visiblePart = birthdate.slice(-2); // 일자는 그대로 표시
  const maskedPart = '*'.repeat(Math.max(0, birthdate.length - 4)); // 연도와 월을 마스킹

  return `${birthdate.slice(0, 4)}${maskedPart}${visiblePart}`;
};

const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  });
};
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
