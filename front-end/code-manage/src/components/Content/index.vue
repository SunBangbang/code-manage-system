<template>
  <router-view v-slot="{ Component }">
    <transition name="transitionRouter">
      <component :is="Component" />
    </transition>
  </router-view>
</template>
