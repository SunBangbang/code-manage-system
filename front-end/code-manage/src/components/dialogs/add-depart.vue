<template>
  <el-dialog
          v-model="dialogVisible"
          :title="title"
          @close="onClose"
          width="500px"
          :close-on-click-modal="false"
  >
    <div class="dialog-border">
      <el-form :model="form" ref="formRef" label-width="90px" :rules="rules">
        <el-form-item label="上层部门:" prop="hlvCvUserId">
          <el-tree-select v-model="form.hlvCvUserId"
                          :data="treeData"
                          :props="treeDefaultProps"
                          check-strictly
                          :render-after-expand="false"
          />
        </el-form-item>
        <el-form-item label="部门名:" prop="userNm">
          <el-input v-model="form.userNm" placeholder="请输入"></el-input>
        </el-form-item>
        <el-form-item label="部门说明:" prop="userExplTxt">
          <el-input v-model="form.userExplTxt" placeholder="请输入" type="textarea"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="onSuccess">确定</el-button>
        <el-button @click="onClose">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
  import { reactive, toRefs, watch } from 'vue'
  import { getDeptTree, addDept, editDept, getDeptById } from '@/api'
  import { treeDefaultProps } from '@/assets/js/common-data'
  import { ElMessage } from 'element-plus'
  const data = reactive({
    dialogVisible: false,
    title: '',
    form: {
      hlvCvUserId: '',
      userExplTxt: '',
      userNm: '',
    },
    departId: '',
    formRef: '',
    treeData: [],
    rules: {
      // hlvCvUserId: [{ required: true, message: '请选择上级部门', trigger: 'change' }],
      userNm: [{ required: true, message: '请输入部门名', trigger: 'blur' }],
    }
  })
  const { dialogVisible, title, form, departId, formRef, treeData, rules } = toRefs(data)

  const props = defineProps({
    visibleObj: {
      type: Object,
      require: true
    }
  })
  watch(props.visibleObj, val => {
    dialogVisible.value = val.visible
    if(!val.visible) return
    title.value = val.id ? '修改部门' : '添加部门'
    departId.value = val.id
    getTreeData(departId.value, val.parentId)
  })
  const emits = defineEmits(['close', 'success'])

  const getTreeData = (id, parentId) => getDeptTree().then(res => {
    treeData.value = res.data.data
    form.value.hlvCvUserId = parentId
    if(id) getDetail(id)
  })

  const getDetail = (id) => {
    getDeptById({ id }).then(res => {
      let d = res.data.data
      if(d.hlvCvUserId === '0') {
        form.value.hlvCvUserId = ''
      }else {
        form.value.hlvCvUserId = d.hlvCvUserId
      }
      form.value.userExplTxt = d.userExplTxt
      form.value.userNm = d.userNm
    })
  }
  const onClose = (val) => {
    departId.value = ''
    formRef.value.resetFields()
    emits('close', 'close')
    if(val === 'done') emits('success')
  }
  const onSuccess = async() => {
    await formRef.value.validate()
    if(!form.value.hlvCvUserId) form.value.hlvCvUserId = '0'
    if(departId.value) {
      let params = {
        cvUserId: departId.value,
        ...form.value
      }
      editDept(params).then(res => {
        ElMessage(res.data.msg)
        onClose('done')
      })
    }else {
      addDept(form.value).then(res => {
        ElMessage(res.data.msg)
        onClose('done')
      })
    }
  }
</script>

<style scoped>

</style>
