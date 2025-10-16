<script setup>
import { getLabelsAPI } from "@/api/article";
import { useHead } from "@vueuse/head";
import { onMounted, ref, unref } from "vue";

const keywords = ref('');
useHead({
  meta: [
    {
      name: 'keywords',
      content: keywords
    }
  ]
})
const labels = ref([]);
onMounted(async () => {
  const res = await getLabelsAPI();
  labels.value = res.data;
  keywords.value = unref(labels).join();
})
</script>

<template>
  <div class="categories">
    <div class="categories-card">
      <div class="title">文章分类</div>
      <div class="triangle"></div>
    </div>
    <hr>
    <ul>
      <li v-for="label in labels" :key="label.id">
        <RouterLink to="/" class="categorie">
          <span class="categorie-name">{{ label.labelName }}</span>
          <div class="categorie-number">{{ label.articleCount }}</div>
        </RouterLink>
      </li>
    </ul>
  </div>

</template>

<style scoped lang="scss">
.categories {
  position: relative;
  width: 100%;
  padding: 60px 18px 20px 18px;
  border-radius: 10px;
  background: var(--bg-color);
  transition: 0.6s ease-out;

  &:hover {
    background: rgba(40, 44, 52, 0.8);
  }

  .categories-card {
    position: absolute;
    top: 20px;
    left: -11px;

    .title {
      background: #f3bdbc;
      color: white;
      font-size: 16px;
      padding: 2px 14px;
      border-radius: 2px;
    }

    .triangle {
      position: absolute;
      top: 27px;
      left: -0.7px;
      width: 0;
      height: 0;
      border-top: 6px solid #f3bdbc;
      border-right: 6px solid #f3bdbc;
      border-left: 6px solid transparent;
      border-bottom: 6px solid transparent;
      filter: drop-shadow(-1px 1px 1px rgba(0, 0, 0, 0.1));
    }
  }

  hr {
    margin: 6px 0 12px 0;
    border: none;
    border-top: 1px solid #414243;
  }

  ul {

    .categorie {
      padding: 7px 25px;
      display: flex;
      justify-content: space-between;
      border-radius: 3px;

      &:hover {
        background: #373d48;
      }

      .categorie-name {
        color: #999;
      }

      .categorie-number {
        background: #43464e;
        padding: 0 8px;
        border-radius: 5px;
      }

    }
  }
}
</style>
