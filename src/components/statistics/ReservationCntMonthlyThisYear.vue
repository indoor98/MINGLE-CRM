<template>
  <q-page class="q-pa-md">
    <div>월별 예약수</div>
    <Line v-if="loaded" :data="chartData" :options="chartOptions" />
  </q-page>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { api as axios } from "src/boot/axios";
import { Line } from "vue-chartjs";
import Chart from "chart.js/auto";
import ChartDataLabels from "chartjs-plugin-datalabels";

const loaded = ref(false);

const chartData = ref({
  labels: [],
  datasets: [
    {
      label: "Monthly Reservation Count",
      backgroundColor: "rgba(75, 192, 192, 0.2)",
      borderColor: "rgba(75, 192, 192, 1)",
      borderWidth: 1,
      data: [], // 초기값 설정
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
      text: "월별 예약수 통계",
    },
    datalabels: {
      anchor: "end",
      align: "top",
      formatter: (value) => value,
      font: {
        weight: "bold",
      },
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
      // suggestedMax: 14, // 최대값을 데이터 값보다 약간 더 높게 설정
    },
  },
});

const monthlyReservations = ref([]);
const errorMessage = ref("");
const loading = ref(true);

const todayDate = new Date();
const thisYear = todayDate.getFullYear();

const fetchMonthlyReservations = async () => {
  try {
    const response = await axios.get(
      `/api/v1/statistic/reservation/monthly-reservation-cnt/all`
    );
    console.log(thisYear);
    for (let index = 0; index < response.data.length; index++) {
      if (response.data[index].reservationYear == thisYear) {
        monthlyReservations.value.push(response.data[index]);
      }
    }
    errorMessage.value = "";

    // 차트 데이터 설정
    const labels = monthlyReservations.value.map(
      (item) => `${item.reservationMonth}`
    );
    const data = monthlyReservations.value.map((item) => item.reservationCount);

    chartData.value.labels = labels;
    chartData.value.datasets[0].data = data;

    loaded.value = true; // 데이터 로딩이 완료되면 true로 설정
  } catch (error) {
    console.error("월별 예약수 목록을 불러오는 중 에러 발생:", error);
    errorMessage.value = "월별 예약수 목록을 불러오는 중 에러가 발생했습니다.";
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchMonthlyReservations();
});
</script>

<style scoped></style>
