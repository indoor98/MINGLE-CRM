<template>
  <div>
    <!-- 기본 링크 -->
    <q-item
      v-if="link && !link.children"
      clickable
      tag="router-link"
      :to="link.to"
      v-ripple
    >
      <q-item-section avatar>
        <q-icon :name="link.icon" />
      </q-item-section>

      <q-item-section>
        <q-item-label>{{ link.title }}</q-item-label>
        <q-item-label caption>{{ link.caption }}</q-item-label>
      </q-item-section>
    </q-item>

    <!-- 하위 메뉴가 있는 경우 -->
    <q-expansion-item v-else-if="link && link.children">
      <template v-slot:header>
        <q-item-section avatar>
          <q-icon :name="link.icon" />
        </q-item-section>
        <q-item-section>
          <q-item-label>{{ link.title }}</q-item-label>
          <q-item-label caption>{{ link.caption }}</q-item-label>
        </q-item-section>
      </template>
      <q-list>
        <EssentialLink
          v-for="child in link.children"
          :key="child.title"
          :link="child"
          :isChild="true"
          :style="{
            paddingLeft: '20px',
          }"
        />
      </q-list>
    </q-expansion-item>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  link: {
    type: Object,
    required: true,
  },
  isChild: {
    type: Boolean,
    default: false,
  },
});
</script>

<style scoped></style>
