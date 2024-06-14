<template>
  <div>
    <div class="text-h4">전체 문의 목록</div>
    <q-input filled v-model="filter" label="Search" class="q-mb-md" />
    <!-- 검색 입력 필드 생성
      v-model을 통해 filter 변수와 양방향 바인딩
      사용자가 입력한 검색어를 filter 변수에 저장
      @request 이벤트 : 사용자가 페이지를 변경할 때마다 fetchInquiries 함수 호출-->
    <q-table
      :rows="filteredInquiries"
      :columns="columns"
      row-key="id"
      :loading="loading"
      pagination.sync="pagination"
      @request="fetchInquiries"
      @row-click="onRowClick"
    />
    <q-pagination
      v-model="pagination.page"
      :max="Math.min(5, maxPages)"
      @page-change="fetchInquiries"
      color="primary"
      boundary-links
      class="q-mt-md"
      layout="pages"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const inquiries = ref([]); // 데이터를 저장하는 반응형 변수
const loading = ref(false); // 데이터 로딩 상태를 나타냄
const filter = ref(""); // 검색 입력 값을 저장
const pagination = ref({ page: 1, rowsPerPage: 30, rowsNumber: 0 }); // 페이징 정보를 저장하는 반응형 변수 초기값 설정
const maxPages = ref(1); // 전체 페이지 수를 저장하는 변수

// 컬럼 정의
const columns = [
  {
    name: "id",
    required: true,
    label: "ID",
    align: "left",
    field: "id",
    format: (val) => `${val}`,
    sortable: true,
  },
  {
    name: "customerName",
    align: "left",
    label: "Customer Name",
    field: "customerName",
    sortable: true,
  },
  {
    name: "customerPhone",
    align: "left",
    label: "Customer Phone",
    field: "customerPhone",
    sortable: true,
  },
  {
    name: "date",
    align: "left",
    label: "Date",
    field: "date",
    format: (val) => new Date(val).toLocaleString(),
    sortable: true,
  },
  { name: "type", align: "left", label: "Type", field: "type", sortable: true },
  {
    name: "isReply",
    align: "left",
    label: "Reply Status",
    field: "isReply",
    format: (val) => (val ? "Yes" : "No"),
    sortable: true,
  },
  {
    name: "employName",
    align: "left",
    label: "Employee Name",
    field: "employName",
    sortable: true,
  },
  {
    name: "inquiryTitle",
    align: "left",
    label: "Inquiry Title",
    field: "inquiryTitle",
    sortable: true,
  },
  {
    name: "inquiryContent",
    align: "left",
    label: "Inquiry Content",
    field: "inquiryContent",
    sortable: true,
  },
  {
    name: "actionStatus",
    align: "left",
    label: "Action Status",
    field: "actionStatus",
    sortable: true,
  },
];

const router = useRouter();

// 데이터 요청 함수
const fetchInquiries = async () => {
  loading.value = true; // 데이터 요청할 때 로딩 상태 true로 설정
  try {
    const response = await axios.get("http://localhost:8080/api/v1/inquiries", {
      params: {
        page: pagination.value.page - 1, // 백엔드 페이지 번호가 0부터 시작
        size: pagination.value.rowsPerPage,
      },
    });

    const { content, totalElements, totalPages } = response.data.data;
    console.log("응답 데이터:", response.data.data);
    console.log("페이지:", pagination.value.page);
    console.log("한 페이지당 항목 수:", pagination.value.rowsPerPage);
    console.log("총 항목 수:", totalElements);
    console.log("총 페이지 수:", totalPages);

    inquiries.value = content.map((item) => ({
      id: item.id,
      customerName: item.customerName,
      customerPhone: item.customerPhone,
      date: new Date(item.date).toLocaleString(),
      type: item.type,
      isReply: item.isReply,
      employName: item.employName,
      inquiryTitle: item.inquiryTitle,
      inquiryContent: item.inquiryContent,
      actionStatus: item.actionStatus,
    })); // 응답 데이터에서 목록 추출
    // pagination.value.rowsNumber = response.data.data.totalElements; // 전체 데이터의 개수를 pagination 객체에 저장
    // maxPages.value = Math.ceil(
    //   data.totalElements / pagination.value.rowsPerPage
    // );

    pagination.value.rowsNumber = totalElements;
    // pagination.value.page = totalPages; // 현재 페이지 설정
    maxPages.value = totalPages; // 최대 페이지 수 설정
    console.log("데이터 로드 완료:", response.data.data);
    console.log("페이지 정보:", pagination.value);

    console.log("페이지:", pagination.value.page);
    console.log("한 페이지당 항목 수:", pagination.value.rowsPerPage);
    console.log("총 항목 수:", pagination.value.rowsNumber);
  } catch (error) {
    console.error("문의를 가져오지 못했습니다. :", error);
  } finally {
    loading.value = false; // 로딩 상태 종료
  }
};

const onRowClick = (event, row) => {
  console.log("선택한 문의 상세 정보:", row);
  if (row && row.id) {
    router.push({ name: "InquiryDetailPage", params: { inquiryId: row.id } });
  } else {
    console.error("유효하지 않은 문의입니다. :", row);
  }
};

onMounted(fetchInquiries); // 컴포넌트가 마운트될 때 fetchInquiries 함수 호출

watch(
  // pagination.value.page 와 pagination.value.rowsPerPage가 변경될 때마다 fetchInquiries 함수 호출
  [() => pagination.value.page, () => pagination.value.rowsPerPage],
  fetchInquiries,
  { immediate: true } // 컴포넌트가 마운트될 때 바로 데이터 로드
);

// computed 속성 : filteredInquiries - 필터링된 목록을 반환하는 계산된 속성
const filteredInquiries = computed(() => {
  if (!filter.value) {
    return inquiries.value; // 필터가 비어 있으면 전체 inquiries 반환
  }
  const lowerCaseFilter = filter.value.toLowerCase();
  return inquiries.value.filter(
    (
      inquiry // 각 inquiry 항목의 속성을 필터링
    ) =>
      inquiry.customerName.toLowerCase().includes(lowerCaseFilter) ||
      inquiry.customerPhone.toLowerCase().includes(lowerCaseFilter) ||
      inquiry.inquiryTitle.toLowerCase().includes(lowerCaseFilter) ||
      inquiry.inquiryContent.toLowerCase().includes(lowerCaseFilter)
    // 고객이름, 번호, 제목, 내용 값을 소문자로 변환 후, 검색어(filter.value)가 포함되어 있는지 확인
    // .toLowerCase() 사용하여 대소문자 구분하지 않음.
  );
});
</script>

<style lang="scss" scoped>
/* 직접 변수 값을 설정 */
$primary-color: #007bff;
$secondary-color: #6c757d;

.q-pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;

  .q-pagination__list {
    display: flex;
    justify-content: center;
  }

  .q-pagination__item {
    margin: 0 5px;
    &.q-pagination__item--active {
      background-color: $primary-color; // 활성 페이지 버튼 색상
      color: white;
      border-radius: 50%;
    }
  }

  .q-pagination__item:hover {
    background-color: $secondary-color;
  }
}
</style>
