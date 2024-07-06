import { defineConfig } from "vite";

export default defineConfig({
  resolve: {
    alias: {
      "./preload-helper": "vite/dist/node/chunks/preload-helper.js",
    },
  },
});
