<template>
  <q-card>
    <q-card-section>
      <div class="text-h6">Customer Inquiry Details</div>
    </q-card-section>

    <q-separator />

    <q-card-section>
      <q-list>
        <q-item>
          <q-item-label>Customer Name</q-item-label>
          <q-item-section>{{ inquiryDetails.customerName }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Phone Number</q-item-label>
          <q-item-section>{{ inquiryDetails.customerPhone }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Date</q-item-label>
          <q-item-section>{{ inquiryDetails.date }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Type</q-item-label>
          <q-item-section>{{ inquiryDetails.type }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Replied</q-item-label>
          <q-item-section>{{ inquiryDetails.isReply ? 'Yes' : 'No' }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Employee</q-item-label>
          <q-item-section>{{ inquiryDetails.employName }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Inquiry Title</q-item-label>
          <q-item-section>{{ inquiryDetails.inquiryTitle }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Inquiry Content</q-item-label>
          <q-item-section>{{ inquiryDetails.inquiryContent }}</q-item-section>
        </q-item>
        <q-item>
          <q-item-label>Action Status</q-item-label>
          <q-item-section>{{ inquiryDetails.actionStatus }}</q-item-section>
        </q-item>
      </q-list>
    </q-card-section>
  </q-card>
</template>

<script setup>
import { ref, watchEffect } from 'vue';
import axios from 'axios';

const props = defineProps(['inquiry']);
const inquiryDetails = ref({});

const fetchInquiryDetail = async (customerId, inquiryId) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/customers/${customerId}/inquiries/${inquiryId}`);
    inquiryDetails.value = response.data.data.inquiryResponse;
  } catch (error) {
    console.error('Error fetching inquiry detail:', error);
  }
};

watchEffect(() => {
  if (props.inquiry.customerId && props.inquiry.id) {
    fetchInquiryDetail(props.inquiry.customerId, props.inquiry.id);
  }
});
</script>

<style scoped>
.q-card {
  width: 400px; /* 카드의 너비 조정 */
  margin: auto; /* 가운데 정렬 */
}

.text-h6 {
  font-size: 1.25rem; /* 제목 글꼴 크기 조정 */
  font-weight: bold; /* 제목 글꼴 두껍게 */
}

.q-item {
  padding: 10px 0; /* 각 항목 사이의 간격 조정 */
}

.q-item-label {
  font-weight: bold; /* 항목 레이블 글꼴 두껍게 */
}
</style>
