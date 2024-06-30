<template>
  <div style="text-align: center">기간별 메뉴 판매수 통계 - 연령, 성별별</div>
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
      v-if="loaded"
      :data="chartData"
      :options="chartOptions"
      class="q-mt-md"
    />
  </div>
</template>

<script setup>
import { ref } from "vue";
import { Bar } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from "chart.js";
import { api as axios } from "src/boot/axios";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
);

const startDate = ref("");
const endDate = ref("");
const showStartDatePicker = ref(false);
const showEndDatePicker = ref(false);
const loaded = ref(false);
const chartData = ref({
  labels: ["20대", "30대", "40대", "50대", "60대 이상"],
  datasets: [
    {
      label: "여성 예약수",
      backgroundColor: "rgba(255, 99, 132, 0.2)",
      borderColor: "rgba(255, 99, 132, 1)",
      borderWidth: 1,
      data: [], // Data will be populated after API calls
    },
    {
      label: "남성 예약수",
      backgroundColor: "rgba(54, 162, 235, 0.2)",
      borderColor: "rgba(54, 162, 235, 1)",
      borderWidth: 1,
      data: [], // Data will be populated after API calls
    },
  ],
});

const chartOptions = ref({
  responsive: true,
  plugins: {
    legend: {
      position: "top",
    },
    title: {
      display: true,
      text: "연령 및 성별별 예약수",
    },
    datalabels: {
      anchor: "end",
      align: "top",
      formatter: (value) => value.toFixed(0),
      font: {
        weight: "bold",
      },
    },
  },
  scales: {
    x: {
      grid: {
        display: false,
      },
    },
    y: {
      beginAtZero: true,
      ticks: {
        stepSize: 2, // y축 눈금의 간격을 더 촘촘하게 설정
      },
    },
  },
});

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
      `/api/dining/reservation/statistics?startDate=${startDate.value}&endDate=${endDate.value}`
    );
    const data = response.data.data;

    const femaleReservations = [
      data.twentyFemale,
      data.thirtyFemale,
      data.fortyFemale,
      data.fiftyFemale,
      data.overSixtyFemale,
    ];
    const maleReservations = [
      data.twentyMale,
      data.thirtyMale,
      data.fortyMale,
      data.fiftyMale,
      data.overSixtyMale,
    ];

    chartData.value.datasets[0].data = femaleReservations;
    chartData.value.datasets[1].data = maleReservations;

    loaded.value = true; // 데이터를 가져온 후 loaded 상태 변경
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};
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
