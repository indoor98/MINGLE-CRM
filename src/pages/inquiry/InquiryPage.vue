<template>
  <q-page class="q-pa-xl">
    <q-tabs
      v-model="tab"
      dense
      class="text-h6 q-ma-md"
      indicator-color="primary"
      text-color="primary"
    >
      <q-tab label="문의 목록" name="list" />
      <q-tab label="답변" name="reply" />
    </q-tabs>
    <q-separator class="q-my-md" />
    <q-tab-panels v-model="tab">
      <q-tab-panel name="list">
        <InquiryList :pagination="pagination" />
      </q-tab-panel>
      <q-tab-panel name="reply">
        <InquiryReply :inquiryId="inquiryId" />
      </q-tab-panel>
    </q-tab-panels>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import InquiryList from "pages/inquiry/InquiryList.vue";
import InquiryReply from "pages/inquiry/InquiryReply.vue";
import { useRoute } from "vue-router";

const route = useRoute();
const tab = ref("list");
const pagination = ref({ page: 1, rowsPerPage: 10, rowsNumber: 0 });
const inquiryId = route.params.inquiryId; // 추가: 문의 ID를 라우터에서 가져옵니다.
</script>

<style scoped>
.q-tabs {
  background-color: #f7f7f7;
  border-radius: 8px;
  padding: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.q-tab {
  font-weight: bold;
  color: #333;
}

.q-separator {
  border-top: 1px solid #ddd;
  margin: 20px 0;
}

.q-page {
  background-color: #f9f9f9;
  border-radius: 12px;
  padding: 20px;
}
</style>
