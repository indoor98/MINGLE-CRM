import { defineStore } from "pinia";
import { ref, computed } from "vue";

export const useTokenStore = defineStore("token", () => {
  // state
  const atk = ref("");
  const atkExpiration = ref("");
  const rtkExpiration = ref("");

  // getters
  const getAtk = computed(() => atk.value);

  // actions
  function setAtk(token) {
    atk.value = token;
  }

  function setSigninResponse(atkParam, atkExpirationParam) {
    atk.value = atkParam;
    atkExpiration.value = new Date(atkExpirationParam);
  }

  return {
    atk,
    atkExpiration,
    rtkExpiration,
    setAtk,
    getAtk,
    setSigninResponse,
  };
});
