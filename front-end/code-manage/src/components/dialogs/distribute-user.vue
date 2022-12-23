<template>
  <el-dialog
          v-model="dialogVisible"
          title="分配用户"
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
          {{ item.userName }}
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
  import { getUserByRole, getUserList, saveByRole } from '@/api'
  import { ElMessage } from 'element-plus'

  const dialogVisible = ref(false)
  const treeData = ref([])
  const checkedData = ref([])
  const roleId = ref('')
  const treeRef = ref()
  const defaultProps = reactive({
    label: 'userName'
  })

  const props = defineProps({
    visible: Object
  })
  watch(props.visible, val => {
    dialogVisible.value = val.visible
    roleId.value = val.rowId
    if(val.visible) {
      getTreeData(val.rowId)
    }
  })
  const emits = defineEmits(['resetVisible'])

  //查询用户树
  const getTreeData = (rowId) => getUserList().then(
    res => {
      treeData.value = res.data.data
      getUserByRoles(rowId)
    },
    err => {
      ElMessage.error(err)
    }
  )

  //查询角色关联的用户
  const getUserByRoles = (roleId) => getUserByRole({ roleId }).then(
    res => {
      let data = res.data.data
      treeRef.value.setCheckedKeys(data)
      checkedData.value = treeRef.value.getCheckedNodes()
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
      roleId: roleId.value,
      userIdList: checkedData.value.map(v => v.id)
    }
    saveByRole(obj).then(
      res => {
        ElMessage.success(res.data.msg)
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
