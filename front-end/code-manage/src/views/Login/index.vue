<template>
  <div class="login">
    <img class="login-img" src="../../assets/img/loginBg_02.png" alt="">
    <div class="login-content">
      <div class="login-box">
        <div class="login-title">编码管理系统</div>
        <el-form :model="form" ref="formRef" :rules="rules">
          <el-form-item prop="cvLgnId">
            <el-input v-model="form.cvLgnId" :prefix-icon="User" size="large" placeholder="请输入登录名"></el-input>
          </el-form-item>
          <el-form-item prop="lgnPwdEncd">
            <el-input v-model="form.lgnPwdEncd" :prefix-icon="Lock" size="large" @keydown.enter="loginUser" type="password" show-password placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="loginUser" size="large" :loading="loading">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { reactive, ref } from 'vue'
  import { login, getLoginUser } from '@/api'
  import { ElMessage } from 'element-plus'
  import { useRouter } from 'vue-router'
  import { useStore } from 'vuex'
  import { getMenus } from '@/assets/js/getMenu'
  import { User, Lock } from '@element-plus/icons-vue'

  const router = useRouter()
  const store = useStore()
  const loading = ref(false)
  const formRef = ref()
  const form = reactive({
    cvLgnId: '',
    lgnPwdEncd: ''
  })
  const rules = reactive({
    cvLgnId: [{ required: true, message: '请输入登录名', trigger: 'blur' }],
    lgnPwdEncd: [{ required: true, message: '请输入密码', trigger: 'blur' }]
  })

  const getUser = () => getLoginUser().then(
      res => {
        const { id, userName } = res.data.data
        sessionStorage.setItem('userName', userName)
        sessionStorage.setItem('userId', id)
      },
      err => {
        ElMessage(err)
      }
  )

  const loginUser = async () => {
    await formRef.value.validate()
    loading.value = true
    console.log(222);
    login(form).then(res => {
      console.log(res);
      if(res.data.code === 200) {
        // getUser()
        sessionStorage.setItem('token', res.data.data)
        store.commit('getLoginName', form.cvLgnId)
        sessionStorage.setItem('loginName', form.cvLgnId)
        setTimeout(() => {
          getMenus().then(res => {
            let arr = res.filter(v => v.name !== '首页')
            sessionStorage.setItem('arr', JSON.stringify(arr))
            store.commit('changeMenu', arr)
            loading.value = false
            router.push('/home')
          })
        }, 100)
      }
    }).catch(err => {
      loading.value = false
      ElMessage.error(err.data.msg)
    })
  }
</script>
