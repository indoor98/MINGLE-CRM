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
            <div class="q-table">
              <div class="q-th">ID</div>
              <div class="q-td">{{ inquiryDetail.inquiryResponse.id }}</div>
              <div class="q-th">고객 이름</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryResponse.customerName }}
              </div>
              <div class="q-th">고객 전화</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryResponse.customerPhone }}
              </div>
              <div class="q-th">문의 제목</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryResponse.inquiryTitle }}
              </div>
              <div class="q-th">문의 내용</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryResponse.inquiryContent }}
              </div>
              <div class="q-th">문의 날짜</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryResponse.date }}
              </div>
              <div class="q-th">답변 상태</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryResponse.isReply ? "Yes" : "No" }}
              </div>
              <div class="q-th">담당 직원</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryResponse.employName }}
              </div>
              <div class="q-th">조치 상태</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryResponse.actionStatus }}
              </div>
            </div>
          </q-card-section>
        </q-card>

        <!-- 답변 정보 -->
        <q-card v-if="inquiryDetail.inquiryReplyResponse">
          <q-card-section>
            <div class="text-h6">답변 정보</div>
            <q-separator />
            <div class="q-table">
              <div class="q-th">답변 담당자 이메일</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryReplyResponse.email }}
              </div>
              <div class="q-th">답변 담당자 이름</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryReplyResponse.name }}
              </div>
              <div class="q-th">답변 내용</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryReplyResponse.reply }}
              </div>
              <div class="q-th">답변 날짜</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryReplyResponse.date }}
              </div>
            </div>
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
            @cancelEdit="isEditing = false"
          />
        </q-card>

        <!-- 답변 등록 폼 -->
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
            <div class="q-table">
              <div class="q-th">조치 담당자 이메일</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryActionResponse.email }}
              </div>
              <div class="q-th">조치 담당자 이름</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryActionResponse.name }}
              </div>
              <div class="q-th">조치 내용</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryActionResponse.actionContent }}
              </div>
              <div class="q-th">조치 상태</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryActionResponse.actionStatus }}
              </div>
              <div class="q-th">조치 날짜</div>
              <div class="q-td">
                {{ inquiryDetail.inquiryActionResponse.date }}
              </div>
            </div>
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

        <!-- 조치 등록 폼 -->
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
import { api as axios } from "src/boot/axios";
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
  fetchInquiryDetail(); // 조치 내용이 수정된 후, 문의 상세 정보를 새로고침
};

onMounted(fetchInquiryDetail);
</script>

<style scoped>
.q-page {
  max-width: 800px;
  margin: auto;
}

.text-h4 {
  font-size: 1.5rem;
  font-weight: bold;
}

.q-separator {
  margin: 20px 0;
}

.q-card {
  margin-bottom: 20px;
}

.q-card-section {
  padding: 20px;
}

.q-table {
  display: grid;
  grid-template-columns: 150px auto;
  gap: 10px;
}

.q-th {
  background-color: #f2f2f2;
  padding: 10px;
  border: 1px solid #ddd;
  font-weight: bold;
}

.q-td {
  padding: 10px;
  border: 1px solid #ddd;
}

.q-btn {
  margin-top: 10px;
}
</style>
