<template>
  <div class="q-pa-md">
    <q-input
      clearable
      filled
      color="purple-12"
      v-model="search"
      label="고객명을 입력해주세요"
    />
    <q-table
      flat
      bordered
      title="고객 목록"
      :rows="filteredRows"
      :columns="columns"
      row-key="id"
      v-model:pagination="pagination"
      @request="onRequest"
    >
      <template v-slot:body="props">
        <q-tr :props="props" @click="rowClicked(props.row)">
          <q-td v-for="col in columns" :key="col.name" :props="props">
            <!-- 필드별로 적절한 마스킹 함수 적용 -->
            {{ col.field === 'name' ? maskName(props.row[col.field]) :
            col.field === 'phone' ? maskPhoneNumber(props.row[col.field]) :
              col.field === 'birth' ? maskBirthdate(props.row[col.field]) :
                props.row[col.field] }}
          </q-td>
        </q-tr>
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { api as axios } from "src/boot/axios";

const router = useRouter();
const search = ref('');
const customers = ref([]);
const pagination = ref({
  rowsPerPage: 20,
  page: 1,
  rowsNumber: 0
});

const fetchCustomers = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/v1/customers', {
      params: {
        page: pagination.value.page - 1,
        size: pagination.value.rowsPerPage
      }
    });
    customers.value = response.data.content;
    pagination.value.page = response.data.number + 1;
    pagination.value.rowsPerPage = response.data.size;
    pagination.value.rowsNumber = response.data.totalElements;
  } catch (error) {
    console.error('Error fetching customers:', error);
  }
};

const rowClicked = (row) => {
  if (row && row.id) {
    router.push({ path: `/customer-detail/${row.id}` });
  } else {
    console.error('Invalid row data:', row);
  }
};

const filteredRows = computed(() => {
  if (search.value) {
    return customers.value.filter(row =>
      row.name.toLowerCase().includes(search.value.toLowerCase())
    );
  }
  return customers.value;
});

const onRequest = (params) => {
  const { page, rowsPerPage } = params.pagination;
  pagination.value.page = page;
  pagination.value.rowsPerPage = rowsPerPage;
  fetchCustomers();
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

const maskName = (name) => {
  if (!name) return '';

  const length = name.length;
  const visibleChars = 4; // 처음 두 글자와 마지막 두 글자를 노출
  const maskedChars = length - visibleChars; // 마스킹할 문자 수

  const visiblePart = name.slice(0, 2); // 처음 두 글자
  const maskedPart = '*'.repeat(maskedChars); // 나머지 부분을 '*'로 마스킹

  const lastName = name.slice(-2); // 마지막 두 글자

  return `${visiblePart}${maskedPart}${lastName}`;
};


const maskPhoneNumber = (phoneNumber) => {
  if (!phoneNumber) return '';

  const visibleDigits = phoneNumber.slice(-4);
  const maskedDigits = '*'.repeat(phoneNumber.length - 8); // 처음 세 자리와 마지막 네 자리를 제외한 부분을 마스킹

  return `${phoneNumber.slice(0, 3)}${maskedDigits}${visibleDigits}`;
};


const maskBirthdate = (birthdate) => {
  if (!birthdate) return '';

  const visiblePart = birthdate.slice(-2); // 일자는 그대로 표시
  const maskedPart = '*'.repeat(birthdate.length - 4); // 연도와 월을 마스킹

  return `${birthdate.slice(0, 4)}${maskedPart}${visiblePart}`;
};

onMounted(() => {
  fetchCustomers();
});
</script>
