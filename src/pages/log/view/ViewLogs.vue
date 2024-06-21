<template>
  <q-page class="q-pa-md">
    <q-table
      :rows="viewLogs"
      :columns="columns"
      row-key="id"
      :pagination="pagination"
    >
      <template v-slot:body-cell-viewTime="props">
        <q-td :props="props">
          {{ formatDateTime(props.row.viewTime) }}
        </q-td>
      </template>
    </q-table>
  </q-page>
</template>

<script>
import { api as axios } from "src/boot/axios";

export default {
  data() {
    return {
      viewLogs: [],
      columns: [
        { name: 'id', label: 'ID', align: 'left', field: 'id' },
        { name: 'employeeName', label: '직원 이름', align: 'left', field: 'employeeName' },
        { name: 'employeeGrade', label: '직원 직급', align: 'left', field: 'employeeGrade' },
        { name: 'customerName', label: '조회 고객명', align: 'left', field: 'customerName' },
        { name: 'customerGrade', label: '조회 고객 등급', align: 'left', field: 'customerGrade' },
        { name: 'viewTime', label: '조회 시간', align: 'left', field: 'viewTime' },
        { name: 'viewCount', label: '조회수', align: 'left', field: 'viewCount' }
      ],
      pagination: {
        sortBy: 'id',
        descending: false,
        page: 1,
        rowsPerPage: 10
      }
    };
  },
  methods: {
    fetchData() {
      axios.get('http://localhost:8080/api/v1/view-logs')
        .then(response => {
          this.viewLogs = response.data;
        })
        .catch(error => {
          console.error('There was an error fetching the view logs:', error);
        });
    },
    formatDateTime(dateTime) {
      return new Date(dateTime).toLocaleString();
    }
  },
  mounted() {
    this.fetchData();
  }
}
</script>

<style>
.q-page {
  max-width: 800px;
  margin: auto;
}
</style>
