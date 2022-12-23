<template>
  <div class="user">
    <div class="user-button">
      <span>登录ID:</span>
      <el-input v-model="form.cvLgnId" placeholder="请输入" clearable @clear="getTableData"></el-input>
      <span>用户名:</span>
      <el-input v-model="form.userNm" placeholder="请输入" clearable @clear="getTableData"></el-input>
      <span>部门名称:</span>
      <el-input v-model="form.dept" placeholder="请输入" clearable @clear="getTableData"></el-input>
      <span>菜单权限:</span>
      <el-select v-model="form.roleList" multiple collapse-tags clearable @clear="getTableData">
        <el-option v-for="item in roleArr"
                   :label="item.userNm"
                   :key="item.userNm"
                   :value="item.userNm"
        ></el-option>
      </el-select>
<!--      <el-input v-model="form.menus" placeholder="请输入"></el-input>-->
      <span>已过期:</span>
      <el-select v-model="form.isExpired" clearable @clear="getTableData" @change="getTableData">
        <el-option v-for="item in expiredData"
                   :label="item.label"
                   :key="item.label"
                   :value="item.value"
        ></el-option>
      </el-select>
<!--      <el-input v-model="form.bnacExprDt" placeholder="请输入"></el-input>-->
      <el-button type="primary" @click="getTableData">查看</el-button>
    </div>
    <div class="user-opera">
<!--      <el-button :icon="Plus" text @click="handleIconClick('edit')"></el-button>-->
<!--      <el-button :disabled="isDis" :icon="User" text @click="handleIconClick('edit')"></el-button>-->
<!--      <el-button :disabled="isDis" :icon="EditPen" text @click="handleIconClick('add')"></el-button>-->
<!--      <el-button :disabled="isDis" :icon="Lock" text @click="handleIconClick('delete')"></el-button>-->
<!--      <el-button :disabled="isDis" :icon="Delete" text @click="handleIconClick('add')"></el-button>-->

      <el-icon title="添加" @click="handleIconClick('add')"><Plus /></el-icon>
      <el-icon title="角色" @click="handleIconClick('user')"><User /></el-icon>
      <el-icon title="修改" @click="handleIconClick('edit')"><EditPen /></el-icon>
      <el-icon title="锁定" @click="handleIconClick('lock')"><Lock /></el-icon>
      <el-icon title="删除" @click="handleIconClick('delete')"><Delete /></el-icon>
    </div>
    <div class="user-table">
      <general-table :tableNav="tableNav"
                     :tableData="tableData"
                     :total="total"
                     @iconClick="handleIconClick"
                     @selectionChange="handleSelectionChange"
      ></general-table>
    </div>

    <AddUser :visibleObj="addVisible" @close="handleIconClick" @success="getTableData"></AddUser>

    <UserGrant :visibleObj="grantVisible" @close="handleIconClick" @success="getTableData"></UserGrant>
  </div>
</template>

<script setup>
  import { reactive, toRefs, onMounted } from 'vue'
  import { tableNav } from '@/assets/js/user'
  import {getUserBasiMList, delUser, getAllRole, unlockUser} from '@/api'
  import { ElMessageBox, ElMessage } from 'element-plus'
  import AddUser from '@/components/dialogs/add-user'
  import UserGrant from '@/components/dialogs/user-grant'
  import { Plus, User, EditPen, Delete, Lock } from '@element-plus/icons-vue'
  const data = reactive({
    form: {
      cvLgnId: '',
      userNn: '',
      dept: '',
      roleList: [],
      isExpired: ''
    },
    expiredData: [
      {
        label: '全部',
        value: ''
      },{
        label: '已到期',
        value: 'expired'
      },{
        label: '未到期',
        value: 'unExpired'
      }
    ],
    roleArr: [],
    isDis: true,
    total: 0,
    addVisible: {
      visible: false,
      id: ''
    },
    grantVisible: {
      visible: false
    },
    tableData: [],
    checkedData: []
  })
  const { form, total, addVisible, grantVisible, expiredData,
    tableData, isDis, checkedData, roleArr } = toRefs(data)

  onMounted(() => {
    getTableData()
    getRoleList()
  })

  const getRoleList = () => getAllRole().then(res => {
    roleArr.value = res.data.data
  })
  const getTableData = () => getUserBasiMList(form.value).then(res => {
    tableData.value = res.data.data
    total.value = tableData.value.length
  })

  const deleteItem = (id) => {
    delUser({ id }).then(res => {
      ElMessage.success(res.data.msg)
      getTableData()
    })
  }

  const lockItem = (id) => {
    unlockUser({ id }).then(res => {
      ElMessage.success(res.data.msg)
      getTableData()
    })
  }

  const handleSelectionChange = (val) => {
    checkedData.value = val
  }
  const handleIconClick = (val, row) => {
    console.log(val);
    console.log(row);
    console.log(checkedData.value);
    switch(val) {
      case 'add':
        addVisible.value.visible = true
        addVisible.value.id = ''
        break
      case 'close':
        addVisible.value.visible = false
        break
      case 'user':
        if(checkedData.value.length !== 1) {
          ElMessage.info('请选择一个用户')
          return
        }
        grantVisible.value.visible = true
        grantVisible.value.id = checkedData.value[0].cvUserId
        break
      case 'userClose':
        grantVisible.value.visible = false
        break
      case 'edit':
        if(checkedData.value.length !== 1) {
          ElMessage.info('请选择一个用户')
          return
        }
        addVisible.value.visible = true
        addVisible.value.id = checkedData.value[0].cvUserId
        break
      case 'delete':
        if(checkedData.value.length === 0) {
          ElMessage.info('请选择至少一个用户')
          return
        }
        ElMessageBox.confirm('确定选中的用户吗?', '提示', {
          type: 'warning'
        }).then(() => {
          checkedData.value.forEach(v => {
            deleteItem(v.cvUserId)
          })
        }).catch(() => {})
        break
      case 'lock':
        if(checkedData.value.length === 0) {
          if(checkedData.value.length === 0) {
            ElMessage.info('请选择至少一个用户')
            return
          }
        }
        ElMessageBox.confirm('确定解锁选中的用户吗?', '提示', {
          type: 'warning'
        }).then(() => {
          checkedData.value.forEach(v => {
            lockItem(v.cvUserId)
          })
        }).catch(() => {})
        break
      default:
    }
  }
</script>
