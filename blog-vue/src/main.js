import { createApp } from "vue";
import pinia from "./stores";

import "@/styles/common.scss";
import App from "./App.vue";
import router from "./router";
import { createHead } from "@vueuse/head";
import { setupDirectives } from "@/directive";

const app = createApp(App);
app.use(pinia);
app.use(router);
app.use(createHead());
setupDirectives(app);
app.mount("#app");
window.addEventListener("load", function () {
  console.log("所有资源（包括图片、样式等）已完全加载完毕。");
  // 可以在这里执行需要依赖所有资源的代码
});
