<script setup>
const emit = defineEmits(['to-publish'])
const emitToPublish = () => {
  emit('to-publish');
}
</script>

<template>
  <div class="edit-operation">
    <button class="categories">分类</button>
    <button class="categories">设置</button>
    <button class="add" @click="emitToPublish">发布</button>
  </div>
</template>

<style scoped lang="scss">
.edit-operation {
  display: flex;
  align-items: center;
  gap: 10px;

  .add {
    background-color: black;
    color: #fff;

    &:hover {
      background: rgb(64, 64, 66);
    }
  }

  .add,
  .categories {
    padding: 3px 18px;
    border: 1px solid rgb(217, 219, 224);
    border-radius: 6px;
    transition: all 0.3s;
  }

  .categories {
    &:hover {
      background: rgb(217, 219, 224);
    }
  }

}
</style>
