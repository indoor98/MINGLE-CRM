<template>
  <q-page class="q-pa-md">
    <q-table
      :rows="formattedRows"
      :columns="columns"
      row-key="id"
      flat
      :filter="filter"
      title=""
    >
      <template v-slot:body-cell="props">
        <q-td :props="props">
          <span
            @click="openReservationDetail(props.row)"
            style="cursor: pointer"
            >{{ props.value }}</span
          >
        </q-td>
      </template>
      <template v-slot:body-cell-actions="props">
        <q-td :props="props">
          <q-btn flat round icon="edit" @click="editRow(props.row)" />
          <q-btn flat round icon="delete" @click="deleteRow(props.row)" />
        </q-td>
      </template>
    </q-table>

    <q-dialog v-model="showDialog">
      <q-card>
        <q-card-section>
          <div class="text-h6">Reservation Details</div>
        </q-card-section>

        <q-card-section>
          <div v-if="reservationDetails">
            <p>
              <strong>예약일:</strong> {{ reservationDetails.reservationDate }}
            </p>
            <p><strong>고객명:</strong> {{ reservationDetails.name }}</p>
            <p><strong>연락처:</strong> {{ reservationDetails.phoneNumber }}</p>
            <p><strong>상품명:</strong> {{ reservationDetails.hotelName }}</p>
            <p>
              <strong>예약 타입:</strong>
              {{ reservationDetails.reservationType }}
            </p>
            <p><strong>메모:</strong> {{ reservationDetails.memo }}</p>
          </div>
          <div v-else>Loading...</div>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn flat label="닫기" color="primary" v-close-popup />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </q-page>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";
import { date } from "quasar";

const columns = [
  { name: "index", label: "번호", align: "left", field: "index" },
  {
    name: "reservationDate",
    required: true,
    label: "예약일",
    align: "left",
    field: "reservationDate",
    format: (val) => (val ? date.formatDate(val, "YYYY-MM-DD") : ""),
    sortable: true,
  },
  {
    name: "name",
    label: "고객명",
    align: "left",
    field: "name",
    format: (val) => val || "",
    sortable: true,
  },
  {
    name: "phoneNumber",
    label: "연락처",
    align: "left",
    field: "phoneNumber",
    format: (val) => val || "",
    sortable: true,
  },
  {
    name: "hotelName",
    label: "상품명",
    align: "left",
    field: "hotelName",
    format: (val) => val || "",
    sortable: true,
  },
  {
    name: "reservationType",
    label: "예약 타입",
    align: "left",
    field: "reservationType",
    format: (val) => val || "",
    sortable: true,
  },
  {
    name: "memo",
    label: "메모",
    align: "left",
    field: "memo",
    format: (val) => (val && val !== "Fusce consequat. Nulla nisl." ? val : ""),
    sortable: true,
  },
];

const rows = ref([]);

const filter = ref("");

const route = useRoute();
const customerId = ref(route.params.customerId);

const fetchData = async () => {
  try {
    const response = await axios.get(
      `https://httpstest.mingle-crm.com/api/v1/customers/${customerId.value}/hotel/reservations`
    );
    console.log("Fetched reservation list:", response.data); // 디버깅 로그
    rows.value = response.data.map((row, index) => ({
      ...row,
      id: row.id, // ensure the ID is included
      index: index + 1,
    }));
  } catch (error) {
    console.error("Error fetching data:", error);
  }
};

const formattedRows = computed(() =>
  rows.value.map((row) => ({
    ...row,
    status: row.status || "",
    reservationDate: row.reservationDate
      ? date.formatDate(row.reservationDate, "YYYY-MM-DD")
      : "",
    name: row.name || "",
    phoneNumber: row.phoneNumber || "",
    hotelName: row.hotelName || "",
    reservationType: row.reservationType || "",
    memo:
      row.memo && row.memo !== "Fusce consequat. Nulla nisl." ? row.memo : "",
    registrationDate: row.registrationDate
      ? date.formatDate(row.registrationDate, "YYYY-MM-DD")
      : "",
  }))
);

const showDialog = ref(false);
const reservationDetails = ref(null);

const openReservationDetail = async (row) => {
  console.log("Row data:", row); // Row 데이터 출력
  const reservationId = row.id;
  try {
    console.log(`Fetching details for reservation ID: ${reservationId}`); // 디버깅 로그
    if (!reservationId) {
      console.error("Invalid reservation ID:", reservationId); // 디버깅 로그
      return;
    }
    const response = await axios.get(
      `https://httpstest.mingle-crm.com/api/v1/customers/${customerId.value}/hotel/reservations/${reservationId}`
    );
    console.log("Fetched reservation details:", response.data); // 디버깅 로그
    if (response.data) {
      reservationDetails.value = {
        ...response.data,
        reservationDate: response.data.reservationDate
          ? date.formatDate(response.data.reservationDate, "YYYY-MM-DD")
          : "",
        registrationDate: response.data.registrationDate
          ? date.formatDate(response.data.registrationDate, "YYYY-MM-DD")
          : "",
      };
      showDialog.value = true;
    } else {
      console.error("Failed to fetch reservation details:", response.data);
    }
  } catch (error) {
    console.error("Error fetching reservation details:", error);
  }
};

function editRow(row) {
  console.log("Edit row:", row);
}

function deleteRow(row) {
  console.log("Delete row:", row);
}

onMounted(() => {
  fetchData();
});
</script>

<style scoped>
.q-page {
  max-width: 1200px;
  margin: 0 auto;
}

.q-table__title {
  text-align: left;
  font-size: 24px;
  font-weight: bold;
  padding-bottom: 20px;
}

.q-input {
  max-width: 300px;
}
</style>
