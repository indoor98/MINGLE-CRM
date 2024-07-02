<template>
  <q-card-section class="action-section">
    <!-- <div class="text-h6">조치 내용 수정</div> -->
    <q-separator />
    <q-form @submit="updateAction">
      <q-select
        v-model="actionStatus"
        label="조치 상태"
        :options="actionStatusOptions"
        required
        class="action-select"
      />
      <q-input
        v-model="actionContent.content"
        label="조치 내용"
        rows="5"
        type="textarea"
        required
        class="action-input"
      />
      <q-btn
        label="저장"
        color="primary"
        type="submit"
        :loading="loading"
        class="action-btn"
      />
      <q-btn
        label="취소"
        color="secondary"
        @click="$emit('cancelEdit')"
        class="action-btn"
      />
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
import { api as axios } from "src/boot/axios";
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
      `https://httpstest.mingle-crm.com/api/v1/inquiries/action/${props.inquiryActionId}`,
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
.action-section {
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  background-color: #ffffff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.action-select,
.action-input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
  line-height: 1.5;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.action-input:focus {
  outline: none;
  border-color: #6c63ff;
  box-shadow: 0 0 0 0.2rem rgba(108, 99, 255, 0.25);
}

.action-btn {
  margin-top: 10px;
  padding: 8px 16px;
  font-size: 14px;
  border-radius: 4px;
  background-color: #6c63ff;
  color: #ffffff;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.action-btn:hover {
  background-color: #574fdc;
}

.action-btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
