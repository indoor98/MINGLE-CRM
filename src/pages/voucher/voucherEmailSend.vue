<template>
  <q-page class="q-gutter-sm">
    <q-btn flat icon="arrow_back" @click="goBack"> 뒤로</q-btn>

    <div class="row">
      <div class="row">
        <q-input v-model="toEmails" filled label="받는 사람" />
        <q-btn flat icon="send" @click="sendPersonalEmail"></q-btn>
      </div>
    </div>
    <q-input v-model="title" filled label="제목" />

    <q-editor
      class="scroll"
      style="min-height: 500px; max-height: 500px"
      v-model="content"
      min-height="5rem"
    />
  </q-page>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { api as axios } from "src/boot/axios";
import { useRouter } from "vue-router";

const props = defineProps({
  customerEmail: {
    type: String,
    required: true,
  },
  voucherCode: {
    type: String,
    required: true,
  },
});

const emits = defineEmits(["go-back"]);

const router = useRouter();

const content = ref(`Voucher Code: ${props.voucherCode}`);
const toEmails = ref(props.customerEmail);
const title = ref("");
const selectedTab = ref("개인");
const group = ref("전체");
const GroupOptions = ref(["전체", "VIP", "신규 회원", "MINGLE 팀원"]);

const sendPersonalEmail = async () => {
  try {
    const response = await axios.post(
      "http://localhost:8080/api/email/personal",
      {
        toEmail: toEmails.value,
        title: title.value,
        content: content.value,
      }
    );

    window.alert("이메일이 발송되었습니다!");
    content.value = "";
    title.value = "";
    toEmails.value = "";
  } catch (error) {
    console.log("개인 메일 발송 에러 발생");
    console.log(error);
  }
};

const goBack = () => {
  emits("go-back");
};
</script>

<style scoped></style>
