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
import { useScrollStore } from "@/stores/scrollStore";
import PhoneLayoutFooter from "@/components/PhoneLayoutFooter.vue";
import PhoneLayuoutNav from "@/components/PhoneLayuoutNav.vue";
import StarSky from "@/components/StarSky.vue";

const isHidden = ref(false);
const observer = ref(null);

const route = useRoute();
const isChanging = ref(false);

const markdownCatalogue = ref(null);

const scrollStore = useScrollStore();

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

</script>

<template>
  <div class="user-out-box">
    <StarSky class="star-sky"></StarSky>
    <LayoutNav
      :style="{ transform: isHidden ? 'translateY(-100%)' : 'translateY(0)', transition: 'transform 0.8s ease' }">
    </LayoutNav>
    <PhoneLayuoutNav
      :style="{ transform: isHidden ? 'translateY(-100%)' : 'translateY(0)', transition: 'transform 0.8s ease' }">
    </PhoneLayuoutNav>
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
    <div class="control-container" :style="{ 'opacity': scrollStore.scrollProportion == 0 ? 0 : 1 }">
      <BackToTop class="back-to-top"></BackToTop>
      <NavBall class="nav-ball"></NavBall>
    </div>
    <MusicPlayerStrip class="music" ref="playerRef"></MusicPlayerStrip>
    <LyricStrip class="music" @toggle-music="toggleMusic" @skip-forward="skipForward" @skip-back="skipBack"
      @turn-off-lrc="trunOffLrc">
    </LyricStrip>
    <LayoutFooter class="footer"></LayoutFooter>
    <PhoneLayoutFooter class="footer"></PhoneLayoutFooter>
  </div>
</template>

<style scoped lang="scss">
.user-out-box {
  position: relative;
  min-height: 100vh;
  background:
    linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)),
    url('https://static.frium.top/blog/sea_of_flowers.jpg') no-repeat;
  background-size: cover;
  background-position: center;
  background-attachment: fixed;

  .star-sky {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    z-index: 0;
  }

  .selection {
    position: relative;
    padding: 80px 5vw 0 5vw;
    width: 100%;
    min-height: calc(100vh - 75px);
    z-index: 1;

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
            }
          }
        }

        .router-view {
          transition: opacity 0.5s ease, transform 0.5s ease;
          transform-origin: top;
          width: v-bind('isTimeRoute ? "100%" : "77%"');
        }
      }
    }
  }

  .control-container {
    transition: all 0.5s cubic-bezier(0.4, 0, 0.6, 1);
    z-index: 2;

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
  }

  .footer {
    width: 100%;
    position: absolute;
    z-index: 1;
  }
}

@media (max-width: 1920px) {
  .user-out-box .selection .container .cloumns .router-view {
    width: v-bind('isTimeRoute ? "100%" : "79%"');
  }
}

@media (max-width: 1440px) {
  .user-out-box {
    .selection .container .cloumns {
      .right .content {

        .personal-card,
        .markdown-catalogue {
          transform: scale(0.8);
        }
      }

      .router-view {
        width: v-bind('isTimeRoute ? "100%" : "80%"');
      }
    }
  }
}

@media (max-width: 1180px) {
  .user-out-box {
    .selection .container .cloumns {
      .right {
        width: 240px;

        .content {

          .personal-card,
          .markdown-catalogue {
            transform: scale(0.7);
          }
        }
      }
    }
  }
}

@media (max-width: 1050px) {
  .user-out-box .selection .container .cloumns {
    .right {
      display: none;
    }

    .router-view {
      width: 100%;
    }
  }
}

@media (max-width: 750px) {
  .user-out-box {
    background:
      linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)),
      url('https://static.frium.top/blog/start.jpg') no-repeat;
    background-size: cover;
    background-position: center;
    background-attachment: fixed;
    min-height: 100vh;
    margin: 0;

    .selection {
      padding: 60px 4vw 0 4vw;

      .container .cloumns {
        .right {
          display: none;
        }

        .router-view {
          width: 100%;
        }
      }
    }
  }
}
</style>
