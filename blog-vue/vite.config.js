import { fileURLToPath, URL } from "node:url";
import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import vueDevTools from "vite-plugin-vue-devtools";
import { visualizer } from "rollup-plugin-visualizer";
import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { ElementPlusResolver } from "unplugin-vue-components/resolvers";
import viteCompression from "vite-plugin-compression";
// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
    visualizer(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
    viteCompression({
      algorithm: "gzip", // 压缩算法
      ext: ".gz", // 生成的文件后缀
      threshold: 10240, // 只压缩大于10KB的文件
      deleteOriginFile: false, // 是否删除源文件
      filter: /\.(js|css|json|txt|html|ico|svg)(\?.*)?$/i, // 要压缩的文件类型
    }),
    // viteCDNPlugin({
    //   modules: [
    //     {
    //       name: "vue",
    //       var: "Vue",
    //       path: "https://cdn.jsdelivr.net/npm/vue@3.5.13/dist/vue.global.min.js",
    //     },
    //   ],
    // }),
  ],
  build: {
    assetsInlineLimit: 10240,
    rollupOptions: {
      output: {
        entryFileNames: "js/[name]-[hash].js",
        chunkFileNames: "js/[name]-[hash].js",
        assetFileNames: (assetInfo) => {
          const extType = assetInfo?.names[0].split(".").pop();
          if (extType === "css") {
            return "css/[name]-[hash][extname]";
          }
          return "assets/[name]-[hash][extname]";
        },
      },
    },
  },
  esbuild: {
    pure: ["console.log"],
    drop: ["debugger"],
  },
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
    extensions: [".mjs", ".js", ".ts", ".jsx", ".tsx", ".json", ".scss", ".css"],
  },
  server: {
    host: "0.0.0.0",
    port: 80,
    proxy: {
      "/api": {
        target: "https://blog.frium.top",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, "/api"),
      },
    },
  },
});
