vue
코드 복사
<template>
  <q-page class="q-pa-xl">
    <q-card class="my-card" flat bordered>
      <q-card-section>
        <div class="text-h6">Customer Details</div>
        <CustomerSummary />
      </q-card-section>

      <q-tabs v-model="activeTab" class="text-teal">
        <q-tab label="고객 요약" name="summary" @click="changeTab('summary')" />
        <q-tab label="예약" name="reservation" @click="changeTab('reservation')" />
        <q-tab label="상담" name="consultation" @click="changeTab('consultation')" />
        <q-tab label="결제" name="payment" @click="changeTab('payment')" />
        <q-tab label="바우처" name="voucher" @click="changeTab('voucher')" />
        <q-tab label="리워드" name="reward" @click="changeTab('reward')" />
      </q-tabs>

      <q-separator />

      <q-tab-panels v-model="activeTab" animated>
        <q-tab-panel name="summary">
          <!-- 고객 요약 탭은 별도의 내용 없음 -->
        </q-tab-panel>

        <q-tab-panel name="reservation">
          <div class="text-h5">예약</div>
          <q-card>
            <q-card-section>예약 정보를 표시합니다.</q-card-section>
          </q-card>
        </q-tab-panel>

        <q-tab-panel name="consultation">
          <div class="text-h5">상담</div>
          <q-card>
            <q-card-section>상담 정보를 표시합니다.</q-card-section>
          </q-card>
        </q-tab-panel>

        <q-tab-panel name="payment">
          <div class="text-h5">결제</div>
          <q-card>
            <CustomerPaymentList></CustomerPaymentList>
          </q-card>
        </q-tab-panel>

        <q-tab-panel name="voucher">
          <div class="text-h5">바우처</div>
          <q-card>
            <q-card-section>바우처 정보를 표시합니다.</q-card-section>
          </q-card>
        </q-tab-panel>

        <q-tab-panel name="reward">
          <div class="text-h5">리워드</div>
          <q-card>
            <q-card-section>리워드 정보를 표시합니다.</q-card-section>
          </q-card>
        </q-tab-panel>
      </q-tab-panels>
    </q-card>
  </q-page>
</template>

<script setup>
import { ref } from 'vue';
import CustomerSummary from 'pages/customer/detail/summary/CustomerSummary.vue';
import CustomerPaymentList from "pages/customer/detail/payment/CustomerPaymentList.vue";

const activeTab = ref('summary');

const changeTab = (tabName) => {
  activeTab.value = tabName;
  // 여기에 필요한 경우 해당 탭에 맞는 데이터를 로드하는 로직 추가 가능
};
</script>

<style scoped>
.q-page {
  max-width: 1200px; /* 페이지의 최대 너비를 더 넓게 설정 */
  margin: auto;
}
.my-card {
  max-width: 1000px; /* 카드의 최대 너비를 더 넓게 설정 */
  margin: auto;
}
</style>
