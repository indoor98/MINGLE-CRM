vue
코드 복사
<template>
  <q-card class="custom-card">
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
import { ref, onMounted, watch } from 'vue';
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

watch([() => props.inquiry.customerId, () => props.inquiry.id], ([newCustomerId, newInquiryId]) => {
  if (newCustomerId && newInquiryId) {
    fetchInquiryDetail(newCustomerId, newInquiryId);
  }
});

onMounted(() => {
  fetchInquiryDetail(props.inquiry.customerId, props.inquiry.id);
});
</script>

<style scoped>
.custom-card {
  max-width: 600px;
  margin: auto;
}

.text-h6 {
  font-size: 1.25rem;
  font-weight: bold;
}

.q-item {
  padding: 10px 0;
}

.q-item-label {
  font-weight: bold;
}
</style>
