<template>
  <div class="search-bar">
    <div class="filters">
      <q-input
        v-model="filters.keyword"
        debounce="300"
        label="Search"
        class="q-mb-sm"
        dense
      />
      <q-input
        v-model="filters.customerName"
        debounce="300"
        label="Customer Name"
        class="q-mb-sm"
        dense
      />
      <q-input
        v-model="filters.customerPhone"
        debounce="300"
        label="Customer Phone"
        class="q-mb-sm"
        dense
      />
      <q-input
        v-model="filters.inquiryTitle"
        debounce="300"
        label="Inquiry Title"
        class="q-mb-sm"
        dense
      />
      <q-input
        v-model="filters.inquiryContent"
        debounce="300"
        label="Inquiry Content"
        class="q-mb-sm"
        dense
      />
      <div class="date-filters">
        <q-btn
          @click="toggleDatePicker"
          label="날짜 선택"
          color="primary"
          class="q-mb-sm"
          dense
        />
        <div v-if="showDatePicker" class="date-picker-container">
          <q-date
            v-model="filters.startDate"
            label="시작 날짜"
            mask="YYYY-MM-DD"
            dense
          />
          <q-date
            v-model="filters.endDate"
            label="종료 날짜"
            mask="YYYY-MM-DD"
            dense
          />
        </div>
      </div>
      <q-btn
        @click="applyFilters"
        label="Search"
        color="secondary"
        class="q-mt-sm"
        dense
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { defineEmits } from "vue";

const emit = defineEmits(["update:filters"]);

const filters = ref({
  keyword: "",
  customerName: "",
  customerPhone: "",
  inquiryTitle: "",
  inquiryContent: "",
  startDate: null,
  endDate: null,
});

const showDatePicker = ref(false);

const toggleDatePicker = () => {
  showDatePicker.value = !showDatePicker.value;
};

const applyFilters = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8080/api/v1/inquiries/search",
      {
        params: {
          ...filters.value,
          startDate: filters.value.startDate
            ? filters.value.startDate.format("YYYY-MM-DD")
            : null,
          endDate: filters.value.endDate
            ? filters.value.endDate.format("YYYY-MM-DD")
            : null,
        },
      }
    );
    emit("update:filters", { ...filters.value, data: response.data.data });
  } catch (error) {
    console.error("Error fetching inquiries:", error);
  }
};
</script>

<style scoped lang="scss">
.search-bar {
  margin-bottom: 20px;
  .filters {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;

    .q-input,
    .q-date {
      flex: 1 1 calc(33.333% - 10px);
    }

    .q-input {
      @media (max-width: 600px) {
        flex: 1 1 100%;
      }
    }

    .date-filters {
      display: flex;
      flex-direction: column;
      gap: 10px;
      .q-btn {
        width: 100%;
      }
      .date-picker-container {
        display: flex;
        gap: 10px;
        .q-date {
          flex: 1;
        }
      }
    }
  }
  .q-btn {
    width: 100%;
  }
}
</style>
