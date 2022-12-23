<template>
  <el-dialog
      v-model="dialogVisible"
      title="编码属性列关系"
      @close="onClose"
      width="1200px"
      top="10vh"
      :close-on-click-modal="false"
  >
    <div class="code-relation">
      <!--<div class="code-one">-->
      <!--<el-radio-group v-model="codeModal">-->
      <!--<el-radio :label="1">上层代码</el-radio>-->
      <!--<el-radio :label="2">下层代码</el-radio>-->
      <!--</el-radio-group>-->
      <!--<el-button type="primary" class="first-button">申请</el-button>-->
      <!--<el-button type="primary">下一个</el-button>-->
      <!--</div>-->
      <div class="code-two">
        <el-radio-group v-model="codeOrder">
          <el-radio :label="1">顺序</el-radio>
          <el-radio :label="2">名称</el-radio>
        </el-radio-group>
				<div>
					<el-button type="primary" class="first-button" @click="onAutoMapping">自动映射</el-button>
					<el-button type="primary" @click="onMapping">映射</el-button>
					<el-button type="primary" @click="onDelete">删除映射</el-button>
					<el-button type="primary" @click="onBatchDelete">批量删除</el-button>
					<el-button type="primary" @click="onClose">申请</el-button>
				</div>
      </div>
      <div class="code-three">
        <el-row :gutter="10">
          <el-col :span="8">
            <div class="left">
              <el-input v-model="leftValue" placeholder="请输入"></el-input>
              <span class="title">编码属性信息</span>
              <el-table :data="leftTableData" border height="500" @row-click="onLeftTableRowClick"
                        @selection-change="onLeftTableRowSelectionChange">
                <el-table-column type="selection" width="60" align="center"></el-table-column>
                <el-table-column type="index" label="序号" width="60" align="center"></el-table-column>
                <el-table-column label="属性名称" prop="cdPptyNm" width="100"></el-table-column>
                <el-table-column label="是否PK" prop="pkYn"></el-table-column>
                <el-table-column label="数据类型" prop="dataTpNm" width="100"></el-table-column>
                <el-table-column label="长度" prop="dataLen"></el-table-column>
              </el-table>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="center">
              <span class="title">映射结果</span>
              <el-table :data="centerTableData" border height="500" @row-click="onCenterTableRowClick"
                        @selection-change="onCenterTableRowSelectionChange">
                <el-table-column type="selection" width="60" align="center"></el-table-column>
                <el-table-column label="属性名" prop="cdPptyNm"></el-table-column>
                <el-table-column label="列名" prop="cvDbFildId"></el-table-column>
                <el-table-column label="列属性名称" prop="dbFildNm"></el-table-column>
              </el-table>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="right">
              <el-input v-model="rightValue" placeholder="请输入"></el-input>
              <span class="title">表列信息</span>
              <el-table :data="rightTableData" border height="500" @row-click="onRightTableRowClick"
                        @selection-change="onRightTableRowSelectionChange">
                <el-table-column type="selection" width="60" align="center"></el-table-column>
                <el-table-column label="数据库管理系统" prop="cvDbmsId" width="100"></el-table-column>
                <el-table-column label="架构" prop="cvSchmId"></el-table-column>
                <el-table-column label="数据库表" prop="cvDbTabId"></el-table-column>
                <el-table-column label="字段名称" prop="dbFildNm"></el-table-column>
                <el-table-column label="数据类型" prop="dataTpNm"></el-table-column>
                <el-table-column label="长度" prop="dataLen"></el-table-column>
              </el-table>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <!--    <template #footer>-->
    <!--      <span class="dialog-footer">-->
    <!--        <el-button type="primary" @click="onClose">确定</el-button>-->
    <!--        <el-button @click="onClose">关闭</el-button>-->
    <!--      </span>-->
    <!--    </template>-->
  </el-dialog>
</template>

<script setup>
import {reactive, toRefs, watch, defineEmits, defineProps} from 'vue'
import {ElMessage} from 'element-plus'
import {
  CdPptyFildBndR_addBatchPptyFildBndR,
  CdPptyFildBndR_addPptyFildBndR, CdPptyFildBndR_delBatchPptyFildBndR,
  CdPptyFildBndR_getMappingLeftData,
  CdPptyFildBndR_getMappingRightData,
  getPptyFildBndR
} from '@/api'

const data = reactive({
  dialogVisible: false,
  codeModal: 1,
  codeOrder: 1,
  leftValue: '',
  rightValue: '',
  centerTableChecked: [],
  centerTableData: [],
  leftTableChecked: [],
  leftTableData: [],
  rightTableChecked: [],
  rightTableData: [],
})
const {
  dialogVisible, codeOrder, codeModal, leftValue, rightValue, centerTableData, centerTableChecked,
  leftTableData, rightTableData, leftTableChecked, rightTableChecked
} = toRefs(data)

const props = defineProps({
  visibleObj: Object
})
watch(props.visibleObj, val => {
  dialogVisible.value = val.visible
  if (!val.visible) return
  getTableData(val.params)
})

const getTableData = (val) => {
  CdPptyFildBndR_getMappingLeftData({cvCdId: val.editId}).then(res => {
    leftTableData.value = res.data.data;
  });

  CdPptyFildBndR_getMappingRightData({
    cvDbmsId: val.cvDbmsId,
    cvSchmId: val.cvSchmId,
    cvDbTabId: val.cvDbTabId
  }).then(res => {
    rightTableData.value = res.data.data;
  });

  getPptyFildBndR({
    cvCdId: val.editId,
    cvDbmsId: val.cvDbmsId,
    cvSchmId: val.cvSchmId,
    cvDbTabId: val.cvDbTabId
  }).then(res => {
    let d = res.data.data
    centerTableData.value = d;
  })
}

const onLeftTableRowClick = (row) => {
  leftTableChecked.value = [row];
}

const onLeftTableRowSelectionChange = (rows) => {
  leftTableChecked.value = rows;
}

const onRightTableRowClick = (row) => {
  rightTableChecked.value = [row];
  console.log(rightTableChecked.value, row)
}

const onRightTableRowSelectionChange = (rows) => {
  rightTableChecked.value = rows;
  console.log(rightTableChecked.value, rows)
}

const onCenterTableRowClick = (row) => {
  centerTableChecked.value = [row];
  console.log(centerTableChecked.value, row)
}

const onCenterTableRowSelectionChange = (rows) => {
  centerTableChecked.value = rows;
  console.log(centerTableChecked.value, rows)
}

const emits = defineEmits(['close'])

const onClose = () => {
  emits('close', 'codeAttr')
}

const onMapping = () => {
  console.log(leftTableChecked.value, leftValue.value, rightTableChecked.value, rightValue.value)
  if (leftValue.value !== '' && rightValue.value !== '') {
    ElMessage.info("请选择一项编码属性信息或表列信息");
    return;
  }

  if (leftTableChecked.value.length !== 1 && leftValue.value !== '=100') {
    ElMessage.info("请选择一项编码属性信息");
    return;
  }
  if (rightTableChecked.value.length !== 1 && rightValue.value !== '=100') {
    ElMessage.info("请选择一项表列信息");
    return;
  }

  const obj = {
    cvCdId: props.visibleObj.params.editId,
    cvCdPptyId: leftTableChecked.value.length > 0 ? leftTableChecked.value[0].cvCdPptyId : '',
    cvDbmsId: props.visibleObj.params.cvDbmsId,
    cvSchmId: props.visibleObj.params.cvSchmId,
    cvDbTabId: props.visibleObj.params.cvDbTabId,
    cvDbFildId: rightTableChecked.value.length > 0 ? rightTableChecked.value[0].cvDbFildId : '',
    cvSortSrno: 1,
    leftValue: leftValue.value,
    rightValue: rightValue.value
  };
  console.log(obj)

  CdPptyFildBndR_addPptyFildBndR(obj).then(res => {
    ElMessage.info(res.data.msg);

    getPptyFildBndR(obj).then(res => {
      let d = res.data.data
      centerTableData.value = d;
      leftValue.value = '';
      rightValue.value = '';
    })
  });
};

const onAutoMapping = () => {
  const obj = {
    cvCdId: props.visibleObj.params.editId,
    cvDbmsId: props.visibleObj.params.cvDbmsId,
    cvSchmId: props.visibleObj.params.cvSchmId,
    cvDbTabId: props.visibleObj.params.cvDbTabId,
    codeOrder: codeOrder.value,
    //equals100: leftValue.value === "=100" || rightValue.value === "=100",
  };
  console.log(obj);

  CdPptyFildBndR_addBatchPptyFildBndR(obj).then(res=>{
    ElMessage.info(res.data.msg);
    getPptyFildBndR(obj).then(res => {
      let d = res.data.data
      centerTableData.value = d;
    })
  })
};

const onDelete = () => {
  if (centerTableChecked.value.length !== 1) {
    ElMessage.info("请选择需要删除的映射信息");
    return
  }

  onBatchDelete();
};

const onBatchDelete = () => {
  if (centerTableChecked.value.length === 0) {
    ElMessage.info("请选择需要删除的映射信息");
    return
  }

  const load = {
    cvCdId: props.visibleObj.params.editId,
    cvDbmsId: props.visibleObj.params.cvDbmsId,
    cvSchmId: props.visibleObj.params.cvSchmId,
    cvDbTabId: props.visibleObj.params.cvDbTabId
  }

  const listToDelete = centerTableChecked.value.map(item => {
    return {
      cvCdId: props.visibleObj.params.editId,
      cvDbmsId: props.visibleObj.params.cvDbmsId,
      cvSchmId: props.visibleObj.params.cvSchmId,
      cvDbTabId: props.visibleObj.params.cvDbTabId,
      cvCdPptyId: item.cvCdPptyId,
      cvDbFildId: item.cvDbFildId
    };
  });

  CdPptyFildBndR_delBatchPptyFildBndR(listToDelete).then(res => {
    ElMessage.info(res.data.msg);
    getPptyFildBndR(load).then(res => {
      let d = res.data.data
      centerTableData.value = d;
    })
  });
};
</script>

<style scoped lang="less">
  .code-relation .code-two {
    justify-content: space-between;
  }
</style>
