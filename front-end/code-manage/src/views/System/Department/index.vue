<template>
  <div class="depart">
    <div class="depart-left">
      <div class="depart-opera">
        <div class="font-word">部门名称:</div>
        <div class="depart-icon">
          <el-icon title="添加" @click="handleIconClick('add')"><Plus /></el-icon>
          <el-icon title="修改" @click="handleIconClick('edit')"><EditPen /></el-icon>
          <el-icon title="删除" @click="handleIconClick('delete')"><Delete /></el-icon>
        </div>
      </div>
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
        <general-table :tableNav="tableNav"
                       :tableData="tableData"
                       :total="total"
        ></general-table>

      </div>
    </div>

    <add-depart :visibleObj="openObj" @close="handleIconClick" @success="getTreeData"></add-depart>
  </div>
</template>

<script setup>
  import { reactive, toRefs, onMounted, nextTick } from 'vue'
  // import { tableData } from '@/assets/js/department'
  import { tableNav } from '@/assets/js/user'
  import { getDeptTree, getUserListByDept, delDept } from '@/api'
  import { defaultProps } from '@/assets/js/common-data'
  import { ElMessage, ElMessageBox } from 'element-plus'
  import AddDepart from '@/components/dialogs/add-depart'

  const data = reactive({
    treeData: [],
    tableData: [],
    form: {
      cvLgnId: '',
      userNm: ''
    },
    openObj: {},
    tree: '',
    total: 0,
    currentNode: null
  })
  const {
    treeData, tableData, form, total, currentNode, tree, openObj
  } = toRefs(data)

  onMounted(() => {
    getTreeData()
  })

  const getTreeData = () => getDeptTree().then(res => {
    treeData.value = res.data.data
    if(treeData.value.length !== 0) {
      currentNode.value = treeData.value[0]
      nextTick(() => {
        tree.value.setCurrentKey(treeData.value[0].id)
      })
      getTableData(treeData.value[0].id)
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
    })
  }

  const deleteDepart = (id) => {
    ElMessageBox.confirm(`该部门下的所有子部门也会被删除,是否确定要删除该部门?`, `提示`, {
      type: 'warning'
    }).then(() => {
      delDept({ id }).then(res => {
        ElMessage.success(res.data.msg)
        getTreeData()
      })
    }).catch(() => {})
  }

  const handleNodeClick = (node) => {
    currentNode.value = node
    getTableData(node.id, 'left')
  }

  const handleIconClick = (val) => {
    switch(val) {
      case 'add':
        openObj.value.visible = true
        openObj.value.parentId = currentNode.value.id
        openObj.value.id = ''
        break
      case 'edit':
        if(!currentNode.value) return
        openObj.value.visible = true
        openObj.value.id = currentNode.value.id
        break
      case 'close':
        openObj.value.visible = false
        break
      case 'delete':
        if(!currentNode.value) return
        deleteDepart(currentNode.value.id)
        break
      default:
    }
  }

</script>
