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
  { name: "id", label: "ID", align: "left", field: "id", sortable: true },
  { name: "name", label: "Name", align: "left", field: "name", sortable: true },
  {
    name: "phone",
    label: "Phone",
    align: "left",
    field: "phone",
    sortable: true,
  },
  {
    name: "employeeName",
    label: "Employee Name",
    align: "left",
    field: "employeeName",
    sortable: true,
  },
  {
    name: "createdDate",
    label: "Created Date",
    align: "left",
    field: "createdDate",
    sortable: true,
  },
  {
    name: "grade",
    label: "Grade",
    align: "left",
    field: "grade",
    sortable: true,
  },
  { name: "address", label: "Address", align: "left", field: "address" },
  { name: "memo", label: "Memo", align: "left", field: "memo", sortable: true },
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
      "/api/v1/statistics/customers/visit-customers",
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
