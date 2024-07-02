<template>
  <q-card class="q-mb-md">
    <q-card-section class="reply-section">
      <!-- <div class="text-h6">답변 수정</div> -->
      <q-separator />
      <q-form @submit="submitEdit">
        <q-input
          v-model="updatedReply.content"
          label="답변 내용"
          rows="5"
          type="textarea"
          required
          class="reply-input"
        />
        <q-btn
          label="저장"
          color="primary"
          type="submit"
          :loading="loading"
          class="reply-btn"
        />
        <q-btn
          label="취소"
          color="secondary"
          @click="cancelEdit"
          class="reply-btn"
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
  </q-card>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { api as axios } from "src/boot/axios";
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  inquiryReplyId: {
    type: Number,
    required: true,
  },
  initialReply: {
    type: String,
    required: true,
  },
});

const emit = defineEmits(["replyUpdated", "cancelEdit"]);
const updatedReply = ref({
  content: props.initialReply,
});
const loading = ref(false);
const errorMessage = ref(null);

const submitEdit = async () => {
  if (!updatedReply.value.content) return;
  loading.value = true;
  errorMessage.value = null;
  try {
    const response = await axios.post(
      `https://httpstest.mingle-crm.com/api/v1/inquiries/reply/${props.inquiryReplyId}`,
      { updatedReply: updatedReply.value.content },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    console.log("답변 수정 성공:", response.data);
    emit("replyUpdated", response.data); // 부모 컴포넌트에 알림
  } catch (error) {
    console.error("답변 수정 실패:", error.response?.data || error.message);
    errorMessage.value = error.response?.data?.message || "답변 수정 실패";
  } finally {
    loading.value = false;
  }
};

const cancelEdit = () => {
  emit("cancelEdit");
};
</script>

<style scoped>
.reply-section {
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  background-color: #ffffff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.reply-input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
  line-height: 1.5;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.reply-input:focus {
  outline: none;
  border-color: #6c63ff;
  box-shadow: 0 0 0 0.2rem rgba(108, 99, 255, 0.25);
}

.reply-btn {
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

.reply-btn:hover {
  background-color: #574fdc;
}

.reply-btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}
</style>
