<template>
  <q-page padding>
    <section class="q-pa-md">
      <div class="row q-pa-xs q-gutter-md">
        <q-btn flat style="color: black; font-size: 24px" label="호텔 리뷰" />
        <q-btn flat style="color: black; font-size: 24px" label="호텔 리뷰" />
      </div>
    </section>

    <section class="row q-gutter-md q-pa-md flex flex-center">
      <!-- First Scroll Area -->
      <q-input
        filled
        bottom-slots
        v-model="text"
        label="Label"
        counter
        maxlength="12"
        :dense="dense"
      >
        <template v-slot:append>
          <q-icon
            v-if="text !== ''"
            name="close"
            @click="text = ''"
            class="cursor-pointer"
          />
          <q-icon name="search" />
        </template>
        <template v-slot:hint> Field hint </template>
      </q-input>

      <div class="col">
        <q-scroll-area style="height: 200px; max-width: 300px">
          <div v-for="n in 100" :key="`first-${n}`" class="q-py-xs">
            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
            eiusmod tempor incididunt ut labore et dolore magna aliqua.
          </div>
        </q-scroll-area>
      </div>
      <!-- Second Scroll Area -->
      <div class="col">
        <q-scroll-area style="height: 200px; max-width: 300px">
          <div v-for="n in 100" :key="`second-${n}`" class="q-py-xs">
            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do
            eiusmod tempor incididunt ut labore et dolore magna aliqua.
          </div>
        </q-scroll-area>
      </div>
    </section>

    <section class="q-mt-xl">
      <div class="row q-col-gutter-xl">
        <div v-for="n in 9" :key="n" class="col-12 col-sm-6 col-md-4 col-lg-4">
          <q-card class="my-card">
            <q-img ratio="1" src="https://cdn.quasar.dev/img/parallax2.jpg">
              <div
                class="absolute-bottom text-subtitle2 flex flex-center full-height"
              >
                Title
              </div>
            </q-img>
          </q-card>
        </div>
      </div>
    </section>
  </q-page>
</template>

<script setup></script>

<style lang="scss" scoped></style>
