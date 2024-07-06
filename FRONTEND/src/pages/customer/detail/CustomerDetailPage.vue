<template>
  <q-page class="q-pa-xl">
    <q-card class="my-card" flat bordered>
      <q-card-section>
        <CustomerSummary />
      </q-card-section>

      <q-tabs v-model="activeTab" class="text-teal">
<!--        <q-tab label="고객 요약" name="summary" @click="changeTab('summary')" />-->
        <q-tab label="고객 선호도 내역" name="preference" @click="changeTab('preference')" />
        <q-tab label="예약 내역" name="reservation" @click="changeTab('reservation')" />
        <q-tab label="상담 내역" name="consultation" @click="changeTab('consultation')" />
        <q-tab label="결제 내역" name="payment" @click="changeTab('payment')" />
        <q-tab label="바우처 내역" name="voucher" @click="changeTab('voucher')" />
        <q-tab label="리워드 내역" name="reward" @click="changeTab('reward')" />
      </q-tabs>

      <q-separator />

      <q-tab-panels v-model="activeTab" animated>
        <q-tab-panel name="summary">
        </q-tab-panel>

        <q-tab-panel name="preference">
          <q-card>
            <CustomerPreferenceDetail/>
          </q-card>
        </q-tab-panel>

        <q-tab-panel name="reservation">

          <q-tabs v-model="activeReservationTab">
            <q-tab label="호텔 예약" name="hotel" @click="changeReservationTab('hotel')" />
            <q-tab label="다이닝 예약" name="dining" @click="changeReservationTab('dining')" />
          </q-tabs>

          <q-tab-panels v-model="activeReservationTab">
            <q-tab-panel name="hotel">
              <q-card>
                <HotelReservationList/>
              </q-card>
            </q-tab-panel>
            <q-tab-panel name="dining">
              <q-card>
                <DiningReservationList/>
              </q-card>
            </q-tab-panel>
          </q-tab-panels>
        </q-tab-panel>

        <q-tab-panel name="consultation">
          <q-card>
            <CustomerConsultationList/>
          </q-card>
        </q-tab-panel>

        <q-tab-panel name="payment">
          <q-card>
            <CustomerPaymentList/>
          </q-card>
        </q-tab-panel>

        <q-tab-panel name="voucher">
          <q-card>
            <CustomerVoucherList/>
          </q-card>
        </q-tab-panel>

        <q-tab-panel name="reward">
          <q-card>
            <CustomerRewardDetail/>
          </q-card>
        </q-tab-panel>

      </q-tab-panels>
    </q-card>
  </q-page>
</template>

<script setup>
import {ref} from 'vue';
import CustomerSummary from 'pages/customer/detail/summary/CustomerSummary.vue';
import CustomerPaymentList from "pages/customer/detail/payment/CustomerPaymentList.vue";
import HotelReservationList from "pages/customer/detail/reservation/hotel/HotelReservationList.vue";
import DiningReservationList from "pages/customer/detail/reservation/dining/DiningReservationList.vue";
import CustomerConsultationList from "pages/customer/detail/consultation/CustomerConsultationList.vue";
import CustomerRewardDetail from "pages/customer/detail/reward/CustomerRewardDetail.vue";
import CustomerVoucherList from "pages/customer/detail/voucher/CustomerVoucherList.vue";
import CustomerPreferenceList from "pages/customer/detail/preference/CustomerPreferenceDetail.vue";
import CustomerPreferenceDetail from "pages/customer/detail/preference/CustomerPreferenceDetail.vue";

const activeTab = ref('summary');
const activeReservationTab = ref('hotel'); // 기본적으로 호텔 예약 탭이 활성화되도록 설정

const changeTab = (tabName) => {
  activeTab.value = tabName;
};

const changeReservationTab = (tabName) => {
  activeReservationTab.value = tabName;
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
