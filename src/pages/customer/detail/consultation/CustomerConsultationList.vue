<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md" />

    <!-- 테이블로 고객 문의 목록 표시 -->
    <q-table
      flat
      bordered
      title="Customer Inquiries"
      :rows="inquiries"
      :columns="inquiryColumns"
      row-key="id"
      v-model:pagination="inquiryPagination"
    >
      <template v-slot:body="props">
        <q-tr :props="props" @click="showInquiryDetail(props.row)">
          <q-td v-for="col in inquiryColumns" :key="col.name" :props="props">
            {{ props.row[col.field] }}
          </q-td>
        </q-tr>
      </template>
    </q-table>

    <!-- 문의 상세 정보 다이얼로그 -->
    <q-dialog v-model="showDialog">
      <customer-consultation-detail :inquiry="selectedInquiry" />
    </q-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';
import CustomerConsultationDetail from './CustomerConsultationDetail.vue';

const route = useRoute();
const customerId = route.params.id;
const inquiries = ref([]);
const inquiryPagination = ref({
  rowsPerPage: 10,
  page: 1,
  pagesNumber: 1,
  isFirstPage: true,
  isLastPage: true
});
const showDialog = ref(false);
const selectedInquiry = ref({});

const fetchInquiries = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/customers/${customerId}/inquiries`);
    inquiries.value = response.data.data.content.map((inquiry) => ({
      id: inquiry.id,
      customerId: customerId,
      customerName: inquiry.customerName,
      customerPhone: inquiry.customerPhone,
      date: new Date(inquiry.date).toLocaleDateString(),
      type: inquiry.type,
      isReply: inquiry.isReply ? 'Yes' : 'No',
      employName: inquiry.employName || '-',
      inquiryTitle: inquiry.inquiryTitle,
      inquiryContent: inquiry.inquiryContent,
      actionStatus: inquiry.actionStatus || '-'
    }));

    // 페이지네이션 설정
    inquiryPagination.value.pagesNumber = Math.ceil(response.data.data.content.length / inquiryPagination.value.rowsPerPage);
    inquiryPagination.value.isFirstPage = inquiryPagination.value.page === 1;
    inquiryPagination.value.isLastPage = inquiryPagination.value.page === inquiryPagination.value.pagesNumber;
  } catch (error) {
    console.error('Error fetching inquiries:', error);
  }
};

const showInquiryDetail = (inquiry) => {
  selectedInquiry.value = inquiry;
  showDialog.value = true;
};

onMounted(() => {
  fetchInquiries();
});

const inquiryColumns = [
  { name: 'id', label: '#', align: 'left', field: 'id' },
  { name: 'customerName', label: 'Customer Name', align: 'left', field: 'customerName' },
  { name: 'customerPhone', label: 'Phone Number', align: 'center', field: 'customerPhone' },
  { name: 'date', label: 'Date', align: 'center', field: 'date' },
  { name: 'type', label: 'Type', align: 'center', field: 'type' },
  { name: 'isReply', label: 'Replied', align: 'center', field: 'isReply' },
  { name: 'employName', label: 'Employee', align: 'center', field: 'employName' },
  { name: 'inquiryTitle', label: 'Inquiry Title', align: 'center', field: 'inquiryTitle' },
  { name: 'inquiryContent', label: 'Inquiry Content', align: 'center', field: 'inquiryContent' },
  { name: 'actionStatus', label: 'Action Status', align: 'center', field: 'actionStatus' }
];
</script>

<style scoped>
/* 필요한 스타일을 추가할 수 있습니다. */
</style>
