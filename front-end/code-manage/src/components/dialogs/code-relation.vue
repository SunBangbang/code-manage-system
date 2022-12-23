<template>
  <el-dialog
      v-model="dialogVisible"
      title="编码属性关系"
      @close="onClose"
      width="1200px"
      top="10vh"
      :close-on-click-modal="false"
  >
    <div class="code-relation">
      <div class="code-one">
        <el-radio-group v-model="codeModal" disabled>
          <el-radio :label="1">上层编码</el-radio>
          <el-radio :label="2">下层编码</el-radio>
        </el-radio-group>
				<div>
					<el-button type="primary" class="first-button" @click="onClose">申请</el-button>
					<el-button type="primary" @click="next">下一个</el-button>
				</div>
      </div>
      <div class="code-two">
        <el-radio-group v-model="codeOrder" @change="onCodeOrderChanged">
          <el-radio :label="1">顺序</el-radio>
          <el-radio :label="2">名称</el-radio>
        </el-radio-group>
				<div>
					<el-button type="primary" class="first-button" @click="autoInsert">自动映射</el-button>
					<el-button type="primary" @click="insertItem">映射</el-button>
					<el-button type="primary" @click="deleteItem">删除映射</el-button>
					<el-button type="primary" @click="batchDeleteItem">批量删除</el-button>
				</div>
      </div>
      <div class="code-three">
        <el-row :gutter="10">
          <el-col :span="8">
            <div class="left">
              <el-input v-model="leftValue" placeholder="请输入"></el-input>
              <span class="title">上层编码信息</span>
              <div class="col-table">
                <el-table :data="leftTableData" class="public-color" border height="500"
                          @selection-change="changeLeftSelection">
                  <el-table-column type="selection" width="60" align="center"></el-table-column>
                  <el-table-column type="index" label="序号" width="60" align="center"></el-table-column>
                  <el-table-column label="属性名称" prop="cdPptyNm" width="100" show-overflow-tooltip></el-table-column>
                  <el-table-column label="是否PK" prop="pkYn" show-overflow-tooltip></el-table-column>
                  <el-table-column label="数据类型" prop="dataTpNm" width="100" show-overflow-tooltip></el-table-column>
                  <el-table-column label="长度" prop="dataLen" show-overflow-tooltip></el-table-column>
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
                  <el-table-column label="上层属性名" prop="hlvCdPptyNm"></el-table-column>
                  <el-table-column label="下层属性名" prop="llvCdPptyNm"></el-table-column>
                </el-table>
              </div>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="right">
              <el-input v-model="rightValue" placeholder="请输入"></el-input>
              <span class="title">下层编码信息</span>
              <div class="col-table">
                <el-table :data="rightTableData" class="public-color" border height="500"
                          @selection-change="changeRightSelection">
                  <el-table-column type="selection" width="60" align="center"></el-table-column>
                  <el-table-column type="index" label="序号" width="60" align="center"></el-table-column>
                  <el-table-column label="属性名称" prop="cdPptyNm" width="100" show-overflow-tooltip></el-table-column>
                  <el-table-column label="是否PK" prop="pkYn" show-overflow-tooltip></el-table-column>
                  <el-table-column label="数据类型" prop="dataTpNm" width="100" show-overflow-tooltip></el-table-column>
                  <el-table-column label="长度" prop="dataLen" show-overflow-tooltip></el-table-column>
                </el-table>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </el-dialog>
</template>

<script setup>
import {reactive, toRefs, watch, defineProps, defineEmits} from 'vue'
import {ElMessage} from 'element-plus'
import {addBatchPptyR, addPptyR, delBatchPptyR, getCodeCdPptyMList, getPptyR} from '@/api'

const data = reactive({
  dialogVisible: false,
  codeModal: 1,
  codeOrder: 1,
  leftValue: '',
  rightValue: '',
  centerTableData: [],
  leftTableData: [],
  rightTableData: [],
  checkedData: [],
  checkedLeftData: [],
  checkedCenterData: [],
  checkedRightData: [],
  sendParams: {}
})
const {
  dialogVisible, codeOrder, codeModal, leftValue, rightValue, centerTableData, sendParams,
  leftTableData, rightTableData, checkedLeftData, checkedCenterData, checkedRightData
} = toRefs(data)

const props = defineProps({
  visibleObj: Object
})
watch(props.visibleObj, val => {
  console.log(val);
  dialogVisible.value = val.visible
  if (!val.visible) return
  sendParams.value = val
  getTableData(val)
  codeModal.value = val.codeModal
})

const emits = defineEmits(['close', 'next'])

const getTableData = (val) => {
  //获取左边表格数据
  getCodeCdPptyMList({cvCdId: val.editId}).then(res => {
    console.log('上层');
    console.log(res);
    leftTableData.value = res.data.data
  })
  // //中间表格数据
  getPptyR({hlvCvCdId: val.editId, llvCvCdId: val.bottomId}).then(res => {
    centerTableData.value = res.data.data
  });

  //右边表格数据
  getCodeCdPptyMList({cvCdId: val.bottomId}).then(res => {
    console.log('下层');
    console.log(res);
    rightTableData.value = res.data.data
  })
}
const onClose = () => {
  emits('close', 'codeRelation')
}
const onSuccess = () => {

}
const next = () => {
  emits('next', sendParams.value)
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
const autoInsert = () => {
  const params = {
    hlvCvCdId: sendParams.value.editId,
    llvCvCdId: sendParams.value.bottomId,
    codeOrder: codeOrder.value,
    //equals100: leftValue.value === "=100" || rightValue.value === "=100"
  };

  addBatchPptyR(params).then(() => {
    getPptyR({hlvCvCdId: params.hlvCvCdId, llvCvCdId: params.llvCvCdId}).then(res => {
      centerTableData.value = res.data.data
    })
  });
}
const insertItem = () => {
  if (leftValue.value !== '' && rightValue.value !== '') {
    ElMessage.info("请选择一项编码属性信息");
    return;
  }
  if (checkedLeftData.value.length !== 1 && leftValue.value !== '=100') {
    ElMessage.info('请选择一条要映射的上层编码信息')
    return
  }
  if (checkedRightData.value.length !== 1 && rightValue.value !== '=100') {
    ElMessage.info('请选择一条要映射的下层编码信息')
    return
  }
  console.log(checkedLeftData.value);
  console.log(checkedRightData.value);
  let params = {
    cvCdvlRltsDstsCd: '0100',
    hlvCvCdId: sendParams.value.editId,
    hlvCvCdPptyId: checkedLeftData.value.length > 0 ? checkedLeftData.value[0].cvCdPptyId : '',
    llvCvCdId: sendParams.value.bottomId,
    llvCvCdPptyId: checkedRightData.value.length > 0 ? checkedRightData.value[0].cvCdPptyId : '',
    rltsExplTxt: '',
    sortSrno: 1,
    //equals100: leftValue.value === "=100" || rightValue.value === "=100"
    leftValue: leftValue.value,
    rightValue: rightValue.value,
  }
  addPptyR(params).then(res => {
    ElMessage.success(res.data.msg)
    console.log(res);

    getPptyR({hlvCvCdId: sendParams.value.editId, llvCvCdId: sendParams.value.bottomId}).then(res => {
      centerTableData.value = res.data.data
      leftValue.value = '';
      rightValue.value = '';
    })
  })
}
const deleteItem = () => {
  if (checkedCenterData.value.length !== 1) {
    ElMessage.info('请选择一条要删除的映射')
    return
  }
  let params = {
    hlvCvCdId: checkedCenterData.value[0].hlvCvCdId,
    hlvCvCdPptyId: checkedCenterData.value[0].hlvCvCdPptyId,
    llvCvCdId: checkedCenterData.value[0].llvCvCdId,
    llvCvCdPptyId: checkedCenterData.value[0].llvCvCdPptyId,
  }

  console.log(params);
  delBatchPptyR([params]).then(res => {
    console.log(res.data);
    ElMessage.info("删除映射成功")
    getPptyR({hlvCvCdId: sendParams.value.editId, llvCvCdId: sendParams.value.bottomId}).then(res => {
      centerTableData.value = res.data.data
    })
  });
}
const batchDeleteItem = () => {
  if (checkedCenterData.value.length === 0) {
    ElMessage.info('请选择要删除的映射')
    return
  }

  const params = checkedCenterData.value.map(item=>{
    return {
      hlvCvCdId: item.hlvCvCdId,
      hlvCvCdPptyId: item.hlvCvCdPptyId,
      llvCvCdId: item.llvCvCdId,
      llvCvCdPptyId: item.llvCvCdPptyId,
    }
  });

  console.log(params);
  delBatchPptyR(params).then(res => {
    console.log(res.data);
    ElMessage.info("删除映射成功")
    getPptyR({hlvCvCdId: sendParams.value.editId, llvCvCdId: sendParams.value.bottomId}).then(res => {
      centerTableData.value = res.data.data
    })
  });
}
const onCodeOrderChanged = (val) => {
  const sortField = val === 1 ? "cv_sort_srno" : "cd_ppty_nm";
  getCodeCdPptyMList({cvCdId: sendParams.value.editId, sortField}).then(res => {
    leftTableData.value = res.data.data
  })
  //右边表格数据
  getCodeCdPptyMList({cvCdId: sendParams.value.bottomId, sortField}).then(res => {
    rightTableData.value = res.data.data
  })
}
</script>

<style scoped>
  .code-relation .code-one,.code-relation .code-two {
    justify-content: space-between;
  }
</style>
