<template>
  <div class="user">
    <div class="menu-button">
      <div class="button-search">
        <div>菜单名称:</div>
        <el-input v-model="menuName" placeholder="请输入" clearable @clear="getTreeData"></el-input>
        <el-button type="primary" @click="getTreeData">查看</el-button>
      </div>
      <div class="button-opera">
        <el-icon title="添加菜单" @click="handleIconClick('add')"><Plus /></el-icon>
        <el-icon title="修改菜单" @click="handleIconClick('edit')"><EditPen /></el-icon>
        <el-icon title="删除菜单" @click="handleIconClick('delete')"><Delete /></el-icon>
      </div>
    </div>
    <div class="menu-table">
      <div class="general-full-table">
        <el-table :data="tableData"
                  border
                  class="table"
                  ref="tableRef"
                  default-expand-all
                  row-key="id"
                  :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        >
          <el-table-column type="selection" width="60" align="center"></el-table-column>
          <el-table-column label="菜单名称" prop="name" show-overflow-tooltip></el-table-column>
          <el-table-column label="屏幕ID" prop="cvUiId"></el-table-column>
          <el-table-column label="表示顺序" prop="dsplSrno"></el-table-column>
          <el-table-column label="是否使用" prop="menuUsgYn">
            <template #default="scope">
              <span v-if="scope.row.menuUsgYn === 'Y'">使用</span>
              <span v-if="scope.row.menuUsgYn === 'N'">不使用</span>
            </template>
          </el-table-column>
          <el-table-column label="说明" prop="authObjExplTxt" show-overflow-tooltip></el-table-column>
<!--          <el-table-column label="操作" fixed="right" align="center" width="60">-->
<!--            <template #default="scope">-->
<!--              <div class="operation">-->
<!--                <el-tooltip content="编辑" placement="top"-->
<!--                >-->
<!--                  <el-icon @click="handleIconClick({ label: 'edit' }, scope.row)"><Edit /></el-icon>-->
<!--                </el-tooltip>-->
<!--              </div>-->
<!--            </template>-->
<!--          </el-table-column>-->
        </el-table>
      </div>
<!--      <div class="general-pagination">-->
<!--        <el-pagination layout="total"-->
<!--                       :total="0">-->
<!--        </el-pagination>-->
<!--      </div>-->
    </div>

    <add-menu :visibleObj="addVisible" @close="handleIconClick" @success="getTreeData"></add-menu>
  </div>
</template>

<script setup>
  import { reactive, toRefs, onMounted } from 'vue'
  // import { tableNav, tableData } from '@/assets/js/menu'
  import AddMenu from '@/components/dialogs/add-menu'
  import { getAuthObjTree, delAuthObj } from '@/api'
  import { ElMessage, ElMessageBox } from 'element-plus'

  const data = reactive({
    addVisible: {
      visible: false,
      rowId: ''
    },
    tableRef: '',
    tableData: [],
    menuName: ''
  })
  const { addVisible, tableData, menuName, tableRef } = toRefs(data)

  onMounted(() => {
    getTreeData()
  })

  const getTreeData = () => {
    getAuthObjTree({ name: menuName.value }).then(res => {
      tableData.value = res.data.data
    })
  }

  const handleIconClick = (item, row) => {
    switch(item) {
      case 'add':
        addVisible.value.visible = true
        addVisible.value.id = ''
        break
      case 'close':
        addVisible.value.visible = false
        addVisible.value.rowId = ''
        break
      case 'edit':
        let arr = tableRef.value.getSelectionRows()
        if(arr.length !== 1) {
          ElMessage.info('请选择一个文件夹或菜单')
          return
        }
        addVisible.value.visible = true
        addVisible.value.id = arr[0].id
        break
      case 'delete':
        let arr1 = tableRef.value.getSelectionRows()
        if(arr1.length !== 1) {
          ElMessage.info('请选择一个文件夹或菜单')
          return
        }
        ElMessageBox.confirm(`确定删除选中的文件夹或菜单吗?`, `提示`, {
          type: 'warning'
        }).then(() => {
          delAuthObj({ id: arr1[0].id }).then(res => {
            ElMessage.success(res.data.msg)
            getTreeData()
          })
        }).catch(() => {})
        break
      default:
    }
  }

</script>
