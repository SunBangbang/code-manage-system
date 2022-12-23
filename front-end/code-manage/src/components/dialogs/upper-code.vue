<template>
  <el-dialog
          v-model="dialogVisible"
          :title="title"
          @close="onClose"
          width="1200px"
          top="10vh"
          :close-on-click-modal="false"
  >
    <div class="upper-code">
      <search-condition @searchTable="searchTable"
                        :count="changeCount"
      ></search-condition>

      <div class="upper-table">
        <div class="table-button" v-if="codeModalShow">
          <span class="upper-span">基准编码: {{sendParams.codeName}}</span>
          <span class="upper-span upper-span-margin">对象编码: {{editCodeName}}</span>
          <el-radio-group v-model="codeModal">
            <el-radio :label="1">上层编码</el-radio>
            <el-radio :label="2">下层编码</el-radio>
          </el-radio-group>
          <el-button type="primary" class="next-button" @click="next">下一个</el-button>
        </div>
        <div class="table-button" v-else>
          <el-button type="primary" @click="apply">申请</el-button>
        </div>
        <div class="table-border">
          <general-table :tableNav="upperTableNav"
                         :tableData="upperTableData"
                         :total="total"
                         pageFlag="code"
                         :pageSize="search.pageSize"
                         @sizeChange="handleSizeChange"
                         @currentChange="handleCurrentChange"
                         @selectionChange="handleSelectionChange"
          ></general-table>
        </div>
      </div>
    </div>
<!--    <template #footer v-if="codeModalShow">-->
<!--      <span class="dialog-footer">-->
<!--        <el-button type="primary" @click="onClose">确定</el-button>-->
<!--        <el-button @click="onClose">关闭</el-button>-->
<!--      </span>-->
<!--    </template>-->
  </el-dialog>
</template>

<script setup>
  import { reactive, watch, toRefs, defineProps, defineEmits } from 'vue'
  import { upperTableNav } from '@/assets/js/applicationCode'
  import { ElMessage } from 'element-plus'
  import SearchCondition from '@/views/Browsing/Components/search-condition'
  import { getCdMByPage, addCdPptyM, addCdR } from '@/api'
  const data = reactive({
    dialogVisible: false,
    form: {},
    highForm: {},
    total: 0,
    codeModal: 1,
    codeModalShow: false,
    title: '',
    search: {
      currentPage: 1,
      pageSize: 20
    },
    upperTableData: [],
    checked: [],
    changeCount: 0,
    editId: '',
    editCodeName: '',
    sendParams: {}
  })
  const {
    dialogVisible, form, highForm, total, codeModal, search, editId, editCodeName, sendParams,
    codeModalShow, title, upperTableData, checked, changeCount
  } = toRefs(data)

  const props = defineProps({
    visibleObj: Object
  })
  watch(props.visibleObj, val => {
    console.log(val);
    dialogVisible.value = val.visible
    if(!val.visible) return
    editId.value = val.id
    codeModalShow.value = val.sign === 'next'
    title.value = val.sign === 'apply' ? '上层编码名' : '编码编码关系'
    if(val.sign === 'next') sendParams.value = val.params
    getTableData()
  })
  const emits = defineEmits(['close', 'next', 'applyItemLeft', 'applyItemRight'])

  const onClose = () => {
    emits('close', 'upperCode')
  }
  const onSuccess = () => {

  }
  const next = () => {
    if(checked.value.length !== 1) {
      ElMessage.info('请选择一项编码')
      return
    }
    const objToAdd = {hlvCvCdId: '', llvCvCdId: '', cvCdRltsCd: '0100'};
    if(codeModal.value === 1) {
      objToAdd.hlvCvCdId = checked.value[0].cvCdId;
      objToAdd.llvCvCdId = sendParams.value.editId;
    }
    else {
      objToAdd.llvCvCdId = checked.value[0].cvCdId;
      objToAdd.hlvCvCdId = sendParams.value.editId;
    }
    console.log(objToAdd);

    addCdR(objToAdd).then(res=>{
      console.log(res.data);
      emits('next', {
        codeModal: codeModal.value,
        editId: objToAdd.hlvCvCdId,
        bottomId: objToAdd.llvCvCdId,
        hlvCvCdId: objToAdd.hlvCvCdId,
        llvCvCdId: objToAdd.llvCvCdId,
      });
    });
  }
  const apply = () => {
    if(checked.value.length !== 1) {
      ElMessage.info('请选择一项编码')
      return
    }
    // addCdPptyM
    if(props.visibleObj.type === 'Right') {
      let a = checked.value[0]
      let params = {
        cdPptyNm: a.cdLgcNm || '',
        cvCdId: editId.value || '',
        cvCdSphrId: a.cvCdSphrId || '',
        dataLen: a.dataLen || '',
        dataTpNm: a.dataTpNm || '',
        nullYn: a.nullYn || 'Y',
        pkYn: a.pkYn || 'N'
      }
      console.log(params)
      addCdPptyM(params).then(res => {
        ElMessage.success(res.data.msg)
        changeCount.value = Math.random()
        emits('applyItemRight')
      })
    }else {
      changeCount.value = Math.random()
      emits('applyItem' + props.visibleObj.type, checked.value[0])
    }
  }
  const searchTable = (a, b) => {
    form.value = a
    highForm.value = b
    getTableData()
  }
  const getTableData = () => {
    let params = {
      ...form.value,
      ...highForm.value,
      ...search.value
    }
    getCdMByPage(params).then(res => {
      let d = res.data.data
      upperTableData.value = d.records
      total.value = d.total
    })
  }
  const handleSizeChange = (val) => {
    search.value.pageSize = val
    getTableData()
  }
  const handleCurrentChange = (val) => {
    search.value.currentPage = val
    getTableData()
  }
  const handleSelectionChange = (val) => {
    checked.value = val
    if(val && val.length > 0) {
      editCodeName.value = val[0].cdLgcNm;
    }
  }

</script>
