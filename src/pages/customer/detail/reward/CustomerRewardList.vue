<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <!-- 리워드 정보 표시 -->
    <q-card class="q-mt-md">
      <q-card-section class="q-pa-md">
        <q-list>
          <q-item>
            <q-item-section>Customer ID</q-item-section>
            <q-item-section>{{ rewardInfo.customerId }}</q-item-section>
          </q-item>
          <q-item>
            <q-item-section>Amount</q-item-section>
            <q-item-section>{{ rewardInfo.amount }}</q-item-section>
          </q-item>
        </q-list>
      </q-card-section>
    </q-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';

const route = useRoute();
const customerId = route.params.id;
const rewardInfo = ref({
  customerId: null,
  amount: null
});

const fetchRewardInfo = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/customers/${customerId}/rewards`);
    rewardInfo.value.customerId = response.data.data.customerId;
    rewardInfo.value.amount = response.data.data.amount;
  } catch (error) {
    console.error('Error fetching reward info:', error);
  }
};

onMounted(() => {
  fetchRewardInfo();
});
</script>

<style scoped>
/* 필요한 스타일을 추가할 수 있습니다. */
</style>
