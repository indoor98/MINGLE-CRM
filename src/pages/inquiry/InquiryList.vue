<template>
  <div>
    <div class="text-h4">전체 문의 목록</div>
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
        <q-select
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
        />
        <q-btn
          color="primary"
          label="검색"
          @click="fetchInquiriesSearch"
          class="q-ml-sm search-btn"
        />
      </div>
    </section>
    <!-- 검색 입력 필드 생성
      v-model을 통해 filter 변수와 양방향 바인딩
      사용자가 입력한 검색어를 filter 변수에 저장
      @request 이벤트 : 사용자가 페이지를 변경할 때마다 fetchInquiries 함수 호출-->
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
      v-model="pagination.page"
      :max="Math.min(5, maxPages)"
      @page-change="fetchInquiries"
      color="primary"
      boundary-links
      class="q-mt-md"
      layout="pages"
    /> -->
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { api as axios } from "src/boot/axios";
// import axios from "axios";
import { useRouter } from "vue-router";
import { useTokenStore } from "src/stores/token-store";

const inquiries = ref([]); // 데이터를 저장하는 반응형 변수
const loading = ref(false); // 데이터 로딩 상태를 나타냄
const filter = ref(""); // 검색 입력 값을 저장
const pagination = ref({ page: 1, rowsPerPage: 20, rowsNumber: 0 }); // 페이징 정보를 저장하는 반응형 변수 초기값 설정
const maxPages = ref(1); // 전체 페이지 수를 저장하는 변수

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
    label: "이름",
    field: "customerName",
    sortable: true,
  },
  {
    name: "customerPhone",
    align: "left",
    label: "전화번호",
    field: "customerPhone",
    sortable: true,
  },
  {
    name: "date",
    align: "left",
    label: "문의 날짜",
    field: "date",
    format: (val) => new Date(val).toLocaleString(),
    sortable: true,
  },
  {
    name: "type",
    align: "left",
    label: "문의 타입",
    field: "type",
    sortable: true,
  },
  {
    name: "isReply",
    align: "left",
    label: "답변 여부",
    field: "isReply",
    format: (val) => (val ? "Yes" : "No"),
    sortable: true,
  },
  {
    name: "employName",
    align: "left",
    label: "직원 이름",
    field: (row) => (row.employName ? row.employName : "답변 직원 없음"),
    sortable: true,
  },
  {
    name: "inquiryTitle",
    align: "left",
    label: "문의 제목",
    field: "inquiryTitle",
    sortable: true,
  },
  {
    name: "actionStatus",
    align: "left",
    label: "조치 상태",
    field: (row) => (row.actionStatus ? row.actionStatus : "조치 상태 없음"),
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

// 데이터 요청 함수
const fetchInquiries = async () => {
  loading.value = true; // 데이터 요청할 때 로딩 상태 true로 설정
  try {
    const response = await axios.get("/api/v1/inquiries", {
      params: {
        page: pagination.value.page - 1, // 백엔드 페이지 번호가 0부터 시작
        size: pagination.value.rowsPerPage,
      },
    });

    inquiries.value = response.data.data.content;
    pagination.value.page = response.data.data.number + 1;
    pagination.value.rowsPerPage = response.data.data.size;
    pagination.value.rowsNumber = response.data.data.totalElements;

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
      params.append("isReply", JSON.stringify(searchParams.value.isReply));
    }
    if (searchParams.value.actionStatus)
      params.append("actionStatus", searchParams.value.actionStatus);

    // 로그: 요청 전에 로그 찍기
    console.log("요청 보낼 파라미터:", params.toString());

    const response = await axios.get(
      `/api/v1/inquiries/search?${params.toString()}`,
      {
        headers: {
          Authorization: `Bearer ${useTokenStore().getAtk}`, // store.atk는 토큰 상태를 나타내는 변수
        },
      }
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

onMounted(fetchInquiries); // 컴포넌트가 마운트될 때 fetchInquiries 함수 호출

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

.text-h4 {
  text-align: center;
}

.search-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 1rem;
  background-color: #f9f9f9;
  border-radius: 0.5rem;
  margin-bottom: 1rem;
  border: 1px solid #dcdcdc; /* 컨테이너 선을 잘 보이게 추가 */
  margin-left: 0rem; /* 좌측 여백 */
  margin-right: 0rem; /* 우측 여백 */
}

.search-fields-container {
  display: flex;
  flex-wrap: wrap;
  width: 100%;
}

.search-field {
  flex: 1 1 calc(25% - 1rem);
  margin-right: 1rem;
  margin-bottom: 1rem;
}

.date-type-search-container {
  display: flex;
  flex-wrap: nowrap;
  width: 100%;
}

.date-type-search-container .search-field {
  flex: 1 1 calc(20% - 1rem);
  margin-right: 1rem;
}

.search-btn {
  flex: 0 0 auto;
  margin: 1rem;
  height: 35px; /* 버튼의 높이 조정 */
  line-height: 32px; /* 텍스트가 버튼의 가운데에 오도록 설정 */
  padding: 0 16px; /* 좌우 여백 추가 */
  font-size: 14px; /* 버튼의 폰트 크기 조정 */
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
