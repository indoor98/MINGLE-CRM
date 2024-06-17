<template>
  <q-page>
    <div class="q-pa-md">
      <q-tabs v-model="selectedTab" dense>
        <q-tab name="all" label="리워드 리스트" />
        <q-tab name="histories" label="리워드 히스토리 리스트" />
      </q-tabs>
      <q-separator />
      <div v-if="selectedTab === 'all' && !showCustomerRewardHistory">
        <RewardList @row-click="handleRowClick" />
      </div>
      <div v-else-if="selectedTab === 'all' && showCustomerRewardHistory">
        <CustomerRewardHistory
          :customerId="selectedCustomerId"
          :customerName="selectedCustomerName"
          @back="handleBack"
        />
      </div>
      <div v-else-if="selectedTab === 'histories'">
        <RewardHistoryList />
      </div>
    </div>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import RewardList from "./RewardList.vue";
import RewardHistoryList from "./RewardHistoryList.vue";
import CustomerRewardHistory from "./CustomerRewardHistory.vue";

const selectedTab = ref("all");
const showCustomerRewardHistory = ref(false);
const selectedCustomerId = ref(null);
const selectedCustomerName = ref("");

const handleRowClick = (customerId, customerName) => {
  selectedCustomerId.value = customerId;
  selectedCustomerName.value = customerName;
  showCustomerRewardHistory.value = true;
};

const handleBack = () => {
  showCustomerRewardHistory.value = false;
};
</script>

<style></style>
