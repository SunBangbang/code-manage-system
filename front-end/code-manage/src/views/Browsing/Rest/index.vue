<template>
  <div class="application-code">
    <div class="code-body">
      <div class="body-left">
        <div class="code-area">
          <span>编码区</span>
          <el-select v-model="form1.cvCdSphrId">
            <el-option v-for="item in sphrList"
                       :label="item.cdSphrNm"
                       :value="item.cvCdSphrId"
                       :key="item.cvCdSphrId"
            ></el-option>
          </el-select>
        </div>
        <div class="left-form">
          <el-form :model="form" label-width="120px" label-position="left">
            <el-form-item v-for="item in formList" :label="item.label" :key="item.label">
              <template v-if="item.type === 'input'">
                <el-input v-model="item.value" placeholder="请输入"></el-input>
              </template>
              <template v-else-if="item.type === 'input-search'">
                <el-input type="hidden" v-model="item.value"></el-input>
                <el-input v-model="item.valueDisplay" readonly placeholder="请选择">
                  <template #append>
                    <el-button :icon="Search" class="append-button" @click="searchDialog(item)"></el-button>
                  </template>
                </el-input>
              </template>
              <template v-else-if="item.type === 'special-select'">
                <el-select v-model="item.value">
                  <el-option value="Y">Y</el-option>
                  <el-option value="N">N</el-option>
                </el-select>
              </template>
              <template v-else-if="item.type === 'get-select'">
                <el-select v-model="item.value">
                  <el-option v-for="item in dataTypeList"
                             :label="item.cdvlNm"
                             :value="item.cdvlNm"
                             :key="item.cdvlNm"
                  ></el-option>
                </el-select>
              </template>
              <template v-else-if="item.type === 'input-state'">
                <el-select v-model="item.value">
                  <el-option v-for="item in inputState"
                             :label="item.cdvlNm"
                             :value="item.cdvlNm"
                             :key="item.cdvlNm"
                  ></el-option>
                </el-select>
              </template>
              <template v-else-if="item.type === 'input-type'">
                <el-select v-model="item.value">
                  <el-option v-for="item in inputType"
                             :label="item.cdvlNm"
                             :value="item.cdvlNm"
                             :key="item.cdvlNm"
                  ></el-option>
                </el-select>
              </template>
              <template v-else>
                <el-input type="textarea" v-model="item.value" placeholder="请输入"></el-input>
              </template>
            </el-form-item>
          </el-form>

        </div>
        <div class="left-button">
          <el-button type="primary" @click="save('add')" v-if="editId === ''">添加</el-button>
          <el-button type="primary" @click="save('edit')" v-if="editId !== ''">修改</el-button>
        </div>
      </div>
      <div class="body-right">
        <div class="code-button">
          <el-button type="primary" @click="getBack">返回</el-button>
          <el-button type="primary" @click="handleSave">申请</el-button>
        </div>
        <div class="right-table">
          <general-table :tableNav="formList"
                         :tableData="sendDatas"
                         :total="total"
                         :loading="loading"
                         :isCheckbox="true"
                         @clickRow="handleClickRow"
          ></general-table>
        </div>

        <div class="right-tab">
          <div class="first-tab">
            <el-tabs type="border-card" @tab-click="clickTab" v-model="activeName">
              <el-tab-pane label="编码属性" name="first">
<!--                <general-table :tableNav="topTableNav"-->
<!--                               :tableData="codeAttrTableData"-->
<!--                               :total="codeAttrTotal"-->
<!--                               @selectionChange="handleCodeAttrChange"-->
<!--                ></general-table>-->
                <div class="general-table">
                  <el-table :data="codeAttrTableData"
                            class="table"
                            border @selection-change="handleCodeAttrChange">
                    <el-table-column type="selection" width="60" align="center"></el-table-column>
                    <el-table-column type="index"
                                     label="序号"
                                     width="60"
                                     align="center"
                    ></el-table-column>
                    <el-table-column label="编码名称" prop="cdLgcNm" show-overflow-tooltip width="120"></el-table-column>
                    <el-table-column label="属性序号" show-overflow-tooltip width="120">
                      <template #default="scope">
                        <span v-if="!scope.row.isEdit">{{ scope.row.cvSortSrno }}</span>
                        <el-input v-else v-model="scope.row.cvSortSrno" placeholder="请输入" size="small"></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column label="编码属性名称" show-overflow-tooltip width="160">
                      <template #default="scope">
                        <span v-if="!scope.row.isEdit">{{ scope.row.cdPptyNm }}</span>
                        <el-input v-else v-model="scope.row.cdPptyNm" placeholder="请输入" size="small"></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column label="PK与否" show-overflow-tooltip width="120">
                      <template #default="scope">
                        <span v-if="!scope.row.isEdit">{{ scope.row.pkYn }}</span>
                        <el-select v-else v-model="scope.row.pkYn" size="small">
                          <el-option value="Y">Y</el-option>
                          <el-option value="N">N</el-option>
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column label="NN与否" show-overflow-tooltip width="120">
                      <template #default="scope">
                        <span v-if="!scope.row.isEdit">{{ scope.row.nullYn }}</span>
                        <el-select v-else v-model="scope.row.nullYn" size="small">
                          <el-option value="Y">Y</el-option>
                          <el-option value="N">N</el-option>
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column label="数据类型" prop="dataTpNm" show-overflow-tooltip width="120">
                      <template #default="scope">
                        <span v-if="!scope.row.isEdit">{{ scope.row.dataTpNm }}</span>
                        <el-select v-else v-model="scope.row.dataTpNm" size="small">
                          <el-option v-for="item in dataTypeList"
                                     :label="item.cdvlNm"
                                     :value="item.cdvlNm"
                                     :key="item.cdvlNm"
                          ></el-option>
                        </el-select>
                      </template>
                    </el-table-column>
                    <el-table-column label="长度" show-overflow-tooltip width="100">
                      <template #default="scope">
                        <span v-if="!scope.row.isEdit">{{ scope.row.dataLen }}</span>
                        <el-input v-else v-model="scope.row.dataLen" placeholder="请输入" size="small"></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column label="历史起始日期" min-width="160" prop="recBgnDttm" show-overflow-tooltip></el-table-column>
                    <el-table-column label="创建用户ID" min-width="160" prop="cretCvUserNm" show-overflow-tooltip></el-table-column>
                  </el-table>
                </div>
                <div class="general-pagination">
                  <el-pagination layout="total"
                                 :total="codeAttrTotal">
                  </el-pagination>
                </div>
              </el-tab-pane>
              <el-tab-pane label="编码编码关系" name="second">
                <general-table :tableNav="topRelationTableNav"
                               :tableData="relationTableData"
                               :total="relationTotal"
                               @clickRow="handleCodeClickRow"
                               @selectionChange="handleRelationAttrChange"
              ></general-table></el-tab-pane>
              <el-tab-pane label="编码表关系" name="third">
                <general-table :tableNav="topTableTableNav"
                                :tableData="codeTableTableData"
                                :total="codeTableTotal"
                               @clickRow="handleTableClickRow"
                               @selectionChange="handleColumnAttrChange"
              ></general-table></el-tab-pane>
            </el-tabs>
            <div class="position-plus">
              <el-icon @click="topAdd" title="新增" v-if="!saveShow"><Plus /></el-icon>
              <el-icon @click="topFirstEdit" v-if="activeName === 'first' && !saveShow" title="修改"><Edit /></el-icon>
              <el-icon @click="topSave" v-if="activeName === 'first' && saveShow" title="保存"><CircleCheck /></el-icon>
              <el-icon @click="topClose" v-if="activeName === 'first' && saveShow" title="取消"><CircleClose /></el-icon>
              <el-icon @click="topDelete" title="删除"><Delete /></el-icon>
            </div>
          </div>

          <div class="second-tab">
            <el-tabs type="border-card" v-model="activeName2">
              <el-tab-pane label="编码值" name="first" :disabled="codeAttrDisabled">
                <div class="general-table">
                  <el-table :data="codeValueAttrData"
                            class="table"
                            border
                            @selectionChange="handleCodeValueChange"
                  >
                    <el-table-column type="selection" width="60" align="center"></el-table-column>
                    <el-table-column type="index"
                                     label="序号"
                                     width="60"
                                     align="center"
                    ></el-table-column>
                    <el-table-column v-for="item in codeValueAttr"
                                     :key="item.prop"
                                     :label="item.label"
                                     show-overflow-tooltip
                                     :width="setColumnWidth(item, codeValueAttr)"
                    >
                      <template #default="scope">
                        <span v-if="!scope.row.isEdit">{{ scope.row[item.prop] }}</span>
                        <el-input v-else v-model="scope.row[item.prop]" placeholder="请输入" size="small"></el-input>
                      </template>
                    </el-table-column>
                  </el-table>
                  <!--                <general-table :tableNav="codeValueAttr"-->
                  <!--                               :tableData="codeValueAttrData"-->
                  <!--                               :total="codeValueAttrTotal"-->
                  <!--                               @selectionChange="handleCodeValueChange"-->
                  <!--                ></general-table>-->
                </div>
                <div class="general-pagination">
                  <el-pagination layout="total"
                                 :total="codeValueAttrTotal">
                  </el-pagination>
                </div>
              </el-tab-pane>
              <el-tab-pane label="编码属性关系" name="second" :disabled="codeRelaDisabled">
                <general-table :tableNav="relationAttr"
                               :tableData="relationAttrTableData"
                               :total="relationAttrTotal"
                               @selectionChange="handleCodeAttrRelationChange"
                ></general-table>
              </el-tab-pane>
              <el-tab-pane label="编码值关系" name="third" :disabled="codeRelaDisabled">
                <general-table :tableNav="codeValueRelationAttr"
                               :tableData="codeValueRelationAttrTableData"
                               :total="codeValueRelationAttrTotal"
                               @selectionChange="handleCodeValueRelationChange"
                ></general-table>
              </el-tab-pane>
              <el-tab-pane label="编码属性列关系" name="fourth" :disabled="codeFormDisabled">
                <general-table :tableNav="codeColumnAttr"
                               :tableData="codeColumnTableData"
                               :total="codeColumnTotal"
                               @selectionChange="handleCodeColumnChange"
                ></general-table>
              </el-tab-pane>
            </el-tabs>
            <el-button class="position-button" v-if="activeName === 'first'" type="primary" size="small" @click="loadUpper">加载上层编码值</el-button>
            <div class="position-plus">
              <el-icon @click="bottomAdd" title="新增" v-if="!bottomSaveShow"><plus /></el-icon>
              <el-icon @click="bottomEdit" v-if="activeName2 === 'first' && !bottomSaveShow" title="修改"><Edit /></el-icon>
              <el-icon @click="bottomSave" v-if="activeName2 === 'first' && bottomSaveShow" title="保存"><CircleCheck /></el-icon>
              <el-icon @click="bottomClose" v-if="activeName2 === 'first' && bottomSaveShow" title="取消"><CircleClose /></el-icon>
              <el-icon @click="bottomDelete" title="删除"><Delete /></el-icon>
            </div>
          </div>
        </div>
      </div>
    </div>

    <CodeClassify :visibleObj="codeClassObj" @success="updateCodeClassify" @close="closeCodeClassify"></CodeClassify>

    <BelongSystem :visibleObj="belongSystemObj" @success="updateSystem" @resetDialog="resetDialog"></BelongSystem>

    <UpperCode :visibleObj="upperCodeObj"
               @close="resetDialog"
               @next="nextDialog"
               @applyItemLeft="applyItemLeft"
               @applyItemRight="applyItemRight"
    ></UpperCode>

    <LoadUpperCode :visibleObj="loadUpperCodeObj" @close="resetDialog" @success="handleLoadUpperCode"></LoadUpperCode>

    <CodeRelation :visibleObj="codeRelationObj" @close="resetDialog" @next="nextValue"></CodeRelation>

    <CodeValue :visibleObj="codeValueObj" @close="resetDialog"></CodeValue>

    <CodeAttr :visibleObj="codeAttrObj" @close="resetDialog"></CodeAttr>

    <CodeTable :visibleObj="codeTableObj" @close="resetDialog"></CodeTable>
  </div>
</template>

<script setup>
import { ref, reactive, toRefs, watch, onMounted, toRaw, defineProps, defineEmits } from 'vue'
import { Search } from '@element-plus/icons-vue'
import CodeClassify from '@/components/dialogs/code-classify'
import BelongSystem from '@/components/dialogs/belong-system'
import UpperCode from '@/components/dialogs/upper-code'
import LoadUpperCode from '@/components/dialogs/load-upper-code'
import CodeRelation from '@/components/dialogs/code-relation'
import CodeValue from '@/components/dialogs/code-value'
import CodeAttr from '@/components/dialogs/code-attr'
import { ElMessage, ElMessageBox } from 'element-plus'
import { setColumnWidth } from '@/assets/js/column-width'
import {
  addCdM,
  editCdM,
  getCdPptyMList,
  getCodeCodeRelation,
  getCdPtyvList,
  delBatchCdPptyM,
  delBatchCdvlM,
  delCdR,
  getCodeAttributeRelation,
  getCodeTableRelation,
  getCodeValueRelation,
  getCodeAttrColumnRelation,
  delBatchTabCdBndR,
  delBatchPptyR,
  delBatchPptyFildBndR,
  getDataTpList,
  addCdPptyM,
  editCdPptyM,
  addCdPtyvM,
  editCdPtyvM,
  delBatchCdvlR, getCdStCdList,
} from '@/api'
import { formList, topTableNav, topRelationTableNav, topTableTableNav,
  topTableData, bottomTableNav, bottomTableData } from '@/assets/js/applicationCode'
import CodeTable from "@/components/dialogs/code-table";

const props = defineProps({
  sphrList: Array,
  sendData: Array,
  buttonFlag: String
})
watch(props.sendData, val => {
  console.log(val);
  if(props.sendData.length === 0) {
    resetForm()
  }
})
onMounted(() => {
  // let obj1 = {
  //   "cdPptyNm": "121212",
  //   "cvCdId": "52c60cde-53c0-409f-8c6f-12c7cc334a89",
  //   "cvCdPptyId": "111",
  //   "cvCdSphrId": "111",
  //   "dataLen": 10,
  //   "dataTpNm": "11",
  //   "nullYn": "Y",
  //   "pkYn": "Y"
  // }
  // console.log(obj1);
  // getqqq(obj1)
  console.log(props);
  resetForm();
  sendDatas.value = props.sendData
  getDataType()
  if(sendDatas.value.length === 0) {
    editId.value = ''
    return
  }

  sendDatas.value.forEach(obj=>{
    for(let i in obj) {
      obj[i] = obj[i] ?? '';
    }
    obj.cvRefId = ''
  });

  if (props.buttonFlag === "add") {
    // sendDatas.value.forEach((obj, idx)=>{
    //   addCdM(obj).then(res => {
    //     console.log(res);
    //     obj.cvCdId = res.data.data.cvCdId;
    //
    //     if(idx === sendDatas.value.length - 1) {
    //       getCodeData(obj, props.buttonFlag)
    //     }
    //   })
    // });
  }
  else {
    let obj = props.sendData[props.sendData.length - 1]
    getCodeData(obj, props.buttonFlag)
  }
})

//默认获取数据类型
const getDataType = () => {
  getDataTpList().then(res => {
    dataTypeList.value = res.data.data
  })
}

//查询编码属性和编码值
const getCodeData = (obj, flag) => {
  console.log(obj);
  for(let key in obj) {
    for(let i in formList) {
      if(key === formList[i].prop) {
        formList[i].value = obj[key]
      }
      if(formList[i].propDisplay && key === formList[i].propDisplay) {
        formList[i].valueDisplay = obj[key]
      }
    }
  }

  form1.value.cvCdSphrId = obj.cvCdSphrId
  editId.value = obj.cvCdId
  getCodeAttrList(editId.value)
  getCodeAttrValueList(editId.value)
}
//查询编码属性
const getCodeAttrList = (cvCdId) => {
  getCdPptyMList({ cvCdId }).then(res => {
    console.log(res);
    let d = res.data.data
    codeAttrTableData.value = d
    codeAttrTotal.value = d.length
  })
}
//查询编码属性值
const getCodeAttrValueList = (cvCdId) => {
  codeValueAttr.value = []
  getCdPtyvList({ cvCdId }).then(res => {
    console.log(res);
    let d = res.data.data
    if(d.table && d.tableId) {
      for(let key in d.table) {
        let param = {
          id: d.tableId[key],
          label: d.table[key],
          prop: key
        }
        codeValueAttr.value.push(param)
      }
      console.log(codeValueAttr.value);
      codeValueAttrData.value = d.data
      codeValueAttrTotal.value = d.data.length
    }
  })
}

//查询编码编码关系
const getCodeCodeRelationFun = (cvCdId) => {
  getCodeCodeRelation({ cvCdId }).then(res => {
    console.log(res);
    let d = res.data.data
    relationTableData.value = d
    relationTotal.value = d.length
    getCodeCodeAttrRelation(cvCdId)
  })
}
//查询编码属性关系
const getCodeCodeAttrRelation = (cvCdId) => {
  getCodeAttributeRelation({ cvCdId }).then(res => {
    let d = res.data.data
    console.log(d)
    relationAttrTableData.value = d
    relationAttrTotal.value = d.length
  })
}
//查询编码值关系

//查询编码表关系
const getCodeTableRelationFun = (cvCdId) => {
  getCodeTableRelation({ cvCdId }).then(res => {
    let d = res.data.data
    codeTableTableData.value = d
    codeTableTotal.value = d.length
  })
}

const pageData = reactive({
  form: {},
  total: 0,
  sendDatas: [],
  loading: false,
  codeAttrDisabled: false,
  codeRelaDisabled: true,
  codeFormDisabled: true,
  activeName: 'first',
  activeName2: 'first',
  codeState: [],
  editId: '',
  codeValueAttr: [],
  codeValueAttrData: [],
  codeValueAttrTotal: 0,

  relationAttr: [
    { label: '上层编码名称', prop: 'hlvCdLgcNm' },
    { label: '上层编码属性名称', prop: 'hlvCdPptyNm' },
    { label: '下层编码名称', prop: 'llvCdLgcNm' },
    { label: '下层编码属性名称', prop: 'llvCdPptyNm' }
  ],
  relationAttrTableData: [],
  relationAttrTotal: 0,

  codeValueRelationAttr: [],
  codeValueRelationAttrTableData: [],
  codeValueRelationAttrTotal: 0,

  codeColumnAttr: [
    { label: "编码名称", prop: "cdLgcNm" },
    { label: "编码属性名称", prop: "cdPptyNm" },
    { label: "表名称", prop: "dbTabNm" },
    { label: "column名称", prop: "dbFildNm" }
  ],
  codeColumnTableData: [],
  codeColumnTotal: 0,

  codeAttrChecked: [],
  relationAttrChecked: [],
  columnCheckedData: [],
  codeValueCheckedData: [],
  codeAttrRelationChecked: [],
  codeValueRelationChecked: [],
  codeColumnChecked: [],
  dataTypeList: [],
  form1: {
    cvCdSphrId: ''
  },
  codeClassObj: {
    visible: false
  },
  belongSystemObj: {
    visible: false
  },
  upperCodeObj: {
    visible: false,
    sign: ''
  },
  loadUpperCodeObj: {
    visible: false
  },
  codeRelation: {
    visible: false
  },
  codeRelationObj: {
    visible: false
  },
  codeValueObj: {
    visible: false
  },
  codeAttrObj: {
    visible: false
  },
  codeTableObj: {
    visible: false
  },
  codeAttrTableData: [],
  codeAttrTotal: 0,

  relationTableData: [],
  relationTotal: 0,

  codeTableTableData: [],
  codeTableTotal: 0,
  codeTableChecked: [],

  saveShow: false,
  addSign: false,
  bottomSaveShow: false,
  bottomAddSign: false
})

// inputState
const inputState = ref([]);
const inputType = ref([]);

const {
  form, total, loading, editId, sendDatas, codeValueAttr, codeValueAttrData, codeValueAttrTotal, codeAttrChecked, relationAttrChecked, columnCheckedData,
  codeAttrDisabled, codeRelaDisabled, codeFormDisabled, codeState, form1, relationTableData, relationTotal, codeValueCheckedData,
  activeName, activeName2, codeClassObj, belongSystemObj, upperCodeObj, relationAttrTableData, relationAttrTotal, relationAttr,
  loadUpperCodeObj, codeRelationObj, codeValueObj, codeAttrObj, codeAttrTableData, codeAttrTotal, codeTableTableData, codeTableTotal,
  codeValueRelationAttr, codeValueRelationAttrTableData, codeValueRelationAttrTotal, dataTypeList, saveShow, addSign, bottomAddSign,
  codeColumnAttr, codeColumnTableData, codeColumnTotal, codeAttrRelationChecked, codeValueRelationChecked, codeColumnChecked, bottomSaveShow,
  codeTableObj, codeTableChecked
} = toRefs(pageData)

const emits = defineEmits(['back'])

const handleClickRow = (row) => {
  saveShow.value = false
  bottomSaveShow.value = false
  addSign.value = false
  bottomAddSign.value = false
  getCodeData(row, props.buttonFlag)
}
//点击编码编码关系行查询编码值关系
const handleCodeClickRow = (row) => {
  let params = {
    hlvCvCdId: row.hlvCvCdId,
    llvCvCdId: row.llvCvCdId
  }
  codeValueRelationAttr.value = [];
  codeValueRelationAttrTableData.value = [];
  codeValueRelationAttrTotal.value = 0;

  getCodeValueRelation(params).then(res => {
    let d = res.data.data
    if (d.table && d.data) {
      for (let key in d.table) {
        let obj = {
          label: d.table[key],
          prop: key
        }
        codeValueRelationAttr.value.push(obj)
      }
      codeValueRelationAttrTableData.value = d.data
      codeValueRelationAttrTotal.value = d.data.length
    }
  });
}
//点击编码表关系行查询编码属性列关系
const handleTableClickRow = (row) => {
  let params = {
    cvCdId: editId.value,
    cvDbTabId: row.cvDbTabId,
    cvDbmsId: row.cvDbmsId,
    cvSchmId: row.cvSchmId
  }

  getCodeAttrColumnRelation(params).then(res => {
    let d = res.data.data
    codeColumnTableData.value = d
    codeColumnTotal.value = d.length
  })

  columnCheckedData.value = [row]
}
const clickTab = (val) => {
  switch(val.props.name || val) {
    case 'first':
      pageData.codeAttrDisabled = false
      pageData.codeRelaDisabled = true
      pageData.codeFormDisabled = true
      pageData.activeName2 = 'first'
      break
    case 'second':
      pageData.codeAttrDisabled = true
      pageData.codeRelaDisabled = false
      pageData.codeFormDisabled = true
      pageData.activeName2 = 'second'
      console.log('编码编码关系');
      if(editId.value) {
        getCodeCodeRelationFun(editId.value)
      }
      break
    case 'third':
      pageData.codeAttrDisabled = true
      pageData.codeRelaDisabled = true
      pageData.codeFormDisabled = false
      pageData.activeName2 = 'fourth'
      console.log('编码表关系');
      if(editId.value) {
        getCodeTableRelationFun(editId.value)
      }
      break
    default:
  }
}
const handleLoadUpperCode = (val) => {
  pageData.loadUpperCodeObj.visible = false
  const dataToAppend = val.map(item => toRaw(item));
  //console.log(dataToAppend);
  if (dataToAppend.length === 0) {
    return;
  }

  if (codeValueAttr.value.length === 0) {
    ElMessage.warning("请先添加任何编码属性");
    return;
  }

  const codeValueAttrRaw = toRaw(codeValueAttr.value);
  const dataToAppendList = [];

  dataToAppend.forEach(item => {
    let check = false;
    let obj = {};
    let objToSave = {
      cvCdId: editId.value,
      values: {}
    };

    codeValueAttrRaw.forEach(codeValueAttrRawItem => {
      if (Object.hasOwn(item, codeValueAttrRawItem.label)) {
        obj[codeValueAttrRawItem.prop] = item[codeValueAttrRawItem.label];
        objToSave.values[codeValueAttrRawItem.id] = item[codeValueAttrRawItem.label];
        check = true;
      }
    });
    console.log(item, codeValueAttrRaw, objToSave)
    if (check) {
      dataToAppendList.push(addCdPtyvM(objToSave).then(res=>{
        console.log(res);
        //codeValueAttrData.value.push(obj);
      }));
    }
  });

  Promise.all(dataToAppendList).then(()=>{getCodeAttrValueList(editId.value);});
}
const resetForm = () => {
  for(let i in formList) {
    formList[i].value = ''
    formList[i].valueDisplay = ''
  }
  form1.value.cvCdSphrId = ''
}
const getBack = () => {
  resetForm()
  emits('back', true)
}
const handleSave = () => {
  resetForm()
  getBack();
}
//编码分类返回名称
const updateCodeClassify = (val) => {
  formList[0].value = val.cvCdCatId
  formList[0].valueDisplay = val.cdCatNm;
  closeCodeClassify()
  console.log(formList[0])
}
//编码分类取消
const closeCodeClassify = () => {
  pageData.codeClassObj.visible = false
}
//所属系统返回
const updateSystem = (val) => {
  for(let i in formList) {
    if(formList[i].prop === 'blngCvSysId') {
      formList[i].value = val.cvSysId
      formList[i].valueDisplay = val.sysNm;
    }
  }
  closeSystem()
}
//所属系统取消
const closeSystem = () => {
  pageData.belongSystemObj.visible = false
}
//上层编码名左侧
const applyItemLeft = (val) => {
  for(let i in formList) {
    if(formList[i].prop === 'hlvCvCdId') {
      formList[i].value = val.cvCdId
      formList[i].valueDisplay = val.cdPhysNm
    }
  }
  closeUpperCode()
}
//上层编码名取消
const closeUpperCode = () => {
  pageData.upperCodeObj.visible = false
}
//编码属性(右侧)
const applyItemRight = () => {
  closeUpperCode()
  getCodeAttrList(editId.value)
  getCodeAttrValueList(editId.value)
}
const save = (val) => {
  console.log(formList);
  console.log(form1.value);
  console.log(val);
  if(!form1.value.cvCdSphrId) {
    ElMessage.info('请选择编码区')
    return
  }
  let arr = formList.filter(v => v.prop !== 'cdExplTxt' && v.prop !== 'cvRefId' && v.prop !== 'hlvCvCdId' && v.prop !== 'blngCvSysId' && v.prop !== 'cvCdCatId')
  for(let i in arr) {
    if(!arr[i].value) {
      console.log(arr[i].label);
      let arrType = ['special-select', 'get-select', 'input-search']
      if(arrType.includes(arr[i].type)) {
        ElMessage.info(`请选择${arr[i].label}`)
      }else if(arr[i].type === 'input') {
        ElMessage.info(`请输入${arr[i].label}`)
      }
      return
    }
  }
  let params = {}
  for(let i in formList) {
    params[formList[i].prop] = formList[i].value
    if(formList[i].propDisplay && formList[i].valueDisplay) {
      params[formList[i].propDisplay] = formList[i].valueDisplay
    }
  }
  let obj = {
    ...params,
    ...form1.value
  }
  console.log(obj)
  if(val === 'add') {
    //新增
    addCdM(obj).then(res => {
      console.log(res);
      obj.cvCdId = res.data.data.cvCdId
      sendDatas.value.push(obj)
      ElMessage.success(res.data.msg)
      //resetForm()
      editId.value = res.data.data.cvCdId
      getCodeData(obj, props.buttonFlag)
    })
  }else {
    //编辑
    // let params = {}
    // for(let i in formList) {
    //   params[formList[i].prop] = formList[i].value
    // }
    // sendDatas.value.push(params)
    obj.cvCdId = editId.value
    editCdM(obj).then(res => {
      console.log(res);
    })

    let sendDatasItem = sendDatas.value.find(item=>item.cvCdId === obj.cvCdId);
    sendDatasItem = Object.assign(sendDatasItem, obj);

    Object.keys(sendDatasItem).forEach(key=>{
      sendDatasItem[key] = obj[key];
    });

    console.log(sendDatasItem)
  }
}
//中间表格的新增
const topAdd = () => {
  console.log(activeName);
  // if(!editId.value) {
  //   ElMessage.info('请选择编码')
  //   return
  // }
  if(!editId.value) {
    ElMessage.info('请选择编码')
    return
  }
  if(activeName.value === 'first') {
    let obj = sendDatas.value.find(v => v.cvCdId === editId.value)
    console.log(obj);
    // searchDialog({ prop: 'upperCodeNameRight' })
    codeAttrTableData.value.push({
      cvCdId: obj.cvCdId,
      cdLgcNm: obj.cdLgcNm,
      cretCvUserId: obj.cretCvUserId,
      cvCdSphrId: obj.cvCdSphrId,
      cvSortSrno: '',
      recBgnDttm: '',
      cdPptyNm: '',
      pkYn: 'Y',
      nullYn: 'Y',
      dataTpNm: '',
      dataLen: '',
      isEdit: true
    })
    addSign.value = true
    saveShow.value = true
  }
  else if(activeName.value === 'second') {
    console.log(relationAttrChecked.value);
    // if(relationAttrChecked.value.length !== 1) {
    //   ElMessage.info('请选择一项编码编码关系')
    //   return
    // }
    let params = {
      codeName: sendDatas.value.find(item=>item.cvCdId === editId.value).cdLgcNm,
      editId: editId.value,
      //bottomId: relationAttrChecked.value[0].cvCdId
    }
    console.log(params)
    searchDialog({ prop: 'upperCodeNameNext' }, params)
  }
  else if(activeName.value === 'third') {
    pageData.codeTableObj.visible = true
    pageData.codeTableObj.editId = editId.value
  }
}
//中间表格编辑
const topFirstEdit = () => {
  if(!editId.value) {
    ElMessage.info('请选择编码')
    return
  }
  if(codeAttrChecked.value.length !== 1) {
    ElMessage.info('请选择一项要修改的编码属性')
    return
  }
  saveShow.value = true
  codeAttrChecked.value[0].isEdit = true
  console.log(codeAttrChecked.value);
  //searchDialog({ prop: 'upperCodeNameRight' })
}
//中间表格保存
const topSave = () => {
  console.log(codeAttrTableData.value[0]);
  if(addSign.value) {
    let obj = codeAttrTableData.value.find(v => v.isEdit)
    console.log(obj);
    if(!obj.cdPptyNm) {
      ElMessage.info('请输入编码属性名称')
      return
    }
    addCdPptyM(obj).then(res => {
      ElMessage.success(res.data.msg)
      saveShow.value = false
      addSign.value = false
      getCodeAttrList(editId.value)
      getCodeAttrValueList(editId.value)
    })
  }else {
    let a = codeAttrChecked.value[0]  //编码属性当前修改的行
    let obj = sendDatas.value.find(v => v.cvCdId === editId.value)  //编码列表选中的行
    let p = {
      cdPptyNm: a.cdPptyNm,
      cvCdId: editId.value,
      cvCdPptyId: a.cvCdPptyId,
      cvCdSphrId: obj.cvCdSphrId,
      cvSortSrno: a.cvSortSrno,
      dataLen: a.dataLen,
      dataTpNm: a.dataTpNm,
      nullYn: a.nullYn,
      pkYn: a.pkYn
    }
    console.log(p);
    if(!p.cdPptyNm) {
      ElMessage.info('请输入编码属性名称')
      return
    }
    editCdPptyM(p).then(res => {
      ElMessage.success(res.data.msg)
      saveShow.value = false
      getCodeAttrList(editId.value)
      getCodeAttrValueList(editId.value)
    })
  }
}
//中间表格取消
const topClose = () => {
  saveShow.value = false
  addSign.value = false
  getCodeAttrList(editId.value)
}

//中间表格的删除
const topDelete = () => {
  if(!editId.value) {
    ElMessage.info('请选择编码')
    return
  }
  if(activeName.value === 'first') {
    if(codeAttrChecked.value.length === 0) {
      ElMessage.info('请选择要删除的编码属性')
      return
    }
    console.log(codeAttrChecked.value);
    let p = {
      cvCdId: editId.value,
      cvCdPptyIdList: codeAttrChecked.value.map(v => v.cvCdPptyId)
    }
    ElMessageBox.confirm('确定删除选中的编码属性吗?', '提示', {
      type: 'warning'
    }).then(() => {
      delBatchCdPptyM(p).then(res => {
        ElMessage.success(res.data.msg)
        getCodeAttrList(editId.value)
        getCodeAttrValueList(editId.value)
      })
    }).catch(() => {})
  }else if(activeName.value === 'second') {
    if(relationAttrChecked.value.length === 0) {
      ElMessage.info('请选择一条要删除的编码编码关系')
      return
    }
    let p = relationAttrChecked.value.map(item=>{
      return {
        hlvCvCdId: item.hlvCvCdId,
        llvCvCdId: item.llvCvCdId
      }
    });
    delCdR(p).then(res => {
      ElMessage.success(res.data.msg)
      getCodeCodeRelationFun(editId.value)
    })
  }else if(activeName.value === 'third') {
    if(columnCheckedData.value.length === 0) {
      ElMessage.info('请选择一条要删除的编码表关系')
      return
    }

    let p = columnCheckedData.value.map(item=>{
      return {
        cvCdId: editId.value,
        cvDbTabId: item.cvDbTabId,
        cvDbmsId: item.cvDbmsId,
        cvSchmId: item.cvSchmId
      }
    });

    delBatchTabCdBndR(p).then(res => {
      ElMessage.success(res.data.msg)
      getCodeTableRelationFun(editId.value)
    })
  }
}

//底部新增
const bottomAdd = () => {
  if(activeName.value === 'first') {
    if(!editId.value) {
      ElMessage.info('请选择编码')
      return
    }
    console.log(codeValueAttr.value);
    let obj = {}
    codeValueAttr.value.forEach(v => {
      obj[v.prop] = ''
    })
    console.log(obj);
    codeValueAttrData.value.push({
      ...obj,
      isEdit: true
    })
    bottomAddSign.value = true
    bottomSaveShow.value = true
  }else if(activeName.value === 'second') {
    if(activeName2.value === 'second') {
      if(relationAttrChecked.value.length !== 1) {
        ElMessage.info('请选择一条编码编码关系')
        return
      }
      let params = {
        codeModal: relationAttrChecked.value[0].llvCvCdId === editId.value ? 1 : 2,
        editId: relationAttrChecked.value[0].hlvCvCdId,
        bottomId: relationAttrChecked.value[0].llvCvCdId,
      }
      console.log(relationAttrChecked.value, params);
      nextDialog(params)
    }else if(activeName2.value === 'third') {
      if(relationAttrChecked.value.length !== 1) {
        ElMessage.info('请选择一条编码编码关系')
        return
      }
      const selected = relationAttrChecked.value[0];
      const editData = sendDatas.value.find(item=>item.cvCdId === editId.value);
      let obj = {
        editId: selected.hierarchy === "上级" ? selected.cvCdId : editId.value,
        editName: selected.hierarchy === "上级" ? selected.cdLgcNm : editData.cdLgcNm,
        bottomId: selected.hierarchy === "下级" ? selected.cvCdId : editId.value,
        bottomName: selected.hierarchy === "下级" ? selected.cdLgcNm : editData.cdLgcNm,
      }
      console.log(selected, editData, obj)
      nextValue(obj)
    }
  }else if(activeName2.value === 'fourth') {
    if(columnCheckedData.value.length !== 1) {
      ElMessage.info('请选择一条编码表关系信息')
      return
    }

    let params = {
      editId: editId.value,
      cvDbmsId: columnCheckedData.value[0].cvDbmsId,
      cvDbTabId: columnCheckedData.value[0].cvDbTabId,
      cvSchmId: columnCheckedData.value[0].cvSchmId,
    }
    pageData.codeAttrObj.visible = true
    pageData.codeAttrObj.params = params
  }
}

//下面表格修改
const bottomEdit = () => {
  if(!editId.value) {
    ElMessage.info('请选择编码')
    return
  }
  if(codeValueCheckedData.value.length !== 1) {
    ElMessage.info('请选择一项编码值')
    return
  }
  console.log(codeValueCheckedData.value);
  bottomSaveShow.value = true
  codeValueCheckedData.value[0].isEdit = true
}

//下面表格保存
const bottomSave = () => {
  if(bottomAddSign.value) {
    const codeValueAttrRaw = toRaw(codeValueAttr.value);
    const obj = toRaw(codeValueAttrData.value.find(v => v.isEdit));
    const objToSave = {
      cvCdId: editId.value,
      values: {}
    };

    let check = false;
    codeValueAttrRaw.forEach(codeValueAttrRawItem => {
      if (Object.hasOwn(obj, codeValueAttrRawItem.prop)) {
        objToSave.values[codeValueAttrRawItem.id] = obj[codeValueAttrRawItem.prop];
        check = true;
      }
    });

    console.log(check, codeValueAttrRaw, obj, objToSave);
    if (check) {
      addCdPtyvM(objToSave).then(res=>{
        ElMessage.success(res.data.msg)
        bottomSaveShow.value = false
        bottomAddSign.value = false
        getCodeAttrValueList(editId.value)
      });
    }
  }else {
    const codeValueAttrRaw = toRaw(codeValueAttr.value);
    const obj = toRaw(codeValueCheckedData.value[0]);
    const objToSave = {
      cvCdvlId: obj.cv_cdvl_id || '',
      values: {}
    };
    let check = false;
    codeValueAttrRaw.forEach(codeValueAttrRawItem => {
      if (Object.hasOwn(obj, codeValueAttrRawItem.prop)) {
        objToSave.values[codeValueAttrRawItem.id] = obj[codeValueAttrRawItem.prop];
        check = true;
      }
    });

    console.log(obj, objToSave);
    if (check) {
      editCdPtyvM(objToSave).then(res=>{
        ElMessage.success(res.data.msg);
        bottomSaveShow.value = false;
        getCodeAttrValueList(editId.value);
      });
    }
  }
}

//下面表格取消
const bottomClose = () => {
  bottomAddSign.value = false
  bottomSaveShow.value = false
  getCodeAttrValueList(editId.value)
}

//下面表格删除
const bottomDelete = () => {
  if(activeName2.value === 'first') {
    if(codeValueCheckedData.value.length === 0) {
      ElMessage.info('请选择要删除的编码值')
      return
    }
    console.log(codeValueCheckedData.value);
    let p = {
      cvCdId: editId.value,
      cvCdvlIdList: codeValueCheckedData.value.map(v => v.cv_cdvl_id)
    }
    ElMessageBox.confirm('确定删除选中的编码值吗?', '提示', {
      type: 'warning'
    }).then(() => {
      delBatchCdvlM(p).then(res => {
        ElMessage.success(res.data.msg)
        getCodeAttrValueList(editId.value)
      })
    }).catch(() => {})
  }else if(activeName2.value === 'second') {
    if(codeAttrRelationChecked.value.length === 0) {
      ElMessage.info('请选择要删除的编码属性关系')
      return
    }
    let p = codeAttrRelationChecked.value.map(item=>{
      return {
        hlvCvCdId: item.hlvCvCdId,
        hlvCvCdPptyId:  item.hlvCvCdPptyId,
        llvCvCdId:  item.llvCvCdId,
        llvCvCdPptyId:  item.llvCvCdPptyId
      };
    });
    delBatchPptyR(p).then(res => {
      ElMessage.success(res.data.msg)
      getCodeCodeAttrRelation(editId.value)
    })
  }else if(activeName2.value === 'third') {
    if(codeValueRelationChecked.value.length === 0) {
      ElMessage.info('请选择要删除的编码值关系')
      return
    }

    let p = codeValueRelationChecked.value.map(item=>{
      return {
        hlvCvCdId: item.hlv_cv_cd_id,
        hlvCvCdvlId: item.hlv_cv_cdvl_id,
        llvCvCdId: item.llv_cv_cd_id,
        llvCvCdvlId: item.llv_cv_cdvl_id,
      }
    });
    delBatchCdvlR(p).then(res => {
      ElMessage.success(res.data.msg)
      getCodeCodeAttrRelation(editId.value)
    })
  }else if(activeName2.value === 'fourth') {
    if(codeColumnChecked.value.length === 0) {
      ElMessage.info('请选择要删除的编码属性列关系')
      return
    }
    let p = codeColumnChecked.value.map(item=>{
      return {
        cvCdId: item.cvCdId,
        cvDbmsId: item.cvDbmsId,
        cvSchmId: item.cvSchmId,
        cvDbTabId: item.cvDbTabId,
        cvCdPptyId: item.cvCdPptyId,
        cvDbFildId: item.cvDbFildId
      };
    });
    delBatchPptyFildBndR(p).then(res => {
      ElMessage.success(res.data.msg)
      getCodeAttrColumnRelation(p[0]).then(res => {
        let d = res.data.data
        codeColumnTableData.value = d
        codeColumnTotal.value = d.length
      })
    })
  }
}

const loadUpper = () => {
  pageData.loadUpperCodeObj.visible = true
}
const searchDialog = (item, val) => {
  console.log(item);
  if(item.prop === 'codeClass' || item.prop === 'cvCdCatId') {
    pageData.codeClassObj.visible = true
  }else if(item.prop === 'belongSystem' || item.prop === 'blngCvSysId') {
    pageData.belongSystemObj.visible = true
  }else if(item.prop === 'upperCodeName' || item.prop === 'hlvCvCdId') {
    //左侧编辑
    pageData.upperCodeObj.visible = true
    pageData.upperCodeObj.type = 'Left'
    pageData.upperCodeObj.sign = 'apply'
  }else if(item.prop === 'upperCodeNameNext') {
    pageData.upperCodeObj.visible = true
    pageData.upperCodeObj.sign = 'next'
    pageData.upperCodeObj.params = val
  }else if(item.prop === 'upperCodeNameRight') {
    //右侧编码属性新增
    pageData.upperCodeObj.visible = true
    pageData.upperCodeObj.type = 'Right'
    pageData.upperCodeObj.sign = 'apply'
    pageData.upperCodeObj.id = editId.value
  }
}
const resetDialog = (val) => {
  switch(val) {
    case 'belongSystem':
      pageData.belongSystemObj.visible = false
      break
    case 'upperCode':
      pageData.upperCodeObj.visible = false
      getCodeCodeRelation({ cvCdId: editId.value }).then(res => {
        console.log(res);
        let d = res.data.data
        relationTableData.value = d
        relationTotal.value = d.length
        getCodeCodeAttrRelation(editId.value)
      })
      break
    case 'loadUpper':
      pageData.loadUpperCodeObj.visible = false
      break
    case 'codeRelation':
      pageData.codeRelationObj.visible = false
      getCodeAttributeRelation({ cvCdId: editId.value }).then(res => {
        relationAttrTableData.value = res.data.data
        relationAttrTotal.value = res.data.data.length
      })
      break
    case 'codeValue':
      pageData.codeValueObj.visible = false
        console.log(pageData.codeValueObj)
      handleCodeClickRow({
          hlvCvCdId: pageData.codeValueObj.editId,
          llvCvCdId: pageData.codeValueObj.bottomId
      });
      break
    case 'codeAttr':
      pageData.codeAttrObj.visible = false
        console.log(codeColumnChecked.value, pageData.codeAttrObj);
      handleTableClickRow({
        cvDbTabId: pageData.codeAttrObj.params.cvDbTabId,
        cvDbmsId: pageData.codeAttrObj.params.cvDbmsId,
        cvSchmId: pageData.codeAttrObj.params.cvSchmId
      });
      break

    case 'code-table':
      pageData.codeTableObj.visible = false;
      getCodeTableRelationFun(editId.value);
      break;

    default:
      break;
  }
}
const nextDialog = (params) => {
  pageData.upperCodeObj.visible = false;
  pageData.codeRelationObj.visible = true
  pageData.codeRelationObj.editId = params.editId
  pageData.codeRelationObj.bottomId = params.bottomId
  pageData.codeRelationObj.codeModal = params.codeModal
}
const nextValue = (params) => {
  console.log(params)
  pageData.codeRelationObj.visible = false
  pageData.codeValueObj.visible = true
  pageData.codeValueObj.editId = params.editId
  pageData.codeValueObj.bottomId = params.bottomId
  pageData.codeValueObj.editName = params.editName
  pageData.codeValueObj.bottomName = params.bottomName
}
//编码属性的change事件
const handleCodeAttrChange = (val) => {
  codeAttrChecked.value = val
}
//编码属性关系的change事件
const handleCodeAttrRelationChange = (val) => {
  codeAttrRelationChecked.value = val
}
//编码值关系的change事件
const handleCodeValueRelationChange = (val) => {
  console.log(val)
  codeValueRelationChecked.value = val
}
//编码属性列关系的change事件
const handleCodeColumnChange = (val) => {
  codeColumnChecked.value = val
}
//编码编码关系的change事件
const handleRelationAttrChange = (val) => {
  console.log(val);
  relationAttrChecked.value = val
}
//编码表关系的change事件
const handleColumnAttrChange = (val) => {
  console.log(val);
  columnCheckedData.value = val
}
//编码值的change事件
const handleCodeValueChange = (val) => {
  codeValueCheckedData.value = val
}

</script>
<style scoped lang="less">
  /deep/.el-tabs--border-card>.el-tabs__header .el-tabs__item {
    background-color: #262673;
    color: #fff;
    margin-right: 5px;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
    font-weight: bold;
    border-top: 1px solid #dcdfe6;
    &.is-active {
      color: #409eff;
      background-color: #fff;
      border-right-color: #dcdfe6;
      border-left-color: #dcdfe6;
    }
  }
</style>
