<template>
  <el-dialog
          v-model="dialogVisible"
          :title="title"
          @close="onClose"
          width="500px"
          :close-on-click-modal="false"
  >
    <el-form :model="form" ref="formRef" label-width="100px" :rules="rules">
      <el-form-item label="DBMS名:">
        <el-input v-model="dbmsNm" disabled placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="模式名称:">
        <el-input v-model="schmNm" disabled placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="表名称:" class="special-form" prop="dbTabNm">
        <el-input v-model="form.dbTabNm" placeholder="请输入"></el-input>
        <el-button type="primary" @click="handleDbNm">再次确认</el-button>
      </el-form-item>
      <el-form-item label="排列顺序:" prop="cvSortSrno">
        <el-input v-model="form.cvSortSrno" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="表说明:" prop="dbTabExplTxt">
        <el-input v-model="form.dbTabExplTxt" type="textarea" placeholder="请输入"></el-input>
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
  import { getSchmEditById, getSortSrnoDb, checkTabNm, addTab, editTab, getTabEditById } from '@/api'
  import { ElMessage } from 'element-plus'
  const data = reactive({
    dialogVisible: false,
    form: {
      cvDbmsId: '',
      cvSchmId: '',
      dbTabNm: '',
      cvSortSrno: '',
      dbTabExplTxt: ''
    },
    currentObj: {},
    title: '',
    dbmsNm: '',
    schmNm: '',
    pfTest: true,
    pfCount: 0,
    rules: {
      dbTabNm: [{
        required: true,
        validator: (rule, value, callback) => {
          if(!value) {
            callback('请输入表名')
          }else {
            if(value !== currentObj.value.dbTabNm) {
              if(!pfTest.value) {
                callback('表名重复')
                pfCount.value = 0
              }
            }
          }
          return callback()
        },
        trigger: 'blur' }],
      cvSortSrno: [{
        validator: (rule, value, callback) => {
          if(isNaN(value)) {
            callback('请输入数字')
          }
          return callback()
        },
        trigger: 'blur'
      }]
    },
    currentId: '',
    formRef: ''
  })
  const {
    dialogVisible, form, title, dbmsNm, schmNm, rules, currentId, currentObj, pfTest, pfCount, formRef
  } = toRefs(data)

  const props = defineProps({
    visible: Object
  })
  watch(props.visible, val => {
    dialogVisible.value = val.visible
    if(!val.visible) return
    title.value = val.id ? '修改表' : '新增表'
    currentId.value = val.id
    getParentInfo(val.obj)
    if(val.id) getDetail(val.obj)
    else getSort(val.obj)
  })
  const emits = defineEmits(['close', 'success'])

  const getDetail = (val) => {
    let obj = {
      cvDbTabId: val.cvDbTabId,
      cvDbmsId: val.cvDbmsId,
      cvSchmId: val.cvSchmId,
      dbTabNm: val.name,
    }
    getTabEditById(obj).then(res => {
      let d = res.data.data
      for(let key in form.value) {
        for(let key1 in d) {
          if(key === key1) {
            form.value[key] = d[key1]
          }
        }
      }
      currentObj.value.dbTabNm = d.dbTabNm
    })
  }
  const getParentInfo = (val) => {
    let params = {
      cvDbmsId: val.cvDbmsId,
      cvSchmId: val.cvSchmId,
      schmNm: val.name
    }

    getSchmEditById(params).then(res => {
      let d = res.data.data
      dbmsNm.value = d.dbmsNm
      schmNm.value = d.schmNm
      form.value.cvDbmsId = d.cvDbmsId
      form.value.cvSchmId = d.cvSchmId
    })
  }
  const handleDbNm = (val) => {
    if(!form.value.dbTabNm) {
      formRef.value.validateField('dbTabNm')
      return
    }
    let params = {
      cvDbmsId: form.value.cvDbmsId,
      cvSchmId: form.value.cvSchmId,
      dbTabNm: form.value.dbTabNm
    }
    checkTabNm(params).then(res => {
      pfTest.value = res.data.data
      pfCount.value ++
      formRef.value.validateField('dbTabNm')
      if(val === 'save') {
        handleSave()
      }else {
        if(form.value.dbTabNm === currentObj.value.dbTabNm) {
          ElMessage.success('验证通过')
        }else {
          if(pfTest.value) ElMessage.success('验证通过')
        }
      }
    })
  }
  const handleSave = async() => {
    await formRef.value.validate()
    if(currentId.value) {
      let params = {
        cvDbTabId: currentId.value,
        ...form.value
      }
      editTab(params).then(res => {
        ElMessage.success(res.data.msg)
        onClose('done')
      })
    }else {
      addTab(form.value).then(res => {
        ElMessage.success(res.data.msg)
        onClose('done')
      })
    }
  }
  const getSort = (val) => {
    let params = {
      dbmsId: val.cvDbmsId,
      schmId: val.cvSchmId
    }
    getSortSrnoDb(params).then(res => {
      form.value.cvSortSrno = res.data.data
    })
  }
  const onClose = (val) => {
    formRef.value.resetFields()
    currentId.value = ''
    pfTest.value = true
    pfCount.value = 0
    emits('close', 'pfClose')
    if(val === 'done') {
      emits('success')
    }
  }
  const onSuccess = async() => {
    pfTest.value = true
    await formRef.value.validate()
    if(!pfCount.value) {
      handleDbNm('save')
      return
    }
    handleSave()
  }
  const resetForm = () => {
    formRef.value.resetFields()
    getSort({
      cvDbmsId: form.value.cvDbmsId,
      cvSchmId: form.value.cvSchmId
    })
  }
</script>
