<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <!-- 리워드 정보 표시 -->
    <q-card class="q-mt-md">
      <q-card-section class="q-pa-md">
        <q-table
          :rows="[rewardInfo]"
          :columns="columns"
          row-key="customerId"
          flat
          bordered
        />
      </q-card-section>
    </q-card>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import {api as axios} from 'src/boot/axios';
import {useRoute} from 'vue-router';

const route = useRoute();
const customerId = route.params.id;
const rewardInfo = ref({
  customerId: null,
  amount: null
});

const columns = [
  {
    name: 'customerId',
    label: '고객 ID',
    align: 'left',
    field: row => row.customerId
  },
  {
    name: 'amount',
    label: '리워드 총 금액',
    align: 'left',
    field: row => formatAmount(row.amount) // Use formatAmount function here
  }
];

const fetchRewardInfo = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/customers/${customerId}/rewards`);
    rewardInfo.value.customerId = response.data.data.customerId;
    rewardInfo.value.amount = response.data.data.amount;
  } catch (error) {
    console.error('Error fetching reward info:', error);
  }
};

// Function to format amount with thousand separators
function formatAmount(amount) {
  if (typeof amount !== 'number') {
    return amount;
  }
  return amount.toLocaleString('en-US'); // Example: Formats with comma separators (,)
}

onMounted(() => {
  fetchRewardInfo();
});
</script>

<style scoped>
.q-mt-md {
  margin-top: 16px;
}

.q-pa-md {
  padding: 16px;
}

.q-my-md {
  margin: 16px 0;
}
</style>
