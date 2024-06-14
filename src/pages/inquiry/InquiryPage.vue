<template>
  <q-page class="q-pa-xl">
    <section>
      <div class="text-h4">상담</div>
      <q-separator class="q-my-md" />
      <q-select
        v-model="selectedOption"
        :options="options"
        label="문의 유형 선택"
        class="q-my-md"
        @change="handleOptionChange"
      />
    </section>
    <InquiryList
      v-if="selectedOption.value === 'all'"
      :pagination="pagination"
      @row-click="onRowClick"
    />
    <UnansweredInquiryList
      v-if="selectedOption.value === 'unanswered'"
      :pagination="pagination"
      @row-click="onRowClick"
    />
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import InquiryList from "pages/inquiry/InquiryList.vue";
import UnansweredInquiryList from "pages/inquiry/UnansweredInquiryList.vue";

const pagination = ref({ page: 1, rowsPerPage: 10, rowsNumber: 0 });
const selectedOption = ref({ label: "전체 문의", value: "all" });
const options = [
  { label: "전체 문의", value: "all" },
  { label: "답변 없는 문의", value: "unanswered" },
];

const handleOptionChange = (value) => {
  console.log("선택된 옵션:", value);
  selectedOption.value = value; // 옵션 변경 시 selectedOption을 업데이트
};

const onRowClick = (row) => {
  console.log("클릭한 행의 상세 보기: ", row);
  selectedOption.value = row;
};
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
