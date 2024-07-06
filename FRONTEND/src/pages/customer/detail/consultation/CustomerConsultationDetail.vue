<template>
  <q-card class="custom-card">
    <q-card-section class="q-pa-md">
      <div class="text-h6">고객 문의 상세</div>
    </q-card-section>

    <q-separator />

    <q-card-section>
      <table class="inquiry-table">
        <tbody>
        <tr>
          <th>고객명</th>
          <td>{{ inquiry.customerName }}</td>
        </tr>
        <tr>
          <th>전화번호</th>
          <td>{{ inquiry.customerPhone }}</td>
        </tr>
        <tr>
          <th>문의 날짜</th>
          <td>{{ inquiry.date }}</td>
        </tr>
        <tr>
          <th>문의 타입</th>
          <td>{{ inquiry.type }}</td>
        </tr>
        <tr>
          <th>답변 여부</th>
          <td>{{ inquiry.isReply ? 'Yes' : 'No' }}</td>
        </tr>
        <tr>
          <th>담당 직원명</th>
          <td>{{ inquiry.employName }}</td>
        </tr>
        <tr>
          <th>문의 제목</th>
          <td>{{ inquiry.inquiryTitle }}</td>
        </tr>
        <tr>
          <th>문의 내용</th>
          <td>{{ inquiry.inquiryContent }}</td>
        </tr>
        <tr>
          <th>문의 상태</th>
          <td>{{ inquiry.actionStatus }}</td>
        </tr>
        </tbody>
      </table>
    </q-card-section>

    <q-card-actions align="right">
      <q-btn flat label="닫기" color="primary" @click="closeModal" />
    </q-card-actions>
  </q-card>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps(['inquiry']);
const emit = defineEmits(['close']);

const closeModal = () => {
  emit('close');
};
</script>

<style scoped>
.custom-card {
  width: 100%;
  max-width: 800px;
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
