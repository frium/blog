import { createApp } from 'vue'
import { createPinia } from 'pinia'
import '@/styles/common.scss'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
const app = createApp(App);

app.use(router);
app.use(pinia);
app.use(ElementPlus);

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


