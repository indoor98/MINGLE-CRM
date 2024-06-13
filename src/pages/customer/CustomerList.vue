<template>
  <div class="q-pa-md">
    <!-- 검색 입력 필드 -->
    <q-input
      clearable
      filled
      color="purple-12"
      v-model="search"
      label="고객명을 입력해주세요"
    />

    <!-- 테이블로 고객 목록 표시 -->
    <q-table
      flat
      bordered
      title="Customers"
      :rows="filteredRows"
      :columns="columns"
      row-key="id"
      v-model:pagination="pagination"

    >
      <template v-slot:body="props">
        <q-tr :props="props" @click="rowClicked(props.row)">
          <q-td v-for="col in columns" :key="col.name" :props="props">
            {{ props.row[col.field] }}
          </q-td>
        </q-tr>
      </template>

      <!-- 페이지네이션 컨트롤 버튼 -->
      <template v-slot:top-right>
        <q-btn
          v-if="pagination.pagesNumber > 2"
          icon="first_page"
          color="grey-8"
          round
          dense
          flat
          :disable="pagination.isFirstPage"
          @click="firstPage"
        />
        <q-btn
          icon="chevron_left"
          color="grey-8"
          round
          dense
          flat
          :disable="pagination.isFirstPage"
          @click="prevPage"
        />
        <q-btn
          icon="chevron_right"
          color="grey-8"
          round
          dense
          flat
          :disable="pagination.isLastPage"
          @click="nextPage"
        />
        <q-btn
          v-if="pagination.pagesNumber > 2"
          icon="last_page"
          color="grey-8"
          round
          dense
          flat
          :disable="pagination.isLastPage"
          @click="lastPage"
        />
      </template>
    </q-table>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();
const search = ref('');
const customers = ref([]);
const pagination = ref({
  rowsPerPage: 10, // 페이지당 행 수
  page: 1,         // 현재 페이지
  pagesNumber: 1,  // 전체 페이지 수
  isFirstPage: true,
  isLastPage: true
});

const fetchCustomers = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/v1/customers');
    customers.value = response.data.map((customer) => ({
      id: customer.id,
      name: customer.name,
      grade: customer.grade,
      phone: customer.phone,
      address: customer.address,
      employee_id: customer.employee_id,
      memo: customer.memo,
      gender: customer.gender,
      birth: customer.birth,
    }));

    // 페이지네이션 설정
    pagination.value.pagesNumber = Math.ceil(customers.value.length / pagination.value.rowsPerPage);
    pagination.value.isFirstPage = pagination.value.page === 1;
    pagination.value.isLastPage = pagination.value.page === pagination.value.pagesNumber;

    console.log(customers.value); // 데이터 확인용 콘솔 출력
  } catch (error) {
    console.error('Error fetching customers:', error);
  }
};

const rowClicked = (row) => {
  console.log('Clicked row:', row);
  console.log('Clicked row id:', row.id);
  if (row && row.id) {
    router.push({ path: `/customer-detail/${row.id}` });
  } else {
    console.error('Invalid row data:', row);
  }
};

const filteredRows = computed(() => {
  return customers.value.filter(row =>
    row.name.toLowerCase().includes(search.value.toLowerCase())
  );
});

const firstPage = () => {
  pagination.value.page = 1;
  pagination.value.isFirstPage = true;
  pagination.value.isLastPage = pagination.value.page === pagination.value.pagesNumber;
};

const prevPage = () => {
  if (pagination.value.page > 1) {
    pagination.value.page--;
    pagination.value.isFirstPage = pagination.value.page === 1;
    pagination.value.isLastPage = pagination.value.page === pagination.value.pagesNumber;
  }
};

const nextPage = () => {
  if (pagination.value.page < pagination.value.pagesNumber) {
    pagination.value.page++;
    pagination.value.isFirstPage = pagination.value.page === 1;
    pagination.value.isLastPage = pagination.value.page === pagination.value.pagesNumber;
  }
};

const lastPage = () => {
  pagination.value.page = pagination.value.pagesNumber;
  pagination.value.isFirstPage = pagination.value.page === 1;
  pagination.value.isLastPage = true;
};

onMounted(() => {
  fetchCustomers();
});

const columns = [
  { name: 'id', label: '#', align: 'left', field: 'id' },
  { name: 'name', label: 'Name', align: 'left', field: 'name', sortable: true },
  { name: 'grade', label: 'Grade', align: 'center', field: 'grade', sortable: true },
  { name: 'phone', label: 'Phone', align: 'center', field: 'phone', sortable: true },
  { name: 'address', label: 'Address', align: 'left', field: 'address' },
  { name: 'employee_id', label: 'Employee ID', align: 'center', field: 'employee_id' },
  { name: 'memo', label: 'Memo', align: 'left', field: 'memo' },
  { name: 'gender', label: 'Gender', align: 'center', field: 'gender' },
  { name: 'birth', label: 'Birth', align: 'center', field: 'birth' },
];
</script>
