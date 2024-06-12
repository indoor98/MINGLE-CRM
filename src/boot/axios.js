import { boot } from "quasar/wrappers";
import axios from "axios";
import { useTokenStore } from "src/stores/token-store";

// Be careful when using SSR for cross-request state pollution
// due to creating a Singleton instance here;
// If any client changes this (global) instance, it might be a
// good idea to move this instance creation inside of the
// "export default () => {}" function below (which runs individually
// for each client)
const api = axios.create({ baseURL: "https://localhost:8080" });

export default boot(({ app }) => {
  // for use inside Vue files (Options API) through this.$axios and this.$api
  const store = useTokenStore();

  api.interceptors.request.use(async (config) => {
    // 상태관리에서 토큰 가져오기
    const atk = store.atk;
    const atkExpiration = store.atkExpiration;

    if (atk == null || Date(atkExpiration) < Date.now) {
      const response = await api.get("/api/v1/auth/renew", {
        withCredentials: true,
      });

      store.setSigninResponse(
        response.data.data.atk,
        response.data.data.atkExpiration,
        response.data.data.rtkExpiration
      );
    }
  });

  app.config.globalProperties.$axios = axios;
  // ^ ^ ^ this will allow you to use this.$axios (for Vue Options API form)
  //       so you won't necessarily have to import axios in each vue file

  app.config.globalProperties.$api = api;
  // ^ ^ ^ this will allow you to use this.$api (for Vue Options API form)
  //       so you can easily perform requests against your app's API
});

export { api };
