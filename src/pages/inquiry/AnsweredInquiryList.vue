<template>
  <div>
    <div class="text-h4">답변 있는 문의</div>
    <section class="search-container row q-col-gutter-xl flex-center q-pa-xs">
      <div class="search-fields-container row q-col-gutter-sm q-mb-md">
        <q-input
          v-model="searchParams.customerName"
          label="이름"
          filled
          class="search-field"
        />
        <q-input
          v-model="searchParams.customerPhone"
          label="전화번호"
          filled
          class="search-field"
        />
        <q-input
          v-model="searchParams.inquiryTitle"
          label="문의 제목"
          filled
          class="search-field"
        />
        <q-input
          v-model="searchParams.inquiryContent"
          label="문의 내용"
          filled
          class="search-field"
        />
        <q-input
          v-model="searchParams.keyword"
          label="검색어"
          filled
          class="search-field"
        />
        <q-input
          v-model="searchParams.startDate"
          label="시작일"
          filled
          class="search-field"
        >
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer">
              <q-popup-proxy
                cover
                transition-show="scale"
                transition-hide="scale"
              >
                <q-date v-model="searchParams.startDate" mask="YYYY-MM-DD">
                  <div class="row items-center justify-end">
                    <q-btn v-close-popup label="Close" color="primary" flat />
                  </div>
                </q-date>
              </q-popup-proxy>
            </q-icon>
          </template>
        </q-input>

        <q-input
          v-model="searchParams.endDate"
          label="종료일"
          filled
          class="search-field"
        >
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer">
              <q-popup-proxy
                cover
                transition-show="scale"
                transition-hide="scale"
              >
                <q-date v-model="searchParams.endDate" mask="YYYY-MM-DD">
                  <div class="row items-center justify-end">
                    <q-btn v-close-popup label="Close" color="primary" flat />
                  </div>
                </q-date>
              </q-popup-proxy>
            </q-icon>
          </template>
        </q-input>
        <q-select
          v-model="searchParams.type"
          :options="typeOptions"
          label="문의 타입"
          class="search-field"
        />
        <!-- <q-select
          v-model="searchParams.isReply"
          :options="replyOptions"
          label="답변 여부"
          class="search-field"
        />
        <q-select
          v-model="searchParams.actionStatus"
          :options="actionStatusOptions"
          label="조치 상태"
          class="search-field"
        /> -->
        <q-btn
          color="primary"
          label="Search"
          @click="fetchInquiriesSearch"
          class="q-ml-sm search-btn"
        />
      </div>
    </section>
    <q-table
      :rows="filteredInquiries"
      :columns="columns"
      row-key="id"
      :loading="loading"
      v-model:pagination="pagination"
      @request="onRequest"
      @row-click="onRowClick"
    />
    <!-- <q-pagination
      :page="pagination.page"
      :rows-per-page="pagination.rowsPerPage"
      :rows-number="pagination.rowsNumber"
      @page-change="fetchInquiries"
      class="q-mt-md"
      color="primary"
      boundary-links
      :max-pages="Math.ceil(pagination.rowsNumber / pagination.rowsPerPage)"
      layout="pages"
    /> -->
    <div v-if="error" class="q-mt-md q-pa-md q-mb-md q-bg-red-2 q-text-white">
      <span>{{ error }}</span>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { api as axios } from "src/boot/axios";
import { useRouter } from "vue-router";

const inquiries = ref([]);
const loading = ref(false);
const filter = ref("");
const pagination = ref({ page: 1, rowsPerPage: 20, rowsNumber: 0 });
const error = ref(null);

const searchParams = ref({
  customerName: "",
  customerPhone: "",
  inquiryTitle: "",
  inquiryContent: "",
  keyword: "",
  startDate: "",
  endDate: "",
  type: "",
  isReply: null,
  actionStatus: "",
});

const typeOptions = ["온라인 문의", "전화 문의", "방문 문의"];
const replyOptions = [
  { label: "Yes", value: true },
  { label: "No", value: false },
];
const actionStatusOptions = [
  { label: "조치 불필요", value: "조치 불필요" },
  { label: "조치 전", value: "조치 전" },
  { label: "조치 후", value: "조치 후" },
];

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
  // {
  //   name: "inquiryContent",
  //   align: "left",
  //   label: "Inquiry Content",
  //   field: "inquiryContent",
  //   sortable: true,
  // },
  {
    name: "actionStatus",
    align: "left",
    label: "Action Status",
    field: "actionStatus",
    sortable: true,
  },
];

const router = useRouter();

const onRequest = (params) => {
  const { page, rowsPerPage } = params.pagination;
  pagination.value.page = page;
  pagination.value.rowsPerPage = rowsPerPage;

  if (hasSearchCriteria()) {
    fetchInquiriesSearch();
  } else {
    fetchInquiries();
  }
};

const hasSearchCriteria = () => {
  const {
    customerName,
    customerPhone,
    inquiryTitle,
    inquiryContent,
    keyword,
    startDate,
    endDate,
    type,
    isReply,
    actionStatus,
  } = searchParams.value;

  return (
    customerName ||
    customerPhone ||
    inquiryTitle ||
    inquiryContent ||
    keyword ||
    (startDate && endDate) ||
    type ||
    isReply !== null ||
    actionStatus
  );
};

const fetchInquiries = async () => {
  loading.value = true;
  error.value = null;
  try {
    const response = await axios.get(
      "http://localhost:8080/api/v1/inquiries/answered",
      {
        params: {
          page: pagination.value.page - 1,
          size: pagination.value.rowsPerPage,
        },
      }
    );

    inquiries.value = response.data.data.content;
    pagination.value.page = response.data.data.number + 1;
    pagination.value.rowsPerPage = response.data.data.size;
    pagination.value.rowsNumber = response.data.data.totalElements;
  } catch (error) {
    console.error("문의를 가져오지 못했습니다. :", error);
    error.value = "문의를 가져오는데 실패했습니다. 잠시 후 다시 시도해주세요.";
  } finally {
    loading.value = false;
  }
};

// 검색 조건을 기반으로 데이터를 요청하는 함수
const fetchInquiriesSearch = async () => {
  loading.value = true; // 데이터 요청할 때 로딩 상태 true로 설정
  try {
    const params = new URLSearchParams();
    params.append("page", pagination.value.page - 1);
    params.append("size", pagination.value.rowsPerPage);
    params.append("customerName", searchParams.value.customerName);
    params.append("customerPhone", searchParams.value.customerPhone);
    params.append("inquiryTitle", searchParams.value.inquiryTitle);
    params.append("inquiryContent", searchParams.value.inquiryContent);
    params.append("keyword", searchParams.value.keyword);

    // 대괄호를 직접 인코딩하여 추가
    if (searchParams.value.startDate) {
      params.append("startDate", searchParams.value.startDate + "T00:00:00");
    }
    if (searchParams.value.endDate) {
      params.append("endDate", searchParams.value.endDate + "T23:59:59");
    }
    if (searchParams.value.type) params.append("type", searchParams.value.type);
    if (searchParams.value.isReply !== null) {
      params.append("isReply", searchParams.value.isReply);
    }
    if (searchParams.value.actionStatus)
      params.append("actionStatus", searchParams.value.actionStatus);

    const response = await axios.get(
      `/api/v1/inquiries/search?${params.toString()}`
    );
    console.log("응답 데이터:", response.data.data);

    if (!response.data.data || !response.data.data.content) {
      console.error("응답 데이터에서 필요한 내용이 없습니다.");
      return;
    }

    inquiries.value = response.data.data.content;
    pagination.value.page = response.data.data.number + 1;
    pagination.value.rowsPerPage = response.data.data.size;
    pagination.value.rowsNumber = response.data.data.totalElements;
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

onMounted(fetchInquiries);

const filteredInquiries = computed(() => {
  if (!filter.value) {
    return inquiries.value;
  }
  const lowerCaseFilter = filter.value.toLowerCase();
  return inquiries.value.filter(
    (inquiry) =>
      inquiry.customerName.toLowerCase().includes(lowerCaseFilter) ||
      inquiry.customerPhone.toLowerCase().includes(lowerCaseFilter) ||
      inquiry.inquiryTitle.toLowerCase().includes(lowerCaseFilter) ||
      inquiry.inquiryContent.toLowerCase().includes(lowerCaseFilter)
  );
});
</script>

<style lang="scss" scoped>
$primary-color: #007bff;
$secondary-color: #6c757d;

.search-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 1rem;
  background-color: #f9f9f9;
  border-radius: 0.5rem;
  margin-bottom: 1rem;
}

.search-fields-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.search-field {
  flex: 1 1 15%;
  margin-right: 1rem;
}

.search-btn {
  flex: 0 0 auto;
  margin-left: 1rem;
}

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
      background-color: $primary-color;
      color: white;
      border-radius: 50%;
    }
  }

  .q-pagination__item:hover {
    background-color: $secondary-color;
  }
}

.error-message {
  background-color: #ff4d4f;
  color: white;
  padding: 10px;
  border-radius: 5px;
  text-align: center;
  margin-top: 10px;
}
</style>
