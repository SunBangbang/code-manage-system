<template>
  <el-dialog
          v-model="dialogVisible"
          :title="title"
          @close="onClose"
          width="500px"
          :close-on-click-modal="false"
  >
    <div class="des">
      <el-descriptions border :column="1">
        <template v-if="title === '用户详情'">
          <el-descriptions-item v-for="item in user.tableNav" :key="item.prop">
            <template #label>
              {{ item.label }}
            </template>
            <template v-if="item.prop === 'userType'">
              <span v-if="reactiveData.form[item.prop] === 1">销售商用户</span>
              <span v-if="reactiveData.form[item.prop] === 2">生产商用户</span>
            </template>
            <template v-else-if="item.prop === 'sex'">
              <span v-if="reactiveData.form[item.prop] === '1'">男</span>
              <span v-if="reactiveData.form[item.prop] === '2'">女</span>
            </template>
            <template v-else>
              <span>{{ reactiveData.form[item.prop] }}</span>
            </template>
          </el-descriptions-item>
        </template>
        <template v-else-if="title === '角色详情'">
          <el-descriptions-item v-for="item in role.tableNav" :key="item.prop">
            <template #label>
              {{ item.label }}
            </template>
            {{ reactiveData.form[item.prop] }}
          </el-descriptions-item>
        </template>
        <template v-else-if="title === 'DDI用户详情'">
          <el-descriptions-item v-for="item in regis.tableNav" :key="item.prop">
            <template #label>
              {{ item.label }}
            </template>
            {{ reactiveData.form[item.prop] }}
          </el-descriptions-item>
        </template>
      </el-descriptions>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="onClose">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
  import { reactive, ref, defineProps, defineEmits, watch } from 'vue'
  import * as user from '@/assets/js/user'
  import * as role from '@/assets/js/role'
  // import * as regis from '@/assets/js/register'
  import { getUserById, getRoleInfo, getAccountInfo } from '@/api'
  const dialogVisible = ref(false)
  const title = ref('')
  const reactiveData = reactive({
    form: {}
  })

  const props = defineProps({
    visible: Object
  })
  watch(props.visible, val => {
    title.value = val.title + '详情'
    dialogVisible.value = val.visible
    getDetail(val.rowId, val.title)
  })
  const emits = defineEmits(['resetVisible'])

  const getDetail = (userId, title) => {
    if(title === '用户') {
      getUserById({ userId }).then(
          res => {
            let { delFlag, id, password, sort, ...rest } = res.data.data
            reactiveData.form = rest
          },
          err => {
            ElMessage.error(err)
          }
      )
    }else if(title === '角色') {
      getRoleInfo({ roleId: userId }).then(
          res => {
            reactiveData.form = res.data.data
          },
          err => {
            ElMessage.error(err)
          }
      )
    }else if(title === 'DDI用户') {
      getAccountInfo({ accountId: userId }).then(
        res => {
          let { admin, id, password, ...rest } = res.data.data
          reactiveData.form = rest
        },
        err => {
          ElMessageBox(err)
        }
      )
    }
  }

  const onClose = () => {
    emits('resetVisible')
  }
</script>

<style scoped>

</style>
