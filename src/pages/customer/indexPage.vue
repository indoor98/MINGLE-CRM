<template>
  <div class="q-pa-md">
    <q-table
      style="height: 400px"
      flat
      bordered
      title="Customers"
      :rows="rows"
      :columns="columns"
      row-key="id"
      virtual-scroll
      v-model:pagination="pagination"
      :rows-per-page-options="[0]"
      @row-click="goToDetails"
    />
  </div>
</template>

<script>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const columns = [
  {
    name: 'id',
    label: '#',
    field: 'id',
  },
  {
    name: 'name',
    required: true,
    label: 'Name',
    align: 'left',
    field: row => row.name,
    format: val => `${val}`,
    sortable: true,
  },
  {
    name: 'grade',
    align: 'center',
    label: 'Grade',
    field: 'grade',
    sortable: true,
  },
  { name: 'phone', label: 'Phone', field: 'phone', sortable: true },
  { name: 'address', label: 'Address', field: 'address' },
  { name: 'employee_id', label: 'Employee ID', field: 'employee_id' },
  { name: 'memo', label: 'Memo', field: 'memo' },
  { name: 'gender', label: 'Gender', field: 'gender' },
  { name: 'birth', label: 'Birth', field: 'birth' },
];

const seed = [
  {
    name: '권성지',
    grade: 'VVIP',
    phone: '010-1234-4321',
    address: 'seongji123@test.com',
    employee_id: 1,
    memo: '',
    gender: 'Male',
    birth: '1999-01-29',
  },
  {
    name: '하지민',
    grade: '신규',
    phone: '010-4321-4321',
    address: 'jimin123@test.com',
    employee_id: 1,
    memo: '',
    gender: 'Female',
    birth: '1998-01-01',
  },
  {
    name: '강찬미',
    grade: '기존',
    phone: '010-4132-4132',
    address: 'chanmi123@test.com',
    employee_id: 1,
    memo: '',
    gender: 'Female',
    birth: '2002-01-02',
  },
  {
    name: '김동윽',
    grade: 'VIP',
    phone: '010-9876-9876',
    address: 'dongwwwwww@test.com',
    employee_id: 2,
    memo: '',
    gender: 'Male',
    birth: '1995-04-10',
  },
  {
    name: '장띵훈',
    grade: '기존',
    phone: '010-3289-8903',
    address: 'thinghoon123@test.com',
    employee_id: 2,
    memo: '',
    gender: 'Male',
    birth: '1998-04-21',
  },
  {
    name: '풀도젠깃갓리찬미',
    grade: '기존',
    phone: '010-3428-1342',
    address: 'fulli123@test.com',
    employee_id: 2,
    memo: '',
    gender: 'Female',
    birth: '2002-10-30',
  },
  // 나머지 데이터도 유사한 방식으로 추가하십시오.
];

let rows = [];
seed.forEach((customer, index) => {
  rows.push({
    id: index + 1,
    ...customer,
  });
});

export default {
  setup() {
    const router = useRouter();
    const pagination = ref({
      rowsPerPage: 1000,
    });

    const goToDetails = (row) => {
      router.push({path: `/customer-detail/${row.id}`});
    };

    return {
      columns,
      rows,
      pagination,
      goToDetails,
    };
  },
};
</script>
