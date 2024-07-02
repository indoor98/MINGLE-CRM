<template>
  <q-page class="q-gutter-sm">
    <div class="col flex" style="justify-content: space-between">
      <q-btn @click="showCreationModal = true" label="수신자 선택하기" />
      <q-btn
        flat-lined
        @click="SendingValidate"
        label="발송"
        icon="send"
      ></q-btn>
    </div>
    <q-input
      v-model="toEmails"
      filled
      label="수신자"
      hint="수신자 선택하기를 사용해주세요!"
      readonly
    />
    <q-input v-model="title" filled label="제목" />

    <q-editor
      class="scroll"
      style="min-height: 500px; max-height: 500px"
      v-model="content"
      min-height="5rem"
    />

    <q-dialog v-model="showCreationModal">
      <customer-list-modal @selected-emails="onSelectedEmails" />
    </q-dialog>

    <q-dialog v-model="showSendModal">
      <q-card>
        <q-card-section class="text-h6 q-pa-xl">
          <div>선택된 수신자 수 : {{ selectedCount }}</div>
          <div>메일을 보내시겠습니까?</div>
        </q-card-section>

        <q-card-section class="col flex" style="justify-content: space-between"
          ><q-btn @click="showSendModal = false"> 취소 </q-btn>
          <q-btn :loading="loadingState" @click="sendGroupEmail"
            >발송 하기
          </q-btn>
        </q-card-section>
      </q-card>
    </q-dialog>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
// import axios from "axios";
import { api as axios } from "src/boot/axios";
import CustomerListModal from "src/components/email/CustomerListModal.vue";

const showCreationModal = ref(false);
const showSendModal = ref(false);

const content = ref("");
const toEmails = ref([]);
const title = ref("");
const selectedCount = ref(0);
const loadingState = ref(false);

const SendingValidate = () => {
  if (selectedCount.value === 0) {
    window.alert("받을 사람을 선택해주세요 !");
  } else {
    showSendModal.value = true;
  }
};

const sendGroupEmail = async () => {
  try {
    loadingState.value = true;

    const response = await axios.post(
      "https://httpstest.mingle-crm.com/api/email/group",
      {
        toEmails: toEmails.value,
        title: title.value,
        content: content.value,
      }
    );

    window.alert("이메일이 발송되었습니다!");
    content.value = "";
    title.value = "";
    toEmails.value = "";
    showSendModal.value = false;
  } catch (error) {
    console.log("개인 메일 발송 에러 발생");
    console.log(error);
  } finally {
    loadingState.value = false;
  }
};

const onSelectedEmails = (emails) => {
  // toEmails.value = emails;
  showCreationModal.value = false;
  toEmails.value = emails;
  console.log(toEmails);
  selectedCount.value = toEmails.value.length;
};
</script>
