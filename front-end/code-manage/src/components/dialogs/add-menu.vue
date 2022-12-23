<template>
  <el-dialog
          v-model="dialogVisible"
          :title="title"
          @close="onClose"
          width="500px"
          :close-on-click-modal="false"
  >
    <div class="dialog-border">
      <el-form :model="form" label-width="90px" ref="formRef" :rules="rules">
        <el-form-item label="菜单划分:" prop="cvAuthObjDstsCd">
          <el-radio-group v-model="form.cvAuthObjDstsCd">
            <el-radio label="1010">文件夹</el-radio>
            <el-radio label="1020">菜单</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上层菜单:" prop="hlvCvAuthObjId">
          <el-tree-select v-model="form.hlvCvAuthObjId"
                          :data="treeData"
                          :check-strictly="true"
                          :props="treeDefaultProps"
                          :render-after-expand="false"
                          default-expand-all
          />
        </el-form-item>
        <el-row>
          <el-col :span="11">
            <el-form-item label="是否使用:" prop="menuUsgYn">
              <el-select v-model="form.menuUsgYn">
                <el-option label="是" value="Y"></el-option>
                <el-option label="否" value="N"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="11" :offset="2">
            <el-form-item label="显示顺序:" prop="dsplSrno">
              <el-input v-model="form.dsplSrno" placeholder="请输入"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="屏幕ID:" class="special-form" prop="cvUiId">
          <el-input v-model="form.cvUiId" placeholder="请输入"></el-input>
          <el-button type="primary" @click="handleUiId">再次确认</el-button>
        </el-form-item>
        <el-form-item label="菜单名称:" class="special-form" prop="authObjNm">
          <el-input v-model="form.authObjNm" placeholder="请输入"></el-input>
          <el-button type="primary" @click="handleName">再次确认</el-button>
        </el-form-item>
        <el-form-item label="菜单说明:" prop="authObjExplTxt">
          <el-input v-model="form.authObjExplTxt" type="textarea" placeholder="请输入"></el-input>
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
  import { checkUiId, checkAuthName, getAuthFolderTree, getAuthObjById, addAuthObj, editAuthObj } from '@/api'
  import { ElMessage } from 'element-plus'
  import { treeDefaultProps } from '@/assets/js/common-data'
  const data = reactive({
    dialogVisible: false,
    title: '',
    form: {
      cvAuthObjDstsCd: '1010',
      hlvCvAuthObjId: '',
      dsplSrno: '',
      menuUsgYn: '',
      cvUiId: '',
      authObjNm: '',
      authObjExplTxt: '',
    },
    currentObj: {},
    formRef: '',
    treeData: [],
    uiidCount: 0,
    uiidTest: true,
    nameCount: 0,
    nameTest: true,
    currentId: '',
    rules: {
      cvUiId: [{
        required: true,
        trigger: 'blur',
        validator: (rule, value, callback) => {
          if(!value) {
            callback('请输入屏幕ID')
          }else {
            if(value !== currentObj.value.cvUiId) {
              if(!uiidTest.value) {
                callback('屏幕ID重复')
                uiidCount.value = 0
              }
            }
          }
          return callback()
        },
      }],
      authObjNm: [{
        required: true,
        trigger: 'blur',
        validator: (rule, value, callback) => {
          if(!value) {
            callback('请输入菜单名称')
          }else {
            if(value !== currentObj.value.authObjNm) {
              if(!nameTest.value) {
                callback('菜单名称重复')
                nameCount.value = 0
              }
            }
          }
          return callback()
        },
      }],
      menuUsgYn: [{
        required: true,
        message: '请选择是否使用',
        trigger: 'change'
      }]
    }
  })
  const { dialogVisible, title, form, treeData, rules, nameCount, nameTest, formRef, currentObj,
    uiidTest, uiidCount, currentId } = toRefs(data)

  const props = defineProps({
    visibleObj: {
      type: Object,
      require: true
    }
  })
  watch(props.visibleObj, val => {
    dialogVisible.value = val.visible
    if(!val.visible) return
    title.value = val.id ? '修改菜单' : '新增菜单'
    currentId.value = val.id
    getTreeData(val.id)
  })
  const emits = defineEmits(['close', 'success'])

  const getTreeData = (id) => getAuthFolderTree().then(res => {
    treeData.value = res.data.data
    if(id) getDetail(id)
  })
  const getDetail = (id) => {
    getAuthObjById({ id }).then(res => {
      let d = res.data.data
      for(let key in form.value) {
        for(let key1 in d) {
          if(key === key1) form.value[key] = d[key1]
        }
      }
      if(form.value.hlvCvAuthObjId === '0') form.value.hlvCvAuthObjId = ''
      currentObj.value = {
        cvUiId: d.cvUiId,
        authObjNm: d.authObjNm
      }
    })
  }
  const handleUiId = (val) => {
    if(!form.value.cvUiId) {
      formRef.value.validateField('cvUiId')
      return
    }
    checkUiId({ uiId: form.value.cvUiId }).then(res => {
      uiidTest.value = res.data.data
      uiidCount.value ++
      formRef.value.validateField('cvUiId')
      if(val === 'save') {
        handleName(val)
      }else {
        if(form.value.cvUiId === currentObj.value.cvUiId) {
          ElMessage.success('验证通过')
        }else {
          if(uiidTest.value) ElMessage.success('验证通过')
        }
      }
    })
  }
  const handleName = (val) => {
    if(!form.value.authObjNm) {
      formRef.value.validateField('authObjNm')
      return
    }
    checkAuthName({ name: form.value.authObjNm }).then(res => {
      nameTest.value = res.data.data
      nameCount.value ++
      formRef.value.validateField('authObjNm')
      if(val === 'save') {
        handleSave()
      }else {
        if(form.value.authObjNm === currentObj.value.authObjNm) {
          ElMessage.success('验证通过')
        }else {
          if(nameTest.value) ElMessage.success('验证通过')
        }
      }
    })
  }
  const handleSave = async() => {
    await formRef.value.validate()
    if(form.value.hlvCvAuthObjId === '') form.value.hlvCvAuthObjId = '0'
    if(currentId.value) {
      let params = {
        cvAuthObjId: currentId.value,
        ...form.value
      }
      editAuthObj(params).then(res => {
        ElMessage.success(res.data.msg)
        onClose('done')
      })
    }else {
      addAuthObj(form.value).then(res => {
        ElMessage.success(res.data.msg)
        onClose('done')
      })
    }
  }

  const resetForm = () => {
    formRef.value.resetFields()
    currentId.value = ''
    uiidTest.value = true
    nameTest.value = true
    uiidCount.value = 0
    nameCount.value = 0
  }
  const onClose = (val) => {
    resetForm()
    emits('close', 'close')
    if(val === 'done') emits('success')
  }
  const onSuccess = async() => {
    uiidTest.value = true
    nameTest.value = true
    console.log(uiidCount.value);
    console.log(nameCount.value);
    await formRef.value.validate()
    if(!uiidCount.value || !nameCount.value) {
      handleUiId('save')
      return
    }
    handleSave()
  }
</script>

<style scoped>

</style>
