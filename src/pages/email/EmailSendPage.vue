<template>
  <q-page class="q-gutter-sm">
    <div class="row">
      <q-btn @click="showCreationModal = true" label="고객 이메일 조회하기" />
      <q-btn
        flat-lined
        @click="sendPersonalEmail"
        label="발송 하기"
        icon="send"
      ></q-btn>
    </div>
    <q-input v-model="title" filled label="제목" />

    <q-editor
      class="scroll"
      style="min-height: 500px; max-height: 500px"
      v-model="content"
      min-height="5rem"
    />

    <q-dialog v-model="showCreationModal">
      <customer-list-modal />
      <q-card>
        <q-card-actions align="right">
          <q-btn
            flat
            label="선택 완료"
            color="primary"
            @click="createVoucher"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
// import axios from "axios";
import { api as axios } from "src/boot/axios";
import { useRouter } from "vue-router";
import CustomerListModal from "./CustomerListModal.vue";

const router = useRouter();
const showCreationModal = ref(false);

const content = ref("");
const toEmails = ref("");
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
</script>
