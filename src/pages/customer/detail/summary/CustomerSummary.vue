vue
코드 복사
<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <div v-if="customer">
      <q-card>
        <q-card-section>고객 ID: {{ customer.id }}</q-card-section>
        <q-card-section>고객 이름: {{ customer.name }}</q-card-section>
        <q-card-section>고객 등급: {{ customer.grade }}</q-card-section>
        <q-card-section>전화번호: {{ customer.phone }}</q-card-section>
        <q-card-section>주소: {{ customer.address }}</q-card-section>
        <q-card-section>직원 ID: {{ customer.employee_id }}</q-card-section>
        <q-card-section>메모: {{ customer.memo }}</q-card-section>
        <q-card-section>성별: {{ customer.gender }}</q-card-section>
        <q-card-section>생일: {{ customer.birth }}</q-card-section>
      </q-card>
    </div>

    <div v-else>
      <q-spinner size="50px" color="primary" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const customerId = route.params.id;  // URL 파라미터에서 ID를 가져옴
const customer = ref(null);

const fetchCustomerDetail = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/customers/${customerId}`);
    customer.value = response.data;
  } catch (error) {
    console.error('고객 상세 정보를 불러오는 데 실패했습니다:', error);
  }
};

onMounted(fetchCustomerDetail);
</script>
