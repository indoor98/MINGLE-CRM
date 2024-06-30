<template>
  <q-page class="q-pa-md">
    <h2>기간별 방문 고객 조회</h2>
    <q-form @submit.prevent="fetchCustomerVisits">
      <q-grid cols="2" row-class="q-mb-md">
        <q-col>
          <q-input
            filled
            v-model="startDate"
            label="Start Date"
            mask="####-##-##"
            :rules="[(val) => !!val || 'Start date is required']"
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
        </q-col>

        <q-col>
          <q-input
            filled
            v-model="endDate"
            label="End Date"
            mask="####-##-##"
            :rules="[(val) => !!val || 'End date is required']"
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
        </q-col>
      </q-grid>

      <q-btn label="조회" type="submit" color="primary" class="q-mt-md" />
    </q-form>

    <q-table
      :rows="customerVisits"
      :columns="columns"
      row-key="id"
      v-if="customerVisits.length"
      class="q-mt-md"
    />
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import { api as axios } from "/src/boot/axios";

const startDate = ref("");
const endDate = ref("");
const showStartDatePicker = ref(false);
const showEndDatePicker = ref(false);
const customerVisits = ref([]);

const columns = [
  {
    name: "예약 ID",
    label: "ID",
    align: "center",
    field: "id",
    sortable: true,
  },
  {
    name: "reservationDate",
    label: "예약 날짜",
    align: "center",
    field: "reservationDate",
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
      "/api/v1/statistic/reservation/daily-reservation-cnt-date",
      {
        params: {
          start: startDate.value,
          end: endDate.value,
        },
      }
    );
    customerVisits.value = response.data;
  } catch (error) {
    console.error("Error fetching customer visits:", error);
  }
};
</script>
