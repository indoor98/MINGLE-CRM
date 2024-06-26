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
            {{ userName }} {{ formattedUserRole }} 님 환영합니다! :)
          </div>
          <q-btn
            outline
            rounded
            color="accent"
            icon="logout"
            label="로그아웃 "
            to="/"
            @click="logout"
          />
          <q-btn
            outline
            rounded
            color="accent"
            icon="manage_accounts"
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
            icon="login"
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
    caption: "고객 정보 조회",
    icon: "people",
    to: "/customers",
  },
  {
    title: "리뷰",
    caption: "호텔 / 다이닝 리뷰 조회",
    icon: "rate_review",
    to: "/review",
  },
  {
    title: "바우처",
    caption: "바우처 관리 - 매니저",
    icon: "redeem",
    children: [
      {
        title: "승인 / 거절",
        // caption: "승인 요청된 바우처 목록",
        icon: "rule",
        to: "/voucher-manager/requested",
      },
      {
        title: "검토 완료된 바우처 조회",
        // caption: "검토 완료된 바우처 목록",
        icon: "assignment_turned_in",
        to: "/voucher-manager/confirmed",
      },
      {
        title: "전체 바우처 조회",
        // caption: "바우처 목록",
        icon: "web_stories",
        to: "/voucher-manager/all",
      },
    ],
    roles: ["ROLE_MANAGER"],
  },
  {
    title: "바우처",
    caption: "바우처 관리 - 마케터",
    icon: "redeem",
    children: [
      {
        title: "초안 작성",
        // caption: "승인 요청전 바우처 목록",
        icon: "create",
        to: "/voucher-marketer/draft",
      },
      {
        title: "승인 확인 및 발송",
        // caption: "승인 요청한 바우처 목록",
        icon: "send",
        to: "/voucher-marketer/approval-check",
      },
      {
        title: "발송 및 전환 상태 조회",
        // caption: "발송 및 전환 여부 확인",
        icon: "mark_chat_read",
        to: "/voucher-marketer/email",
      },
    ],
    roles: ["ROLE_MARKETER"],
  },
  {
    title: "리워드",
    caption: "고객별 리워드 조회",
    icon: "paid",
    to: "/reward",
  },
  {
    title: "상담",
    caption: "상담 탭",
    icon: "support_agent",
    to: "/inquiry",
    roles: ["ROLE_CONSULTANT"],
  },
  {
    title: "이메일",
    caption: "이메일 관리",
    icon: "email",
    to: "/email",
  },
  {
    title: "통계",
    caption: "통계 보기",
    icon: "leaderboard",
    to: "/statistics",
  },
  {
    title: "로그",
    caption: "로그 조회",
    icon: "map",
    children: [
      {
        title: "고객 상세 조회 로그",
        // caption: "고객 상세 조회 로그 보기",
        icon: "visibility",
        to: "/view-log",
      },
      {
        title: "이메일 로그",
        // caption: "이메일 로그 보기",
        icon: "email",
        to: "/email-log",
      },
    ],
    roles: ["ROLE_MANAGER"],
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

const userName = computed(() => userStore.name);
const userRole = computed(() => userStore.role);

const roleMapping = {
  ROLE_MANAGER: "관리자",
  ROLE_MARKETER: "마케터",
  ROLE_CONSULTANT: "상담사",
  ROLE_STAFF: "직원",
};

const formattedUserRole = computed(() => {
  return roleMapping[userRole.value] || "";
});

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
