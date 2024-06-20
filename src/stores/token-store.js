import { defineStore } from "pinia";
import { ref, computed } from "vue";
import { useUserStore } from "src/stores/user-store";

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

    // 액세스 토큰 설정 시 userStore의 loadUserInfo 호출
    const userStore = useUserStore();
    userStore.loadUserInfo();
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
