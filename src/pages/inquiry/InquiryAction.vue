<template>
  <q-card class="q-mb-md">
    <q-card-section>
      <div class="text-h6">조치 내용 작성</div>
      <q-separator />
      <q-form @submit="submitAction">
        <q-select
          v-model="actionStatus"
          label="조치 상태"
          :options="actionStatusOptions"
          required
        />
        <q-input
          v-model="actionContent"
          label="조치 내용"
          rows="5"
          type="textarea"
          required
        />
        <q-btn label="등록" color="primary" type="submit" :loading="loading" />
      </q-form>
      <q-notify
        v-if="errorMessage"
        :color="'negative'"
        :message="errorMessage"
        position="top"
        :timeout="3000"
      />
    </q-card-section>
  </q-card>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  inquiryId: {
    type: Number,
    required: true,
  },
});

const emit = defineEmits(["actionSubmitted"]);
const actionStatus = ref("조치 상태 없음");
const actionContent = ref("");
const actionStatusOptions = [
  { label: "조치 불필요", value: "조치 불필요" },
  { label: "조치 전", value: "조치 전" },
  { label: "조치 후", value: "조치 후" },
];
const loading = ref(false);
const errorMessage = ref("");

const submitAction = async () => {
  if (!actionContent.value || !actionStatus.value.value) return;
  loading.value = true;
  errorMessage.value = "";

  try {
    console.log("액션 상태 전송 확인: ", actionStatus.value.value);

    const response = await axios.post(
      "http://localhost:8080/api/v1/inquiries/action",
      {
        inquiryId: props.inquiryId,
        actionStatus: actionStatus.value.value,
        actionContent: actionContent.value,
      },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    console.log("조치 내용 등록 성공:", response);
    emit("actionSubmitted");
  } catch (error) {
    console.error(
      "조치 내용 등록 실패:",
      error.response?.data || error.message
    );
    errorMessage.value = error.response?.data?.message || "조치 내용 등록 실패";
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.q-card {
  margin-bottom: 1rem;
}
</style>
