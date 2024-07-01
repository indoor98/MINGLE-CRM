<template>
  <div class="q-pa-md">
    <Line v-if="loaded" :data="chartData" :options="chartOptions" />
  </div>
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
      // label: `${new Date().getFullYear()}년 예약수`,
      label: `올해 예약수`,
      backgroundColor: "rgba(255, 99, 132, 0.2)",
      borderColor: "rgba(255, 99, 132, 1)",
      borderWidth: 1,
      data: [],
    },
    {
      // label: `${new Date().getFullYear() - 1}년 예약수`,
      label: `작년 예약수`,
      backgroundColor: "rgba(75, 192, 192, 0.2)",
      borderColor: "rgba(75, 192, 192, 1)",

      borderWidth: 1,
      data: [],
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
        display: false,
      },
    },
    y: {
      beginAtZero: true,
      borderColor: "rgba(0, 0, 0, 0.1)",
      suggestedMax: 14,
    },
  },
});

const monthlyReservations = ref([]);
const errorMessage = ref("");
const loading = ref(true);

const todayDate = new Date();
const thisYear = todayDate.getFullYear();
const thisMonth = todayDate.getMonth() + 1; // 현재 월 (1월이 0, 12월이 11)

const fetchMonthlyReservations = async () => {
  try {
    const response = await axios.get(
      `/api/v1/statistic/reservation/monthly-reservation-cnt/all`
    );

    monthlyReservations.value = response.data;

    // 올해 데이터 설정
    const thisYearData = monthlyReservations.value.filter(
      (item) =>
        item.reservationYear === thisYear && item.reservationMonth <= thisMonth // 7월 이후 데이터는 제외
    );

    // 작년 데이터 설정
    const lastYearData = monthlyReservations.value.filter(
      (item) => item.reservationYear === thisYear - 1
    );

    // 차트 데이터 설정
    const labels = lastYearData.map((item) => `${item.reservationMonth}월`);
    const thisYearCounts = thisYearData.map((item) => item.reservationCount);
    const lastYearCounts = new Array(12).fill(null); // 작년 데이터는 기본적으로 null로 채움

    // 작년 데이터가 있는 월에 대해 값 설정
    lastYearData.forEach((item) => {
      const index = item.reservationMonth - 1; // 월은 1월부터 시작이므로 인덱스는 -1 처리
      lastYearCounts[index] = item.reservationCount;
    });

    chartData.value.labels = labels;
    chartData.value.datasets[0].data = thisYearCounts;
    chartData.value.datasets[1].data = lastYearCounts;

    const maxDataValue = Math.max(...thisYearCounts, ...lastYearCounts);
    // suggestedMax 설정
    chartOptions.value.scales.y.suggestedMax =
      maxDataValue + maxDataValue * 0.1; // 최대값의 10% 여유 추가

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
