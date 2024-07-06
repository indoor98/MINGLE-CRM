<template>
  <div class="fullscreen bg-primary text-white text-center q-pa-md flex flex-center">
    <div>
      <div style="font-size: 20vh">Mingle</div>
      <div class="text-h2" style="opacity: 0.4">권한이 없거나 존재하지 않는 페이지입니다.</div>
      <q-btn
        class="q-mt-xl"
        color="white"
        text-color="primary"
        unelevated
        to="/"
        label="Go Home"
        no-caps
      />
    </div>
  </div>
</template>

<script setup>
defineOptions({
  name: "ErrorNotFound",
});
</script>

<style scoped>
$primary: #3C5B6E;

.fullscreen {
  background-color: $primary;
}

.q-btn {
  transition: transform 0.3s;
}

.q-btn:hover {
  transform: scale(1.1);
}
</style>
