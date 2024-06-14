<template>
  <div>
    <div class="text-h4">조치 내용 없는 문의</div>
    <q-input filled v-model="filter" label="Search" class="q-mb-md" />
    <q-table
      :rows="filteredInquiries"
      :columns="columns"
      row-key="id"
      :loading="loading"
      :pagination="pagination"
      @request="fetchInquiries"
      @row-click="onRowClick"
    />
    <q-pagination
      :page="pagination.page"
      :rows-per-page="pagination.rowsPerPage"
      :rows-number="pagination.rowsNumber"
      @page-change="fetchInquiries"
      class="q-mt-md"
      color="primary"
      boundary-links
      :max-pages="Math.ceil(pagination.rowsNumber / pagination.rowsPerPage)"
      layout="pages"
    />
    <div v-if="error" class="q-mt-md q-pa-md q-mb-md q-bg-red-2 q-text-white">
      <span>{{ error }}</span>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const inquiries = ref([]);
const loading = ref(false);
const filter = ref("");
const pagination = ref({ page: 1, rowsPerPage: 10, rowsNumber: 0 });
const error = ref(null);

const columns = [
  {
    name: "id",
    required: true,
    label: "ID",
    align: "left",
    field: "id",
    format: (val) => `${val}`,
    sortable: true,
  },
  {
    name: "customerName",
    align: "left",
    label: "Customer Name",
    field: "customerName",
    sortable: true,
  },
  {
    name: "customerPhone",
    align: "left",
    label: "Customer Phone",
    field: "customerPhone",
    sortable: true,
  },
  {
    name: "date",
    align: "left",
    label: "Date",
    field: "date",
    format: (val) => new Date(val).toLocaleString(),
    sortable: true,
  },
  { name: "type", align: "left", label: "Type", field: "type", sortable: true },
  {
    name: "isReply",
    align: "left",
    label: "Reply Status",
    field: "isReply",
    format: (val) => (val ? "Yes" : "No"),
    sortable: true,
  },
  {
    name: "employName",
    align: "left",
    label: "Employee Name",
    field: "employName",
    sortable: true,
  },
  {
    name: "inquiryTitle",
    align: "left",
    label: "Inquiry Title",
    field: "inquiryTitle",
    sortable: true,
  },
  {
    name: "inquiryContent",
    align: "left",
    label: "Inquiry Content",
    field: "inquiryContent",
    sortable: true,
  },
  {
    name: "actionStatus",
    align: "left",
    label: "Action Status",
    field: "actionStatus",
    sortable: true,
  },
];

const router = useRouter();

const fetchInquiries = async () => {
  loading.value = true;
  error.value = null;
  try {
    const response = await axios.get(
      "http://localhost:8080/api/v1/inquiries/without-action",
      {
        params: {
          page: pagination.value.page - 1,
          size: pagination.value.rowsPerPage,
        },
      }
    );
    inquiries.value = response.data.data.content.map((item) => ({
      id: item.id,
      customerName: item.customerName,
      customerPhone: item.customerPhone,
      date: new Date(item.date).toLocaleString(),
      type: item.type,
      isReply: item.isReply,
      employName: item.employName,
      inquiryTitle: item.inquiryTitle,
      inquiryContent: item.inquiryContent,
      actionStatus: item.actionStatus,
    }));
    pagination.value.rowsNumber = response.data.data.totalElements;
  } catch (error) {
    console.error("문의를 가져오지 못했습니다. :", error);
    error.value = "문의를 가져오는데 실패했습니다. 잠시 후 다시 시도해주세요.";
  } finally {
    loading.value = false;
  }
};

const onRowClick = (event, row) => {
  console.log("선택한 문의 상세 정보:", row);
  if (row && row.id) {
    router.push({ name: "InquiryDetailPage", params: { inquiryId: row.id } });
  } else {
    console.error("유효하지 않은 문의입니다. :", row);
  }
};

onMounted(fetchInquiries);

watch(
  [() => pagination.value.page, () => pagination.value.rowsPerPage],
  fetchInquiries,
  { immediate: true }
);

const filteredInquiries = computed(() => {
  if (!filter.value) {
    return inquiries.value;
  }
  const lowerCaseFilter = filter.value.toLowerCase();
  return inquiries.value.filter(
    (inquiry) =>
      inquiry.customerName.toLowerCase().includes(lowerCaseFilter) ||
      inquiry.customerPhone.toLowerCase().includes(lowerCaseFilter) ||
      inquiry.inquiryTitle.toLowerCase().includes(lowerCaseFilter) ||
      inquiry.inquiryContent.toLowerCase().includes(lowerCaseFilter)
  );
});
</script>

<style lang="scss" scoped>
$primary-color: #007bff;
$secondary-color: #6c757d;

.q-pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;

  .q-pagination__list {
    display: flex;
    justify-content: center;
  }

  .q-pagination__item {
    margin: 0 5px;
    &.q-pagination__item--active {
      background-color: $primary-color;
      color: white;
      border-radius: 50%;
    }
  }

  .q-pagination__item:hover {
    background-color: $secondary-color;
  }
}

.error-message {
  background-color: #ff4d4f;
  color: white;
  padding: 10px;
  border-radius: 5px;
  text-align: center;
  margin-top: 10px;
}
</style>
