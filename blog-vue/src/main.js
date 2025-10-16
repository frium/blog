import { createApp } from "vue";
import pinia from "./stores";

import "@/styles/common.scss";
import App from "./App.vue";
import router from "./router";
import "element-plus/dist/index.css";
import { createHead } from "@vueuse/head";

const app = createApp(App);

app.use(pinia);
app.use(router);
app.use(createHead());
app.mount("#app");
