<template>
  <q-page class="q-pa-md">
    <q-card class="q-pa-md q-mb-md">
      <q-card-section>
        <div class="filter-container">
          <div class="filter-row">
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
          <div class="filter-row">
            <div class="date-range">
              <!-- 시작 날짜 -->
              <q-input filled v-model="search.startDate" label="시작 날짜" outlined dense class="filter-input">
                <template v-slot:prepend>
                  <q-icon name="event" class="cursor-pointer">
                    <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                      <q-date v-model="search.startDate" mask="YYYY-MM-DD HH:mm">
                        <div class="row items-center justify-end">
                          <q-btn v-close-popup label="Close" color="primary" flat />
                        </div>
                      </q-date>
                    </q-popup-proxy>
                  </q-icon>
                </template>
              </q-input>

              <!-- 종료 날짜 -->
              <q-input filled v-model="search.endDate" label="종료 날짜" outlined dense class="filter-input">
                <template v-slot:prepend>
                  <q-icon name="event" class="cursor-pointer">
                    <q-popup-proxy cover transition-show="scale" transition-hide="scale">
                      <q-date v-model="endDate" mask="YYYY-MM-DD" @update:model-value="setEndDateToEndOfDay">
                        <div class="row items-center justify-end">
                          <q-btn v-close-popup label="Close" color="primary" flat />
                        </div>
                      </q-date>
                    </q-popup-proxy>
                  </q-icon>
                </template>
              </q-input>
            </div>
          </div>
          <div class="filter-row full-width">
            <q-btn @click="fetchData" label="검색" color="primary" class="q-mt-md" dense/>
          </div>
        </div>
      </q-card-section>
    </q-card>

    <!-- 조회 결과 테이블 -->
    <div class="table-container">
      <q-table
        :rows="groupedViewLogs"
        :columns="groupedColumns"
        row-key="id"
        :pagination="pagination"
      >
        <!-- 로그 확장 템플릿 -->
        <template #body-cell-logs="props">
          <q-td :props="props">
            <q-expansion-item v-for="(log, index) in props.row.logsByDate" :key="index" :label="`${log.date} (${log.times.length} 조회)`">
              <q-list>
                <q-item-label v-for="(time, idx) in log.times" :key="idx">{{ time }}</q-item-label>
              </q-list>
            </q-expansion-item>
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
    const groupedViewLogs = ref([]);
    const columns = [
      { name: 'employeeName', label: '직원 이름', align: 'left', field: 'employeeName' },
      { name: 'employeeEmail', label: '직원 이메일', align: 'left', field: 'employeeEmail' },
      { name: 'employeeGrade', label: '직원 직급', align: 'left', field: 'employeeGrade' },
      { name: 'customerName', label: '고객 이름', align: 'left', field: 'customerName' },
      { name: 'customerEmail', label: '고객 이메일', align: 'left', field: 'customerEmail' },
      { name: 'customerGrade', label: '고객 등급', align: 'left', field: 'customerGrade' },
      { name: 'logs', label: '조회 로그', align: 'left', field: 'logs' }
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
      customerGrade: null,
      startDate: '',
      endDate: ''
    });
    const employeeGrades = [
      { label: 'Manager', value: 'ROLE_MANAGER' },
      { label: 'Staff', value: 'ROLE_STAFF' },
      { label: 'Marketer', value: 'ROLE_MARKETER' },
      { label: 'Consultant', value: 'ROLE_CONSULTANT' }
    ];
    const customerGrades = [
      { label: 'New', value: 'NEW' },
      { label: 'VIP', value: 'VIP' },
      { label: 'VVIP', value: 'VVIP' },
      { label: 'BASIC', value: 'BASIC' }
    ];

    const fetchData = () => {
      const params = {};
      if (search.value.employeeName) params.employeeName = search.value.employeeName;
      if (search.value.employeeEmail) params.employeeEmail = search.value.employeeEmail;
      if (search.value.employeeGrade) params.employeeGrade = search.value.employeeGrade?.value || search.value.employeeGrade;
      if (search.value.customerName) params.customerName = search.value.customerName;
      if (search.value.customerEmail) params.customerEmail = search.value.customerEmail;
      if (search.value.customerGrade) params.customerGrade = search.value.customerGrade?.value || search.value.customerGrade;
      if (search.value.startDate) params.startDate = new Date(search.value.startDate).toISOString(); // 시작 날짜를 ISO 형식으로 변환하여 전송
      if (search.value.endDate) params.endDate = new Date(search.value.endDate).toISOString(); // 종료 날짜를 ISO 형식으로 변환하여 전송

      axios.get('http://localhost:8080/api/v1/view-logs/search', { params })
        .then(response => {
          if (Array.isArray(response.data.content)) {
            viewLogs.value = response.data.content;
            groupLogs();
          } else {
            console.error('서버로부터 반환된 데이터가 배열이 아닙니다:', response.data);
            viewLogs.value = [];
          }
        })
        .catch(error => {
          console.error('조회 로그를 불러오는 중 오류가 발생했습니다:', error);
        });
    };

    const formatDate = (dateTime) => {
      return new Date(dateTime).toLocaleDateString();
    };

    const formatTime = (dateTime) => {
      return new Date(dateTime).toLocaleTimeString();
    };

    const groupLogs = () => {
      const grouped = {};
      viewLogs.value.forEach(log => {
        const date = formatDate(log.viewTime);
        const time = formatTime(log.viewTime);
        const key = `${log.employeeName}-${log.customerName}`;
        if (!grouped[key]) {
          grouped[key] = {
            employeeName: log.employeeName,
            employeeEmail: log.employeeEmail,
            employeeGrade: log.employeeGrade,
            customerName: log.customerName,
            customerEmail: log.customerEmail,
            customerGrade: log.customerGrade,
            logsByDate: []
          };
        }
        let dateEntry = grouped[key].logsByDate.find(entry => entry.date === date);
        if (!dateEntry) {
          dateEntry = { date, times: [] };
          grouped[key].logsByDate.push(dateEntry);
        }
        dateEntry.times.push(time);
      });
      groupedViewLogs.value = Object.values(grouped);
    };

    const setEndDateToEndOfDay = (date) => {
      search.value.endDate = date + ' 23:59'; // 날짜를 YYYY-MM-DD 23:59 형식으로 설정
    };

    onMounted(() => {
      axios.get('http://localhost:8080/api/v1/view-logs')
        .then(response => {
          if (Array.isArray(response.data)) {
            viewLogs.value = response.data;
            groupLogs();
          } else {
            console.error('서버로부터 반환된 기본 데이터가 배열이 아닙니다:', response.data);
            viewLogs.value = [];
          }
        })
        .catch(error => {
          console.error('기본 데이터를 불러오는 중 오류가 발생했습니다:', error);
        });
    });

    return {
      viewLogs,
      groupedViewLogs,
      columns,
      groupedColumns: columns,
      pagination,
      search,
      employeeGrades,
      customerGrades,
      fetchData,
      setEndDateToEndOfDay
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
  flex: 1;
  max-width: 100%;
}

.full-width {
  width: 100%;
}

.q-mt-md {
  margin-top: 20px;
}

.left-section, .right-section {
  flex: 1;
}

.date-range {
  flex: 1;
}
</style>
