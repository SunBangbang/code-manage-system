<template>
  <el-dialog
          v-model="dialogVisible"
          title="分配角色"
          @close="onClose"
          width="600px"
          :close-on-click-modal="false"
  >
    <div class="distribute">
      <div class="distribute-left">
        <el-tree :data="treeData"
                 show-checkbox
                 ref="treeRef"
                 node-key="id"
                 :props="defaultProps"
                 check-on-click-node
                 @check="handleCheckNode"
        ></el-tree>
      </div>
      <ul class="distribute-right">
        <li v-for="item in checkedData" :key="item.id">
          {{ item.roleName }}
        </li>
      </ul>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="onClose">关闭</el-button>
        <el-button type="primary" @click="onSuccess">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
  import { ref, reactive, defineProps, defineEmits, watch } from 'vue'
  import { getRolePage, getRoleByUser, saveUserRole } from '@/api'
  import { ElMessage } from 'element-plus'

  const dialogVisible = ref(false)
  const treeData = ref([])
  const checkedData = ref([])
  const userId = ref('')
  const treeRef = ref()
  const defaultProps = reactive({
    label: 'roleName'
  })

  const props = defineProps({
    visible: Object
  })
  watch(props.visible, val => {
    dialogVisible.value = val.visible
    userId.value = val.rowId
    if(val.visible) {
      getTreeData(val.rowId)
    }
  })
  const emits = defineEmits(['resetVisible'])

  //查询角色树
  const getTreeData = (rowId) => getRolePage().then(
    res => {
      treeData.value = res.data.data
      getRolesByUser(rowId)
    },
    err => {
      ElMessage.error(err)
    }
  )

  //查询用户关联的角色
  const getRolesByUser = (userId) => getRoleByUser({ userId }).then(
    res => {
      let data = res.data.data
      treeRef.value.setCheckedKeys(data)
      let nodes = treeRef.value.getCheckedNodes()
      checkedData.value = nodes
    },
    err => {
      ElMessage.error(err)
    }
  )

  //选择树
  const handleCheckNode = (node, data) => {
    checkedData.value = data.checkedNodes
  }

  const onClose = () => {
    emits('resetVisible')
  }
  const onSuccess = () => {
    const obj = {
      userId: userId.value,
      roleIdList: checkedData.value.map(v => v.id)
    }
    saveUserRole(obj).then(
      res => {
        ElMessage.success(res.data.msg)
        treeRef.value.setCheckedKeys([])
        onClose()
      },
      err => {
        ElMessage.error(err)
      }
    )
  }
</script>

<style scoped>

</style>
