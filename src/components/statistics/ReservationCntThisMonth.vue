<template>
  <div>
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
      label: "이번 달",
      backgroundColor: "rgba(75, 192, 192, 0.2)",
      borderColor: "rgba(75, 192, 192, 1)",
      borderWidth: 1,
      data: [],
    },
    {
      label: "작년 이번 달",
      backgroundColor: "rgba(192, 75, 192, 0.2)",
      borderColor: "rgba(192, 75, 192, 1)",
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
      text: "Comparison of Monthly Reservations",
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

const fetchReservationNum = async () => {
  const response = await axios.get(
    "/api/v1/statistic/reservation/daily-reservation-cnt/all"
  );
  const thisYear = new Date().getFullYear();
  const thisMonth = new Date().getMonth() + 1;
  const lastYear = thisYear - 1;

  const thisMonthData = response.data.filter(
    (item) =>
      item.reservationYear === thisYear && item.reservationMonth === thisMonth
  );

  const lastYearThisMonthData = response.data.filter(
    (item) =>
      item.reservationYear === lastYear && item.reservationMonth === thisMonth
  );

  // 차트 데이터 설정
  const labels = Array.from(
    new Set([
      ...thisMonthData.map((item) => item.reservationDay),
      ...lastYearThisMonthData.map((item) => item.reservationDay),
    ])
  ).sort((a, b) => a - b);

  chartData.value.labels = labels;
  chartData.value.datasets[0].data = labels.map((day) => {
    const data = thisMonthData.find((item) => item.reservationDay === day);
    return data ? data.reservationCount : 0;
  });

  chartData.value.datasets[1].data = labels.map((day) => {
    const data = lastYearThisMonthData.find(
      (item) => item.reservationDay === day
    );
    return data ? data.reservationCount : 0;
  });

  const maxDataValue = Math.max(
    ...chartData.value.datasets[0].data,
    ...chartData.value.datasets[1].data
  );
  chartOptions.value.scales.y.suggestedMax = maxDataValue + maxDataValue * 0.1;

  loaded.value = true;
};

onMounted(() => {
  fetchReservationNum();
});
</script>

<style scoped></style>
