<template>
  <el-dialog
          v-model="dialogVisible"
          title="分配用户"
          @close="onClose"
          width="800px"
          :close-on-click-modal="false"
  >
    <div class="depart relate-depart">
      <div class="depart-left">
        <el-tree :data="treeData"
                 :props="defaultProps"
                 node-key="id"
                 ref="tree"
                 accordion
                 highlight-current
                 :render-after-expand="false"
                 :expand-on-click-node="false"
                 @node-click="handleNodeClick"
        ></el-tree>
      </div>
      <div class="depart-right">
        <div class="right-button">
          <span>登录ID:</span>
          <el-input v-model="form.cvLgnId" placeholder="请输入"></el-input>
          <span>用户名:</span>
          <el-input v-model="form.userNm" placeholder="请输入"></el-input>
          <el-button type="primary" @click="getTableData(currentNode.id)">查看</el-button>
        </div>
        <div class="right-table">
          <el-table :data="tableData" ref="tableRef" class="public-color" border row-key="cvUserId"
                    @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="60px" align="center" reserve-selection></el-table-column>
            <el-table-column label="登录ID" prop="cvLgnId" show-overflow-tooltip></el-table-column>
            <el-table-column label="用户名" prop="userNm" show-overflow-tooltip></el-table-column>
            <el-table-column label="部门" prop="dept" show-overflow-tooltip></el-table-column>
          </el-table>
        </div>
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
import { reactive, defineProps, nextTick, defineEmits, watch, toRefs } from 'vue'
import {getDeptTree, getUserListByDept, addBatchRoleUserRelation, getRoleById} from '@/api'
import { defaultProps } from '@/assets/js/common-data'
import { ElMessage } from 'element-plus'
import { tableNav } from '@/assets/js/user'

  const data = reactive({
    dialogVisible: false,
    treeData: [],
    currentNode: null,
    form: {
      cvLgnId: '',
      userNm: ''
    },
    tableRef: '',
    total: 0,
    tableData: [],
    tree: '',
    saveData: [],
    general: '',
    currentId: '',
    currentData: []
  })
  const { dialogVisible, treeData, currentNode, form, total, tableRef, tree, tableData, saveData, general, currentId, currentData } = toRefs(data)

  const props = defineProps({
    visibleObj: Object
  })
  watch(props.visibleObj, val => {
    dialogVisible.value = val.visible
    if(!val.visible) return
    currentId.value = val.id
    currentData.value = val.data
    getTreeData()
  })

  const emits = defineEmits(['close', 'success'])

  const getTreeData = () => getDeptTree().then(res => {
    treeData.value = res.data.data
    if(treeData.value.length !== 0) {
      currentNode.value = treeData.value[0]
      nextTick(() => {
        tree.value.setCurrentKey(treeData.value[0].id)
        handleNodeClick(currentNode.value)
      })
    }else {
      tableData.value = []
      total.value = 0
    }
  })

  const getTableData = (deptId, val) => {
    if(val === 'left') {
      form.value.cvLgnId = ''
      form.value.userNm = ''
    }
    let params = {
      deptId,
      ...form.value
    }
    getUserListByDept(params).then(res => {
      tableData.value = res.data.data
      total.value = tableData.value.length
      handleNodeClick(currentNode.value, 'click')
    })
  }

  const handleNodeClick = (node, val) => {
    currentNode.value = node
    if(val !== 'click') {
      getTableData(node.id, 'left')
    }else {
      currentData.value.forEach(v => {
        nextTick(() => {
          tableRef.value.toggleRowSelection(v)
        })
      })
    }
  }
  const handleSelectionChange = (val) => {
    let arr = val.map(v => v.cvUserId)
    saveData.value = Array.from(new Set(arr))
    console.log(saveData.value);
  }

  const onClose = (val) => {
    form.value.cvLgnId = ''
    form.value.userNm = ''
    // treeData.value = []
    // tableData.value = []
    // saveData.value = []
    // currentData.value = []
    emits('close', 'addUserClose')
    if(val === 'done') emits('success')
  }

  const onSuccess = () => {
    let params = {
      cvUserId: currentId.value,
      userIdList: saveData.value
    }
    addBatchRoleUserRelation(params).then(res => {
      ElMessage.success(res.data.msg)
      onClose('done')
    })
  }
</script>

<style scoped>

</style>
