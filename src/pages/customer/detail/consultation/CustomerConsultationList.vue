<template>
  <div class="q-pa-md">
    <q-separator class="q-my-md"/>

    <!-- SearchInput 컴포넌트 사용 -->
    <SearchInput
      v-model="search"
      label="검색어를 입력해주세요"
      :searchFields="['customerName', 'customerPhone', 'date', 'type', 'inquiryTitle', 'inquiryContent']"
      @search="handleSearch"
    />

    <!-- 테이블로 고객 문의 목록 표시 -->
    <q-table
      flat
      bordered
      title=""
      :rows="filteredInquiries"
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
      <customer-consultation-detail :inquiry="selectedInquiry"/>
    </q-dialog>
  </div>
</template>

<script setup>
import {ref, computed, onMounted} from 'vue';
import axios from 'axios';
import {useRoute} from 'vue-router';
import Fuse from 'fuse.js';
import CustomerConsultationDetail from './CustomerConsultationDetail.vue';
import SearchInput from 'src/components/SearchInput.vue'; // SearchInput 컴포넌트 임포트

const route = useRoute();
const customerId = route.params.id;
const inquiries = ref([]);
const search = ref('');
const inquiryPagination = ref({
  rowsPerPage: 10,
  page: 1,
  pagesNumber: 1,
  isFirstPage: true,
  isLastPage: true
});
const showDialog = ref(false);
const selectedInquiry = ref({});
let fuse; // fuse.js 인스턴스

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

    // fuse.js 설정
    fuse = new Fuse(inquiries.value, {
      keys: ['customerName', 'customerPhone', 'date', 'type', 'inquiryTitle', 'inquiryContent'],
      threshold: 0.3 // 유사도 설정 (0.0 - 1.0, 낮을수록 엄격)
    });

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

const filteredInquiries = computed(() => {
  if (!search.value) {
    return inquiries.value;
  }
  return fuse.search(search.value).map(result => result.item);
});

const handleSearch = (searchTerm, searchFields) => {
  search.value = searchTerm;
};

onMounted(() => {
  fetchInquiries();
});

const inquiryColumns = [
  {name: 'id', label: '#', align: 'left', field: 'id'},
  {name: 'customerName', label: '고갹명', align: 'left', field: 'customerName'},
  {name: 'customerPhone', label: '전화번호', align: 'center', field: 'customerPhone'},
  {name: 'date', label: '문의 날짜', align: 'center', field: 'date'},
  {name: 'type', label: '종류', align: 'center', field: 'type'},
  {name: 'isReply', label: '답변여부', align: 'center', field: 'isReply'},
  {name: 'employName', label: '고객명', align: 'center', field: 'employName'},
  {name: 'inquiryTitle', label: '조치 제목', align: 'center', field: 'inquiryTitle'},
  {name: 'inquiryContent', label: '조치 내용', align: 'center', field: 'inquiryContent'},
  {name: 'actionStatus', label: '조치 상태', align: 'center', field: 'actionStatus'}
];
</script>

<style scoped>
/* 필요한 스타일을 추가할 수 있습니다. */
</style>
