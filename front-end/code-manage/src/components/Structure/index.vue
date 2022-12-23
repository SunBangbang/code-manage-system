<template>
  <el-container>
    <el-header><header-nav></header-nav></el-header>
    <el-container style="height: calc(100% - 60px)">
      <aside-menu></aside-menu>
      <el-main><main-content></main-content></el-main>
    </el-container>
  </el-container>
</template>

<script setup>
  import AsideMenu from '../Aside'
  import MainContent from '../Content'
  import HeaderNav from '../Header'
</script>
