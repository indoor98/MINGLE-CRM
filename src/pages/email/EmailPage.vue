<template>
  <q-page>
    <div class="q-pa-md">
      <q-tabs
        v-model="selectedTab"
        narrow-indicator
        densealign="justify"
        class="text-primary"
      >
        <q-tab name="send" label="이메일 보내기" />
        <q-tab name="log" label="이메일 로그" />
      </q-tabs>
      <div v-if="selectedTab === 'send'">
        <EmailSendPage />
      </div>
      <div v-else-if="selectedTab === 'log'">
        <EmailLogPage @row-click="handleRowClick" />
      </div>
      <div v-else-if="selectedTab === 'detail'">
        <EmailLogDetailPage :eventId="selectedEventId" @back="handleBack" />
      </div>
    </div>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import EmailSendPage from "./EmailSendPage.vue";
import EmailLogPage from "./EmailLogPage.vue";
import EmailLogDetailPage from "./EmailLogDetailPage.vue";

const selectedTab = ref("log");
const selectedEventId = ref(null);

const handleRowClick = (eventId) => {
  selectedEventId.value = eventId;
  selectedTab.value = "detail";
};

const handleBack = () => {
  selectedTab.value = "log";
};
</script>

<style lang="scss"></style>
