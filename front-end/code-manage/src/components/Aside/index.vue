<template>
  <div class="left-menu">
    <el-menu ref="menu"
             router
             class="el-menu-vertical-demo"
             collapse-transition
             :collapse="collapse"
             :default-active="defaultArr"
             unique-opened>
      <el-sub-menu v-for="item in navs" :key="item.path" :index="item.path">
        <template #title>
          <el-icon><component :is="item.icon"></component></el-icon>
          <span>{{ item.name }}</span>
        </template>
        <el-menu-item v-for="item1 in item.children"
                      :key="item1.path"
                      :index="item1.path"
                      @click="handleClick(item1)"
        >
          <span>{{ item1.name }}</span>
        </el-menu-item>
      </el-sub-menu>

    </el-menu>
  </div>
</template>

<script setup>
  import { computed, ref, onMounted } from 'vue'
  import { useRouter, useRoute } from 'vue-router'
  import { useStore } from 'vuex'
  import { findAll } from '@/assets/js/findAllParentNodes'
  const router = useRouter()
  const route = useRoute()
  const store = useStore()

  const navs = router.options.routes[1].children

  const handleClick = (item) => {
    console.log(item);
    let breadList = findAll(navs, item).reverse()

    sessionStorage.setItem('breadList', JSON.stringify(breadList))
    sessionStorage.setItem('defaultIndex', item.path)
    store.commit('changeIndex', item.path)
    store.commit('changeBreadList', breadList)
  }
  const collapse = computed(() => {
    return store.state.isCollapse
  })
  // const navs = computed(() => {
  //   return store.state.menuList
  // })
  const defaultArr = computed(() => {
    return store.state.defaultIndex
  })
</script>

<style scoped lang="less">
  .left-menu {
    height: 100%;
    .el-menu {
      height: 100%;
      // background: #404759;
      .el-sub-menu {
        /deep/.el-sub-menu__title {
					height: 40px;
          i, span {
            color: rgba(0, 0, 0, .8);
          }

          /deep/&:hover {
            background: rgba(0, 0, 0, .8);

            i, span {
              color: rgba(0, 0, 0, .8);
            }
          }
        }

        .el-menu {
          // background: #404759;
          .el-menu-item {
						justify-content: flex-end;
            span {
              color: rgba(0, 0, 0, .8);
            }

            &:hover {
              // background: rgba(0, 0, 0, .8);

              i, span {
                color: rgba(0, 0, 0, .8);;
              }
            }
          }

          .el-menu-item.is-active {
            background: #d9ecff;
            span {
              color: rgba(0, 0, 0, .8);
            }
          }
        }
      }
    }
  }
</style>
