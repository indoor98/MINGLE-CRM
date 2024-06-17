<template>
  <q-page class="flex flex-center">
    <q-card flat :style="{ minWidth: '400px' }">
      <q-card-section>
        <div class="text-h6 text-weight-bold">회원가입</div>
        <div class="text-subtitle2 text-grey">감사합니다 🙂</div>
      </q-card-section>
      <q-card-section>
        <q-form>
          <div class="q-gutter-y-lg">
            <q-input
              filled
              type="text"
              label="이름"
              hint="이름을 입력해주세요!"
              v-model="name"
            ></q-input>
            <q-input
              filled
              label="이메일"
              hint="Email을 입력해주세요!"
              v-model="email"
            ></q-input>
            <!-- class="q-mt-md" -->
            <q-input
              filled
              type="password"
              label="비밀번호"
              hint="영문 대/소문자 포함 8자 이상"
              v-model="password"
            ></q-input>
            <q-input
              filled
              type="password"
              label="비밀번호 확인"
              hint="영문 대/소문자 포함 8자 이상"
              v-model="passwordCheck"
            ></q-input>
            <q-select
              outlined
              filled
              type="text"
              v-model="authority"
              :options="authorityOptions"
              label="권한"
            />
          </div>

          <q-btn
            class="full-width q-mt-lg"
            unelevated
            color="primary"
            size="lg"
            label="가입하기"
            @click="signUp"
          />
          <q-btn
            class="full-width q-mt-md"
            flat
            label="로그인 하기"
            to="/signin"
          />
        </q-form>
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";

const email = ref("");
const password = ref("");
const passwordCheck = ref("");
const name = ref("");
const authority = ref("");
const authorityOptions = ref(["매니저", "마케터", "상담사"]);

const router = useRoute;
const authorityConverter = (korean) => {
  if (korean === "매니저") {
    authority.value = "ROLE_MANAGER";
  } else if (korean === "상담사") {
    authority.value = "ROLE_CONSULTANT";
  } else if (korean === "마케터") {
    authority.value = "ROLE_MARKETER";
  }
};

const signUp = async () => {
  try {
    if (password.value !== passwordCheck.value) {
      window.alert("비밀번호를 다시 확인해주세요!");
      return;
    }

    const request = ref({});
    request.value.name = name.value;
    request.value.email = email.value;
    request.value.password = password.value;
    authorityConverter(authority.value);
    request.value.authority = authority.value;
    console.log(request.value);

    const response = await axios.post(
      "http://localhost:8080/api/v1/auth/signup",
      request.value
    );

    console.log(response.data);
    router("/");
  } catch (error) {
    console.log(error);
  }
};

// /api/v1/auth/signup
</script>
