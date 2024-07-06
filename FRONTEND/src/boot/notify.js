// src/boot/notify.js
import { Notify } from "quasar";

export default ({ app }) => {
  app.config.globalProperties.$notify = Notify;
};
