<template>
  <div class="q-pa-md customer-preference-container">
    <div class="text-h6">고객 선호 사항</div>
    <q-separator class="q-my-md" />

    <div v-if="preferences.length > 0" class="preference-details">
      <table class="preference-table">
        <tbody>
        <tr v-for="col in preferenceColumns" :key="col.name">
          <th>{{ col.label }}</th>
          <td>{{ formatValue(preferences[0][col.field]) }}</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div v-else class="text-center">
      고객 선호 사항이 없습니다.
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { api as axios } from "src/boot/axios";
import { useRoute } from 'vue-router';

const route = useRoute();
const customerId = route.params.id;
const preferences = ref([]);

const fetchPreferences = async () => {
  try {
    const response = await axios.get(`/api/v1/customers/${customerId}/preferences`);
    preferences.value = [response.data];
    console.log(preferences.value); // 데이터 확인용 콘솔 출력
  } catch (error) {
    console.error('Error fetching preferences:', error);
  }
};

onMounted(() => {
  fetchPreferences();
});

const preferenceColumns = [
  { name: 'isSmoking', label: '흡연 여부', align: 'left', field: 'isSmoking', sortable: true },
  { name: 'purpose', label: '목적', align: 'left', field: 'purpose', sortable: true },
  { name: 'isPet', label: '반려동물 동반 여부', align: 'left', field: 'isPet', sortable: true },
  { name: 'preferredCheckinTime', label: '선호 체크인 시간', align: 'left', field: 'preferredCheckinTime', sortable: true },
  { name: 'preferredCheckoutTime', label: '선호 체크아웃 시간', align: 'left', field: 'preferredCheckoutTime', sortable: true },
  { name: 'dietaryRestrictions', label: '식이 제한', align: 'left', field: 'dietaryRestrictions', sortable: true },
  { name: 'funnel', label: '유입 경로', align: 'left', field: 'funnel', sortable: true },
  { name: 'interest', label: '관심사', align: 'left', field: 'interest', sortable: true },
  { name: 'isBreakfastPreferred', label: '아침 식사 선호 여부', align: 'left', field: 'isBreakfastPreferred', sortable: true },
];

const formatValue = (value) => {
  if (typeof value === 'boolean') {
    return value ? '예' : '아니오';
  }
  return value;
};
</script>

<style scoped>
.customer-preference-container {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 10px;
}

.preference-details {
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.preference-table {
  width: 100%;
  border-collapse: collapse;
}

.preference-table th,
.preference-table td {
  padding: 10px;
  text-align: left;
  vertical-align: top;
  border-bottom: 1px solid #ddd;
}

.preference-table th {
  background-color: #f0f0f0;
  font-weight: bold;
  width: 200px; /* Adjust this width as needed */
}

.preference-table td {
  background-color: #ffffff;
}

.q-separator {
  margin: 20px 0;
}
</style>
