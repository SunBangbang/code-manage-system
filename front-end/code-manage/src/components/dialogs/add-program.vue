<template>
  <el-dialog
          v-model="dialogVisible"
          :title="title"
          @close="onClose"
          width="500px"
          top="10vh"
          :close-on-click-modal="false"
  >
    <div class="dialog-border">
      <el-form :model="form" ref="formRef" label-width="90px" :rules="rules">
        <el-form-item label="系统名称:" class="special-form" prop="sysNm">
          <el-input v-model="form.sysNm" placeholder="请输入"></el-input>
          <el-button type="primary" @click="handleSysNm">再次确认</el-button>
        </el-form-item>
        <el-form-item label="排序顺序:" prop="cvSortSrno">
          <el-input v-model="form.cvSortSrno" placeholder="请输入"></el-input>
        </el-form-item>
        <el-form-item label="系统说明:" prop="sysExplTxt">
          <el-input v-model="form.sysExplTxt" type="textarea" placeholder="请输入"></el-input>
        </el-form-item>
        <div class="border-short"></div>
        <div class="border-opera">
          <el-icon title="添加" @click="handleIconClick('add')"><Plus /></el-icon>
          <el-icon title="修改" @click="handleIconClick('edit')"><EditPen /></el-icon>
          <el-icon title="删除" @click="handleIconClick('delete')"><Delete /></el-icon>
        </div>
        <el-form-item label="DBMS:">
          <el-select v-model="send.cvDbmsId" @change="changeDbms">
            <el-option v-for="item in dbmsData"
                       :label="item.dbmsNm"
                       :value="item.cvDbmsId"
                       :key="item.cvDbmsId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="模式:">
          <el-select v-model="send.cvSchmId" @change="changeSchm">
            <el-option v-for="item in modeData"
                       :label="item.schmNm"
                       :value="item.cvSchmId"
                       :key="item.cvSchmId"
            ></el-option>
          </el-select>
        </el-form-item>
        <div class="program-div">
          <el-table :data="tableData"
                    border
                    ref="tableRef"
                    class="public-color"
                    max-height="300px"
                    @row-click="handleRowClick"
                    @selection-change="handleChange">
            <el-table-column type="selection" width="50px" align="center"></el-table-column>
            <el-table-column label="DBMS" prop="dbmsNm"></el-table-column>
            <el-table-column label="模式" prop="schmNm"></el-table-column>
          </el-table>
        </div>
      </el-form>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="onInit">初始化</el-button>
        <el-button type="primary" @click="onSuccess">保存</el-button>
        <el-button @click="onClose">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
  import { reactive, toRefs, watch } from 'vue'
  import { editSysM, checkSysNm, addSysM, getDbmsList, getSchmByDbmsId, getSysMVoById, getSortSrnoSys } from '@/api'
  import { ElMessage } from 'element-plus'
  const data = reactive({
    dialogVisible: false,
    title: '',
    form: {
      sysNm: '',
      cvSortSrno: '',
      sysExplTxt: ''
    },
    send: {
      cvDbmsId: '',
      dbmsNm: '',
      cvSchmId: '',
      schmNm: ''
    },
    tableRef: '',
    tableData: [],
    checkedData: [],
    currentObj: {},
    dbmsData: [],
    modeData: [],
    sysCount: 0,
    sysTest: true,
    currentId: '',
    formRef: '',
    tableRow: {},
    rules: {
      sysNm: [{
        required: true,
        trigger: 'blur',
        validator: (rule, value, callback) => {
          if(!value) {
            callback('请输入系统名称')
          }else {
            if(value !== currentObj.value.sysNm) {
              if(!sysTest.value) {
                callback('系统名称重复')
                sysCount.value = 0
              }
            }
          }
          return callback()
        },
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
  const { dialogVisible, title, form, currentId, send, rules, currentObj, checkedData, tableRow,
    formRef, sysTest, sysCount, tableData, dbmsData, modeData, tableRef } = toRefs(data)

  const props = defineProps({
    visibleObj: {
      type: Object,
      require: true
    }
  })
  watch(props.visibleObj, val => {
    dialogVisible.value = val.visible
    if(!val.visible) return
    title.value = val.id ? '修改应用程序' : '新增应用程序'
    currentId.value = val.id
    if(val.id) {
      getDetail(val.schmId, val.id, val.dbmsId)
      getSort()
    }
    getDbmsData()
  })
  const emits = defineEmits(['close', 'success'])

  const handleRowClick = (row) => {
    console.log(row)
    if(checkedData.value.length === 0) {
      tableRef.value.toggleRowSelection(row)
      tableRow.value = row
      send.value.cvDbmsId = row.cvDbmsId
      changeDbms(row.cvDbmsId, 'change', row.cvSchmId)
    }else {
      if(row.id === checkedData.value[0].id) {
        tableRef.value.clearSelection()
        tableRow.value = {}
        for(let key in send.value) {
          send.value[key] = ''
        }
      }else {
        tableRef.value.clearSelection()
        tableRef.value.toggleRowSelection(row)
        tableRow.value = row
        send.value.cvDbmsId = row.cvDbmsId
        changeDbms(row.cvDbmsId, 'change', row.cvSchmId)
      }
    }
  }
  const changeDbms = (cvDbmsId, type, val) => {
    send.value.cvSchmId = ''
    send.value.schmNm = ''
    send.value.dbmsNm = dbmsData.value.find(v => v.cvDbmsId === cvDbmsId).dbmsNm
    getSchmByDbmsId({ dbmsId: cvDbmsId }).then(res => {
      modeData.value = res.data.data
      if(type === 'change') {
        send.value.cvSchmId = val
        changeSchm(val)
      }
    })
  }
  const handleChange = (val) => {
    checkedData.value = val
  }
  const changeSchm = (cvSchmId) => {
    send.value.schmNm = modeData.value.find(v => v.cvSchmId === cvSchmId).schmNm
  }
  const getSort = () => getSortSrnoSys().then(res => {
    form.value.cvSortSrno = res.data.data
  })
  const getDbmsData = () => getDbmsList().then(res => {
    dbmsData.value = res.data.data
  })
  const getDetail = (cvSchmId, cvSysId, cvDbmsId) => {
    let params = {
      cvSchmId,
      cvSysId,
      cvDbmsId
    }
    getSysMVoById(params).then(res => {
      console.log(res);
      let d = res.data.data
      for(let key in form.value) {
        for(let key1 in d) {
          if(key === key1) {
            form.value[key] = d[key1]
          }
        }
      }
      currentObj.value.sysNm = d.sysNm
      tableData.value = [
        {
          id: Date.now(),
          cvDbmsId: d.cvDbmsId,
          dbmsNm: d.dbmsNm,
          cvSchmId: d.cvSchmId,
          schmNm: d.schmNm
        }
      ]
    })
  }
  const handleSysNm = (val) => {
    if(!form.value.sysNm) {
      formRef.value.validateField('sysNm')
      return
    }
    checkSysNm({ sysNm: form.value.sysNm }).then(res => {
      sysTest.value = res.data.data
      sysCount.value ++
      formRef.value.validateField('sysNm')
      if(val === 'save') {
        handleSave()
      }else {
        if(form.value.sysNm === currentObj.value.sysNm) {
          ElMessage.success('验证通过')
        }else {
          if(sysTest.value) ElMessage.success('验证通过')
        }
      }
    })
  }
  const handleSave = async() => {
    await formRef.value.validate()
    if(tableData.value.length === 0) {
      ElMessage.info('请关联DBMS和模式')
      return
    }
    form.value.cvSortSrno = parseFloat(form.value.cvSortSrno)
    let params = {}
    if(currentId.value) {
      params = {
        cvSysId: currentId.value,
        ...form.value,
        schmList: tableData.value.map(v => {
          return {
            cvDbmsId: v.cvDbmsId,
            cvSchmId: v.cvSchmId
          }
        })
      }
      editSysM(params).then(res => {
        ElMessage.success(res.data.msg)
        onClose('done')
      })
    }else {
      params = {
        ...form.value,
        schmList: tableData.value.map(v => {
          return {
            cvDbmsId: v.cvDbmsId,
            cvSchmId: v.cvSchmId
          }
        })
      }
      addSysM(params).then(res => {
        ElMessage.success(res.data.msg)
        onClose('done')
      })
    }
  }
  const resetForm = (val) => {
    if(val) {
      //表示关闭 把id清空掉
      currentId.value = ''
    }
    formRef.value.resetFields()
    tableData.value = []
    sysTest.value = true
    sysCount.value = 0
  }
  const onClose = (val) => {
    resetForm(1)
    emits('close', 'close')
    if(val === 'done') emits('success')
  }
  const onInit = () => {
    resetForm(0)
  }
  const onSuccess = async() => {
    sysTest.value = true
    await formRef.value.validate()
    if(!sysCount.value) {
      handleSysNm('save')
      return
    }
    handleSave()
  }
  const reset = (obj) => {
    for(let key in obj) {
      obj[key] = ''
    }
  }
  const handleIconClick = (val) => {
    switch(val) {
      case 'add':
        if(checkedData.value.length !== 0) {
          ElMessage.info('编辑状态无法添加')
          return
        }
        console.log(send.value);
        if(!send.value.cvDbmsId) {
          ElMessage.info('请选择DBMS')
          return
        }
        if(!send.value.cvSchmId) {
          ElMessage.info('请选择模式')
          return
        }
        let obj = {
          ...send.value,
          id: Date.now()
        }
        tableData.value.push(obj)
        modeData.value = []
        console.log(tableData.value);
        reset(send.value)
        break
      case 'edit':
        if(checkedData.value.length !== 1) {
          ElMessage.info('请选择一项DBMS')
          return
        }
        if(!send.value.cvDbmsId) {
          ElMessage.info('请选择DBMS')
          return
        }
        if(!send.value.cvSchmId) {
          ElMessage.info('请选择模式')
          return
        }
        let index = tableData.value.map(v => v.id).indexOf(tableRow.value.id)
        tableData.value = tableData.value.filter(v => v.id !== tableRow.value.id)
        let p = {
          ...send.value,
          id: Date.now()
        }
        tableData.value.splice(index, 0, p)
        for(let key in send.value) {
          send.value[key] = ''
        }
        break
      case 'delete':
        if(checkedData.value.length !== 1) {
          ElMessage.info('请选择一项DBMS')
          return
        }
        tableData.value = tableData.value.filter(v => v.id !== checkedData.value[0].id)
        for(let key in send.value) {
          send.value[key] = ''
        }
        break
      default:
    }
  }
</script>

<style scoped>

</style>
