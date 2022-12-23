<template>
  <router-view v-slot="{ Component }">
    <transition name="transitionRouter">
      <component :is="Component" />
    </transition>
  </router-view>
</template>

<script setup>
  import { onMounted } from 'vue'

  onMounted(() => {
    window.addEventListener('contextmenu', e => {
      e.preventDefault()
    })
  })
</script>
