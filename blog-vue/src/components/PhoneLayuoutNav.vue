<script setup>
import { ref } from 'vue';
import PhonePersonalCard from './PhonePersonalCard.vue';

const personalCard = ref(false);
const showPersonalCard = () => {
  personalCard.value = true;
}
const offPersonalCard = () => {
  personalCard.value = false;
}
const handelSearch = () => {
  console.log('search');

}
</script>

<template>
  <div class="phone-layout-nav">
    <img class="operation" src="@/assets/icons/menu.svg" @click="showPersonalCard" />
    <h3 class="title">frium's blog</h3>
    <img class="search" src="@/assets/icons/search.svg" @click="handelSearch" />
  </div>
  <div @click="offPersonalCard" class="blur-bg" :style="{
    background: personalCard ? 'rgba(0, 0, 0, 0.5)' : 'rgba(0, 0, 0, 0)',
    backdropFilter: personalCard ? 'blur(4px)' : 'blur(0px)',
    zIndex: personalCard ? '99' : '-1',
  }"></div>
  <PhonePersonalCard class="phone-personal-card-out"
    :style="{ transform: personalCard ? 'translateX(0)' : 'translateX(-100%)' }" @off-persoal-card="offPersonalCard" />
</template>

<style scoped lang="scss">
.phone-layout-nav {
  position: fixed;
  z-index: 9;
  display: none;
  height: 50px;
  width: 100%;
  background: var(--bg-color);
  align-items: center;
  padding: 0 15px;

  .search,
  .operation {
    width: 26px;
  }

  .title {
    margin: 0 auto;
    font-size: 20px;
    font-weight: 600;
    letter-spacing: 1px;
    color: var(--primary-color);
  }
}

.blur-bg {
  z-index: 99;
  position: fixed;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  transition: all 0.5s ease;
  backdrop-filter: blur(10px);
}

.phone-personal-card-out {
  position: fixed;
  z-index: 999;
  transition: transform 0.5s ease;
}

@media (max-width:750px) {
  .phone-layout-nav {
    display: flex;
  }
}
</style>
