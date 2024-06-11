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
      </q-toolbar>
    </q-header>
    <q-drawer show-if-above v-model="leftDrawerOpen" side="left" bordered>
      <q-list>
        <EssentialLink
          v-for="link in linksList"
          :key="link.title"
          v-bind="link"
        />
      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script setup>
import { ref } from "vue";
import EssentialLink from "components/EssentialLink.vue";

defineOptions({
  name: "MainLayout",
});

const linksList = [
  {
    title: "고객",
    caption: "고객 탭",
    icon: "school",
    to: "/customer",
  },
  {
    title: "리뷰",
    caption: "리뷰 탭",
    icon: "school",
    to: "/review",
  },
  {
    title: "바우처",
    caption: "바우처 탭",
    icon: "school",
    to: "/voucher",
  },
  {
    title: "상담",
    caption: "상담 탭",
    icon: "school",
    to: "/inquiry",
  },
];

const leftDrawerOpen = ref(false);
function toggleLeftDrawer() {
  leftDrawerOpen.value = !leftDrawerOpen.value;
  //
}
</script>
