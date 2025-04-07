<script setup>
import LayoutNav from "@/components/LayoutNav.vue";
import LayoutFooter from "@/components/LayoutFooter.vue";
import PersonalCard from "@/components/PersonalCard.vue";
import NavBall from "@/components/NavBall.vue";
import BackToTop from '@/components/BackToTop.vue';
import MusicPlayerStrip from '@/components/MusicPlayerStrip.vue';
import LyricStrip from '@/components/LyricStrip.vue';
import MarkdownCatalogue from '@/views/User/Article/components/MarkdownCatalogue.vue';
import { useRoute } from 'vue-router'
import { ref, onMounted, onUnmounted, watch } from 'vue';

const isHidden = ref(false);
const observer = ref(null);

const route = useRoute();
const isChanging = ref(false);

const markdownCatalogue = ref(null);


const handleComponentLoaded = () => {
  markdownCatalogue.value.handleLoadCatalogue();
};

watch(route, () => {
  isChanging.value = true;
  setTimeout(() => {
    isChanging.value = false;
  }, 300);//应该是routerview的资源加载完毕后 isChanging.value = false;
});
const isTimeRoute = ref(false);

const playerRef = ref(null);
const toggleMusic = () => {
  if (playerRef.value) playerRef.value.toggleMusic();
}
const skipForward = () => {
  if (playerRef.value) playerRef.value.skipForward();
}
const skipBack = () => {
  if (playerRef.value) playerRef.value.skipBack();
}
const trunOffLrc = () => {
  if (playerRef.value) playerRef.value.trunOffLrc();
}

watch(() => route.path, (newPath) => {
  isTimeRoute.value = newPath === '/time';
},
  {
    immediate: true
  }
);

onMounted(() => {
  observer.value = new IntersectionObserver(
    ([entry]) => {
      isHidden.value = !entry.isIntersecting; //目标元素和视口相交
    },
    {
      threshold: 0.1,
      rootMargin: "-35px 0px 0px 0px"
    }
  );
  const target = document.querySelector("#trigger");
  if (target) observer.value.observe(target);
});

onUnmounted(() => {
  if (observer.value) observer.value.disconnect();
});

onMounted(() => {
  updateBodyStyle();
});
const updateBodyStyle = () => {
  document.body.style.background = 'linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)), url("https://static.frium.top/blog/sea%20of%20flowers.jpg") no-repeat';
  document.body.style.backgroundSize = 'cover';
  document.body.style.backgroundPosition = 'center';
  document.body.style.backgroundAttachment = 'fixed';
  document.body.style.color = '#c4c4c4';
}
</script>

<template>
  <div id="app">
    <LayoutNav
      :style="{ transform: isHidden ? 'translateY(-100%)' : 'translateY(0)', transition: 'transform 0.8s ease' }"
      id="nav"></LayoutNav>
    <div class="selection">
      <div class="container">
        <div class="cloumns">
          <div class="router-view"
            :style="{ opacity: isChanging ? '0.5' : '1', transform: isChanging ? 'translateY(20px)' : 'translateY(0px)' }">
            <div id="trigger" style="height: 1px; width: 100%;"></div>
            <router-view @component-loaded="handleComponentLoaded"></router-view>
          </div>
          <div class="right" v-if="!isTimeRoute">
            <div class="content" :style="{ top: isHidden ? '30px' : '80px', transition: 'top 0.8s ease' }">
              <PersonalCard class="personal-card">
              </PersonalCard>
              <MarkdownCatalogue ref="markdownCatalogue" class="markdown-catalogue"
                v-if="route.path.includes('article')"></MarkdownCatalogue>
            </div>
          </div>
        </div>
      </div>
    </div>
    <BackToTop class="back-to-top"></BackToTop>
    <NavBall class="nav-ball"></NavBall>
    <MusicPlayerStrip ref="playerRef"></MusicPlayerStrip>
    <LyricStrip @toggle-music="toggleMusic" @skip-forward="skipForward" @skip-back="skipBack"
      @turn-off-lrc="trunOffLrc"></LyricStrip>
    <LayoutFooter></LayoutFooter>
  </div>
</template>

<style scoped lang="scss">
.selection {
  position: relative;
  padding: 80px 5vw 0 5vw;
  width: 100%;
  min-height: calc(100vh - 75px);

  .container {
    margin: 0 auto;

    .cloumns {
      display: flex;
      justify-content: space-between;

      .right {
        width: 270px;

        .content {
          position: sticky;

          .personal-card,
          .markdown-catalogue {
            transform: translateZ(0) scale(0.95);
            transform-origin: top;
            transition: transform 1s ease;

            @media (max-width: 1440px) {
              & {
                transform: scale(0.8);
              }
            }

            @media (max-width: 1180px) {
              & {
                transform: scale(0.7);
              }
            }
          }
        }

        @media (max-width: 1180px) {
          & {
            width: 240px;
          }
        }

        @media (max-width: 1050px) {
          & {
            display: none;
          }
        }

        @media (max-width: 500px) {
          & {
            display: none;
          }
        }
      }

      .router-view {
        margin-left: 1vw;
        transition: opacity 0.5s ease, transform 0.5s ease;
        transform-origin: top;
        width: v-bind('isTimeRoute ? "100%" : "77%"');

        @media (min-width: 1920px) {
          & {
            width: v-bind('isTimeRoute ? "100%" : "80%"');
          }
        }

        @media (max-width: 1440px) {
          & {
            width: v-bind('isTimeRoute ? "100%" : "80%"');
          }
        }



        @media (max-width: 1050px) {
          & {
            width: 100%
          }
        }

        @media (max-width: 500px) {
          & {
            width: 400px;
          }
        }
      }
    }

  }
}

.back-to-top {
  position: fixed;
  bottom: 80px;
  right: 20px;
}

.nav-ball {
  position: fixed;
  bottom: 30px;
  right: 20px;
}
</style>
