<template>
  <div class="q-pa-md">
    <q-card class="q-mt-md">
      <q-card-section class="q-pa-md">
        <div class="text-h6">Customer Inquiry Details</div>
      </q-card-section>

      <q-separator />

      <q-card-section>
        <table class="inquiry-table">
          <tbody>
          <tr>
            <th>Customer Name</th>
            <td>{{ inquiryDetails.customerName }}</td>
          </tr>
          <tr>
            <th>Phone Number</th>
            <td>{{ inquiryDetails.customerPhone }}</td>
          </tr>
          <tr>
            <th>Date</th>
            <td>{{ inquiryDetails.date }}</td>
          </tr>
          <tr>
            <th>Type</th>
            <td>{{ inquiryDetails.type }}</td>
          </tr>
          <tr>
            <th>Replied</th>
            <td>{{ inquiryDetails.isReply ? 'Yes' : 'No' }}</td>
          </tr>
          <tr>
            <th>Employee</th>
            <td>{{ inquiryDetails.employName }}</td>
          </tr>
          <tr>
            <th>Inquiry Title</th>
            <td>{{ inquiryDetails.inquiryTitle }}</td>
          </tr>
          <tr>
            <th>Inquiry Content</th>
            <td>{{ inquiryDetails.inquiryContent }}</td>
          </tr>
          <tr>
            <th>Action Status</th>
            <td>{{ inquiryDetails.actionStatus }}</td>
          </tr>
          </tbody>
        </table>
      </q-card-section>
    </q-card>
  </div>
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
  width: 100%;
  max-width: 600px;
  margin: auto;
}

.text-h6 {
  font-size: 1.25rem;
  font-weight: bold;
}

.inquiry-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 16px;
}

.inquiry-table th,
.inquiry-table td {
  padding: 12px;
  border: 1px solid #e0e0e0;
  text-align: left;
}

.inquiry-table th {
  background-color: #f5f5f5;
  font-weight: bold;
}
</style>
