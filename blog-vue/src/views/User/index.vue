<script setup>
import LayoutNav from "@/components/LayoutNav.vue";
import LayoutFooter from "@/components/LayoutFooter.vue";
import PersonalCard from "@/components/PersonalCard.vue";
import NavBall from "@/components/NavBall.vue";
import BackToTop from '@/components/BackToTop.vue';
import MusicPlayerStrip from '@/components/MusicPlayerStrip.vue';
import LyricStrip from '@/components/LyricStrip.vue';
import { useRoute } from 'vue-router'
import { ref, onMounted, onUnmounted, watch } from 'vue';
const isHidden = ref(false);
const observer = ref(null);


const route = useRoute();
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
  <div id="app">
    <LayoutNav
      :style="{ transform: isHidden ? 'translateY(-100%)' : 'translateY(0)', transition: 'transform 0.8s ease' }"
      id="nav"></LayoutNav>
    <div class="selection">
      <div class="container">
        <div class="cloumns">
          <div class="router-view">
            <div id="trigger" style="height: 1px; width: 100%;"></div>
            <router-view></router-view>
          </div>
          <div class="right">
            <PersonalCard v-if="!isTimeRoute" class="personal-card"
              :style="{ top: isHidden ? '30px' : '80px', transition: 'top 0.8s ease' }">
            </PersonalCard>
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
#app {
  .selection {
    position: relative;
    padding: 80px 5vw 40px 5vw;
    width: 100%;
    min-height: 100vh;

    .container {
      margin: 0 auto;

      .cloumns {
        display: flex;
        justify-content: space-between;
        width: 100%;

        .right {
          .personal-card {
            position: sticky;

            @media (max-width: 1440px) {
              & {
                transform: scale(0.8);
                transform-origin: top;
              }
            }

            @media (max-width: 1180px) {
              & {
                transform: scale(0.7);
                transform-origin: top;
              }
            }

            @media (max-width: 1000px) {
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
        }

        .router-view {
          margin-left: 1vw;
          width: v-bind('isTimeRoute ? "100%" : "76%"');

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

          @media (max-width: 1000px) {
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
}
</style>
