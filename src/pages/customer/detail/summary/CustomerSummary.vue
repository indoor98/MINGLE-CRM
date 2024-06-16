<template>
  <div class="q-pa-md customer-detail-container">
    <div class="text-h6">고객 상세</div>
    <q-separator class="q-my-md" />
    <div v-if="customer">
      <q-card class="customer-card">
        <q-card-section>
          <table class="customer-table">
            <tr>
              <th>고객 ID</th>
              <td>{{ customer.id }}</td>
            </tr>
            <tr>
              <th>고객 이름</th>
              <td>{{ customer.name }}</td>
            </tr>
            <tr>
              <th>고객 등급</th>
              <td>{{ customer.grade }}</td>
            </tr>
            <tr>
              <th>전화번호</th>
              <td>{{ customer.phone }}</td>
            </tr>
            <tr>
              <th>주소</th>
              <td>{{ customer.address }}</td>
            </tr>
            <tr>
              <th>직원 이름</th>
              <td>{{ customer.employeeName }}</td>
            </tr>
            <tr>
              <th>메모</th>
              <td class="memo-cell">{{ customer.memo }}</td>
            </tr>
            <tr>
              <th>성별</th>
              <td>{{ customer.gender }}</td>
            </tr>
            <tr>
              <th>생일</th>
              <td>{{ customer.birth }}</td>
            </tr>
            <tr>
              <th>나이</th>
              <td>{{ customer.age }}</td>
            </tr>
          </table>
        </q-card-section>
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

<style scoped>
.customer-detail-container {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 10px;
}

.customer-card {
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.customer-table {
  width: 100%;
  border-collapse: collapse;
}

.customer-table th,
.customer-table td {
  padding: 10px;
  text-align: left;
  border-bottom: 1px solid #ddd;
  vertical-align: top; /* 상단 정렬 */
}

.customer-table th {
  background-color: #f0f0f0;
  font-weight: bold;
}

.customer-table td {
  background-color: #ffffff;
}

.memo-cell {
  max-width: 400px; /* 필요에 따라 너비 조정 */
  white-space: pre-wrap; /* 줄바꿈을 유지하고, 긴 텍스트는 줄바꿈 처리 */
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
