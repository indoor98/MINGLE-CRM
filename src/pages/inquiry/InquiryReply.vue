<template>
  <q-card class="q-mb-md">
    <q-card-section>
      <div class="text-h6">답변 작성</div>
      <q-separator />
      <q-form @submit="submitReply">
        <q-input
          v-model="reply.content"
          label="답변 내용"
          rows="5"
          type="textarea"
          required
        />
        <q-btn label="등록" color="primary" type="submit" :loading="loading" />
      </q-form>
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

const emit = defineEmits(["replySubmitted"]); // 이벤트 정의

const reply = ref({
  content: "",
});
const loading = ref(false);

const submitReply = async () => {
  if (!reply.value.content) return;
  loading.value = true;
  try {
    // const token = localStorage.getItem("token"); // 로컬 스토리지에서 토큰 가져오기
    const response = await axios.post(
      `http://localhost:8080/api/v1/inquiries/reply`,
      {
        inquiryId: props.inquiryId,
        reply: reply.value.content,
      },
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      }
    );
    console.log("답변 등록 성공:", response.data);
    emit("replySubmitted", response.data.data); // 이벤트 발생
  } catch (error) {
    console.error("답변 등록 실패:", error.response?.data || error.message);
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
