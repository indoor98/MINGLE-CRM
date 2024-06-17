<template>
  <q-card class="q-mb-md">
    <q-card-section>
      <div class="text-h6">답변 수정</div>
      <q-separator />
      <q-form @submit="submitEdit">
        <q-input
          v-model="updatedReply.content"
          label="답변 내용"
          rows="5"
          type="textarea"
          required
        />
        <q-btn label="수정" color="primary" type="submit" :loading="loading" />
      </q-form>
    </q-card-section>
  </q-card>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
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

const emit = defineEmits(["replyUpdated"]);
const updatedReply = ref({
  content: props.initialReply,
});
const loading = ref(false);

const submitEdit = async () => {
  if (!updatedReply.value.content) return;
  loading.value = true;
  try {
    const response = await axios.post(
      `http://localhost:8080/api/v1/inquiries/reply/${props.inquiryReplyId}`,
      { updatedReply: updatedReply.value.content },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    console.log("답변 수정 성공:", response.data);
    emit("replyUpdated"); // 부모 컴포넌트에 알림
  } catch (error) {
    console.error("답변 수정 실패:", error.response?.data || error.message);
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
