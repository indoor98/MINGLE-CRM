<template>
  <q-page class="q-pa-xl">
    <section>
      <q-tabs v-model="activeTab">
        <q-tab name="summary" @click="changeTab('summary')">고객 요약</q-tab>
        <q-tab name="reservation" @click="changeTab('reservation')">예약</q-tab>
        <q-tab name="consultation" @click="changeTab('consultation')">상담</q-tab>
        <q-tab name="payment" @click="changeTab('payment')">결제</q-tab>
        <q-tab name="voucher" @click="changeTab('voucher')">바우처</q-tab>
        <q-tab name="reward" @click="changeTab('reward')">리워드</q-tab>
      </q-tabs>

      <q-separator class="q-my-md" />

      <div v-if="activeTab === 'summary'">
        <!-- 고객 요약 내용 -->
        <q-card>
          <CustomerSummary />
        </q-card>
      </div>

      <div v-else-if="activeTab === 'reservation'">
        <!-- 예약 내용 -->
        <div class="text-h5">예약</div>
        <q-card>
          <q-card-section>예약 정보를 표시합니다.</q-card-section>
        </q-card>
      </div>

      <div v-else-if="activeTab === 'consultation'">
        <!-- 상담 내용 -->
        <div class="text-h5">상담</div>
        <q-card>
          <q-card-section>상담 정보를 표시합니다.</q-card-section>
        </q-card>
      </div>

      <div v-else-if="activeTab === 'payment'">
        <!-- 결제 내용 -->
        <div class="text-h5">결제</div>
        <q-card>
          <q-card-section>결제 정보를 표시합니다.</q-card-section>
        </q-card>
      </div>

      <div v-else-if="activeTab === 'voucher'">
        <!-- 바우처 내용 -->
        <div class="text-h5">바우처</div>
        <q-card>
          <q-card-section>바우처 정보를 표시합니다.</q-card-section>
        </q-card>
      </div>

      <div v-else-if="activeTab === 'reward'">
        <!-- 리워드 내용 -->
        <div class="text-h5">리워드</div>
        <q-card>
          <q-card-section>리워드 정보를 표시합니다.</q-card-section>
        </q-card>
      </div>
    </section>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import CustomerSummary from "pages/customer/detail/summary/CustomerSummary.vue";

// 활성화된 탭을 관리하는 상태
const activeTab = ref("summary");

// 탭 변경 처리 함수
const changeTab = (tabName) => {
  activeTab.value = tabName;
};
</script>

<style scoped>
.q-page {
  max-width: 800px;
  margin: auto;
}
</style>
