<template>
  <div>
    <h1>Customer Details</h1>
    <div v-if="customer">
      <p><strong>ID:</strong> {{ customer.id }}</p>
      <p><strong>Name:</strong> {{ customer.name }}</p>
<!--      <p><strong>Grade:</strong> {{ customer.grade }}</p>-->
<!--      <p><strong>Phone:</strong> {{ customer.phone }}</p>-->
      <p><strong>Address:</strong> {{ customer.address }}</p>
<!--      <p><strong>Employee ID:</strong> {{ customer.employee_id }}</p>-->
<!--      <p><strong>Memo:</strong> {{ customer.memo }}</p>-->
      <p><strong>Gender:</strong> {{ customer.gender }}</p>
<!--      <p><strong>Birth:</strong> {{ customer.birth }}</p>-->
    </div>
    <div v-else>
      <p>Customer not found.</p>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

export default {
  setup() {
    const route = useRoute();
    const customer = ref(null);

    const fetchCustomer = async (id) => {
      try {
        const response = await axios.get(`http://localhost:8080/api/v1/customers/${id}`);
        customer.value = response.data;
      } catch (error) {
        console.error('Error fetching customer:', error);
      }
    };

    onMounted(() => {
      const id = route.params.id;
      console.log(route.params.id)
      if (id) {
        fetchCustomer(id);
      }
    });


    return {
      customer,
    };
  },
};
</script>
