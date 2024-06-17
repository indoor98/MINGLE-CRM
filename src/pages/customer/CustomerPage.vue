<template>
  <div class="q-pa-md">
    <CustomerList />
  </div>
</template>

<script>
import CustomerList from './CustomerList.vue';
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

const columns = [
  {
    name: "id",
    label: "#",
    field: "id",
  },
  {
    name: "name",
    required: true,
    label: "Name",
    align: "left",
    field: (row) => row.name,
    format: (val) => `${val}`,
    sortable: true,
  },
  {
    name: "grade",
    align: "center",
    label: "Grade",
    field: "grade",
    sortable: true,
  },
  { name: "phone", label: "Phone", field: "phone", sortable: true },
  { name: "address", label: "Address", field: "address" },
  { name: "employee_id", label: "Employee ID", field: "employee_id" },
  { name: "memo", label: "Memo", field: "memo" },
  { name: "gender", label: "Gender", field: "gender" },
  { name: "birth", label: "Birth", field: "birth" },
];

export default {
  components: {
    CustomerList,
  },
  setup() {
    const router = useRouter();
    const search = ref("");
    const rows = ref([]);
    const pagination = ref({
      rowsPerPage: 1000,
    });

    const fetchCustomers = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/v1/customers"
        );
        console.log(response.data); // 데이터를 콘솔에 출력하여 확인합니다.
        rows.value = response.data.map((customer) => ({
          id: customer.id,
          ...customer,
        }));
      } catch (error) {
        console.error("Error fetching customers:", error);
      }
    };

    const goToDetails = (row) => {
      console.log("Selected row:", row); // 클릭된 행의 데이터를 콘솔에 출력하여 확인합니다.
      if (row && row.id) {
        router.push({ path: `/mingle/customer-detail/${row.id}` });
      } else {
        console.error("Selected row or row.id is undefined:", row);
      }
    };

    const filteredRows = computed(() => {
      return rows.value.filter((row) =>
        row.name.toLowerCase().includes(search.value.toLowerCase())
      );
    });

    onMounted(() => {
      fetchCustomers();
    });

    return {
      columns,
      rows,
      search,
      pagination,
      filteredRows,
      goToDetails,
    };
  },
};
</script>
