<template>
  <q-layout view="hHh lpR lff">
    <q-header elevated class="bg-primary text-white">
      <q-toolbar>
        <q-btn
          flat
          round
          dense
          icon="menu"
          class="q-mr-sm"
          @click="toggleLeftDrawer"
        />
        <q-avatar>
          <q-img
            src="https://github.com/kinggodgeneralteam2/TEAM2-MINGLE-CRM/assets/155680893/d2c27cc2-d62e-4459-9e66-c46426da8fac"
          />
        </q-avatar>

        <q-toolbar-title shrink class="text-subtitle1 text-weight-bolder">
          Mingle CRM
        </q-toolbar-title>
        <q-space />
        <div class="search row items-center"></div>
        <q-space />
        <div v-if="atk" class="row items-center">
          <div v-if="userName" class="q-mr-md">
            {{ userName }}님 환영합니다! :)
          </div>
          <q-btn
            outline
            rounded
            color="accent"
            icon="account_circle"
            label="로그아웃 "
            to="/"
            @click="logout"
          />
          <q-btn
            outline
            rounded
            color="accent"
            icon="account_circle"
            label="마이페이지"
            to="/mypage"
          />
        </div>
        <div v-else>
          <q-btn
            outline
            rounded
            color="accent"
            icon="account_circle"
            label="회원가입 "
            href="#/signup"
          />
          <q-btn
            outline
            rounded
            color="accent"
            icon="account_circle"
            label="로그인"
            href="#/signin"
          />
        </div>
      </q-toolbar>
    </q-header>
    <q-drawer show-if-above v-model="leftDrawerOpen" side="left" bordered>
      <q-list>
        <EssentialLink
          v-for="link in filteredLinks"
          :key="link.title"
          :link="link"
        />
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import EssentialLink from "components/EssentialLink.vue";
import { useTokenStore } from "src/stores/token-store";
import { storeToRefs } from "pinia";
import axios from "axios";
import { api as customAxios } from "/src/boot/axios";
import { useUserStore } from "src/stores/user-store";

const store = useTokenStore();
const { atk } = storeToRefs(store);
const userStore = useUserStore();

const linksList = [
  {
    title: "고객",
    caption: "고객 탭",
    icon: "school",
    to: "/customers",
  },
  {
    title: "리뷰",
    caption: "리뷰 탭",
    icon: "school",
    to: "/review",
  },
  {
    title: "바우처",
    caption: "매니저 바우처 탭",
    icon: "school",
    to: "/voucher-manager",
    roles: ["ROLE_MANAGER"],
  },
  {
    title: "바우처",
    caption: "마케터 바우처 탭",
    icon: "school",
    children: [
      {
        title: "초안 작성",
        caption: "승인 요청전 바우처 목록",
        icon: "assignment_late",
        to: "/voucher-marketer/draft",
      },
      {
        title: "승인 상태 확인",
        caption: "승인 요청한 바우처 목록",
        icon: "assignment_turned_in",
        to: "/voucher-marketer/approval-check",
      },
      {
        title: "발송 관리",
        caption: "바우처 이메일 발송 상태 목록",
        icon: "send",
        to: "/voucher-marketer/email",
      },
    ],
    roles: ["ROLE_MARKETER"],
  },
  {
    title: "리워드",
    caption: "리워드 탭",
    icon: "school",
    to: "/reward",
  },
  {
    title: "상담",
    caption: "상담 탭",
    icon: "school",
    to: "/inquiry",
    roles: ["ROLE_CONSULTANT"],
  },
  {
    title: "이메일",
    caption: "이메일 탭",
    icon: "email",
    to: "/email-log",
  },
  {
    title: "통계",
    caption: "통계 탭",
    icon: "map",
    to: "/statistics",
  },
  {
    title: "로그",
    caption: "로그 탭",
    icon: "map",
    children: [
      {
        title: "뷰 로그",
        caption: "뷰 로그 탭",
        icon: "visibility",
        to: "/view-log",
      },
      {
        title: "이메일 로그",
        caption: "이메일 로그 탭",
        icon: "email",
        to: "/email-log",
      },
    ],
  },
];

const logout = async () => {
  try {
    console.log("로그아웃");
    const response = await customAxios.get(
      "http://localhost:8080/api/v1/auth/logout",
      {
        withCredentials: true,
      }
    );
    console.log(response.status);
    store.setAtk("");
  } catch (error) {
    console.log(error);
  }
};

const renewToken = async () => {
  try {
    const response = await axios.get(
      "http://localhost:8080/api/v1/auth/renew",
      {
        withCredentials: true,
      }
    );
    console.log(response.data);
    console.log("renewToken 실행 완료");
    if (response.data.code === 200) {
      const { atk, atkExpiration } = response.data.data;
      store.setSigninResponse(atk, atkExpiration);
      console.log("갱신 완료");
    } else {
      throw new Error("Token renewal failed");
    }
  } catch (error) {
    console.log(error);
    console.log("토큰 갱신 실패 -> 로그아웃 상태");
    window.location.href = "/#/signin";
    return Promise.reject(error);
  }
};

const leftDrawerOpen = ref(false);
const logMenu = ref(false);

function toggleLeftDrawer() {
  leftDrawerOpen.value = !leftDrawerOpen.value;
}

function openLogMenu() {
  logMenu.value = true;
}

const userName = computed(() => userStore.name);
const userRole = computed(() => userStore.role);

const filteredLinks = computed(() => {
  if (!atk.value) {
    return linksList.filter((link) => !link.roles);
  } else if (userRole.value === "ROLE_MANAGER") {
    return linksList.filter((link) =>
      link.roles ? link.roles.includes("ROLE_MANAGER") : true
    );
  } else if (userRole.value === "ROLE_MARKETER") {
    return linksList.filter((link) =>
      link.roles ? link.roles.includes("ROLE_MARKETER") : true
    );
  } else if (userRole.value === "ROLE_CONSULTANT") {
    return linksList.filter(
      (link) => (link.roles ? link.roles.includes("ROLE_CONSULTANT") : true) // 컨설턴트
    );
  } else {
    return linksList;
  }
});

onMounted(async () => {
  await renewToken();
  userStore.loadUserInfo();
  console.log("Mounted. Current name state:", userStore.name);
});
</script>
