<template>
  <div class="depart">
    <div class="depart-left">
      <div class="depart-opera">
        <div class="font-word">角色名称:</div>
        <div class="depart-icon">
          <el-icon title="添加角色" @click="handleIconClick('add')"><Plus /></el-icon>
          <el-icon title="修改角色" @click="handleIconClick('edit')"><EditPen /></el-icon>
          <el-icon title="删除角色" @click="handleIconClick('delete')"><Delete /></el-icon>
        </div>
      </div>
      <div class="right-table">
        <el-table :data="leftData" ref="leftTable" class="public-color" border @row-click="handleRowClick"
                  @selection-change="handleSelectionChange" @select="handleSelect">
          <el-table-column type="selection" width="40px" align="center"></el-table-column>
<!--          <el-table-column label="序号" type="index" width="60px" align="center"></el-table-column>-->
          <el-table-column label="角色组名" prop="userNm" show-overflow-tooltip></el-table-column>
          <el-table-column label="角色组描述" prop="userExplTxt" show-overflow-tooltip></el-table-column>
        </el-table>
      </div>
    </div>
    <div class="depart-right">
      <div class="right-button-right">
        <span class="font-word">按组列出的用户:</span>
        <div>
          <el-icon title="添加关联用户" class="first-icon" @click="handleIconClick('addUser')"><Plus /></el-icon>
          <el-icon title="删除关联用户" @click="handleIconClick('delUser')"><Delete /></el-icon>
        </div>
      </div>
      <div class="right-half-table">
        <general-table :tableNav="tableNav"
                       ref="general"
                       :tableData="tableData"
                       :total="total"
        ></general-table>
      </div>
      <div class="right-button-right">
        <span class="font-word">组菜单列表:</span>
        <el-icon title="保存" @click="handleIconClick('save')"><Box /></el-icon>
      </div>
      <div class="bottom-half-table">
        <n-data-table
            :columns="columns"
            :data="rightData"
            v-model:checked-row-keys="defaultKeys"
            :row-key="(row) => row.id"
            flex-height
            default-expand-all
        />
<!--        <el-table :data="rightData"-->
<!--                  border-->
<!--                  class="public-color"-->
<!--                  row-key="id"-->
<!--                  ref="tableRef"-->
<!--                  default-expand-all-->
<!--                  :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"-->
<!--        >-->
<!--          <el-table-column width="60px" type="selection">-->
<!--            <template #default="scope">-->
<!--              <el-checkbox :checked="scope.row.checked" @change="changeCheck(scope)"></el-checkbox>-->
<!--            </template>-->
<!--          </el-table-column>-->
<!--          <el-table-column label="菜单名" prop="name"></el-table-column>-->
<!--          <el-table-column label="菜单详情" prop="del"></el-table-column>-->
<!--        </el-table>-->
      </div>
    </div>

    <add-role :visibleObj="addVisible" @close="handleIconClick" @success="getLeftData"></add-role>

    <relate-user :visibleObj="relateVisible" @close="handleIconClick" @success="getLeftData"></relate-user>
  </div>
</template>

<script setup>
import { reactive, toRefs, onMounted, nextTick } from 'vue'
  import { tableNav } from '@/assets/js/role'
  import { getAllRole, getUserPageByRole, delRole, delBatchRoleUserRelation,
    getAuthObjTreeByRoleId, addRoleAuthRelation } from '@/api'
  import AddRole from '@/components/dialogs/add-role'
  import RelateUser from '@/components/dialogs/relate-user'
  import { ElMessage, ElMessageBox } from 'element-plus'
import { NButton, NDataTable } from 'naive-ui'

  const data = reactive({
    leftData: [],
    tableData: [],
    form: {
      con1: '',
      con2: ''
    },
    defaultKeys: [],
    columns: [
      {
        type: 'selection'
      },
      {
        title: '菜单名',
        key: 'name'
      },
      {
        title: '菜单说明',
        key: 'authObjExplTxt'
      }
    ],
    tableRef: '',
    addVisible: {
      visible: false,
      id: ''
    },
    general: '',
    relateVisible: {
      visible: false,
      id: ''
    },
    leftCheckedData: [],
    total: 0,
    currentNode: null,
    currentObj: {
      current: 1,
      size: 10
    },
    leftTable: '',
    rightData: []
  })
  const {
    leftData, form, total, rightData, currentNode, tableRef, general, columns, aData, defaultKeys,
    currentObj, tableData, addVisible, relateVisible, leftCheckedData, leftTable
  } = toRefs(data)

  onMounted(() => {
    getLeftData()
  })

  const getLeftData = () => getAllRole().then(res => {
    leftData.value = res.data.data
    if(leftData.value.length > 0) {
      currentNode.value = leftData.value[0]
      nextTick(() => {
        leftTable.value.toggleRowSelection(currentNode.value)
      })
      getTableData(currentNode.value.cvUserId)
      getMenuTree(currentNode.value.cvUserId)
    }
  })

  const getTableData = (roleId) => {
    let params = {
      roleId,
      ...currentObj.value
    }
    getUserPageByRole(params).then(res => {
      tableData.value = res.data.data.records
      total.value = tableData.value.length
    })
  }
  const getMenuTree = (id) => {
    rightData.value = []
    getAuthObjTreeByRoleId({ id }).then(res => {
      rightData.value = res.data.data
      let arr = []
      let d = getIds(rightData.value, arr)
      defaultKeys.value = d.map(v => v.id)
    })
  }
  const handleSelectionChange = (val) => {
    leftCheckedData.value = val
  }
  const handleSelect = (s, row) => {
    handleRowClick(row)
  }
  const changeCheck = (row) => {
    row.row.checked = !row.row.checked
    let c = row.row.checked
    let arr = row.row.children
    if(arr && arr.length > 0) getChildren(arr, c)
  }
  const getChildren = (data, c) => {
    data.forEach(v => {
      v.checked = c
      nextTick(() => {
        tableRef.value.toggleRowSelection(v, c)
      })

      if(v.children && v.children.length > 0) {
        getChildren(v.children, c)
      }
    })
  }
  const handleRowClick = (row) => {
    nextTick(() => {
      leftTable.value.clearSelection()
      leftTable.value.toggleRowSelection(row)
    })
    currentNode.value = row
    getTableData(row.cvUserId)
    getMenuTree(row.cvUserId)
  }
  const deleteItem = (id) => {
    delRole({ id }).then(res => {
      ElMessage.success(res.data.msg)
      getLeftData()
    })
  }
  const getIds = (data, arr) => {
    data.forEach(v => {
      if(v.checked) arr.push(v)

      if(v.children && v.children.length > 0) {
        getIds(v.children, arr)
      }
    })
    return arr
  }
  const handleIconClick = (val) => {
    switch(val) {
      case 'add':
        addVisible.value.visible = true
        addVisible.value.id = ''
        break
      case 'addClose':
        addVisible.value.visible = false
        break
      case 'edit':
        if(leftCheckedData.value.length !== 1) {
          ElMessage.info('请选择一个角色')
          return;
        }
        addVisible.value.visible = true
        addVisible.value.id = leftCheckedData.value[0].cvUserId
        break
      case 'delete':
        if(leftCheckedData.value.length === 0) {
          ElMessage.info('请选择至少一个角色')
          return
        }
        ElMessageBox.confirm(`确定删除选中的角色吗?`, `提示`, {
          type: 'warning'
        }).then(() => {
          leftCheckedData.value.forEach(v => {
            deleteItem(v.cvUserId)
          })
        }).catch(() => {})
        break
      case 'addUser':
        relateVisible.value.visible = true
        relateVisible.value.id = currentNode.value.cvUserId
        relateVisible.value.data = tableData.value
        break
      case 'addUserClose':
        relateVisible.value.visible = false
        break
      case 'delUser':
        let arr1 = general.value.$refs.tableRef.getSelectionRows()
        if(arr1.length === 0) {
          ElMessage.info('请选择用户')
          return
        }
        ElMessageBox.confirm(`确定解除选中的用户吗`, `提示`, {
          type: 'warning'
        }).then(() => {
          let params = {
            cvUserId: currentNode.value.cvUserId,
            userIdList: arr1.map(v => v.cvUserId)
          }
          delBatchRoleUserRelation(params).then(res => {
            ElMessage.success(res.data.msg)
            getLeftData()
          })
        }).catch(() => {})
        break
      case 'save':
        // console.log(defaultKeys.value);
        // return;
        //保存角色菜单
        // let arr = []
        // let d = getIds(rightData.value, arr)
        // let a = rightData.value[0]
        // let b = rightData.value[1]
        // let c = rightData.value[2]
        // let arr = []
        // defaultKeys.value.forEach(x => {
        //   if(a.children.map(v => v.id).includes(x)) {
        //     arr.push(a.id)
        //   }
        //   if(b.children.map(v => v.id).includes(x)) {
        //     arr.push(b.id)
        //   }
        //   if(c.children.map(v => v.id).includes(x)) {
        //     arr.push(c.id)
        //   }
        // })
        let params = {
          cvUserId: currentNode.value.cvUserId,
          authIdList: defaultKeys.value
        }
        console.log(params);
        addRoleAuthRelation(params).then(res => {
          ElMessage.success(res.data.msg)
          handleRowClick(currentNode.value)
          // getLeftData()
        })
        break
      default:
    }
  }
</script>
