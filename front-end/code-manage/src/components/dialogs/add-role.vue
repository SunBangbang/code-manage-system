<template>
  <el-dialog
          v-model="dialogVisible"
          :title="title"
          @close="onClose"
          width="500px"
          :close-on-click-modal="false"
  >
    <el-form :model="form" ref="formRef" label-width="80px" :rules="rules">
      <el-form-item label="角色名:" prop="userNm" class="special-form">
        <el-input v-model="form.userNm" placeholder="请输入"></el-input>
        <el-button type="primary" @click="handleUserNm">再次确认</el-button>
      </el-form-item>
<!--      <el-form-item label="状态:" prop="forbiddenFlag">-->
<!--        <el-radio-group v-model="form.forbiddenFlag">-->
<!--          <el-radio :label="0">启用</el-radio>-->
<!--          <el-radio :label="1">禁用</el-radio>-->
<!--        </el-radio-group>-->
<!--      </el-form-item>-->
<!--      <div class="border"></div>-->
      <el-form-item label="备注:" prop="userExplTxt">
        <el-input type="textarea"
                  placeholder="请输入"
                  :autosize="{ minRows: 2, maxRows: 4 }"
                  v-model="form.userExplTxt"></el-input>
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
  import { ref, defineProps, defineEmits, watch } from 'vue'
  import { addRole, editRole, getRoleById, checkRoleName } from '@/api'
  import { ElMessage } from 'element-plus'
  const dialogVisible = ref(false)
  const form = ref({
    userNm: '',
    userExplTxt: ''
  })
  const title = ref('')
  const rules = ref({
    userNm: [{
      required: true,
      validator: (rule, value, callback) => {
        if(!value) {
          callback('请输入角色名')
        }else {
          if(value !== currentObj.value.userNm) {
            if(!userTest.value) {
              callback('角色名重复')
              userCount.value = 0
            }
          }
        }
        return callback()
      },
      trigger: 'blur' }]
  })
  const formRef = ref()
  const userTest = ref(true)
  const userCount = ref(0)
  const currentObj = ref({})
  const currentId = ref('')

  const props = defineProps({
    visibleObj: Object
  })
  watch(props.visibleObj, val => {
    console.log(val);
    dialogVisible.value = val.visible
    if(!val.visible) return
    console.log(111111);
    currentId.value = val.id
    title.value = val.id ? '修改角色' : '添加角色'
    if(val.id) getDetail(val.id)
  })

  const emits = defineEmits(['close', 'success'])

  const getDetail = (id) => getRoleById({ id }).then(res => {
    let d = res.data.data
    form.value.userNm = d.userNm
    form.value.userExplTxt = d.userExplTxt
    currentObj.value.userNm = d.userNm
  })
  const handleUserNm = (val) => {
    console.log(val);
    if(!form.value.userNm) {
      formRef.value.validateField('userNm')
      return
    }
    checkRoleName({ roleName: form.value.userNm }).then(res => {
      userTest.value = res.data.data
      userCount.value ++
      formRef.value.validateField('userNm')
      if(val === 'save') {
        handleSave()
      }else {
        if(form.value.userNm === currentObj.value.userNm) {
          ElMessage.success('验证通过')
        }else {
          if(userTest.value) ElMessage.success('验证通过')
        }
      }
    })
  }

  const onClose = (val) => {
    console.log(val);
    formRef.value.resetFields()
    currentId.value = ''
    userTest.value = true
    userCount.value = 0
    emits('close', 'addClose')
    if(val === 'done'){
      console.log(val);
      emits('success')
    }
  }

  const onSuccess = async() => {
    userTest.value = true
    console.log(userCount.value);
    await formRef.value.validate()
    if(!userCount.value) {
      handleUserNm('save')
      return
    }
    handleSave()
  }
  const handleSave = async() => {
    console.log(11);
    console.log(formRef.value.validate());
    await formRef.value.validate()
    console.log(currentId.value);
    if(currentId.value) {
      let params = {
        cvUserId: currentId.value,
        ...form.value
      }
      editRole(params).then(res => {
        ElMessage.success(res.data.msg)
        onClose('done')
      })
    }else {
      addRole(form.value).then(res => {
        ElMessage.success(res.data.msg)
        onClose('done')
      })
    }
  }
</script>

<style scoped>

</style>
