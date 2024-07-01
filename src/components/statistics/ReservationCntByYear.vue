<template>
  <div class="q-pa-md">
    <!-- <h2>연도별 예약수</h2>
    <q-card class="q-mt-md">
      <q-card-section>
        <q-table
          :rows="byYearCnts"
          :columns="columns"
          row-key="id"
          :loading="loading"
          :pagination="{ rowsPerPage: 10 }"
          style="cursor: pointer"
        >
          <template v-slot:no-data>
            <q-tr>
              <q-td :colspan="columns.length" class="text-center">
                예약 횟수가 없습니다.
              </q-td>
            </q-tr>
          </template>
        </q-table>
      </q-card-section>
    </q-card> -->
    <q-card v-if="errorMessage">
      <p style="color: red" class="text-center">{{ errorMessage }}</p>
    </q-card>
    <Bar v-if="loaded" :data="chartData" :options="chartOptions" />
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { api as axios } from "src/boot/axios";
import { Bar, Doughnut } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
  DoughnutController,
  ArcElement,
} from "chart.js";
import ChartDataLabels from "chartjs-plugin-datalabels";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
  DoughnutController,
  ArcElement,
  ChartDataLabels
);

const loaded = ref(false);

const chartData = ref({
  labels: [],
  datasets: [
    {
      label: "By Year Reservation Rate",
      backgroundColor: [], // 다채로운 색상 배열로 설정
      borderColor: [], // 경계 색상 배열로 설정
      data: [], // 초기값 설정
    },
  ],
});

const chartOptions = ref({
  plugins: {
    datalabels: {
      anchor: "end",
      align: "top",
      formatter: (value) => value,
      font: {
        weight: "bold",
      },
    },
    title: {
      display: true,
      text: "연도별 예약 수",
    },
  },
  scales: {
    x: {
      grid: {
        display: true,
        borderColor: "rgba(0, 0, 0, 0.1)",
        drawBorder: true,
      },
    },
    y: {
      beginAtZero: true,
      suggestedMax: 90, // 최대값을 데이터 값보다 약간 더 높게 설정
    },
  },
});

const byYearCnts = ref([]);
const errorMessage = ref("");
const loading = ref(true);

const columns = [
  {
    name: "reservationYear",
    label: "예약 연도",
    align: "center",
    field: "reservationYear",
    sortable: true,
  },
  {
    name: "reservationCount",
    label: "예약 횟수",
    align: "center",
    field: "reservationCount",
    sortable: true,
  },
];

const fetchReservationCnts = async () => {
  try {
    const response = await axios.get(
      `/api/v1/statistic/reservation/by-year-reservation-cnt`
    );
    byYearCnts.value = response.data;
    errorMessage.value = "";

    chartData.value.labels = byYearCnts.value.map(
      (item) => item.reservationYear
    );
    chartData.value.datasets[0].data = byYearCnts.value.map(
      (item) => item.reservationCount
    );

    // 색상 배열 설정
    const colors = [
      "rgba(75, 192, 192, 1)",
      "rgba(54, 162, 235, 1)",
      "rgba(255, 206, 86, 1)",
      "rgba(75, 192, 192, 1)",
      "rgba(153, 102, 255, 1)",
      "rgba(255, 159, 64, 1)",
      "rgba(199, 199, 199, 1)",
      "rgba(83, 102, 255, 1)",
    ];
    const borderColors = [
      "rgba(75, 192, 192, 1)",
      "rgba(54, 162, 235, 1)",
      "rgba(255, 206, 86, 1)",
      "rgba(75, 192, 192, 1)",
      "rgba(153, 102, 255, 1)",
      "rgba(255, 159, 64, 1)",
      "rgba(199, 199, 199, 1)",
      "rgba(83, 102, 255, 1)",
    ];

    chartData.value.datasets[0].backgroundColor = colors.slice(
      0,
      byYearCnts.value.length
    );
    chartData.value.datasets[0].borderColor = borderColors.slice(
      0,
      byYearCnts.value.length
    );

    loaded.value = true; // 데이터 로딩이 완료되면 true로 설정
  } catch (error) {
    console.error("연도별 예약수 목록을 불러오는 중 에러 발생:", error);
    errorMessage.value =
      "연도별 예약수 목록을 불러오는 중 에러가 발생했습니다.";
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchReservationCnts();
});

watch((newSelected) => {
  if (newSelected) {
    loading.value = true;
    fetchReservationCnts();
  }
});
</script>

<style scoped></style>
