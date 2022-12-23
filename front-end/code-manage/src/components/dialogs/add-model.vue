<template>
  <el-dialog
          v-model="dialogVisible"
          :title="title"
          @close="onClose"
          width="500px"
          :close-on-click-modal="false"
  >
    <el-form :model="form" ref="formRef" label-width="100px" :rules="rules">
      <el-form-item label="DBMS名称:">
        <el-input v-model="dbmsNm" disabled placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="模式名称:" prop="schmNm" class="special-form">
        <el-input v-model="form.schmNm" placeholder="请输入"></el-input>
        <el-button type="primary" @click="handleSchmNm">再次确认</el-button>
      </el-form-item>
      <el-form-item label="排列顺序:" prop="cvSortSrno">
        <el-input v-model="form.cvSortSrno" placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="模式说明:" prop="schmExplTxt">
        <el-input v-model="form.schmExplTxt" type="textarea" placeholder="请输入"></el-input>
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
  import { addSchm, checkSchmNm, editSchm, getSortSrnoSchm, getSchmEditById } from '@/api'
  import { ElMessage } from 'element-plus'
  const data = reactive({
    dialogVisible: false,
    form: {
      cvDbmsId: '',
      schmNm: '',
      cvSortSrno: '',
      schmExplTxt: ''
    },
    dbmsNm: '',
    title: '',
    currentId: '',
    currentObj: {},
    dbTest: true,
    dbCount: 0,
    formRef: '',
    rules: {
      schmNm: [{
        required: true,
        validator: (rule, value, callback) => {
          if(!value) {
            callback('请输入模式名')
          }else {
            if(value !== currentObj.value.schmNm) {
              if(!dbTest.value) {
                callback('模式名重复')
                dbCount.value = 0
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
    }
  })
  const {
    dialogVisible, form, title, currentId, dbmsNm, rules, dbTest, dbCount, currentObj, formRef
  } = toRefs(data)

  const props = defineProps({
    visible: Object
  })
  watch(props.visible, val => {
    dialogVisible.value = val.visible
    if(!val.visible) return
    title.value = val.id ? '修改模式' : '新增模式'
    dbmsNm.value = val.dbmsNm
    form.value.cvDbmsId = val.dbmsId
    currentId.value = val.id
    if(val.id) getDetail(val)
    else getSort(val.cvDbmsId)
  })
  const emits = defineEmits(['close', 'success'])

  const getSort = (dbmsId) => {
    // dbmsId
    getSortSrnoSchm({ dbmsId }).then(res => {
      form.value.cvSortSrno = res.data.data
    })
  }
  const getDetail = (val) => {
    let params = {
      cvDbmsId: val.cvDbmsId,
      cvSchmId: val.id,
      schmNm: val.schmNm
    }
    getSchmEditById(params).then(res => {
      let d = res.data.data
      for(let key in form.value) {
        for(let key1 in d) {
          if(key === key1) {
            form.value[key] = d[key1]
          }
        }
      }
      dbmsNm.value = d.dbmsNm
      currentObj.value.schmNm = d.schmNm
    })
  }
  const handleSchmNm = (val) => {
    if(!form.value.schmNm) {
      formRef.value.validateField('schmNm')
      return
    }
    checkSchmNm({ schmNm: form.value.schmNm, cvDbmsId: form.value.cvDbmsId }).then(res => {
      dbTest.value = res.data.data
      dbCount.value ++
      formRef.value.validateField('schmNm')
      if(val === 'save') {
        handleSave()
      }else {
        if(form.value.schmNm === currentObj.value.schmNm) {
          ElMessage.success('验证通过')
        }else {
          if(dbTest.value) ElMessage.success('验证通过')
        }
      }
    })
  }
  const handleSave = async() => {
    await formRef.value.validate()
    if(currentId.value) {
      let params = {
        cvSchmId: currentId.value,
        ...form.value
      }
      editSchm(params).then(res => {
        ElMessage.success(res.data.msg)
        onClose('done')
      })
    }else {
      addSchm(form.value).then(res => {
        ElMessage.success(res.data.msg)
        onClose('done')
      })
    }
  }

  const onClose = (val) => {
    formRef.value.resetFields()
    currentId.value = ''
    dbTest.value = true
    dbCount.value = 0
    emits('close', 'dbClose')
    if(val === 'done') {
      emits('success')
    }
  }
  const onSuccess = async() => {
    dbTest.value = true
    await formRef.value.validate()
    if(!dbCount.value) {
      handleSchmNm('save')
      return
    }
    handleSave()
  }
  const resetForm = () => {
    formRef.value.resetFields()
    getSort(form.value.cvDbmsId)
  }
</script>

<style scoped>

</style>
