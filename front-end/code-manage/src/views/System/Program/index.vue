<template>
  <div class="user">
    <div class="menu-button">
      <div class="button-search">
        <span>系统名称:</span>
        <el-input v-model="form.sysNm" placeholder="请输入" clearable @clear="getTableData"></el-input>
        <span>最终状态:</span>
        <el-select v-model="form.fnlYn" @change="getTableData">
          <el-option label="使用" value="Y"></el-option>
          <el-option label="不使用" value="N"></el-option>
        </el-select>
        <el-button type="primary" @click="getTableData">查看</el-button>
      </div>
      <div class="button-opera">
        <el-icon title="添加菜单" @click="handleIconClick('add')"><Plus /></el-icon>
        <el-icon title="修改菜单" @click="handleIconClick('edit')"><EditPen /></el-icon>
        <el-icon title="删除菜单" @click="handleIconClick('delete')"><Delete /></el-icon>
      </div>

    </div>
    <div class="menu-table">
      <general-table :tableNav="tableNav"
                     :tableData="tableData"
                     :total="total"
                     @selectionChange="handleSelectionChange"
      ></general-table>
    </div>

    <add-program :visibleObj="addVisible" @close="handleIconClick" @success="getTableData"></add-program>
  </div>
</template>

<script setup>
  import { reactive, toRefs, onMounted } from 'vue'
  import { getSysList, delSysM } from '@/api'
  import { tableNav } from '@/assets/js/program'
  import AddProgram from '@/components/dialogs/add-program'
  import {ElMessage, ElMessageBox} from 'element-plus'
  const data = reactive({
    form: {
      sysNm: '',
      fnlYn: 'Y'
    },
    addVisible: {
      visible: false,
      rowId: ''
    },
    tableData: [],
    total: 0,
    checkedData: []
  })
  const { form, addVisible, tableData, total, checkedData } = toRefs(data)

  onMounted(() => {
    getTableData()
  })

  const getTableData = () => getSysList(form.value).then(res => {
    let d = res.data.data
    tableData.value = d
    total.value = d.length
  })

  const handleSelectionChange = (val) => {
    checkedData.value = val
  }
  const deleteItem = (sysId) => {
    delSysM({ sysId }).then(res => {
      ElMessage.success(res.data.msg)
      getTableData()
    })
  }
  const handleIconClick = (item, row) => {
    switch(item) {
      case 'add':
        addVisible.value.visible = true
        addVisible.value.id = ''
        break
      case 'edit':
        if(checkedData.value.length !== 1) {
          ElMessage.info('请选择一个应用程序')
          return
        }
        addVisible.value.visible = true
        addVisible.value.id = checkedData.value[0].cvSysId
        addVisible.value.schmId = checkedData.value[0].cvSchmId
        addVisible.value.dbmsId = checkedData.value[0].cvDbmsId
        break
      case 'delete':
        if(checkedData.value.length === 0) {
          ElMessage.info('请至少选择一个应用程序')
          return
        }
        ElMessageBox.confirm('确定删除选中的应用程序吗?', '提示', {
          type: 'warning'
        }).then(() => {
          checkedData.value.forEach(v => {
            deleteItem(v.cvSysId)
          })
        }).catch(() => {})
        break
      case 'close':
        addVisible.value.visible = false
        break
      default:
    }
  }
</script>
