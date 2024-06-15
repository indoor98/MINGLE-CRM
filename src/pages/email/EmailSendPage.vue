<template>
  <q-page class="q-gutter-sm">
    <q-tabs
      v-model="selectedTab"
      narrow-indicator
      densealign="justify"
      dense
      align="left"
      class="text-primary"
    >
      <q-tab name="개인" label="개인" />
      <q-tab name="그룹" label="그룹" />
    </q-tabs>

    <div class="row">
      <div v-if="selectedTab === '개인'" class="row">
        <q-input v-model="toEmails" filled label="받는 사람" />
        <q-btn flat icon="send" @click="sendPersonalEmail"></q-btn>
      </div>
      <div v-else-if="selectedTab === '그룹'" class="row">
        <q-select
          filled
          v-model="group"
          :options="GroupOptions"
          label="고객 그룹"
          style="min-width: 216px"
        />
        <q-btn flat icon="send" @click="sendGroupEmail"></q-btn>
      </div>
    </div>
    <q-input v-model="title" filled label="제목" />

    <q-editor
      class="scroll"
      style="min-height: 500px; max-height: 500px"
      v-model="content"
      min-height="5rem"
    />

    <q-btn class=""> </q-btn>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
// import axios from "axios";
import { api as axios } from "src/boot/axios";
import { useRouter } from "vue-router";

const router = useRouter();

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
