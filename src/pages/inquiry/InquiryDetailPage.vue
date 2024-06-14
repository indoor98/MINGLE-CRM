<template>
  <q-page class="q-pa-xl">
    <section>
      <div class="text-h4">문의 상세</div>
      <q-separator class="q-my-md" />

      <!-- 로딩 표시 -->
      <q-spinner v-if="loading" />

      <!-- 문의 상세 정보 -->
      <div v-else>
        <q-card class="q-mb-md">
          <q-card-section>
            <div class="text-h6">문의 정보</div>
            <q-separator />
            <q-list>
              <q-item>
                <q-item-section
                  >ID: {{ inquiryDetail.inquiryResponse.id }}</q-item-section
                >
              </q-item>
              <q-item>
                <q-item-section
                  >고객 이름:
                  {{
                    inquiryDetail.inquiryResponse.customerName
                  }}</q-item-section
                >
              </q-item>
              <q-item>
                <q-item-section
                  >고객 전화:
                  {{
                    inquiryDetail.inquiryResponse.customerPhone
                  }}</q-item-section
                >
              </q-item>
              <q-item>
                <q-item-section
                  >문의 제목:
                  {{
                    inquiryDetail.inquiryResponse.inquiryTitle
                  }}</q-item-section
                >
              </q-item>
              <q-item>
                <q-item-section
                  >문의 내용:
                  {{
                    inquiryDetail.inquiryResponse.inquiryContent
                  }}</q-item-section
                >
              </q-item>
              <q-item>
                <q-item-section
                  >답변 상태:
                  {{
                    inquiryDetail.inquiryResponse.isReply ? "Yes" : "No"
                  }}</q-item-section
                >
              </q-item>
              <q-item>
                <q-item-section
                  >담당 직원:
                  {{ inquiryDetail.inquiryResponse.employName }}</q-item-section
                >
              </q-item>
              <q-item>
                <q-item-section
                  >액션 상태:
                  {{
                    inquiryDetail.inquiryResponse.actionStatus
                  }}</q-item-section
                >
              </q-item>
            </q-list>
          </q-card-section>
        </q-card>

        <!-- 답변 정보 -->
        <q-card v-if="inquiryDetail.inquiryReplyResponse">
          <q-card-section>
            <div class="text-h6">답변 정보</div>
            <q-separator />
            <q-list>
              <q-item>
                <q-item-section
                  >답변 담당자 이메일:
                  {{ inquiryDetail.inquiryReplyResponse.email }}</q-item-section
                >
              </q-item>
              <q-item>
                <q-item-section
                  >답변 내용:
                  {{ inquiryDetail.inquiryReplyResponse.reply }}</q-item-section
                >
              </q-item>
              <q-item>
                <q-item-section
                  >답변 날짜:
                  {{ inquiryDetail.inquiryReplyResponse.date }}</q-item-section
                >
              </q-item>
            </q-list>
            <q-btn
              v-if="!isEditing"
              label="수정"
              color="secondary"
              @click="isEditing = true"
            />
          </q-card-section>
          <InquiryReplyEdit
            v-if="isEditing"
            :inquiry-reply-id="inquiryDetail.inquiryReplyResponse.id"
            :initial-reply="inquiryDetail.inquiryReplyResponse.reply"
            @replyUpdated="handleReplyUpdated"
          />
        </q-card>

        <q-card v-else>
          <q-card-section>
            <div class="text-h6">답변 등록</div>
            <InquiryReply
              :inquiry-id="parseInt(inquiryId)"
              @replySubmitted="handleReplySubmitted"
            />
          </q-card-section>
        </q-card>

        <!-- 조치 정보 -->
        <q-card v-if="inquiryDetail.inquiryActionResponse">
          <q-card-section>
            <div class="text-h6">조치 정보</div>
            <q-separator />
            <q-list>
              <q-item v-if="inquiryDetail.inquiryActionResponse">
                <q-item-section
                  >조치 담당자 이메일:
                  {{
                    inquiryDetail.inquiryActionResponse.email
                  }}</q-item-section
                >
              </q-item>
              <q-item v-if="inquiryDetail.inquiryActionResponse">
                <q-item-section
                  >조치 내용:
                  {{
                    inquiryDetail.inquiryActionResponse.actionContent
                  }}</q-item-section
                >
              </q-item>
              <q-item v-if="inquiryDetail.inquiryActionResponse">
                <q-item-section
                  >조치 상태:
                  {{
                    inquiryDetail.inquiryActionResponse.actionStatus
                  }}</q-item-section
                >
              </q-item>
              <q-item v-if="inquiryDetail.inquiryActionResponse">
                <q-item-section
                  >조치 날짜:
                  {{ inquiryDetail.inquiryActionResponse.date }}</q-item-section
                >
              </q-item>
            </q-list>
            <q-btn
              v-if="!isActionEditing"
              label="수정"
              color="secondary"
              @click="isActionEditing = true"
            />
          </q-card-section>
          <InquiryActionEdit
            v-if="isActionEditing"
            :inquiry-action-id="inquiryDetail.inquiryActionResponse.id"
            :initial-action-status="
              inquiryDetail.inquiryActionResponse.actionStatus
            "
            :initial-action-content="
              inquiryDetail.inquiryActionResponse.actionContent
            "
            @actionUpdated="handleActionUpdated"
            @cancelEdit="isActionEditing = false"
          />
        </q-card>

        <q-card v-else>
          <q-card-section>
            <div class="text-h6">조치 내용 등록</div>
            <InquiryAction
              :inquiry-id="parseInt(inquiryId)"
              @actionSubmitted="handleActionSubmitted"
            />
          </q-card-section>
        </q-card>
      </div>
    </section>
  </q-page>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import axios from "axios";
import InquiryReply from "src/pages/inquiry/InquiryReply.vue";
import InquiryReplyEdit from "src/pages/inquiry/InquiryReplyEdit.vue";
import InquiryAction from "src/pages/inquiry/InquiryAction.vue";
import InquiryActionEdit from "src/pages/inquiry/InquiryActionEdit.vue";

const route = useRoute();
const inquiryId = route.params.inquiryId;
const inquiryDetail = ref(null);
const loading = ref(true);
const isEditing = ref(false);
const isActionEditing = ref(false);

const fetchInquiryDetail = async () => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/v1/inquiries/${inquiryId}`
    );
    console.log("문의 상세 정보:", response.data.data); // 로그 추가
    inquiryDetail.value = response.data.data;
  } catch (error) {
    console.error("문의 상세 정보를 가져오는 데 실패했습니다:", error);
  } finally {
    loading.value = false;
  }
};

const handleReplySubmitted = () => {
  fetchInquiryDetail(); // 답변이 등록된 후, 문의 상세 정보를 새로고침
};

const handleReplyUpdated = () => {
  fetchInquiryDetail(); // 답변이 수정된 후, 문의 상세 정보를 새로고침
};

const handleActionSubmitted = () => {
  fetchInquiryDetail(); // 조치 내용이 등록된 후, 문의 상세 정보를 새로고침
};

const handleActionUpdated = () => {
  // isActionEditing.value = false;
  fetchInquiryDetail();
};

onMounted(fetchInquiryDetail);
</script>

<style scoped>
.q-page {
  max-width: 800px;
  margin: auto;
}
</style>
