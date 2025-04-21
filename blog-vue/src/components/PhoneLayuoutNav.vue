<script setup>

const showOperation = () => {
  console.log('showOperation');

}
const handelSearch = () => {
  console.log('search');

}
</script>

<template>
  <div class="phone-layout-nav">
    <img class="operation" src="@/assets/icons/menu.svg" @click="showOperation" />
    <h3 class="title">frium's blog</h3>
    <img class="search" src="@/assets/icons/search.svg" @click="handelSearch" />
  </div>
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

@media (max-width:750px) {
  .phone-layout-nav {
    display: flex;
  }
}
</style>
