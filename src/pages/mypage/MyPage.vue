<template>
  <q-page class="q-pa-md flex justify-center">
    <div class="q-gutter-md" style="max-width: 600px; width: 100%;">
      <q-card>
        <q-card-section>
          <div class="text-h6">마이페이지</div>
        </q-card-section>

        <q-separator color="grey-5" />

        <q-card-section>
          <q-form @submit="updateProfile">
            <q-input filled v-model="form.employeeName" label="이름" readonly />
            <q-separator color="grey-5" />

            <q-input filled v-model="form.employeeEmail" label="이메일" type="email" readonly />
            <q-separator color="grey-5" />

            <q-input filled v-model="form.authority" label="권한" readonly />
            <q-separator color="grey-5" />

            <q-input filled v-model="form.createdDate" label="계정 생성 날짜" readonly />
            <q-separator color="grey-5" />

            <q-input filled v-model="form.password" label="새 비밀번호" type="password" />
            <q-separator color="grey-5" />

            <q-input filled v-model="form.confirmPassword" label="비밀번호 확인" type="password" />
            <q-separator color="grey-5" />

            <div v-if="passwordMismatch" class="text-negative q-mt-md">
              비밀번호가 일치하지 않습니다.
            </div>
            <div v-if="!isPasswordValid" class="text-negative q-mt-md">
              비밀번호는 8자리 이상이어야 합니다.
            </div>

            <div class="q-mt-md">
              <q-btn type="submit" label="저장" color="primary" :disable="passwordMismatch || !isPasswordValid" />
            </div>
          </q-form>
        </q-card-section>
      </q-card>
    </div>
  </q-page>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useUserStore } from 'src/stores/user-store';
import { api as customAxios } from 'src/boot/axios';
import { Notify } from 'quasar';
import dayjs from 'dayjs'; // 날짜 형식을 포맷하기 위한 라이브러리

const userStore = useUserStore();
const form = ref({
  employeeName: '',
  employeeEmail: '',
  authority: '',
  createdDate: '',
  password: '',
  confirmPassword: ''
});

const loadUserInfo = async () => {
  try {
    const response = await customAxios.get('/api/v1/employee/profile');
    const userData = response.data;
    form.value.employeeName = userData.employeeName;
    form.value.employeeEmail = userData.employeeEmail;
    form.value.authority = userData.authority;
    form.value.createdDate = dayjs(userData.createdDate).format('YYYY-MM-DD HH:mm:ss');
  } catch (error) {
    console.error('사용자 정보 로드 중 오류 발생:', error);
  }
};

onMounted(async () => {
  await loadUserInfo();
});

const passwordMismatch = computed(() => form.value.password !== form.value.confirmPassword);
const isPasswordValid = computed(() => form.value.password.length >= 8);

const updateProfile = async (event) => {
  event.preventDefault();
  if (passwordMismatch.value || !isPasswordValid.value) {
    console.log('비밀번호가 일치하지 않거나 유효하지 않습니다.');
    return;
  }
  try {
    const response = await customAxios.post('/api/v1/employee-info', {
      password: form.value.password
    });
    if (response.status === 200) {
      console.log('프로필 업데이트 성공:', response.data);
      Notify.create({
        type: 'positive',
        message: '비밀번호가 수정되었습니다.',
        position: 'top'
      });
      setTimeout(() => {
        window.location.reload();
      }, 1000); // 1초 후에 페이지를 새로고침합니다.
    } else {
      throw new Error('프로필 업데이트 실패');
    }
  } catch (error) {
    console.error('프로필 업데이트 중 오류 발생:', error);
    Notify.create({
      type: 'negative',
      message: '비밀번호 변경 중 오류가 발생했습니다.',
      position: 'top'
    });
  }
};
</script>
