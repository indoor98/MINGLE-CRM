<template>
  <q-page>
    <div class="row">
      <div class="col flex flex-center">
        <q-card flat :style="{ minWidth: '400px' }">
          <q-card-section>
            <div class="text-h6 text-weight-bold q-mt-xl">로그인 하기</div>
          </q-card-section>
          <q-card-section>
            <q-form>
              <div class="q-gutter-y-lg">
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
                  v-model="password"
                  hint="영문 대/소문자 포함 8자 이상"
                ></q-input>
              </div>
              <q-btn
                class="full-width q-mt-lg"
                unelevated
                color="primary"
                size="lg"
                label="로그인 하기"
                @click="signIn"
              />
              <q-btn
                class="full-width q-mt-md"
                flat
                label="회원가입"
                to="/signup"
              />
              <q-btn
                class="full-width q-mt-xs"
                flat
                label="대시보드로 이동"
                to="/"
              />
            </q-form>
          </q-card-section>
        </q-card>
      </div>
    </div>
  </q-page>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { useTokenStore } from "stores/token-store";

const store = useTokenStore();
const email = ref("");
const password = ref("");
const router = useRouter();

const signIn = async () => {
  try {
    const response = await axios.post(
      "http://localhost:8080/api/v1/auth/signintest",
      {
        email: email.value,
        password: password.value,
      },
      {
        withCredentials: true,
      }
    );

    // 메모리에 atk, atk 만료시간, rtk 만료시간 저장
    store.setSigninResponse(
      response.data.data.atk,
      response.data.data.atkExpiration
    );

    router.push("/");
  } catch (error) {
    console.log(error);
  }
};
</script>

<style lang="scss"></style>
