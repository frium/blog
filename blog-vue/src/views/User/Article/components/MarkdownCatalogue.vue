<script setup>
import { onMounted, ref } from 'vue';
const headers = ref([]);
onMounted(() => {
  const markdownContainer = document.getElementById('markdown-container');
  if (markdownContainer) updateCatalogue(markdownContainer);
});
const updateCatalogue = (markdownContainer) => {
  headers.value = markdownContainer.querySelectorAll('h1, h2, h3');
  headers.value.forEach((header, index) => {
    header.id = index;
  });
  let lastActiveIndex = -1;
  const observer = new IntersectionObserver((entries) => {

    entries.reverse().forEach(entry => {
      if (entry.isIntersecting) {
        const mdCatalogueLi = document.querySelectorAll('.md-catalogue ul li');
        const currentIndex = parseInt(entry.target.id);
        if (currentIndex > lastActiveIndex ||
          entry.boundingClientRect.top < window.innerHeight * 0.1) {
          mdCatalogueLi.forEach((li, liIndex) => {
            li.classList.toggle('active', liIndex === currentIndex);
          });
          lastActiveIndex = currentIndex;
        }
      }
    });

  },
    {
      root: null,
      rootMargin: "0px 0px -96% 0px",
      threshold: 0
    }
  );
  headers.value.forEach(header => observer.observe(header));
};
const scrollToHeader = (index) => {
  const header = headers.value[index];
  if (header) {
    header.scrollIntoView({
      behavior: 'smooth',
      block: 'start'
    });
  }
};
</script>

<template>
  <div class="md-catalogue">
    <p>目录</p>
    <hr>
    <ul>
      <li v-for="(header, index) in headers" :key="index">
        <a v-if="header.tagName === 'H1'" @click.prevent="scrollToHeader(index)" :data-index="index">
          {{ header.innerText }}
        </a>
        <ol v-else class="h2-ol">
          <a v-if="header.tagName === 'H2'" @click.prevent="scrollToHeader(index)" :data-index="index">
            {{ header.innerText }}
          </a>
          <ol v-else class="h3-ol">
            <a @click.prevent="scrollToHeader(index)" :data-index="index">
              {{ header.innerText }}
            </a>
          </ol>
        </ol>
      </li>
    </ul>
  </div>

</template>

<style scoped lang="scss">
.md-catalogue {
  position: relative;
  width: 100%;
  height: 36vh;
  background: rgba(40, 44, 52, 0.6);
  padding: 12px 15px;
  color: #999;
  border-radius: 7px;

  p {
    margin-left: 25px;

    &::before {
      top: 14px;
      left: 15px;
      content: "";
      position: absolute;
      width: 20px;
      height: 20px;
      background-image: url("@/assets/icons/book.svg");
      background-size: contain;
    }
  }

  hr {
    margin: 6px 0 10px 0;
    border: none;
    border-top: 2px solid #414243;
  }

  ul {
    overflow: auto;
    height: calc(36vh - 70px);

    &::-webkit-scrollbar {
      display: none;
    }

    .active {
      color: #f6cac9;
      background: #444a55;
    }

    li {
      overflow: hidden;
      padding: 2px 30px;
      border-radius: 3px;

      &:hover {
        background: #444a55;
      }

      a {
        position: relative;
        display: -webkit-box;
        font-size: 15px;
        padding: 2px 0;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;

        &::before {
          top: 6px;
          left: -24px;
          position: absolute;
          content: "";
          display: inline-block;
          width: 16px;
          height: 16px;
          background-image: url("@/assets/icons/clip.svg");
          background-size: contain;
        }
      }
    }
  }


  .h2-ol {
    margin-left: 14px;

    .h3-ol {
      margin-left: 14px;
    }
  }
}
</style>
