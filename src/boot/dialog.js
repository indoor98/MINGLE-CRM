import { Dialog } from "quasar";

export default ({ app }) => {
  app.config.globalProperties.$dialog = Dialog;
};
