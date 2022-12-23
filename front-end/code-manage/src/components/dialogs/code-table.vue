<template>
  <el-dialog
      v-model="dialogVisible"
      title="编码值关系"
      @close="onClose"
      width="1200px"
      height="80%"
      top="10vh"
      :close-on-click-modal="false"
  >
    <div class="depart">
      <div class="depart-left">
        <div class="depart-opera">
          <div class="font-word">DBMS:</div>
          <div class="depart-icon">
          </div>
        </div>
        <el-tree :data="treeData"
                 :props="plusDefaultProps"
                 lazy
                 ref="treeRef"
                 :load="loadNode"
                 node-key="treeId"
                 highlight-current
                 :expand-on-click-node="false"
                 v-loading="treeLoading"
                 @node-click="handleNodeClick"
        ></el-tree>
      </div>
      <div class="depart-right">
        <div class="right-special-button">
          <el-row :gutter="20">
            <el-col :span="11">
              <span>DBMS:</span>
              <el-select v-model="form.cvDbmsId" @change="changeDbms" clearable>
                <el-option v-for="item in dbmsList"
                           :label="item.dbmsNm"
                           :value="item.cvDbmsId"
                           :key="item.cvDbmsId"
                ></el-option>
              </el-select>
            </el-col>
            <el-col :span="11">
              <span>模式:</span>
              <el-select v-model="form.cvSchmId" @change="changeSchm" clearable>
                <el-option v-for="item in schmList"
                           :label="item.schmNm"
                           :value="item.cvSchmId"
                           :key="item.cvSchmId"
                ></el-option>
              </el-select>
            </el-col>
            <el-col :span="2">
              <el-button type="primary" @click="getTableData">查看</el-button>
              <el-button type="primary" @click="onSuccess">申请</el-button>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="11">
              <span>表:</span>
              <el-select v-model="form.cvDbTabId" @change="changeTab" clearable>
                <el-option v-for="item in tabList"
                           :label="item.dbTabNm"
                           :value="item.cvDbTabId"
                           :key="item.cvDbTabId"
                ></el-option>
              </el-select>
            </el-col>
            <el-col :span="11">
              <span>字段:</span>
              <el-select v-model="form.cvDbFildId" clearable @change="changeFild">
                <el-option v-for="item in fildList"
                           :label="item.dbFildNm"
                           :value="item.cvDbFildId"
                           :key="item.cvDbFildId"
                ></el-option>
              </el-select>
            </el-col>
          </el-row>
        </div>
        <div
            style="width: calc(100% + 20px); position: relative; margin-bottom: 10px; left: -10px; border-top: 1px solid #DCDFE6;"></div>
        <div class="right-special-table" @click="clickDepart">
          <div class="table-left">
            <table border="1" cellspacing="0" width="100%" v-if="form.cvDbmsId">
              <tr>
                <td>DBMS名称</td>
                <td>{{ dbmsTableObj.dbmsNm }}</td>
              </tr>
              <tr>
                <td>IP</td>
                <td>{{ dbmsTableObj.dbmsIp }}</td>
              </tr>
              <tr>
                <td>PORT</td>
                <td>{{ dbmsTableObj.dbmsPortVl }}</td>
              </tr>
              <tr>
                <td>用户账号</td>
                <td>{{ dbmsTableObj.cvDbmsBnacId }}</td>
              </tr>
              <tr>
                <td>用户密码</td>
                <td>{{ dbmsTableObj.userPwdEncd }}</td>
              </tr>
              <tr>
                <td>默认连接模式</td>
                <td>{{ dbmsTableObj.dfltCnctCvSchmId }}</td>
              </tr>
              <tr>
                <td>历史开始日期</td>
                <td>{{ dbmsTableObj.recBgnDttm }}</td>
              </tr>
              <tr>
                <td>历史结束日期</td>
                <td>{{ dbmsTableObj.recFnshDttm }}</td>
              </tr>
              <tr>
                <td>DBMS说明</td>
                <td>{{ dbmsTableObj.dbmsExplTxt }}</td>
              </tr>
            </table>
            <table border="1" cellspacing="0" width="100%" v-if="form.cvSchmId">
              <tr>
                <td>模式名称</td>
                <td>{{ schmTableObj.schmNm }}</td>
              </tr>
              <tr>
                <td>模式说明</td>
                <td>{{ schmTableObj.schmExplTxt }}</td>
              </tr>
              <tr>
                <td>历史开始日期</td>
                <td>{{ schmTableObj.recBgnDttm }}</td>
              </tr>
              <tr>
                <td>历史结束日期</td>
                <td>{{ schmTableObj.recFnshDttm }}</td>
              </tr>
            </table>
            <table border="1" cellspacing="0" width="100%" v-if="form.cvDbTabId">
              <tr>
                <td>表名</td>
                <td>{{ tabTableObj.dbTabNm }}</td>
              </tr>
              <tr>
                <td>表说明</td>
                <td>{{ tabTableObj.dbTabExplTxt }}</td>
              </tr>
              <tr>
                <td>历史开始日期</td>
                <td>{{ tabTableObj.recBgnDttm }}</td>
              </tr>
              <tr>
                <td>历史结束日期</td>
                <td>{{ tabTableObj.recFnshDttm }}</td>
              </tr>
            </table>
          </div>
          <div class="table-right">
            <table border="1" cellspacing="0" width="100%" v-if="form.cvDbFildId">
              <tr>
                <td>字段名</td>
                <td>{{ fildTableObj.dbFildNm }}</td>
              </tr>
              <tr>
                <td>字段说明</td>
                <td>{{ fildTableObj.dbFildExplTxt }}</td>
              </tr>
              <tr>
                <td>数据类型ID</td>
                <td>{{ fildTableObj.dataTpNm }}</td>
              </tr>
              <tr>
                <td>数据类型名称</td>
                <td>{{ fildTableObj.dataTpNm }}</td>
              </tr>
              <tr>
                <td>整数</td>
                <td>{{ fildTableObj.dbFildNm }}</td>
              </tr>
              <tr>
                <td>小数点</td>
                <td>{{ fildTableObj.dcmlLen }}</td>
              </tr>
              <tr>
                <td>是否PK</td>
                <td>{{ fildTableObj.pkYn }}</td>
              </tr>
              <tr>
                <td>空值</td>
                <td>{{ fildTableObj.nullYn }}</td>
              </tr>
              <tr>
                <td>默认</td>
                <td>{{ fildTableObj.fildDfltVl }}</td>
              </tr>
              <tr>
                <td>是否FK</td>
                <td>{{ fildTableObj.frkyYn }}</td>
              </tr>
              <tr>
                <td>范围</td>
                <td>{{ fildTableObj.scpTxt }}</td>
              </tr>
              <tr>
                <td>用户输入业务说明</td>
                <td>{{ fildTableObj.bsnsExplTxt }}</td>
              </tr>
              <tr>
                <td>是否删除</td>
                <td>{{ fildTableObj.delYn }}</td>
              </tr>
              <tr>
                <td>历史开始日期</td>
                <td>{{ fildTableObj.recBgnDttm }}</td>
              </tr>
              <tr>
                <td>历史结束日期</td>
                <td>{{ fildTableObj.recFnshDttm }}</td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="onSuccess">申请</el-button>
      </span>
    </template>
  </el-dialog>
</template>


<script setup>
import {reactive, toRefs, onMounted, nextTick, watch, defineProps, defineEmits} from 'vue'
import {tableNav} from '@/assets/js/department'
import {
  getDbmsTree,
  delDbms,
  delSchm,
  delTab,
  delFild,
  getDbmsList,
  getSchmByCondition,
  getTableByCondition,
  getFildByCondition, addTabCdBndR,
} from '@/api'
import {plusDefaultProps} from '@/assets/js/common-data'
import {ElMessage, ElMessageBox} from 'element-plus'

const props = defineProps({
  visibleObj: Object
})

const data = reactive({
  treeData: [],
  form: {
    cvDbmsId: '',
    cvSchmId: '',
    cvDbTabId: '',
    cvDbFildId: ''
  },
  dbmsList: [],
  schmList: [],
  tabList: [],
  fildList: [],
  treeRef: '',
  treeLoading: true,
  search: {
    cvDbTabId: '',
    cvDbmsId: '',
    cvSchmId: '',
    id: '',
    type: 'root'
  },
  departRef: '',
  searchLater: {
    cvDbTabId: '',
    cvDbmsId: '',
    cvSchmId: '',
    id: '',
    type: ''
  },
  tableData: [],
  total: 0,
  currentNode: null,
  dbmsVisible: {},
  dbVisible: {},
  pfVisible: {},
  cVisible: {},

  dbmsTableObj: {},
  schmTableObj: {},
  tabTableObj: {},
  fildTableObj: {},
  dialogVisible: false
})
const {
  treeData, form, total, currentNode, tableData, search, dbmsVisible, dbmsList, schmList, tabList, fildList,
  searchLater, treeRef, treeLoading, dbVisible, pfVisible, cVisible, departRef, dbmsTableObj, schmTableObj,
  tabTableObj, fildTableObj, dialogVisible,
} = toRefs(data)
const emits = defineEmits(["close", "success"]);

watch(props.visibleObj, val => {
  dialogVisible.value = val.visible
  if (!val.visible) return

  getTreeData()
  getUsefulDbms()
})

const changeDbms = (cvDbmsId, type, val, cvSchmId, cvDbTabId, cvDbFildId) => {
  if (type !== 'left') clickDepart()
  console.log(cvDbmsId)
  form.value.cvDbmsId = cvDbmsId
  console.log(dbmsList.value.find(v => v.cvDbmsId === cvDbmsId))
  dbmsTableObj.value = dbmsList.value.find(v => v.cvDbmsId === cvDbmsId)
  schmList.value = []
  tabList.value = []
  fildList.value = []
  form.value.cvSchmId = ''
  form.value.cvDbTabId = ''
  form.value.cvDbFildId = ''
  getSchmByCondition({cvDbmsId}).then(res => {
    console.log(res)
    schmList.value = res.data.data
    if (val === 'schm') {
      changeSchm(cvSchmId, type)
    } else if (val === 'tab') {
      changeSchm(cvSchmId, type, val, cvDbTabId)
    } else if (val === 'fild') {
      changeSchm(cvSchmId, type, val, cvDbTabId, cvDbFildId)
    }
  })
}
const changeSchm = (cvSchmId, type, val, cvDbTabId, cvDbFildId) => {
  if (type !== 'left') clickDepart()
  console.log(cvSchmId)
  form.value.cvSchmId = cvSchmId
  schmTableObj.value = schmList.value.find(v => v.cvSchmId === cvSchmId)
  console.log(schmTableObj.value)
  tabList.value = []
  fildList.value = []
  form.value.cvDbTabId = ''
  form.value.cvDbFildId = ''
  let p = {
    cvDbmsId: form.value.cvDbmsId,
    cvSchmId
  }
  getTableByCondition(p).then(res => {
    console.log(res)
    tabList.value = res.data.data
    if (val === 'tab') {
      changeTab(cvDbTabId, type)
    } else if (val === 'fild') {
      changeTab(cvDbTabId, type, val, cvDbFildId)
    }
  })
}
const changeTab = (cvDbTabId, type, val, cvDbFildId) => {
  if (type !== 'left') clickDepart()
  console.log(cvDbTabId)
  form.value.cvDbTabId = cvDbTabId
  tabTableObj.value = tabList.value.find(v => v.cvDbTabId === cvDbTabId)
  console.log(tabTableObj.value)
  fildList.value = []
  form.value.cvDbFildId = ''
  let p = {
    cvDbmsId: form.value.cvDbmsId,
    cvSchmId: form.value.cvSchmId,
    cvDbTabId
  }
  getFildByCondition(p).then(res => {
    console.log(res)
    fildList.value = res.data.data
    if (val === 'fild') {
      changeFild(cvDbFildId, type)
    }
  })
}
const changeFild = (cvDbFildId, type) => {
  if (type !== 'left') clickDepart()
  form.value.cvDbFildId = cvDbFildId
  fildTableObj.value = fildList.value.find(v => v.cvDbFildId === cvDbFildId)
  console.log(fildTableObj.value)
}
const getUsefulDbms = () => getDbmsList().then(res => {
  console.log(res);
  dbmsList.value = res.data.data
})
const clickDepart = (e) => {
  nextTick(() => {
    treeRef.value.setCurrentKey(null)
    currentNode.value = null
  })
}
const getTreeData = () => getDbmsTree(search.value).then(res => {
  let d = res.data.data
  treeData.value = d.map(v => {
    return {
      ...v,
      treeId: v.cvDbmsId + v.cvSchmId + v.cvDbTabId + v.cvDbFildId
    }
  })
  treeLoading.value = false
  currentNode.value = null
}).catch(err => {
  treeLoading.value = false
})
const loadNode = (node, resolve) => {
  if (node.level === 0) return
  for (let key in searchLater.value) {
    for (let key1 in node.data) {
      if (key === key1) {
        searchLater.value[key] = node.data[key1]
      }
    }
  }
  getDbmsTree(searchLater.value).then(res => {
    let d = res.data.data
    let arr = d.map(v => {
      return {
        ...v,
        treeId: v.cvDbmsId + v.cvSchmId + v.cvDbTabId + v.cvDbFildId
      }
    })
    resolve(arr)
  })
}
const handleNodeClick = (node) => {
  console.log(node);
  currentNode.value = node
  if (node.type === 'db') {
    changeDbms(node.cvDbmsId, 'left')
  } else if (node.type === 'schm') {
    changeDbms(node.cvDbmsId, 'left', 'schm', node.cvSchmId)
  } else if (node.type === 'tab') {
    changeDbms(node.cvDbmsId, 'left', 'tab', node.cvSchmId, node.cvDbTabId)
  } else if (node.type === 'fild') {
    changeDbms(node.cvDbmsId, 'left', 'fild', node.cvSchmId, node.cvDbTabId, node.cvDbFildId)
  }
}
const onClose = () => {
  emits("close", "code-table")
}

const onSuccess = () => {
  const obj = {
    cvCdId: props.visibleObj.editId,
    cvDbmsId: form.value.cvDbmsId,
    cvSchmId: form.value.cvSchmId,
    cvDbTabId: form.value.cvDbTabId,
    rltsExplTxt: '',
    sortSrno: ''
  };
  console.log(obj)
  addTabCdBndR(obj).then(res => {
    ElMessage.info(res.data.msg);
    emits("close", "code-table")
  });
}
</script>

<style scoped>

</style>