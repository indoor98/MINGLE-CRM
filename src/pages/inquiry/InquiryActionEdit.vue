<template>
  <q-card-section>
    <div class="text-h6">조치 내용 수정</div>
    <q-separator />
    <q-form @submit="updateAction">
      <q-select
        v-model="actionStatus"
        label="조치 상태"
        :options="actionStatusOptions"
        required
      />
      <q-input
        v-model="actionContent.content"
        label="조치 내용"
        rows="5"
        type="textarea"
        required
      />
      <q-btn label="저장" color="primary" type="submit" :loading="loading" />
      <q-btn label="취소" color="secondary" @click="$emit('cancelEdit')" />
    </q-form>
    <!-- 오류 메시지 표시 -->
    <div
      v-if="errorMessage"
      class="q-mt-xs q-pa-xs q-mb-xs q-bg-negative q-text-white"
    >
      {{ errorMessage }}
    </div>
  </q-card-section>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  inquiryActionId: {
    type: Number,
    required: true,
  },
  initialActionStatus: {
    type: String,
    required: true,
  },
  initialActionContent: {
    type: String,
    required: true,
  },
});

const emit = defineEmits(["actionUpdated"]);
const actionStatus = ref(props.initialActionStatus);
const actionContent = ref({
  content: props.initialActionContent,
});
const loading = ref(false);
const errorMessage = ref(null);

const actionStatusOptions = [
  { label: "조치 불필요", value: "조치 불필요" },
  { label: "조치 전", value: "조치 전" },
  { label: "조치 후", value: "조치 후" },
];

const updateAction = async () => {
  console.log("업데이트 요청 시작");

  console.log("현재 상태:", actionStatus.value.value);
  console.log("현재 내용:", actionContent.value.content);

  const updatedContent = actionContent.value.content.trim();
  const updatedStatus = actionStatus.value.value;

  if (!updatedContent) {
    console.log("조치 내용이 입력되지 않았습니다.");
    return;
  }

  // 조치 상태가 변경되지 않았을 경우 처리
  const statusChanged = updatedStatus !== props.initialActionStatus;
  console.log("폼 제출 시점 actionContent:", actionContent.value.content);

  const payload = {
    updateActionContent: actionContent.value.content,
    actionStatus: actionStatus.value.value,
  };

  loading.value = true;
  errorMessage.value = null;

  try {
    console.log("request 전송 확인: ", JSON.stringify(payload));

    const response = await axios.post(
      `http://localhost:8080/api/v1/inquiries/action/${props.inquiryActionId}`,
      payload,
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    console.log("서버 응답:", response.data);
    console.log("전송 데이터 확인:", payload);
    console.log("조치 내용 수정 성공:", response);
    emit("actionUpdated", response.data);
  } catch (error) {
    console.error(
      "조치 내용 수정 실패:",
      error.response?.data || error.message
    );
    errorMessage.value = error.response?.data?.message || "조치 내용 수정 실패";
  } finally {
    loading.value = false;
  }
};

const cancelEdit = () => {
  emit("cancelEdit");
};
</script>

<style scoped>
.q-card {
  margin-bottom: 1rem;
}
</style>
