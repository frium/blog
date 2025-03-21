const CACHE_NAME = 'font-cache';

// 需要缓存的资源列表
const FONT_FILES = [
  '/fonts/LXGWWenKaiGBScreen.woff2'// 添加 TTF 文件
];

// 安装 Service Worker 时缓存字体文件
self.addEventListener('install', (event) => {
  event.waitUntil(
    caches.open(CACHE_NAME).then((cache) => {
      return cache.addAll(FONT_FILES);
    })
  );
});

// 拦截网络请求，返回缓存的资源
self.addEventListener('fetch', (event) => {
  event.respondWith(
    caches.match(event.request).then((response) => {
      // 如果缓存中有请求的资源，则返回缓存
      if (response) {
        return response;
      }
      // 否则从网络请求
      return fetch(event.request);
    })
  );
});
