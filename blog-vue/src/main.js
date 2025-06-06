import { createApp } from 'vue'
import pinia from './stores'

import '@/styles/common.scss'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
const app = createApp(App);

app.use(pinia);
app.use(ElementPlus);
app.use(router);

if ('serviceWorker' in navigator) {
  window.addEventListener('load', () => {
    navigator.serviceWorker.register('/sw.js')
      .then((registration) => {
        console.log('Service Worker 注册成功: ', registration.scope);
      })
      .catch((error) => {
        console.log('Service Worker 注册失败: ', error);
      });
  });
}

app.mount('#app');


