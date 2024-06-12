<template>
  <q-page class="q-pa-xl">
    <section>
      <div class="text-h4">상담</div>
      <q-separator class="q-my-md" />
    </section>
    <InquiryList :pagination="pagination" />
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import InquiryList from "pages/inquiry/InquiryList.vue";

const pagination = ref({ page: 1, rowsPerPage: 10, rowsNumber: 0 });
</script>

<style scoped>
.q-page {
  background-color: #f9f9f9;
  border-radius: 12px;
  /* padding: 20px; */
}

.q-separator {
  border-top: 1px solid #ddd;
  margin: 20px 0;
}
</style>
