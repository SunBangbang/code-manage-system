<template>
  <el-dialog
      v-model="dialogVisible"
      title="编码值关系"
      @close="onClose"
      width="1200px"
      top="10vh"
      :close-on-click-modal="false"
  >
    <div class="code-relation">
      <div class="code-two">
        <el-radio-group v-model="codeOrder">
          <el-radio :label="1">顺序</el-radio>
          <el-radio :label="2">名称</el-radio>
        </el-radio-group>
        <el-button type="primary" class="first-button" @click="onAutoMapping">自动映射</el-button>
        <el-button type="primary" @click="onMapping">映射</el-button>
        <el-button type="primary" @click="onDeleteMapping">删除映射</el-button>
        <el-button type="primary" @click="onBatchDeleteMapping">批量删除</el-button>
      </div>
      <div class="code-three">
        <el-row :gutter="10">
          <el-col :span="8">
            <div class="left">
              <el-input v-model="leftValue" placeholder="请输入"></el-input>
              <span class="title">上层 {{ visibleObj.editName }} 编码值信息</span>
              <div class="col-table">
                <el-table :data="leftTableData" class="public-color" border height="500"
                          @selection-change="changeLeftSelection">
                  <el-table-column type="selection" width="60" align="center"></el-table-column>
                  <el-table-column v-for="item in leftTableAttr"
                                   :key="item.prop"
                                   :label="item.label"
                                   :prop="item.prop"
                                   show-overflow-tooltip></el-table-column>
                </el-table>
              </div>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="center">
              <span class="title">映射结果</span>
              <div class="col-table">
                <el-table :data="centerTableData" class="public-color" border height="500"
                          @selection-change="changeCenterSelection">
                  <el-table-column type="selection" width="60" align="center"></el-table-column>
                  <el-table-column v-for="item in centerTableAttr"
                                   :key="item.prop"
                                   :label="item.label"
                                   :prop="item.prop"
                                   show-overflow-tooltip></el-table-column>
                </el-table>
              </div>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="right">
              <el-input v-model="rightValue" placeholder="请输入"></el-input>
              <span class="title">下层 {{ visibleObj.bottomName }} 编码值信息</span>
              <div class="col-table">
                <el-table :data="rightTableData" class="public-color" border height="500"
                          @selection-change="changeRightSelection">
                  <el-table-column type="selection" width="60" align="center"></el-table-column>
                  <el-table-column v-for="item in rightTableAttr"
                                   :key="item.prop"
                                   :label="item.label"
                                   :prop="item.prop"
                                   show-overflow-tooltip></el-table-column>
                </el-table>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="onClose">申请</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {reactive, toRefs, watch, defineProps, defineEmits} from 'vue'
import {ElMessage} from 'element-plus'
import {getCdPtyvList, addCdvlR, addBatchCdvlR, delBatchCdvlR, getCodeValueRelation} from "@/api";

const data = reactive({
  dialogVisible: false,
  codeModal: 1,
  codeOrder: 1,
  leftValue: '',
  rightValue: '',

  centerTableAttr: [],
  centerTableData: [],

  leftTableAttr: [],
  leftTableData: [],

  rightTableAttr: [],
  rightTableData: [],

  checkedLeftData: [],
  checkedCenterData: [],
  checkedRightData: [],
})
const {
  dialogVisible, codeOrder, codeModal, leftValue, rightValue,
  centerTableAttr, centerTableData, leftTableAttr, leftTableData, rightTableAttr, rightTableData,
  checkedLeftData, checkedCenterData, checkedRightData
} = toRefs(data)

const props = defineProps({
  visibleObj: Object
})
watch(props.visibleObj, val => {
  dialogVisible.value = val.visible
  if (!val.visible) return
  getTableData(val)
})

const getTableData = (val) => {
  leftTableAttr.value = [];
  leftTableData.value = [];
  rightTableAttr.value = [];
  rightTableData.value = [];
  centerTableAttr.value = [];
  centerTableData.value = [];

  //获取左边表格数据
  getCdPtyvList({cvCdId: val.editId}).then(res => {
    console.log(res);
    let d = res.data.data
    if (d.table && d.tableId) {
      for (let key in d.table) {
        let param = {
          id: d.tableId[key],
          label: d.table[key],
          prop: key
        }
        leftTableAttr.value.push(param)
      }
      console.log(leftTableAttr.value);
      leftTableData.value = d.data
    }
  })
  getCdPtyvList({cvCdId: val.bottomId}).then(res => {
    console.log(res);
    let d = res.data.data
    if (d.table && d.tableId) {
      for (let key in d.table) {
        let param = {
          id: d.tableId[key],
          label: d.table[key],
          prop: key
        }
        rightTableAttr.value.push(param)
      }
      console.log(rightTableAttr.value);
      rightTableData.value = d.data
    }
  })

  //中间表格数据
  // getCdvlR({hlvCvCdId: val.editId, llvCvCdId: val.bottomId}).then(res => {
  //   centerTableData.value = res.data.data
  // })
  getCodeValueRelation({hlvCvCdId: val.editId, llvCvCdId: val.bottomId}).then(res => {
    let d = res.data.data
    if (d.table && d.data) {
      for (let key in d.table) {
        let obj = {
          label: d.table[key],
          prop: key
        }
        centerTableAttr.value.push(obj)
      }
      centerTableData.value = d.data
    }
  });
}

const emits = defineEmits(['close'])

const onClose = () => {
  emits('close', 'codeValue')
}

const changeLeftSelection = (val) => {
  checkedLeftData.value = val
}
const changeCenterSelection = (val) => {
  checkedCenterData.value = val
}
const changeRightSelection = (val) => {
  checkedRightData.value = val
}

const onMapping = () => {
  if (leftValue.value !== '' && rightValue.value !== '') {
    ElMessage.info("请选择一项编码值信息");
    return;
  }
  if (checkedLeftData.value.length !== 1 && leftValue.value !== '=100') {
    ElMessage.info('请选择一条要映射的上层编码值')
    return
  }
  if (checkedRightData.value.length !== 1 && rightValue.value !== '=100') {
    ElMessage.info('请选择一条要映射的下层编码值')
    return
  }

  console.log(checkedLeftData.value);
  console.log(checkedRightData.value);
  console.log(props.visibleObj)
  let params = {
    cvCdvlRltsDstsCd: '0100',
    hlvCvCdId: props.visibleObj.editId,
    hlvCvCdvlId: checkedLeftData.value.length > 0 ? checkedLeftData.value[0].cv_cdvl_id : '',
    llvCvCdId: props.visibleObj.bottomId,
    llvCvCdvlId: checkedRightData.value.length > 0 ? checkedRightData.value[0].cv_cdvl_id : '',
    rltsExplTxt: '',
    sortSrno: 1,
    //equals100: leftValue.value === "=100" || rightValue.value === "=100"
    leftValue: leftValue.value,
    rightValue: rightValue.value,
  }
  console.log(params)
  addCdvlR(params).then(res => {
    ElMessage.info(res.data.msg)
    getCodeValueRelation({hlvCvCdId: props.visibleObj.editId, llvCvCdId: props.visibleObj.bottomId}).then(res => {
      let d = res.data.data
      if (d.table && d.data) {
        centerTableAttr.value = [];
        for (let key in d.table) {
          let obj = {
            label: d.table[key],
            prop: key
          }
          centerTableAttr.value.push(obj)
        }
        centerTableData.value = d.data
        leftValue.value = ''
        rightValue.value = ''
      }
    });
  });
}
const onAutoMapping = () => {
  let params = {
    hlvCvCdId: props.visibleObj.editId,
    llvCvCdId: props.visibleObj.bottomId,
    codeOrder: codeOrder.value,
    equals100: leftValue.value === "=100" || rightValue.value === "=100"
  }
  console.log(params)

  addBatchCdvlR(params).then(res=> {
    ElMessage.info(res.data.msg)
    getCodeValueRelation({hlvCvCdId: props.visibleObj.editId, llvCvCdId: props.visibleObj.bottomId}).then(res => {
      let d = res.data.data
      if (d.table && d.data) {
        centerTableAttr.value = [];
        for (let key in d.table) {
          let obj = {
            label: d.table[key],
            prop: key
          }
          centerTableAttr.value.push(obj)
        }
        centerTableData.value = d.data
      }
    });
  });
}
const onDeleteMapping = () => {
  if (checkedCenterData.value.length !== 1) {
    ElMessage.info('请选择删除的信息')
    return
  }

  onBatchDeleteMapping();
}
const onBatchDeleteMapping = () => {
  if (checkedCenterData.value.length === 0) {
    ElMessage.info('请选择删除的信息')
    return
  }

  let params = checkedCenterData.value.map(item=>{
    return {
      hlvCvCdId: item.hlv_cv_cd_id,
      hlvCvCdvlId: item.hlv_cv_cdvl_id,
      llvCvCdId: item.llv_cv_cd_id,
      llvCvCdvlId: item.llv_cv_cdvl_id,
    };
  });

  delBatchCdvlR(params).then(res=>{
    ElMessage.info(res.data.msg)
    getCodeValueRelation({hlvCvCdId: props.visibleObj.editId, llvCvCdId: props.visibleObj.bottomId}).then(res => {
      let d = res.data.data
      if (d.table && d.data) {
        for (let key in d.table) {
          let obj = {
            label: d.table[key],
            prop: key
          }
          centerTableAttr.value.push(obj)
        }
        centerTableData.value = d.data
      }
    });
  });
}

</script>

<style scoped>

</style>
