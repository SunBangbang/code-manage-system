<template>
  <div class="header-nav">
    <div class="header-left">
      <div class="logo">
        <span>编码管理系统</span>
      </div>
      <el-icon v-if="collapse" class="pre-icon" @click="changeCollapse(!collapse)"><expand /></el-icon>
      <el-icon v-else="!collapse" class="pre-icon" @click="changeCollapse(!collapse)"><fold /></el-icon>
<!--      <el-icon @click="toHome" class="house-icon" title="首页"><House /></el-icon>-->
      <el-breadcrumb>
        <el-breadcrumb-item @click="toHome">
          <span class="header-home">首页</span>
        </el-breadcrumb-item>
        <el-breadcrumb-item v-for="item in list" :key="item.path">
          {{ item.name }}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="header-right">
      <el-avatar :size="30" round icon="User" />
      <span class="right-name">{{ userName }}</span>
      <el-icon title="退出" @click="logoutSystem"><SwitchButton></SwitchButton></el-icon>
    </div>
  </div>
  <!--<EditPassword :visible="passwordVisible" :passwordId="passwordId" @resetVisible="resetVisible"></EditPassword>-->
</template>

<script setup>
  import { useStore } from 'vuex'
  import { useRouter } from 'vue-router'
  import { computed, ref } from 'vue'
  import { ElMessageBox } from 'element-plus'
  import { logout } from '@/api'
  // import EditPassword from '@/components/dialogs/edit-password'
  const store = useStore()
  const router = useRouter()
  const list = computed(() => {
    return store.state.breadList
  })
  const collapse = computed(() => {
    return store.state.isCollapse
  })
  const passwordVisible = ref(false)
  const passwordId = ref('')
  const userName = sessionStorage.getItem('loginName')

  const changeCollapse = (val) => {
    store.commit('changeIsCollapse', val)
  }
  const logoutSystem = () => {
    ElMessageBox.confirm('确定退出吗?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      logout().then(
          res => {
            store.commit('getLoginName', '')
            router.push('/')
            sessionStorage.clear()
          },
          err => {
            ElMessage(err)
          }
      )
    }).catch(() => {})
  }

  const resetVisible = () => {
    passwordVisible.value = false
  }

  const toHome = () => {
    router.push('/home')
    sessionStorage.setItem('defaultIndex', '/home')
    store.commit('changeIndex', '/home')
    sessionStorage.setItem('breadList', JSON.stringify([]))
    store.commit('changeBreadList', [])
  }

  const handleCommand = (val) => {
    if(val === 1) {
      passwordVisible.value = true
      passwordId.value = sessionStorage.getItem('userId')
    }else if(val === 2) {
      logoutSystem()
    }
  }
</script>

<style scoped lang="less">
  .header-nav {
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .header-left {
      display: flex;
      flex-direction: row;
      align-items: center;

      .house-icon {
        margin-left: 20px;
      }

      .logo {
        width: 220px;
        display: flex;
        align-items: center;

        img {
          border-radius: 4px;
          width: 35px;
        }

        span {
          font-size: 25px;
          font-weight: bold;
          margin-left: 12px;
          color: #fff;
        }
      }

      .pre-icon.el-icon {
        font-size: 24px;
        margin-right: 4px;
        svg {
          padding-top: 1px;
        }
      }
    }

    .header-right {
      display: flex;
      align-items: center;
      .el-avatar {
        background: #fff;
        color: #888;
      }
      .right-name {
        color: #fff;
        margin: 0 10px;
        font-size: 20px;
      }
    }
    .el-icon {
      font-size: 20px;
      color: #fff;
    }
  }
</style>
