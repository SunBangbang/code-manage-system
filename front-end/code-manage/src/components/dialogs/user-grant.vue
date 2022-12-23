<template>
  <el-dialog
          v-model="dialogVisible"
          title="授权"
          @close="onClose"
          width="500px"
          :close-on-click-modal="false"
  >
    <el-form :model="form" label-width="60px">
      <el-button type="primary" style="margin-bottom: 10px">菜单组</el-button>
      <el-form-item label="菜单组:">
        <el-tree-select
            v-model="form.arr"
            :data="treeData"
            multiple
            check-strictly
            :props="userDefaultProps"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="onSuccess">确定</el-button>
        <el-button @click="onClose">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
  import { reactive, watch, toRefs } from 'vue'
  import { getRoleByUserId, getAllRole, addUserRole } from '@/api'
  import { userDefaultProps } from '@/assets/js/common-data'
  import { ElMessage } from 'element-plus'
  const data = reactive({
    dialogVisible: false,
    form: {
      arr: []
    },
    treeData: [],
    currentId: ''
  })
  const {
    dialogVisible, form, treeData, currentId
  } = toRefs(data)

  const props = defineProps({
    visibleObj: Object
  })
  watch(props.visibleObj, val => {
    dialogVisible.value = val.visible
    if(!val.visible) return
    currentId.value = val.id
    getAllRoles(val.id)
  })
  const emits = defineEmits(['close', 'success'])

  const getAllRoles = (id) => getAllRole().then(res => {
    treeData.value = res.data.data
    getRole(id)
  })
  const getRole = (userId) => {
    getRoleByUserId({ userId }).then(res => {
      form.value.arr = res.data.data.map(v => v.cvUserId)
    })
  }

  const onClose = () => {
    emits('close', 'userClose')
  }
  const onSuccess = () => {
    let params = {
      cvUserId: currentId.value,
      roleList: form.value.arr
    }
    console.log(params);
    addUserRole(params).then(res => {
      ElMessage.success(res.data.msg)
      onClose()
      emits('success')
    })
  }
</script>

<style scoped>

</style>
