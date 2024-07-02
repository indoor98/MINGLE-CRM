<template>
  <q-page>
    <div class="q-pa-md">
      <h2 class="text-h4">전체 문의 목록</h2>
      <q-card class="my-card">
        <q-card-section class="row justify-center q-pa-xs">
          <div class="col q-pa-sm">
            <q-input
              v-model="searchParams.customerName"
              clearable
              filled
              color="purple-12"
              label="이름"
              dense
              placeholder="이름을 입력하세요"
            />
          </div>

          <div class="col q-pa-sm">
            <q-input
              v-model="searchParams.customerPhone"
              clearable
              filled
              color="purple-12"
              label="전화번호"
              dense
              placeholder="전화번호를 입력하세요"
            />
          </div>

          <div class="col q-pa-sm">
            <q-input
              v-model="searchParams.inquiryTitle"
              clearable
              filled
              color="purple-12"
              label="문의 제목"
              dense
              placeholder="문의 제목을 입력하세요"
            />
          </div>

          <div class="col q-pa-sm">
            <q-input
              v-model="searchParams.inquiryContent"
              clearable
              filled
              color="purple-12"
              label="문의 내용"
              dense
              placeholder="문의 내용을 입력하세요"
            />
          </div>

          <div class="col q-pa-sm">
            <q-input
              v-model="searchParams.startDate"
              clearable
              filled
              color="purple-12"
              label="시작일"
              dense
              placeholder="시작일을 입력하세요"
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
                        <q-btn
                          v-close-popup
                          label="선택"
                          color="primary"
                          flat
                        />
                      </div>
                    </q-date>
                  </q-popup-proxy>
                </q-icon>
              </template>
            </q-input>
          </div>

          <div class="col q-pa-sm">
            <q-input
              v-model="searchParams.endDate"
              clearable
              filled
              color="purple-12"
              label="종료일"
              dense
              placeholder="종료일을 입력하세요"
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
                        <q-btn
                          v-close-popup
                          label="선택"
                          color="primary"
                          flat
                        />
                      </div>
                    </q-date>
                  </q-popup-proxy>
                </q-icon>
              </template>
            </q-input>
          </div>

          <div class="col q-pa-sm">
            <q-select
              v-model="searchParams.type"
              filled
              color="purple-12"
              label="문의 타입"
              :options="typeOptions"
              emit-value
              map-options
              dense
              placeholder="선택"
            />
          </div>

          <div class="col q-pa-sm">
            <q-select
              v-model="searchParams.isReply"
              filled
              color="purple-12"
              label="답변 여부"
              :options="replyOptions"
              emit-value
              map-options
              dense
              placeholder="선택"
            />
          </div>

          <div class="col q-pa-sm">
            <q-select
              v-model="searchParams.actionStatus"
              filled
              color="purple-12"
              label="조치 상태"
              :options="actionStatusOptions"
              emit-value
              map-options
              dense
              placeholder="선택"
            />
          </div>

          <div class="col-auto q-pa-sm">
            <q-btn
              color="primary"
              label="검색"
              @click="fetchInquiriesSearch"
              dense
              class="full-width"
              style="min-width: 50px; max-width: 100px"
            />
          </div>
        </q-card-section>
      </q-card>

      <q-card class="q-mt-md">
        <q-card-section>
          <q-table
            :rows="filteredInquiries"
            :columns="columns"
            row-key="id"
            :loading="loading"
            v-model:pagination="pagination"
            @request="onRequest"
            @row-click="onRowClick"
          >
            <template v-slot:no-data>
              <q-tr>
                <q-td :colspan="columns.length" class="text-center">
                  검색 결과가 없습니다.
                </q-td>
              </q-tr>
            </template>
          </q-table>
        </q-card-section>
      </q-card>
      <q-card class="q-mt-md">
        <q-card-section v-if="errorMessage">
          <p style="color: red" class="text-center">{{ errorMessage }}</p>
        </q-card-section>
      </q-card>
    </div>
  </q-page>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import { api as axios } from "src/boot/axios";
import { useRouter } from "vue-router";
import { useTokenStore } from "src/stores/token-store";

const inquiries = ref([]);
const loading = ref(false);
const filter = ref("");
const pagination = ref({ page: 1, rowsPerPage: 20, rowsNumber: 0 });
const maxPages = ref(1);
const errorMessage = ref("");

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

const fetchInquiries = async () => {
  loading.value = true;
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
    errorMessage.value = "";

    console.log("데이터 로드 완료:", response.data.data);
    console.log("페이지 정보:", pagination.value);

    console.log("페이지:", pagination.value.page);
    console.log("한 페이지당 항목 수:", pagination.value.rowsPerPage);
    console.log("총 항목 수:", pagination.value.rowsNumber);
  } catch (error) {
    console.error("문의를 가져오지 못했습니다. :", error);
    errorMessage.value = "문의 목록을 불러오는 중 문제가 발생했습니다.";
  } finally {
    loading.value = false; // 로딩 상태 종료
  }
};

const fetchInquiriesSearch = async () => {
  loading.value = true;
  try {
    const params = new URLSearchParams();
    params.append("page", pagination.value.page - 1);
    params.append("size", pagination.value.rowsPerPage);

    if (searchParams.value.customerName)
      params.append("customerName", searchParams.value.customerName);
    if (searchParams.value.customerPhone)
      params.append("customerPhone", searchParams.value.customerPhone);
    if (searchParams.value.inquiryTitle)
      params.append("inquiryTitle", searchParams.value.inquiryTitle);
    if (searchParams.value.inquiryContent)
      params.append("inquiryContent", searchParams.value.inquiryContent);

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
      params.append("actionStatus", searchParams.value.actionStatus.value);
    console.log(searchParams.value.actionStatus.value);

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
    errorMessage.value = "";
  } catch (error) {
    console.error("문의를 가져오지 못했습니다. :", error);
    errorMessage.value = "문의를 검색하는 중 문제가 발생했습니다.";
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

.my-card {
  margin-bottom: 20px;
}

.text-h4 {
  text-align: center;
  font-size: 1.5rem;
  font-weight: bold;
  margin-top: 0;
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
