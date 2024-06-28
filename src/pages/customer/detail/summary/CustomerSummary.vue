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
              <th>이메일</th>
              <td>{{ customer.email }}</td>
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
              <td class="memo-cell">
                <div v-if="editingMemo">
                  <q-input
                    v-model="editedMemo"
                    type="textarea"
                    autofocus
                    dense
                    placeholder="메모를 입력하세요..."
                  />
                  <div class="q-mt-md q-mb-sm text-right">
                    <q-btn label="저장" color="primary" @click="saveMemo" />
                  </div>
                </div>
                <div v-else>
                  {{ customer.memo }}
                  <q-btn
                    v-if="!editingMemo"
                    label="메모 편집"
                    color="primary"
                    @click="editMemo"
                    class="edit-memo-btn"
                  />
                </div>
              </td>
            </tr>
            <tr>
              <th>성별</th>
              <td>{{ getKoreanGender(customer.gender) }}</td>
            </tr>
            <tr>
              <th>생일</th>
              <td>{{ customer.birth }}</td>
            </tr>
            <tr>
              <th>나이</th>
              <td>{{ calculateAge(customer.birth) }}</td>
            </tr>
          </table>
        </q-card-section>
      </q-card>
    </div>

    <div v-else>
      <q-spinner size="50px" color="primary" />
    </div>

    <!-- 맨 위로 가는 버튼 -->
    <q-btn
      class="back-to-top-btn"
      fab
      icon="arrow_upward"
      @click="scrollToTop"
      title="맨 위로 이동"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { api as axios } from "src/boot/axios";

const route = useRoute();
const customerId = route.params.id;
const customer = ref(null);
const editedMemo = ref('');
const editingMemo = ref(false);

const fetchCustomerDetail = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/customers/${customerId}`);
    customer.value = response.data;
  } catch (error) {
    console.error('고객 상세 정보를 불러오는 데 실패했습니다:', error);
  }
};

const editMemo = () => {
  editedMemo.value = customer.value.memo;
  editingMemo.value = true;
};

const saveMemo = async () => {
  try {
    const response = await axios.put(`/api/v1/customers/${customerId}/memo`, {
      memo: editedMemo.value
    });
    customer.value.memo = editedMemo.value;
    editingMemo.value = false;
  } catch (error) {
    console.error('메모 저장에 실패했습니다:', error);
    // 실패 처리 로직 추가
  }
};

const calculateAge = (birthdate) => {
  if (!birthdate) return '';

  const birthYear = new Date(birthdate).getFullYear();
  const currentYear = new Date().getFullYear();

  return currentYear - birthYear;
};

const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  });
};

const getKoreanGender = (gender) => {
  switch (gender) {
    case 'Male':
      return '남자';
    case 'Female':
      return '여자';
    default:
      return gender;
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
  position: relative; /* 버튼을 오른쪽에 정렬하기 위해 position 사용 */
}

.memo-cell .q-btn {
  position: absolute;
  top: 10px;
  right: 10px;
}

.edit-memo-btn {
  background-color: #007bff;
  color: #ffffff;
  border: none;
  border-radius: 5px;
  padding: 5px 10px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.edit-memo-btn:hover {
  background-color: #0056b3;
}

.back-to-top-btn {
  position: fixed;
  bottom: 50px;
  right: 60px; /* 버튼을 왼쪽으로 이동 */
  transform: translateX(-70%); /* 정확한 가운데 정렬 */
  width: 9px; /* 버튼 크기 조정 */
  height: 9px; /* 버튼 크기 조정 */
}
</style>
