<template>
  <el-dialog
          v-model="dialogVisible"
          title="隶属系统"
          @close="onClose"
          width="500px"
          :close-on-click-modal="false"
  >
    <div class="common-modal">
      <div class="top-modal">
        <span class="modal-name2">隶属系统:</span>
        <el-input v-model="sysNm" placeholder="请输入" clearable></el-input>
        <el-button type="primary" @click="getTableData">查看</el-button>
      </div>
      <div class="button-table">
        <el-table :data="tableData" ref="tableRef" v-loading="loading" border class="public-color non-checkbox-table" @row-click="rowClick">
          <el-table-column type="selection" width="60px" align="center"></el-table-column>
          <el-table-column type="index" width="60px" label="序号" align="center"></el-table-column>
          <el-table-column label="隶属系统名称" prop="sysNm"></el-table-column>
          <el-table-column label="隶属系统说明" prop="rltsExplTxt"></el-table-column>
        </el-table>
      </div>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="onSuccess">确定</el-button>
        <el-button @click="onClose">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
  import { ref, reactive, watch, toRefs } from 'vue'
  import { getSysList } from '@/api'
  import {ElMessage} from "element-plus";
  const data = reactive({
    dialogVisible: false,
    sysNm: '',
    tableData: [],
    tableRef: '',
    loading: true
  })
  const { dialogVisible, sysNm, tableData, tableRef, loading } = toRefs(data)

  const props = defineProps({
    visibleObj: Object
  })
  watch(props.visibleObj, val => {
    dialogVisible.value = val.visible
    if(!val.visible) return
    getTableData()
  })
  const emits = defineEmits(['resetDialog', 'success'])

  const getTableData = () => getSysList({ sysNm: sysNm.value }).then(res => {
    tableData.value = res.data.data
    loading.value = false
  })
  const rowClick = (row) => {
    tableRef.value.clearSelection()
    tableRef.value.toggleRowSelection(row)
  }

  const onClose = () => {
    emits('resetDialog', 'belongSystem')
  }
  const onSuccess = () => {
    let d = tableRef.value.getSelectionRows()
    console.log(d);
    if(d.length !== 1) {
      ElMessage.info('请选择一项隶属系统')
      return
    }
    tableRef.value.clearSelection()
    emits('success', d[0])
  }
</script>
