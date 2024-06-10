<template>
  <q-drawer
    v-model="drawer"
    :width="240"
    bordered
    :mini="$q.screen.lt.md"
    :breakpoint="$q.screen.sizes.sm"
  >
    <q-scroll-area class="fit">
      <q-list>
        <template v-for="(menuItem, index) in menuList1" :key="index">
          <q-item clickable :to="menuItem.to" v-ripple>
            <q-item-section avatar>
              <q-icon :name="menuItem.icon" />
            </q-item-section>
            <q-item-section>
              {{ menuItem.label }}
            </q-item-section>
          </q-item>
          <q-separator :key="'sep' + index" v-if="menuItem.separator" />
        </template>
      </q-list>
    </q-scroll-area>
  </q-drawer>
</template>

<script setup>
import { ref } from 'vue';

const drawer = ref(true);
const menuList1 = [
  {
    icon: 'person',
    label: '고객',
    separator: false,
    to: "/mingle/customer",
  },
  {
    icon: 'calendar_today',
    label: '예약',
    separator: false,
    // to: "/mingle/reservation",
  },
  {
    icon: 'chat',
    label: '상담',
    separator: false,
    // to: "/mingle/consultation",
  },
  {
    icon: 'attach_money',
    label: '판매',
    separator: false,
    // to: "/mingle/sales",
  },
  {
    icon: 'insert_chart',
    label: '통계',
    separator: true,
    // to: "/mingle/statistics",
  },
];
</script>

<style lang="scss" scoped></style>
