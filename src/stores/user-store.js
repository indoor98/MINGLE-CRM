import { defineStore } from "pinia";
import { ref } from "vue";
import { useTokenStore } from "src/stores/token-store";
import { jwtDecode } from "jwt-decode";

export const useUserStore = defineStore("user", () => {
  const email = ref("");
  const name = ref("");
  const id = ref();

  const tokenStore = useTokenStore();

  const loadUserInfo = () => {
    const decodedToken = jwtDecode(tokenStore.atk);
    console.log("decodedToken : ", decodedToken);
    const sub = JSON.parse(decodedToken.sub);

    email.value = sub.email ? sub.email : "";
    name.value = sub.name ? sub.name : "";
    id.value = sub.id ? sub.id : null;
  };

  return {
    email,
    name,
    id,
    loadUserInfo,
  };
});
