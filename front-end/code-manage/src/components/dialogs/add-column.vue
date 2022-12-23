<template>
  <el-dialog
          v-model="dialogVisible"
          :title="title"
          @close="onClose"
          width="500px"
          :close-on-click-modal="false"
  >
    <el-form :model="form" ref="formRef" label-width="120px" :rules="rules">
      <el-form-item label="DBMS名:">
        <el-input v-model="dbmsNm" disabled placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="模式名:">
        <el-input v-model="schmNm" disabled placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="表名:">
        <el-input v-model="dbTabNm" disabled placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="字段名:" class="special-form" prop="dbFildNm">
        <el-input v-model="form.dbFildNm" placeholder="请输入"></el-input>
        <el-button type="primary" @click="handleFildNm">再次确认</el-button>
      </el-form-item>
      <el-form-item label="数据类型名称:" prop="dataTpNm">
        <el-input v-model="form.dataTpNm" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="整数:" prop="intmLen">
        <el-input v-model="form.intmLen" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="小数点:" prop="dcmlLen">
        <el-input v-model="form.dcmlLen" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="是否PK:" prop="pkYn">
        <el-input v-model="form.pkYn" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="是否NN:" prop="nullYn">
        <el-input v-model="form.nullYn" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="默认值:" prop="fildDfltVl">
        <el-input v-model="form.fildDfltVl" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="是否FK:" prop="frkyYn">
        <el-input v-model="form.frkyYn" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="范围:" prop="scpTxt">
        <el-input v-model="form.scpTxt" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="用户业务说明:" prop="bsnsExplTxt">
        <el-input v-model="form.bsnsExplTxt" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="是否删除:" prop="delYn">
        <el-input v-model="form.delYn" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="排列顺序:" prop="cvSortSrno">
        <el-input v-model="form.cvSortSrno" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="字段说明:" prop="dbFildExplTxt">
        <el-input v-model="form.dbFildExplTxt" type="textarea" placeholder="请输入"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="resetForm">初始化</el-button>
        <el-button type="primary" @click="onSuccess">确定</el-button>
        <el-button @click="onClose">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
  import { reactive, watch, toRefs } from 'vue'
  import {checkFildNm, addFild, editFild, getFildEditById, getTabEditById, getSortSrnoFild } from '@/api'
  import { ElMessage } from 'element-plus'
  const data = reactive({
    dialogVisible: false,
    form: {
      cvDbmsId: '',
      cvSchmId: '',
      cvDbTabId: '',
      dbFildNm: '',
      cvDttpId: '',
      dataTpNm: '',
      intmLen: '',
      dcmlLen: '',
      pkYn: '',
      nullYn: '',
      fildDfltVl: '',
      frkyYn: '',
      scpTxt: '',
      bsnsExplTxt: '',
      delYn: '',
      cvSortSrno: '',
      dbFildExplTxt: '',
    },
    title: '',
    dbmsNm: '',
    schmNm: '',
    dbTabNm: '',
    currentObj: {},
    formRef: '',
    fildCount: 0,
    currentId: '',
    fildTest: true,
    rules: {
      dbFildNm: [{
        required: true,
        validator: (rule, value, callback) => {
          if(!value) {
            callback('请输入字段名')
          }else {
            if(value !== currentObj.value.dbFildNm) {
              if(!fildTest.value) {
                callback('字段名重复')
                fildCount.value = 0
              }
            }
          }
          return callback()
        },
        trigger: 'blur' }],
      dataTpNm: [{
        required: true,
        message: '请输入数据类型名称',
        trigger: 'blur'
      }],
      pkYn: [{
        required: true,
        message: '请输入主键与否',
        trigger: 'blur'
      }],
      nullYn: [{
        required: true,
        message: '请输入NULL与否',
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
    dialogVisible, form, title, dbmsNm, schmNm, dbTabNm, formRef, rules, currentObj, fildCount, fildTest, currentId
  } = toRefs(data)

  const props = defineProps({
    visible: Object
  })
  watch(props.visible, val => {
    dialogVisible.value = val.visible
    if(!val.visible) return
    title.value = val.id ? '修改字段' : '新增字段'
    currentId.value = val.id
    getParentInfo(val.obj)
    if(val.id) getDetail(val.obj)
    else getSort(val.obj)
  })
  const emits = defineEmits(['close', 'success'])

  const getDetail = (val) => {
    console.log(val)
    let obj = {
      cvDbFildId: val.cvDbFildId,
      cvDbTabId: val.cvDbTabId,
      cvDbmsId: val.cvDbmsId,
      cvSchmId: val.cvSchmId
    }
    getFildEditById(obj).then(res => {
      let d = res.data.data
      for(let key in form.value) {
        for(let key1 in d) {
          if(key === key1) {
            form.value[key] = d[key1]
          }
        }
      }
      currentObj.value.dbFildNm = d.dbFildNm
    })
  }
  const getSort = (val) => {
    let p = {
      dbmsId: val.cvDbTabId,
      schmId: val.cvSchmId
    }
    getSortSrnoFild(p).then(res => {
      form.value.cvSortSrno = res.data.data
    })
  }
  const getParentInfo = (v) => {
    let obj = {
      cvDbTabId: v.cvDbTabId,
      cvDbmsId: v.cvDbmsId,
      cvSchmId: v.cvSchmId,
      dbTabNm: v.name
    }
    getTabEditById(obj).then(res => {
      let d = res.data.data
      dbmsNm.value = d.dbmsNm
      schmNm.value = d.schmNm
      dbTabNm.value = d.dbTabNm
      form.value.cvDbmsId = d.cvDbmsId
      form.value.cvSchmId = d.cvSchmId
      form.value.cvDbTabId = d.cvDbTabId
    })
  }
  const handleFildNm = (val) => {
    console.log(form.value);
    console.log(formRef.value);
    if(!form.value.dbFildNm) {
      formRef.value.validateField('dbFildNm')
      return
    }
    let params = {
      cvDbTabId: form.value.cvDbTabId,
      cvDbmsId: form.value.cvDbmsId,
      cvSchmId: form.value.cvSchmId,
      dbFildNm: form.value.dbFildNm
    }
    checkFildNm(params).then(res => {
      fildTest.value = res.data.data
      fildCount.value ++
      formRef.value.validateField('dbFildNm')
      if(val === 'save') {
        handleSave()
      }else {
        if(form.value.dbFildNm === currentObj.value.dbFildNm) {
          ElMessage.success('验证通过')
        }else {
          if(fildTest.value) ElMessage.success('验证通过')
        }
      }
    })
  }
  const handleSave = async() => {
    if(form.value.dcmlLen === '') form.value.dcmlLen = 0
    if(form.value.intmLen === '') form.value.intmLen = 0
    form.value.cvDttpId = form.value.dataTpNm
    await formRef.value.validate()
    if(currentId.value) {
      let params = {
        cvDbFildId: currentId.value,
        ...form.value
      }
      editFild(params).then(res => {
        ElMessage.success(res.data.msg)
        onClose('done')
      })
    }else {
      addFild(form.value).then(res => {
        ElMessage.success(res.data.msg)
        onClose('done')
      })
    }
  }
  const onClose = (val) => {
    formRef.value.resetFields()
    currentId.value = ''
    fildTest.value = true
    fildCount.value = 0
    emits('close', 'cClose')
    if(val === 'done') {
      emits('success')
    }
  }
  const onSuccess = async() => {
    fildTest.value = true
    await formRef.value.validate()
    if(!fildCount.value) {
      handleFildNm('save')
      return
    }
    handleSave()
  }
  const resetForm = () => {
    formRef.value.resetFields()
    getSort({
      dbmsId: form.value.cvDbmsId,
      schmId: form.value.cvSchmId
    })
  }
</script>
