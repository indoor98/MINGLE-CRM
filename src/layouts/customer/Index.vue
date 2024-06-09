<!-- eslint-disable vue/multi-word-component-names -->
// eslint-disable-next-line vue/multi-word-component-names
<template>
  <q-layout view="hHh Lpr lFf">
    <IndexHeader />

    <IndexDrawer />

    <IndexContent />
  </q-layout>
</template>

<script setup>
import IndexHeader from "layouts/customer/IndexHeader.vue";
import IndexDrawer from "layouts/customer/IndexDrawer.vue";
import IndexContent from "layouts/customer/IndexContent.vue";
</script>

<style lang="scss" scoped></style>
