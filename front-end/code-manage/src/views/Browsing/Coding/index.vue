<template>
  <div class="page">
    <div class="coding-area" v-if="show">
      <search-condition @changeShow="changeHighShow"
                        @searchTable="searchTable"
                        @transSphrList="transSphrList"
      ></search-condition>

      <div :class="highSearchShow ? 'coding-table' : 'long-coding-table'">
        <div class="table-button">
          <el-button type="primary" @click="changeShow(false)">编码申请</el-button>
          <el-button type="primary" @click="changeEditShow">编码更改</el-button>
          <el-button type="primary" @click="deleteItem">删除编码</el-button>
        </div>
        <div class="table-border">
          <general-table :tableNav="upperTableNav"
                         :tableData="upperTableData"
                         pageFlag="code"
                         :pageSize="search.pageSize"
                         :total="total"
                         @sizeChange="handleSizeChange"
                         @currentChange="handleCurrentChange"
                         @selectionChange="handleSelectionChange"
          ></general-table>
        </div>
      </div>
    </div>

    <page-next v-else @back="changeShow"
               :buttonFlag="buttonFlag"
               :sphrList="sphrList"
               :sendData="sendData"></page-next>
  </div>
</template>

<script setup>
import {reactive, toRefs, onMounted, toRaw, defineProps} from 'vue'
import pageNext from '@/views/Browsing/Rest'
import {upperTableNav} from '@/assets/js/applicationCode'
import {delBatchCdM, getCdMByPage} from '@/api'
import {ElMessage} from 'element-plus'
import SearchCondition from '@/views/Browsing/Components/search-condition'

const data = reactive({
  search: {
    currentPage: 1,
    pageSize: 20
  },
  total: 0,
  title: '',
  show: true,
  upperTableData: [],
  checked: [],
  sendData: [],
  highSearchShow: true,
  form: {},
  highForm: {},
  buttonFlag: '',
  sphrList: []
})
const {
  total, title, show, sphrList,
  checked, sendData, search, upperTableData, highSearchShow, form, highForm, buttonFlag
} = toRefs(data)

const props = defineProps({})

const transSphrList = (val) => {
  sphrList.value = val
}
const changeHighShow = (val) => highSearchShow.value = val
const changeShow = (val) => {
  //sendData.value = []
  if(val) {
    search.value.currentPage = 1
    show.value = val
    checked.value = [];
    sendData.value = [];
    getTableData();
  }
  else {
    // console.log(sendData.value)
    // buttonFlag.value = 'add'
    // 修改：当有选择项时，不能添加
    if (checked.value.length !== 0) {
      ElMessage.info('有选择项时只能进行编码更变')
      return
    }
    sendData.value = [];
    show.value = val
  }
}
const changeEditShow = () => {
  if (checked.value.length !== 1) {
    ElMessage.info('请选择一项要修改的编码')
    return
  }
  console.log(checked.value)
  show.value = false
  sendData.value = toRaw(checked.value)
  buttonFlag.value = 'edit'
}
const deleteItem = () => {
  if (checked.value.length === 0) {
    ElMessage.info('请选择要删除的编码')
    return
  }
  // setTimeout(() => {
  //   ElMessage.success('删除成功')
  //   getTableData()
  // }, 1000)

  const list = checked.value.map(item=>toRaw(item).cvCdId);
  //console.log(list)
  delBatchCdM(list).then(()=>{
    ElMessage.success('删除成功')
    getTableData()
  });
}

onMounted(() => {
  getTableData()
})

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
  sendData.value = val.map(item => toRaw(item))

  console.log(sendData.value)
}

</script>
