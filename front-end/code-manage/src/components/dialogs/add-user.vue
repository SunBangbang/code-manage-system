<template>
  <el-dialog
          v-model="dialogVisible"
          :title="title"
          @close="onClose"
          width="500px"
          :close-on-click-modal="false"
  >
    <el-form :model="form" ref="formRef" label-width="100px" :rules="rules">
      <el-form-item label="登录ID:" prop="cvLgnId" class="special-form">
        <el-input v-model="form.cvLgnId" placeholder="请输入"></el-input>
        <el-button type="primary" @click="handleLgnId">再次确认</el-button>
      </el-form-item>
      <el-form-item label="用户名:" prop="userNm">
        <el-input v-model="form.userNm" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="密码:" prop="lgnPwdEncd">
        <el-input v-model="form.lgnPwdEncd" type="password" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="确认密码:" prop="confirmPass">
        <el-input v-model="form.confirmPass" type="password" placeholder="请输入"></el-input>
      </el-form-item>
<!--      <el-form-item label="员工编号:" prop="cvLgnId" class="special-form">-->
<!--        <el-input v-model="form.cvLgnId" placeholder="请输入"></el-input>-->
<!--        <el-button type="primary" @click="handleLgnId">再次确认</el-button>-->
<!--      </el-form-item>-->
      <el-form-item label="电子邮件地址:" prop="mlbxUrl">
        <el-input v-model="form.mlbxUrl" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="部门名称:" prop="hlvCvUserId">
        <el-tree-select v-model="form.hlvCvUserId"
                        :data="treeData"
                        :check-strictly="true"
                        :props="treeDefaultProps"
                        :render-after-expand="false"
        />
      </el-form-item>
      <el-form-item label="ID有效期:" prop="bnacExprDt">
        <el-date-picker
                v-model="form.bnacExprDt"
                type="date"
                placeholder="请选择"
                value-format="YYYYMMDD"
                format="YYYYMMDD"
        ></el-date-picker>
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
  import {addUserBasiM, getUserBasiMById, getDeptTree,
    editUserBasiM, checkLgnId } from '@/api'
  import { treeDefaultProps } from '@/assets/js/common-data'
  import { ElMessage } from 'element-plus'
  const data = reactive({
    dialogVisible: false,
    title: '',
    form: {
      cvUserId: '',
      cvLgnId: '',
      userNm: '',
      lgnPwdEncd: '',
      confirmPass: '',
      code: '',
      mlbxUrl: '',
      hlvCvUserId: '',
      bnacExprDt: '',
    },
    treeData: [],
    rules: {
      cvLgnId: [{
        required: true,
        validator: (rule, value, callback) => {
          if(!value) {
            callback('请输入登录ID')
          }else {
            if(value !== currentObj.value.cvLgnId) {
              if(!lgnTest.value) {
                callback('登录ID重复')
                lgnCount.value = 0
              }
            }
          }
          return callback()
        },
        trigger: 'blur'
      }],
      userNm: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
      // lgnPwdEncd: [{ required: true, message: '请输入密码', trigger: 'blur' }],
      confirmPass: [{
        required: false,
        validator: (rule, value, callback) => {
          if(form.value.lgnPwdEncd !== value) {
            callback('两次密码不一致')
          }
          return callback()
        },
        trigger: 'blur' }],
      bnacExprDt: [{ required: true, message: '请选择ID有效期', trigger: 'change' }],
      hlvCvUserId: [{ required: true, message: '请选择部门名称', trigger: 'change' }],
      mlbxUrl: [{ type: 'email', message: '请输入正确的邮箱', trigger: ['blur', 'change'] }],
    },
    formRef: '',
    currentId: '',
    lgnTest: true,
    lgnCount: 0,
    currentObj: {}
  })
  const {
    dialogVisible, title, form, rules, formRef, currentId,
    lgnTest, lgnCount, treeData, currentObj
  } = toRefs(data)

  const props = defineProps({
    visibleObj: Object
  })
  watch(props.visibleObj, val => {
    dialogVisible.value = val.visible
    if(!val.visible) return
    title.value = val.id ? '修改用户' : '添加用户'
    currentId.value = val.id
    getTreeData(val.id)
  })
  const emits = defineEmits(['close', 'success'])

  const getDetail = (id) => {
    getUserBasiMById(id).then(res => {
      let d = res.data.data
      for(let key in form.value) {
        for(let key1 in d) {
          if(key === key1) form.value[key] = d[key1]
        }
      }
      form.value.confirmPass = form.value.lgnPwdEncd
      currentObj.value = {
        cvUserId: d.cvUserId,
        cvLgnId: d.cvLgnId
      }
    })
  }
  const getTreeData = (id) => getDeptTree().then(res => {
    treeData.value = res.data.data
    if(id) getDetail(id)
  })

  const handleLgnId = (val) => {
    console.log(val);
    if(!form.value.cvLgnId) {
      formRef.value.validateField('cvLgnId')
      return
    }
    checkLgnId(form.value.cvLgnId).then(res => {
      lgnTest.value = res.data.data
      lgnCount.value ++
      formRef.value.validateField('cvLgnId')
      if(val === 'save') {
        handleSave()
      }else {
        if(form.value.cvLgnId === currentObj.value.cvLgnId) {
          ElMessage.success('验证通过')
        }else {
          if(lgnTest.value) ElMessage.success('验证通过')
        }
      }
    })
  }

  const resetForm = () => {
    formRef.value.resetFields()
    currentId.value = ''
    lgnTest.value = true
    lgnCount.value = 0
  }
  const onClose = (val) => {
    resetForm()
    emits('close', 'close')
    if(val === 'done') emits('success')
  }
  const onSuccess = async() => {
    lgnTest.value = true
    console.log(lgnTest.value);
    await formRef.value.validate()
    if(!lgnCount.value) {
      handleLgnId('save')
      return
    }
    handleSave()
  }

  const handleSave = async() => {
    await formRef.value.validate()
    if(currentId.value) {
      let params = {
        cvUserId: currentId.value,
        ...form.value
      }
      editUserBasiM(params).then(res => {
        ElMessage.success(res.data.msg)
        onClose('done')
      })
    }else {
      addUserBasiM(form.value).then(res => {
        ElMessage.success(res.data.msg)
        onClose('done')
      })
    }
  }
</script>

<style scoped>

</style>
