<template>
  <q-page class="q-pa-md" :class="$q.dark.isActive ? '' : 'bg-grey-1'">
    <div class="row col-12">
      <div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
        <div class="row">
          <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6 q-pr-sm">
            <q-card class="q-pa-md">
              <div class="row q-py-md full-width items-center justify-center">
                <div class="total-sales-box justify-center items-center">
                  <q-icon
                    class="items-center full-height full-width vertical-middle"
                    name="trending_up"
                    size="md"
                    color="white"
                  />
                </div>
              </div>
              <div
                class="row text-weight-bold text-h6 full-width justify-center"
              >
                전일 방문고객수
              </div>
              <div class="row text-grey-7 full-width justify-center">
                +70% Income
              </div>
              <div
                class="row text-weight-bold text-h4 full-width justify-center"
              >
                {{ yesterdayNum }}
              </div>
            </q-card>
          </div>
          <div
            class="col-lg-4 col-md-4 col-sm-6 col-xs-6 q-px-lg-sm q-px-md-sm q-pl-sm q-pr-sm"
          >
            <q-card class="q-pa-md">
              <div class="row q-py-md full-width items-center justify-center">
                <div class="avg-sales-box justify-center items-center">
                  <q-icon
                    class="items-center full-height full-width vertical-middle"
                    name="percent"
                    size="md"
                    color="white"
                  />
                </div>
              </div>
              <div
                class="row text-weight-bold text-h6 full-width justify-center"
              >
                재방문률
              </div>
              <div class="row text-grey-7 full-width justify-center">
                +22% Sales
              </div>
              <div
                class="row text-weight-bold text-h4 full-width justify-center"
              >
                {{ revisitRate }} %
              </div>
            </q-card>
          </div>
          <div
            class="col-lg-4 col-md-4 col-sm-6 col-xs-6 q-px-lg-sm q-px-md-sm q-pr-sm-sm q-pr-xs-sm q-mt-lg-none q-mt-md-none q-mt-sm-md q-mt-xs-md q-pl-sm"
          >
            <q-card class="q-pa-md">
              <div class="row q-py-md full-width items-center justify-center">
                <div class="users-box justify-center items-center">
                  <q-icon
                    class="items-center full-height full-width vertical-middle"
                    name="people_alt"
                    size="md"
                    color="white"
                  />
                </div>
              </div>
              <div
                class="row text-weight-bold text-h6 full-width justify-center"
              >
                전일 신규고객수
              </div>
              <div class="row text-grey-7 full-width justify-center">
                +79% New User
              </div>
              <div
                class="row text-weight-bold text-h4 full-width justify-center"
              >
                {{ newCustomerNum }}
              </div>
            </q-card>
          </div>
          <!-- <div
            class="col-lg-4 col-md-4 col-sm-6 col-xs-6 q-pl-sm q-mt-lg-none q-mt-md-none q-mt-sm-md q-mt-xs-md"
          >
            <q-card class="q-pa-md">
              <div class="row q-py-md full-width items-center justify-center">
                <div class="profit-gain-box justify-center items-center">
                  <q-icon
                    class="items-center full-height full-width vertical-middle"
                    name="currency_exchange"
                    size="md"
                    color="white"
                  />
                </div>
              </div>
              <div
                class="row text-weight-bold text-h6 full-width justify-center"
              >
                Profit Gain
              </div>
              <div class="row text-grey-7 full-width justify-center">
                +66% Income
              </div>
              <div
                class="row text-weight-bold text-h4 full-width justify-center"
              >
                $799m
              </div>
            </q-card>
          </div> -->
        </div>
        <div>
          <q-card class="row q-mt-md float-right full-width card-item">
            <ECharts
              class="q-pt-md"
              :option="getLineChartOptions()"
              :resizable="true"
            />
          </q-card>
          <q-card
            class="row q-mt-md float-right full-width card-item-2"
            :class="$q.dark.isActive ? 'text-white' : ''"
          >
            <!-- <ECharts
              class="q-pt-md"
              :option="getBarChartOptions()"
              :resizable="true"
            /> -->
            <ReservationCntByYear class="full-width" />
          </q-card>
        </div>
      </div>
      <div
        class="col-lg-4 col-md-4 col-sm-12 col-xs-12 q-pt-lg-none q-pt-md-none q-pt-sm-md q-pt-xs-md q-pl-lg-md q-pl-md-md q-pl-sm"
        style="padding-left: 16px"
      >
        <q-card
          class="q-pa-md float-right full-width right-card-item text-white"
        >
          <div class="row full-width">
            <div class="row full-width text-h4 text-weight-bold">$ 4,999</div>
            <div class="text-subtitle1">Active Balance</div>
          </div>
          <div class="row full-width q-mt-lg">
            <div class="row full-width items-center">
              <div class="col-6">
                <q-icon
                  size="sm"
                  name="arrow_upward"
                  color="green bg-white"
                  style="border-radius: 5px"
                />
                <span class="q-ml-md text-weight-bold">Income</span>
              </div>
              <div class="col-6">
                <span class="text-weight-bold float-right text-h6"
                  >$ 1699.0</span
                >
              </div>
            </div>
          </div>
          <div class="row full-width q-mt-md">
            <div class="row full-width items-center">
              <div class="col-6">
                <q-icon
                  size="sm"
                  name="arrow_downward"
                  color="red bg-white"
                  style="border-radius: 5px"
                />
                <span class="q-ml-md text-weight-bold">Expenses</span>
              </div>
              <div class="col-6">
                <span class="text-weight-bold float-right text-h6"
                  >$ -799.0</span
                >
              </div>
            </div>
          </div>
          <div class="row full-width q-mt-md">
            <div class="row full-width items-center">
              <div class="col-6">
                <q-icon
                  size="sm"
                  name="arrow_downward"
                  color="red bg-white"
                  style="border-radius: 5px"
                />
                <span class="q-ml-md text-weight-bold">Taxes</span>
              </div>
              <div class="col-6">
                <span class="text-weight-bold float-right text-h6"
                  >$ -199.0</span
                >
              </div>
            </div>
          </div>
          <div class="row full-width q-mt-lg">
            <q-btn
              label="Add Credit Card"
              class="full-width bg-white text-h6 text-weight-bold rounded-borders"
              icon-right="arrow_right"
              style="color: rgb(250, 108, 14)"
              no-caps
            />
          </div>
        </q-card>
        <q-card
          class="q-pa-md q-mt-md float-right full-width right-card-item-2 text-white"
        >
          <div class="row col-12 full-width items-center">
            <div class="col-5">
              <q-avatar size="80px">
                <img src="https://cdn.quasar.dev/img/boy-avatar.png" />
              </q-avatar>
            </div>
            <div class="col-7 text-h5 text-weight-bold">Universal Pay</div>
          </div>
          <div class="row col-12 q-mt-lg text-weight-bold">Name : Jane Doe</div>
          <div class="row col-12 q-mt-xs text-weight-bold">
            Card No : XXXX XXXX XXXX XXXX
          </div>
          <div class="row col-12 q-mt-xs text-weight-bold">
            <span class="col-6">Start : XX/XX</span>
            <span class="col-6">End : XX/XX</span>
          </div>
        </q-card>
        <q-card
          class="q-pb-none q-mt-md float-right full-width right-card-item-3"
        >
          <!-- <ECharts
            class="q-pt-md"
            :option="getPieChartOptions()"
            :resizable="true"
          /> -->
          <RevisitRate class="full-width" />
        </q-card>
      </div>
    </div>
  </q-page>
</template>

<script setup>
import { onMounted, ref } from "vue";
import ECharts from "vue-echarts";
import "echarts";
import { useQuasar } from "quasar";
import ReservationCntByYear from "/src/components/statistics/ReservationCntByYear.vue";
import RevisitRate from "/src/components/statistics/RevisitRate.vue";
import { api as axios } from "src/boot/axios";

const $q = useQuasar();

const errorMessage = ref("");
const yesterdayNum = ref(null);
const revisitRate = ref(null);
const newCustomerNum = ref(null);

const getYesterday = () => {
  // 오늘 날짜 객체 생성
  const today = new Date();

  // 어제 날짜 객체 생성
  const yesterday = new Date(today);
  yesterday.setDate(today.getDate() - 1);

  // 연, 월, 일 추출
  const year = yesterday.getFullYear();
  const month = String(yesterday.getMonth() + 1).padStart(2, "0"); // 월은 0부터 시작하므로 1을 더함
  const day = String(yesterday.getDate()).padStart(2, "0"); // 일

  // 'YYYY-MM-DD' 형식으로 반환
  return `${year}-${month}-${day}`;
};

const fetchVisitNum = async () => {
  try {
    const response = await axios.get(
      "/api/v1/statistic/reservation/daily-reservation-cnt-date",
      {
        params: {
          start: getYesterday(),
          end: getYesterday(),
        },
      }
    );
    yesterdayNum.value = response.data.length;
    console.log(yesterdayNum);
  } catch (error) {
    console.error("에러 발생:", error);
    errorMessage.value = "에러가 발생했습니다.";
  }
  // finally {
  //   loading.value = false;
  // }
};
const fetchRevisitRate = async () => {
  try {
    const response = await axios.get(
      "/api/v1/statistics/customers/revisit-rate"
    );
    revisitRate.value = response.data.overallRevisitRate.toFixed(2);
  } catch (error) {
    console.error("에러 발생:", error);
    errorMessage.value = "에러가 발생했습니다.";
  }
};
const fetchNewCustomerNum = async () => {
  try {
    const response = await axios.get(
      "/api/v1/statistics/customers/new-customers",
      {
        params: {
          start: getYesterday(),
          end: getYesterday(),
        },
      }
    );
    newCustomerNum.value = response.data.length;
  } catch (error) {
    console.error("에러 발생:", error);
    errorMessage.value = "에러가 발생했습니다.";
  }
};
const getLineChartOptions = () => {
  return {
    tooltip: {
      trigger: "item",
    },
    title: {
      left: "center",
      text: "Total Sales",
      textStyle: {
        color: $q.dark.isActive ? "white" : "black",
      },
    },
    xAxis: {
      data: ["A", "B", "C", "D", "E", "F"],
      axisLabel: {
        color: $q.dark.isActive ? "white" : "black",
      },
    },
    yAxis: {
      axisLabel: {
        color: $q.dark.isActive ? "white" : "black",
      },
    },
    series: [
      {
        data: [10, 50, 280, 130, 170, 455],
        type: "line",
        color: ["#FA6C0E"],
      },
    ],
  };
};

const getPieChartOptions = () => {
  return {
    tooltip: {
      trigger: "item",
    },
    legend: {
      top: "5%",
      left: "center",
      textStyle: {
        color: $q.dark.isActive ? "white" : "black",
      },
    },
    series: [
      {
        name: "Access From",
        type: "pie",
        radius: ["40%", "70%"],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: "#fff",
          borderWidth: 2,
        },
        label: {
          show: false,
          position: "center",
        },
        emphasis: {
          label: {
            show: true,
            fontSize: "40",
            fontWeight: "bold",
          },
        },
        labelLine: {
          show: false,
        },
        data: [
          { value: 1048, name: "Search Engine" },
          { value: 735, name: "Direct" },
          { value: 580, name: "Email" },
          { value: 484, name: "Union Ads" },
          { value: 300, name: "Video Ads" },
        ],
      },
    ],
  };
};
onMounted(() => {
  fetchVisitNum();
  fetchRevisitRate();
  fetchNewCustomerNum();
});
</script>

<style>
.card-item {
  height: 20rem;
  border-radius: 5px;
}

.card-item-2 {
  height: 25rem;
  border-radius: 5px;
}

.right-card-item {
  height: 20rem;
  border-radius: 5px;
  background: linear-gradient(
    145deg,
    rgb(252, 189, 138) 10%,
    rgb(250, 108, 14)
  );
}

.right-card-item-2 {
  height: 13.8rem;
  border-radius: 5px;
  background: linear-gradient(145deg, rgb(138, 149, 252) 10%, rgb(65, 66, 143));
}

.right-card-item-3 {
  height: 25rem;
  border-radius: 5px;
}

.total-sales-box {
  height: 4rem;
  width: 4rem;
  background-color: rgb(250, 108, 14);
  border-radius: 50%;
  box-shadow: 0 0 60px 0 rgba(235, 101, 11, 0.37);
}

.avg-sales-box {
  height: 4rem;
  width: 4rem;
  background-color: rgb(37, 139, 170);
  border-radius: 50%;
  box-shadow: 0 0 60px 0 rgba(23, 78, 95, 0.37);
}

.users-box {
  height: 4rem;
  width: 4rem;
  background-color: rgb(70, 177, 60);
  border-radius: 50%;
  box-shadow: 0 0 60px 0 rgba(12, 97, 16, 0.37);
}

.profit-gain-box {
  height: 4rem;
  width: 4rem;
  background-color: rgb(17, 48, 85);
  border-radius: 50%;
  box-shadow: 0 0 60px 0 rgba(16, 61, 119, 0.37);
}
</style>
