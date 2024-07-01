<template>
  <q-page class="container">
    <q-card class="my-card">
      <q-card-section class="row justify-center q-pa-xs">
        <div class="col-12 col-md-4 q-pa-sm">
          <q-input
            v-model="searchName"
            clearable
            filled
            color="purple-12"
            label="이름"
            dense
            placeholder="이름을 입력하세요"
          />
        </div>

        <div class="col-12 col-md-3 q-pa-sm">
          <q-select
            v-model="selectedRole"
            filled
            color="purple-12"
            label="역할"
            :options="roleOptions"
            emit-value
            map-options
            dense
            placeholder="선택"
          />
        </div>

        <div class="col-12 col-md-2 q-pa-sm">
          <q-btn
            color="primary"
            label="검색"
            @click="executeSearch"
            dense
            class="full-width"
          />
        </div>
      </q-card-section>
    </q-card>

    <q-card class="q-mt-md my-card">
      <q-card-section class="q-pa-none">
        <div class="q-table-container">
          <q-table
            flat
            bordered
            title="직원 요청 목록"
            :rows="requests"
            :columns="columns"
            row-key="email"
            v-model:pagination="pagination"
            @request="onRequest"
            hide-pagination
          >
            <template v-slot:body="props">
              <q-tr :props="props" class="q-table-row">
                <q-td v-for="col in columns" :key="col.name" :props="props">
                  <div v-if="col.name === 'action'" class="flex-center">
                    <q-btn
                      color="negative"
                      @click="confirmReject(props.row)"
                      label="거절"
                      dense
                    />
                    <q-btn
                      color="primary"
                      @click="confirmApprove(props.row)"
                      label="승인"
                      dense
                    />
                  </div>
                  <div v-else>
                    {{ props.row[col.field] }}
                  </div>
                </q-td>
              </q-tr>
            </template>
          </q-table>
        </div>
      </q-card-section>
    </q-card>

    <div class="q-pa-lg flex flex-center">
      <q-pagination
        v-model="pagination.page"
        :max="maxPage"
        @update:model-value="onPageChange"
        input
        input-class="text-orange-10"
      />
    </div>

    <q-btn
      class="back-to-top-btn"
      fab
      icon="arrow_upward"
      @click="scrollToTop"
      title="맨 위로 이동"
    />
  </q-page>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { api as axios } from "src/boot/axios";
import { useQuasar } from "quasar";
import { format, formatDistanceToNow, parseISO, isBefore, subHours } from 'date-fns';
import { ko } from 'date-fns/locale';

const $q = useQuasar();
const router = useRouter();
const searchName = ref("");
const selectedRole = ref(null);
const requests = ref([]);
const pagination = ref({
  page: 1,
  rowsPerPage: 20,
  rowsNumber: 0
});

const roleMap = {
  'ROLE_MANAGER': '매니저',
  'ROLE_CONSULTANT': '상담사',
  'ROLE_MARKETER': '마케터'
};

const statusMap = {
  'PENDING': '대기중',
  'APPROVED': '승인됨',
  'REJECTED': '거절됨'
};

const translateRole = (role) => roleMap[role] || role;
const translateStatus = (status) => statusMap[status] || status;

const formatDate = (dateString) => {
  return format(parseISO(dateString), 'yyyy-MM-dd');
};

const formatTimeOrRelative = (dateString) => {
  const date = parseISO(dateString);
  const twentyFourHoursAgo = subHours(new Date(), 24);
  if (isBefore(date, twentyFourHoursAgo)) {
    return format(date, 'yyyy-MM-dd HH:mm:ss');
  }
  return formatDistanceToNow(date, { addSuffix: true, locale: ko });
};

const fetchRequests = async (page = 1, useSearchEndpoint = false) => {
  try {
    const params = new URLSearchParams();
    params.append('page', page - 1);
    params.append('size', pagination.value.rowsPerPage);
    if (searchName.value) params.append('name', searchName.value);
    if (selectedRole.value) params.append('requestedRole', selectedRole.value);

    const endpoint = useSearchEndpoint ? '/api/v1/admin/registers/search' : '/api/v1/admin/registers';

    const response = await axios.get(endpoint, { params });
    requests.value = response.data.content.map(request => {
      return {
        ...request,
        requestedRole: request.requestedRole,
        status: translateStatus(request.status),
        displayTime: formatTimeOrRelative(request.registrationRequestTime)
      };
    });
    pagination.value.page = response.data.number + 1;
    pagination.value.rowsPerPage = response.data.size;
    pagination.value.rowsNumber = response.data.totalElements;
  } catch (error) {
    console.error("Error fetching requests:", error);
  }
};

const maxPage = computed(() => Math.ceil(pagination.value.rowsNumber / pagination.value.rowsPerPage));

onMounted(() => {
  fetchRequests(1, false);
});

const executeSearch = () => {
  pagination.value.page = 1; // 검색을 다시 시작할 때 페이지를 1로 초기화
  fetchRequests(1, true);
};

const onRequest = (params) => {
  const { page, rowsPerPage } = params.pagination;
  pagination.value.page = page;
  pagination.value.rowsPerPage = rowsPerPage;
  fetchRequests(page);
};

const onPageChange = (page) => {
  pagination.value.page = page;
  fetchRequests(page);
};

const columns = [
  { name: 'name', label: '이름', align: 'left', field: 'name', sortable: true },
  { name: 'email', label: '이메일', align: 'left', field: 'email', sortable: true },
  { name: 'requestedRole', label: '요청된 역할', align: 'center', field: 'requestedRole', sortable: true },
  { name: 'status', label: '상태', align: 'center', field: 'status', sortable: true },
  { name: 'displayTime', label: '요청 시간', align: 'center', field: 'displayTime', sortable: true },
  { name: 'action', label: '액션', align: 'center' }
];

const roleOptions = [
  { label: '선택 안 함', value: '' },
  { label: '매니저', value: 'ROLE_MANAGER' },
  { label: '상담사', value: 'ROLE_CONSULTANT' },
  { label: '마케터', value: 'ROLE_MARKETER' }
];

const updateRequestStatus = async (request, action) => {
  try {
    const currentUser = router.currentRoute.value.params.currentUser;

    console.log('Sending request:', {
      signUpRequest: {
        name: request.name,
        email: request.email,
        password: request.password,
        authority: request.requestedRole
      },
      registrationRequest: {
        status: action,
        currentUser
      }
    });

    await axios.post(`/api/v1/admin/registers/updateStatus?action=${action}`, {
      signUpRequest: {
        name: request.name,
        email: request.email,
        password: request.password,
        authority: request.requestedRole
      },
      registrationRequest: {
        status: action,
        currentUser
      }
    }, {
      headers: {
        'Content-Type': 'application/json'
      }
    });

    await fetchRequests();
  } catch (error) {
    console.error(`Error ${action} request:`, error);
  }
};

const confirmApprove = (request) => {
  console.log('Approve request:', request); // 디버깅 로그 추가
  $q.dialog({
    title: '승인 확인',
    message: '승인하시겠습니까?',
    cancel: true,
    persistent: true
  }).onOk(() => {
    updateRequestStatus(request, 'approve');
  });
};

const confirmReject = (request) => {
  console.log('Reject request:', request); // 디버깅 로그 추가
  $q.dialog({
    title: '거절 확인',
    message: '거절하시겠습니까?',
    cancel: true,
    persistent: true
  }).onOk(() => {
    updateRequestStatus(request, 'reject');
  });
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  min-height: 100vh;
}

.my-card {
  width: 100%;
  max-width: 1000px;
  margin: 16px auto; /* 위 아래 카드 간격 설정 */
}

.q-table-container {
  max-width: 100%;
  overflow-x: auto; /* 가로 스크롤바 설정 */
}

.full-width {
  width: 100%;
}

.q-mt-md {
  margin-top: 16px;
}

.back-to-top-btn {
  position: fixed;
  bottom: 30px;
  right: 60px; /* 버튼을 왼쪽으로 이동 */
  transform: translateX(-70%); /* 정확한 가운데 정렬 */
  width: 56px; /* 버튼 크기 조정 */
  height: 56px; /* 버튼 크기 조정 */
}

.q-pa-lg {
  padding: 16px;
}

.flex-center {
  display: flex;
  justify-content: center;
  align-items: center;
}

.q-table-row {
  cursor: pointer; /* 마우스를 올리면 클릭할 수 있는 것처럼 보이도록 */
}

.q-btn {
  margin: 0 5px;
  border-radius: 4px; /* 버튼을 둥글게 */
  text-transform: uppercase; /* 버튼 텍스트를 대문자로 */
  font-weight: bold; /* 버튼 텍스트를 굵게 */
  transition: background-color 0.3s; /* 배경색 전환 애니메이션 */
}

.q-btn:hover {
  background-color: #ffffff20; /* 호버 시 반투명 배경 */
}
</style>
