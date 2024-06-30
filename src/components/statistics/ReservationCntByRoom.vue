<template>
  <div>
    <Bar v-if="loaded" :data="chartData" :options="chartOptions" />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { api as axios } from "src/boot/axios";
import { Bar } from "vue-chartjs";
import Chart from "chart.js/auto";

const chartData = ref({
  labels: [], // RoomType labels will be populated here
  datasets: [
    {
      label: "예약수",
      backgroundColor: [
        "#FF6666",
        "#FF9966",
        "#FFFF66",
        "#66FF66",
        "#66B2FF",
        "#6699FF",
        "#FF99CC",
        "#99FF99",
        "#FF6699",
      ],
      data: [], // RoomType counts will be populated here
    },
  ],
});

const chartOptions = ref({
  indexAxis: "y",
  responsive: true,
  plugins: {
    legend: {
      position: "top",
    },
    title: {
      display: true,
      text: "방 타입별 예약수 통계",
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

const loaded = ref(false);

const fetchRoomTypeStatistics = async () => {
  try {
    const response = await axios.get(
      "/api/v1/statistics/purchase/statistics/roomtype"
    );

    const roomTypeData = response.data.filter(
      (item) => item.consumeType === "Room"
    );
    chartData.value.labels = roomTypeData.map((item) => item.roomType);
    chartData.value.datasets[0].data = roomTypeData.map((item) => item.count);

    loaded.value = true;
  } catch (error) {
    console.error("Error fetching room type statistics:", error);
  }
};

onMounted(() => {
  fetchRoomTypeStatistics();
});
</script>

<style scoped>
.card-item {
  height: 24.5rem;
  border-radius: 5px;
}
</style>
