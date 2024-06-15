<template>
  <q-input
    clearable
    filled
    color="purple-12"
    v-model="localSearch"
    :label="label"
    @input="updateSearch"
  />
</template>

<script setup>
import { ref, watch } from 'vue';

const props = defineProps({
  modelValue: {
    type: String,
    required: true,
  },
  label: {
    type: String,
    required: true,
  },
  searchFields: {
    type: Array,
    required: true,
  }
});

const emit = defineEmits(['update:modelValue', 'search']);

const localSearch = ref(props.modelValue);

watch(localSearch, (newValue) => {
  emit('update:modelValue', newValue);
  emit('search', newValue, props.searchFields);
});

const updateSearch = () => {
  emit('update:modelValue', localSearch.value);
  emit('search', localSearch.value, props.searchFields);
};
</script>
