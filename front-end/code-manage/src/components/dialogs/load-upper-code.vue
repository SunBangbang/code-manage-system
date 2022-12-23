<template>
  <el-dialog
          v-model="dialogVisible"
          title="编码值查询"
          @close="onClose"
          width="700px"
          :close-on-click-modal="false"
  >
    <div class="common-modal">
      <div class="top-modal">
        <span class="modal-name2">编码值:</span>
        <el-input v-model="search.cdVl" placeholder="请输入"></el-input>
        <el-button type="primary" @click="onSearchClick">查看</el-button>
      </div>
      <div class="button-table code-search-value">
        <el-table :data="tableData" border @selection-change="changeSelection">
          <el-table-column type="selection" align="center" width="60"></el-table-column>
          <el-table-column type="index" label="编码值序列号" align="center" width="130"></el-table-column>
          <el-table-column label="编码值" prop="cdVl" show-overflow-tooltip width="80"></el-table-column>
          <el-table-column label="编码值名称" prop="cdvlNm" show-overflow-tooltip width="120"></el-table-column>
          <el-table-column label="说明" prop="cdvlExplTxt" show-overflow-tooltip width="80"></el-table-column>
          <el-table-column label="历史开始日期" prop="recBgnDttm" show-overflow-tooltip width="130"></el-table-column>
          <el-table-column label="创建用户ID" prop="cretCvUserId" show-overflow-tooltip width="130"></el-table-column>
        </el-table>
        <el-pagination background
                       :page-size="search.pageSize"
                       @current-change="currentChange"
                       layout="total, prev, pager, next, jumper"
                       :total="total">
        </el-pagination>
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
  import { ElMessage } from 'element-plus'
  import { getCdvlMByPage } from '@/api'
  const data = reactive({
    dialogVisible: false,
    tableData: [],
    checkedData: [],
    total: 0,
    search: {
      cdVl: '',
      currentPage: 1,
      pageSize: 10
    }
  })
  const { dialogVisible, tableData, total, search, checkedData } = toRefs(data)

  const props = defineProps({
    visibleObj: Object
  })
  watch(props.visibleObj, val => {
    dialogVisible.value = val.visible
    if(!val.visible) return
    getTableData()
  })
  const getTableData = () => {
    getCdvlMByPage(search.value).then(res => {
      console.log(res);
      let d = res.data.data
      tableData.value = d.records
      total.value =d.total
    })
  }
  const currentChange = (val) => {
    search.value.currentPage = val
    getTableData()
  }
  const emits = defineEmits(['close', 'success'])

  const onClose = () => {
    emits('close', 'loadUpper')
  }
  const onSuccess = () => {
    const list = checkedData.value.map(item=>{
      return {
        '编码值': item.cdVl || '',
        "说明": item.cdvlExplTxt || '',
        "编码值名称": item.cdvlNm || '',
        "编码值ID": item.cvCdId || '',
        "上级编码ID": item.cvCdSphrId || '',
        "上级编码值ID": item.cvCdvlId || '',
        "排序顺序": item.cvSortSrno || '1',
        "最终与否": item.fnlYn,
        "创建用户ID": item.cretCvUserId || '',
        "开始时间": item.recBgnDttm || '',
        "截止时间": item.recFnshDttm || '',
        "NN与否": item.nullYn || 'Y',
        "PK与否": item.pkYn || 'N'
      };
    });

    emits('success', list)
  }
  const onSearchClick = () => {
    console.log(search.value)
    getTableData()
  }
  const changeSelection = (val) => {
    checkedData.value = val
  }
</script>

<style scoped>

</style>
