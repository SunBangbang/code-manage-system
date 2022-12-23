<template>
  <el-dialog
          v-model="dialogVisible"
          :title="title"
          @close="onClose"
          width="500px"
          :close-on-click-modal="false"
  >
    <el-form :model="form" ref="formRef" label-width="100px" :rules="rules">
      <el-form-item label="DBMS名称:" class="special-form" prop="dbmsNm">
        <el-input v-model="form.dbmsNm" placeholder="请输入"></el-input>
        <el-button type="primary" @click="handleDbmsNm">再次确认</el-button>
      </el-form-item>
      <el-form-item label="IP:" prop="dbmsIp">
        <el-input v-model="form.dbmsIp" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="PORT:" prop="dbmsPortVl">
        <el-input v-model="form.dbmsPortVl" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="用户账号:" prop="cvDbmsBnacId">
        <el-input v-model="form.cvDbmsBnacId" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="用户密码:" prop="userPwdEncd">
        <el-input v-model="form.userPwdEncd" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="排列顺序:" prop="cvSortSrno">
        <el-input v-model="form.cvSortSrno" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="DBMS说明:" prop="dbmsExplTxt">
        <el-input v-model="form.dbmsExplTxt" type="textarea" placeholder="请输入"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="resetForm">初始化</el-button>
        <el-button type="primary" @click="onSuccess">保存</el-button>
        <el-button @click="onClose">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
  import { reactive, watch, toRefs } from 'vue'
  import { getSortSrno, checkDdmsNm, addDbms, editDbms, getDbmsById } from '@/api'
  import { ElMessage } from 'element-plus'
  const data = reactive({
    dialogVisible: false,
    form: {
      dbmsNm: '',
      dbmsIp: '',
      dbmsPortVl: '',
      cvDbmsBnacId: '',
      userPwdEncd: '',
      cvSortSrno: '',
      dbmsExplTxt: '',
      recBgnDttm: ''
    },
    title: '',
    formRef: '',
    dbmsTest: true,
    dbmsCount: 0,
    currentObj: {},
    currentId: '',
    rules: {
      dbmsNm: [{
        required: true,
        validator: (rule, value, callback) => {
          if(!value) {
            callback('请输入DBMS名')
          }else {
            if(value !== currentObj.value.dbmsNm) {
              if(!dbmsTest.value) {
                callback('DBMS名重复')
                dbmsCount.value = 0
              }
            }
          }
          return callback()
        },
        trigger: 'blur' }],
      dbmsIp: [{
        required: true,
        message: '请输入IP',
        trigger: 'blur'
      }],
      cvDbmsBnacId: [{
        required: true,
        message: '请输入用户账号',
        trigger: 'blur'
      }],
      cvSortSrno: [{
        validator: (rule, value, callback) => {
          if(isNaN(value)) {
            callback('请输入数字')
          }
          return callback()
        },
        trigger: 'blur'
      }]

    }
  })
  const {
    dialogVisible, form, formRef, dbmsTest, dbmsCount, currentObj, rules, currentId, title, currentRecBgnDttm
  } = toRefs(data)

  const props = defineProps({
    visible: Object
  })
  watch(props.visible, val => {
    dialogVisible.value = val.visible
    if(!val.visible) return
    title.value = val.id ? '修改DBMS' : '新增DBMS'
    currentId.value = val.id
    form.value.recBgnDttm = val.recBgnDttm
    getSort()
    if(val.id) getDetail(val.id, val.recBgnDttm)
  })
  const emits = defineEmits(['close', 'success'])

  const getDetail = (dbmsId, recBgnDttm) => {
    // recBgnDttm
    getDbmsById({ dbmsId, recBgnDttm }).then(res => {
      let d = res.data.data
      currentObj.value.dbmsNm = d.dbmsNm
      for(let key in form.value) {
        for(let key1 in d) {
          if(key === key1) {
            form.value[key] = d[key1]
          }
        }
      }
    })
  }
  const getSort = () => getSortSrno().then(res => {
    form.value.cvSortSrno = res.data.data
  })
  const handleDbmsNm = (val) => {
    console.log(val);
    if(!form.value.dbmsNm) {
      formRef.value.validateField('dbmsNm')
      return
    }
    checkDdmsNm({ dbmsNm: form.value.dbmsNm }).then(res => {
      dbmsTest.value = res.data.data
      dbmsCount.value ++
      formRef.value.validateField('dbmsNm')
      if(val === 'save') {
        handleSave()
      }else {
        if(form.value.dbmsNm === currentObj.value.dbmsNm) {
          ElMessage.success('验证通过')
        }else {
          if(dbmsTest.value) ElMessage.success('验证通过')
        }
      }
    })
  }
  const handleSave = async() => {
    await formRef.value.validate()
    console.log(currentId.value);
    if(currentId.value) {
      let params = {
        cvDbmsId: currentId.value,
        ...form.value
      }
      editDbms(params).then(res => {
        ElMessage.success(res.data.msg)
        onClose('done')
      })
    }else {
      addDbms(form.value).then(res => {
        ElMessage.success(res.data.msg)
        onClose('done')
      })
    }
  }

  const onClose = (val) => {
    formRef.value.resetFields()
    currentId.value = ''
    dbmsTest.value = true
    dbmsCount.value = 0
    emits('close', 'dbmsClose')
    if(val === 'done') {
      emits('success')
    }
  }
  const onSuccess = async() => {
    dbmsTest.value = true
    await formRef.value.validate()
    if(!dbmsCount.value) {
      handleDbmsNm('save')
      return
    }
    handleSave()
  }
  const resetForm = () => {
    formRef.value.resetFields()
    getSort()
  }
</script>

<style scoped>

</style>
