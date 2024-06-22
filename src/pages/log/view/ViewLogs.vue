<template>
  <q-page class="q-pa-md">
    <q-card class="q-pa-md q-mb-md">
      <q-card-section>
        <div class="filter-container">
          <div class="filter-row">
            <!-- 왼쪽 영역: 직원 관련 필드 -->
            <div class="left-section">
              <q-input v-model="search.employeeName" label="직원 이름" outlined dense class="filter-input"/>
              <q-input v-model="search.employeeEmail" label="직원 이메일" outlined dense class="filter-input"/>
              <q-select
                v-model="search.employeeGrade"
                :options="employeeGrades"
                label="직원 직급"
                outlined
                dense
                class="filter-select"
              />
            </div>
            <!-- 오른쪽 영역: 고객 관련 필드 -->
            <div class="right-section">
              <q-input v-model="search.customerName" label="고객 이름" outlined dense class="filter-input"/>
              <q-input v-model="search.customerEmail" label="고객 이메일" outlined dense class="filter-input"/>
              <q-select
                v-model="search.customerGrade"
                :options="customerGrades"
                label="고객 등급"
                outlined
                dense
                class="filter-select"
              />
            </div>
          </div>
          <div class="filter-row full-width">
            <!-- 검색 버튼 추가 -->
            <q-btn @click="fetchData" label="검색" color="primary" class="q-mt-md" dense/>
          </div>
        </div>
      </q-card-section>
    </q-card>

    <!-- 조회 결과 테이블 -->
    <div class="table-container">
      <q-table
        :rows="viewLogs"
        :columns="columns"
        row-key="id"
        :pagination="pagination"
      >
        <template #body-cell-viewTime="props">
          <q-td :props="props">
            {{ formatDateTime(props.row.viewTime) }}
          </q-td>
        </template>
      </q-table>
    </div>
  </q-page>
</template>

<script>
import { ref, onMounted } from 'vue';
import { api as axios } from "src/boot/axios";

export default {
  setup() {
    const viewLogs = ref([]);
    const columns = [
      { name: 'id', label: 'ID', align: 'left', field: 'id' },
      { name: 'employeeName', label: '직원 이름', align: 'left', field: 'employeeName' },
      { name: 'employeeEmail', label: '직원 이메일', align: 'left', field: 'employeeEmail' },
      { name: 'employeeGrade', label: '직원 직급', align: 'left', field: 'employeeGrade' },
      { name: 'customerName', label: '고객 이름', align: 'left', field: 'customerName' },
      { name: 'customerEmail', label: '고객 이메일', align: 'left', field: 'customerEmail' },
      { name: 'customerGrade', label: '고객 등급', align: 'left', field: 'customerGrade' },
      { name: 'viewTime', label: '조회 시간', align: 'left', field: 'viewTime' },
      { name: 'viewCount', label: '조회수', align: 'left', field: 'viewCount' }
    ];
    const pagination = {
      sortBy: 'id',
      descending: false,
      page: 1,
      rowsPerPage: 10
    };
    const search = ref({
      employeeName: '',
      employeeEmail: '',
      employeeGrade: null,
      customerName: '',
      customerEmail: '',
      customerGrade: null
    });
    const employeeGrades = [
      { label: '선택 안함', value: null },
      { label: 'Manager', value: 'Manager' },
      { label: 'Staff', value: 'Staff' },
      { label: 'Marketer', value: 'Marketer' },
      { label: 'Consultant', value: 'Consultant' }
    ];
    const customerGrades = [
      { label: '선택 안함', value: null },
      { label: 'New', value: 'New' },
      { label: 'VIP', value: 'VIP' },
      { label: 'VVIP', value: 'VVIP' },
      { label: 'BASIC', value: 'BASIC' }
    ];

    const fetchData = () => {
      axios.get('http://localhost:8080/api/v1/view-logs/search', { params: search.value })
        .then(response => {
          viewLogs.value = response.data;
        })
        .catch(error => {
          console.error('조회 로그를 불러오는 중 오류가 발생했습니다:', error);
        });
    };

    const formatDateTime = (dateTime) => {
      return new Date(dateTime).toLocaleString();
    };

    // 페이지 로드 시 자동으로 데이터 가져오기
    onMounted(() => {
      // 기본 데이터 불러오기
      axios.get('http://localhost:8080/api/v1/view-logs')
        .then(response => {
          viewLogs.value = response.data;
        })
        .catch(error => {
          console.error('기본 데이터를 불러오는 중 오류가 발생했습니다:', error);
        });
    });

    return {
      viewLogs,
      columns,
      pagination,
      search,
      employeeGrades,
      customerGrades,
      formatDateTime,
      fetchData
    };
  },
};
</script>

<style>
.q-page {
  max-width: 1200px;
  margin: auto;
}

.table-container {
  overflow-x: auto;
}

.filter-container {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.filter-row {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.filter-input, .filter-select {
  flex: 1; /* Inputs, selects 등의 너비를 균등하게 배분 */
  max-width: 100%; /* 최대 너비 100%로 설정 */
}

.full-width {
  width: 100%;
}

.q-mt-md {
  margin-top: 20px; /* 버튼과 위의 간격 조정 */
}

.left-section, .right-section {
  flex: 1; /* 좌우 영역을 균등하게 나누기 위해 추가 */
}

.cursor-pointer {
  cursor: pointer;
}

.q-gutter-sm {
  padding: 8px; /* 팝업 내부 간격 설정 */
}

.q-mt-sm {
  margin-top: 8px; /* 검색 및 닫기 버튼과의 간격 설정 */
}
</style>
