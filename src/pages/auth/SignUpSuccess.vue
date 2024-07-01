<!-- src/pages/SignUpSuccess.vue -->
<template>
  <q-page class="flex flex-center">
    <q-card flat :style="{ minWidth: '400px' }">
      <q-card-section>
        <div class="text-h6 text-weight-bold">회원 가입 완료</div>
        <div class="text-subtitle2 text-grey">가입 요청이 완료되었습니다. 메일을 확인해주세요 🙂</div>
      </q-card-section>
      <q-card-section>
        <q-btn
          class="full-width q-mt-lg"
          unelevated
          color="primary"
          size="lg"
          label="로그인 페이지로 이동"
          to="/signin"
        />
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup>
// Add any necessary script here
</script>
