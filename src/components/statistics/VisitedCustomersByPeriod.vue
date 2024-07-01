<template>
  <div class="q-pa-md">
    <q-form @submit.prevent="fetchCustomerVisits">
      <q-card class="my-card">
        <q-card-section class="row justify-center q-gutter-md q-pa-sm">
          <q-input
            filled
            v-model="startDate"
            label="Start Date"
            mask="####-##-##"
            :rules="[(val) => !!val || 'Start date is required']"
            class="col-3"
          >
            <template v-slot:append>
              <q-icon name="event" @click="showStartDatePicker = true" />
            </template>
            <q-popup-proxy
              v-model="showStartDatePicker"
              transition-show="scale"
              transition-hide="scale"
            >
              <q-date
                v-model="startDate"
                mask="YYYY-MM-DD"
                @change="updateStartDate"
              />
            </q-popup-proxy>
          </q-input>

          <q-input
            filled
            v-model="endDate"
            label="End Date"
            mask="####-##-##"
            :rules="[(val) => !!val || 'End date is required']"
            class="col-3"
          >
            <template v-slot:append>
              <q-icon name="event" @click="showEndDatePicker = true" />
            </template>
            <q-popup-proxy
              v-model="showEndDatePicker"
              transition-show="scale"
              transition-hide="scale"
            >
              <q-date
                v-model="endDate"
                mask="YYYY-MM-DD"
                @change="updateEndDate"
              />
            </q-popup-proxy>
          </q-input>

          <q-btn
            label="조회"
            type="submit"
            color="primary"
            class="col-2 q-ml-md btn-height"
          />
        </q-card-section>
      </q-card>
    </q-form>

    <Bar
      v-if="loaded && dailyVisitCounts.length"
      :data="chartData"
      :options="chartOptions"
      class="q-mt-md"
    />

    <q-table
      :rows="customerVisits"
      :columns="columns"
      row-key="id"
      v-if="customerVisits.length"
      class="q-mt-md"
    />
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import { api as axios } from "/src/boot/axios";
import { Bar } from "vue-chartjs";
import Chart from "chart.js/auto";

const startDate = ref("");
const endDate = ref("");
const showStartDatePicker = ref(false);
const showEndDatePicker = ref(false);
const customerVisits = ref([]);
const dailyVisitCounts = ref([]);
const loaded = ref(false);

const columns = [
  { name: "id", label: "ID", align: "left", field: "id", sortable: true },
  { name: "name", label: "Name", align: "left", field: "name", sortable: true },
  {
    name: "phone",
    label: "Phone",
    align: "left",
    field: "phone",
    sortable: true,
  },
  // {
  //   name: "employeeName",
  //   label: "Employee Name",
  //   align: "left",
  //   field: "employeeName",
  //   sortable: true,
  // },
  // {
  //   name: "createdDate",
  //   label: "Created Date",
  //   align: "left",
  //   field: "createdDate",
  //   sortable: true,
  // },
  {
    name: "grade",
    label: "Grade",
    align: "left",
    field: "grade",
    sortable: true,
  },
  // { name: "address", label: "Address", align: "left", field: "address" },
  // { name: "memo", label: "Memo", align: "left", field: "memo", sortable: true },
  {
    name: "gender",
    label: "Gender",
    align: "left",
    field: "gender",
    sortable: true,
  },
  {
    name: "birth",
    label: "Birth",
    align: "left",
    field: "birth",
    sortable: true,
  },
  {
    name: "visitStartDate",
    label: "Visit Start Date",
    align: "left",
    field: "visitStartDate",
    sortable: true,
  },
  {
    name: "visitEndDate",
    label: "Visit End Date",
    align: "left",
    field: "visitEndDate",
    sortable: true,
  },
];

const updateStartDate = (value) => {
  startDate.value = value;
  showStartDatePicker.value = false;
};

const updateEndDate = (value) => {
  endDate.value = value;
  showEndDatePicker.value = false;
};

const fetchCustomerVisits = async () => {
  if (!startDate.value || !endDate.value) {
    alert("Please enter both start and end dates.");
    return;
  }

  try {
    const response = await axios.get(
      "/api/v1/statistics/customers/visit-customers/all",
      {
        params: {
          start: startDate.value,
          end: endDate.value,
        },
      }
    );
    customerVisits.value = response.data;
    calculateDailyVisitCounts();
    loaded.value = true;
  } catch (error) {
    console.error("Error fetching customer visits:", error);
  }
};

const calculateDailyVisitCounts = () => {
  const visitCountMap = {};

  customerVisits.value.forEach((visit) => {
    const start = new Date(visit.visitStartDate);
    const end = new Date(visit.visitEndDate);
    for (let d = start; d <= end; d.setDate(d.getDate() + 1)) {
      const dateString = d.toISOString().split("T")[0];
      if (!visitCountMap[dateString]) {
        visitCountMap[dateString] = 0;
      }
      visitCountMap[dateString]++;
    }
  });

  dailyVisitCounts.value = Object.entries(visitCountMap)
    .map(([date, count]) => ({
      date,
      count,
    }))
    .sort((a, b) => new Date(a.date) - new Date(b.date));
};

const chartData = ref({
  labels: [],
  datasets: [
    {
      label: "방문 수",
      backgroundColor: "#42A5F5",
      data: [],
    },
  ],
});

const chartOptions = ref({
  responsive: true,
  plugins: {
    // legend: {
    //   position: "top",
    // },
    title: {
      display: true,
      text: "일별 방문 통계",
    },
  },
  scales: {
    x: {
      grid: {
        display: false,
        borderColor: "rgba(0, 0, 0, 0.1)",
        drawBorder: true,
      },
    },
    y: {
      beginAtZero: true,
      grid: {
        color: "rgba(0, 0, 0, 0.1)",
      },
    },
  },
});

watch(dailyVisitCounts, (newCounts) => {
  chartData.value.labels = newCounts.map((item) => item.date);
  chartData.value.datasets[0].data = newCounts.map((item) => item.count);
});
</script>

<style scoped>
.my-card {
  max-width: 800px;
  margin: auto;
  border-radius: 10px;
  margin-top: 15px;
}

.q-icon {
  cursor: pointer;
}

.q-pa-md {
  padding: 16px;
}

.q-pa-sm {
  padding: 8px;
}

.btn-height {
  height: 56px; /* Adjust this value to match the q-input height */
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
