import { boot } from "quasar/wrappers";
import axios from "axios";
import { useTokenStore } from "src/stores/token-store";

// Create an axios instance
const api = axios.create({
  baseURL: "http://localhost:8080",
  withCredentials: true,
});

export default boot(({ app }) => {
  const store = useTokenStore();

  api.interceptors.request.use(
    async (config) => {
      const atk = store.atk;
      const atkExpiration = store.atkExpiration;

      console.log(" global axios interceptors ");
      if (config.url.includes("/api/v1/auth/renew")) {
        return config;
      }

      // Check if token is expired or not available
      if (!atk || new Date(atkExpiration) <= new Date()) {
        try {
          console.log("토큰 갱신 요청");
          const response = await api.get("/api/v1/auth/renew", {
            withCredentials: true,
          });
          console.log("요청 보냄");

          if (response.status === 200) {
            const { atk, atkExpiration, rtkExpiration } = response.data.data;
            store.setSigninResponse(atk, atkExpiration, rtkExpiration);

            // Update the config's Authorization header with the new token
            config.headers.Authorization = `Bearer ${atk}`;
          } else {
            throw new Error("Token renewal failed");
          }
        } catch (error) {
          console.log("토큰 갱신 실패 -> 로그아웃 상태");
          window.location.href = "/signin";
          return Promise.reject(error);
        }
      } else {
        // If token is valid, set it to the request's Authorization header
        config.headers.Authorization = `Bearer ${atk}`;
      }

      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  app.config.globalProperties.$axios = axios;
  app.config.globalProperties.$api = api;
});

export { api };
