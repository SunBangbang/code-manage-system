<template>
  <el-dialog
          v-model="dialogVisible"
          title="编码分类"
          @close="onClose"
          width="600px"
          :close-on-click-modal="false"
  >
    <div class="common-modal">
      <div class="top-modal">
        <span class="modal-name">上层编码分类名称:</span>
        <el-input v-model="cdCatNm" placeholder="请输入" clearable @clear="getTableData"></el-input>
        <el-button type="primary" @click="getTableData">查看</el-button>
      </div>
      <div class="button-table">
        <el-table :data="tableData" ref="tableRef" v-loading="loading" border class="public-color non-checkbox-table" @row-click="rowClick">
          <el-table-column type="selection" width="60px" align="center"></el-table-column>
          <el-table-column type="index" width="60px" label="序号" align="center"></el-table-column>
          <el-table-column label="编码分类名称" prop="cdCatNm"></el-table-column>
          <el-table-column label="编码分类说明" prop="cdCatExplTxt"></el-table-column>
        </el-table>
      </div>
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
  import { reactive, watch, toRefs } from 'vue'
  import { getCatMList } from '@/api'
  import { ElMessage } from 'element-plus'
  const data = reactive({
    dialogVisible: false,
    cdCatNm: '',
    tableData: [],
    tableRef: '',
    loading: true
  })
  const { dialogVisible, cdCatNm, tableData, tableRef, loading } = toRefs(data)

  const props = defineProps({
    visibleObj: Object
  })
  watch(props.visibleObj, val => {
    console.log(val);
    dialogVisible.value = val.visible
    if(!val.visible) return
    getTableData()
  })
  const emits = defineEmits(['close', 'success'])

  const getTableData = () => {
    getCatMList({ cdCatNm: cdCatNm.value }).then(res => {
      console.log(res);
      loading.value = false
      tableData.value = res.data.data
    })
  }
  const rowClick = (row) => {
    tableRef.value.clearSelection()
    tableRef.value.toggleRowSelection(row)
  }

  const onClose = () => {
    emits('close')
  }
  const onSuccess = () => {
    let d = tableRef.value.getSelectionRows()
    console.log(d);
    if(d.length !== 1) {
      ElMessage.info('请选择一项编码区')
      return
    }
    tableRef.value.clearSelection()
    emits('success', d[0])
  }
</script>

<style scoped>

</style>
