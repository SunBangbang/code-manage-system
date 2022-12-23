<template>
  <div class="double-coding">
    <div class="left-coding coding">
      <div class="coding-button">
        <span>编码区</span>
        <div class="coding-icon">
          <el-icon title="添加角色" @click="handleIconClick('add')"><Plus /></el-icon>
          <el-icon title="修改角色" @click="handleIconClick('edit')"><EditPen /></el-icon>
          <el-icon title="删除角色" @click="handleIconClick('delete')"><Delete /></el-icon>
        </div>
<!--        <el-button type="primary" @click="addCode">新增</el-button>-->
      </div>
      <div class="coding-border">
<!--        <general-table :tableNav="tableNav1"-->
<!--                       :tableOpera="tableOpera"-->
<!--                       :tableData="tableData1"-->
<!--                       :total="total1"-->
<!--        ></general-table>-->
<!--        @row-click="handleRowClick"-->
<!--        @selection-change="handleSelectionChange" @select="handleSelect"-->
        <el-table :data="tableData1" ref="leftTable" v-loading="loading1" class="public-color" border @selection-change="handleSelectionChange1">
          <el-table-column type="selection" width="60px" align="center"></el-table-column>
          <el-table-column label="编码区名称" prop="cdSphrNm" show-overflow-tooltip></el-table-column>
          <el-table-column label="编码区说明" prop="cdSphrExplTxt" show-overflow-tooltip></el-table-column>
        </el-table>

        <el-pagination layout="total"
                       :total="total1">
        </el-pagination>
      </div>
    </div>
    <div class="right-coding coding">
      <div class="coding-button">
        <span>编码分类</span>
        <div class="coding-icon">
          <el-icon title="添加角色" @click="handleIconClick('add1')"><Plus /></el-icon>
          <el-icon title="修改角色" @click="handleIconClick('edit1')"><EditPen /></el-icon>
          <el-icon title="删除角色" @click="handleIconClick('delete1')"><Delete /></el-icon>
        </div>
      </div>
      <div class="coding-border">
<!--        <general-table :tableNav="tableNav2"-->
<!--                       :tableOpera="tableOpera"-->
<!--                       :tableData="tableData2"-->
<!--                       :total="total2"-->
<!--        ></general-table>-->
<!--        @row-click="handleRowClick"-->
<!--        @selection-change="handleSelectionChange" @select="handleSelect"-->
        <el-table :data="tableData2" ref="leftTable" v-loading="loading2" class="public-color" border @selection-change="handleSelectionChange2">
          <el-table-column type="selection" width="60px" align="center"></el-table-column>
          <!--          <el-table-column label="序号" type="index" width="60px" align="center"></el-table-column>-->
          <el-table-column label="编码分类名称" prop="cdCatNm" show-overflow-tooltip></el-table-column>
          <el-table-column label="编码分类说明" prop="cdCatExplTxt" show-overflow-tooltip></el-table-column>
        </el-table>

        <el-pagination layout="total"
                       :total="total2">
        </el-pagination>
      </div>
    </div>

    <el-dialog
        v-model="dialogVisible1"
        :title="title1"
        @close="onClose1"
        width="500px"
        :close-on-click-modal="false"
    >
      <el-form :model="form1" ref="formRef1" label-width="130px" :rules="rules">
        <el-form-item label="上层编码区名称:" prop="hlvCdSphrNm">
          <el-input v-model="form1.hlvCdSphrNm" readonly placeholder="请输入">
            <template #append>
              <el-button :icon="Search" class="append-button" @click="handleIconClick1(true)"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="编码区名称:" prop="cdSphrNm">
          <el-input v-model="form1.cdSphrNm" placeholder="请输入"></el-input>
        </el-form-item>
        <div class="border"></div>
        <el-form-item label="说明:" prop="cdSphrExplTxt">
          <el-input v-model="form1.cdSphrExplTxt" type="textarea" placeholder="请输入"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="onSuccess1">确定</el-button>
        <el-button @click="onClose1">取消</el-button>
      </span>
      </template>
    </el-dialog>

    <el-dialog
        v-model="dialogVisible2"
        :title="title2"
        @close="onClose2"
        width="500px"
        :close-on-click-modal="false"
    >
      <el-form :model="form2" ref="formRef2" label-width="130px" :rules="rules">
        <el-form-item label="编码区" prop="cvCdSphrId">
          <el-select v-model="form2.cvCdSphrId">
            <el-option v-for="item in tableData1"
                       :label="item.cdSphrNm"
                       :value="item.cvCdSphrId"
                       :key="item.cvCdSphrId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上层编码分类名称:" prop="hlvCvCdCatNm">
          <el-input v-model="form2.hlvCvCdCatNm" readonly placeholder="请输入">
            <template #append>
              <el-button :icon="Search" class="append-button" @click="handleIconClick2(true)"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="编码分类名称:" prop="cdCatNm">
          <el-input v-model="form2.cdCatNm" placeholder="请输入"></el-input>
        </el-form-item>
        <div class="border"></div>
        <el-form-item label="说明:" prop="cdCatExplTxt">
          <el-input v-model="form2.cdCatExplTxt" type="textarea" placeholder="请输入"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="onSuccess2">确定</el-button>
        <el-button @click="onClose2">取消</el-button>
      </span>
      </template>
    </el-dialog>

    <CodeClassify :visibleObj="codeClassObj" @success="getUpperData2" @close="handleIconClick2(false)"></CodeClassify>

    <CodeSearch :visibleObj="searchClassObj" @success="getUpperData" @close="handleIconClick1(false)"></CodeSearch>
  </div>
</template>

<script setup>
import { reactive, toRefs, onMounted } from 'vue'
import { Search } from '@element-plus/icons-vue'
import CodeClassify from '@/components/dialogs/code-classify'
import CodeSearch from '@/components/dialogs/code-search'
import {
  getSphrMList,
  addSphrM,
  delSphrMById,
  getSphrMById,
  editSphrM,

  getCatMList,
  delCatM,
  addCatM,
  editCatM,
  getCatMById
} from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'
const data = reactive({
  tableData1: [],
  tableData2: [],
  currentAreaId: '',
  dialogVisible1: false,
  dialogVisible2: false,
  title1: '',
  title2: '',
  total1: 0,
  total2: 0,
  loading1: true,
  loading2: true,
  formRef1: '',
  formRef2: '',
  form1: {
    hlvCdSphrNm: '',
    hlvCvCdSphrId: '',
    cdSphrNm: '',
    cdSphrExplTxt: ''
  },
  form2: {
    cdCatExplTxt: '',
    cdCatNm: '',
    cvCdSphrId: '',
    hlvCvCdCatId: '',
    hlvCvCdCatNm: ''
  },
  codeClassObj: {
    visible: false
  },
  searchClassObj: {
    visible: false
  },
  rules: {
    // hlvCdSphrNm: [{ required: true, message: '请选择上层编码区名称', trigger: 'blur' }],
    cdSphrNm: [{ required: true, message: '请输入编码区名称', trigger: 'blur' }],
    cdCatNm: [{ required: true, message: '请输入编码分类名称', trigger: 'blur' }],
    cvCdSphrId: [{ required: true, message: '请选择编码区', trigger: 'change' }],
  },
  checkedData1: [],
  checkedData2: [],
  form1Id: '',
  form2Id: ''
})
const { tableData1, dialogVisible1, dialogVisible2, currentAreaId, checkedData1, checkedData2,
  tableData2, title1, title2, form1, form2, codeClassObj, loading1, loading2, form1Id, form2Id,
  searchClassObj, total1, total2, rules, formRef1, formRef2 } = toRefs(data)

onMounted(() => {
  getTableData1()
  getTableData2()
})

const getTableData1 = () => getSphrMList().then(res => {
  let d = res.data.data
  tableData1.value = d
  total1.value = d.length
  loading1.value = false
})
const getTableData2 = () => getCatMList().then(res => {
  let d = res.data.data
  tableData2.value = d
  total2.value = d.length
  loading2.value = false
})
const handleSelectionChange1 = (val) => {
  checkedData1.value = val
}
const handleSelectionChange2 = (val) => {
  checkedData2.value = val
}

const handleIconClick = (val) => {
  switch(val) {
    case 'add':
      dialogVisible1.value = true
      title1.value = '新增编码区'
      form1Id.value = ''
      break
    case 'add1':
      dialogVisible2.value = true
      title2.value = '新增编码分类'
      form2Id.value = ''
      break
    case 'edit':
      if(checkedData1.value.length !== 1) {
        ElMessage.info('请选择一项编码区')
        return
      }
      dialogVisible1.value = true
      title1.value = '修改编码区'
      console.log(checkedData1.value);
      form1Id.value = checkedData1.value[0].cvCdSphrId
      getSphrMById({ id: form1Id.value }).then(res => {
        console.log(res);
        let d = res.data.data
        for(let key in form1.value) {
          for(let key1 in d) {
            if(key === key1) {
              form1.value[key] = d[key]
            }
          }
        }
        if(form1.value.hlvCvCdSphrId === '0') form1.value.hlvCvCdSphrId = ''
      })
      break
    case 'edit1':
      if(checkedData2.value.length !== 1) {
        ElMessage.info('请选择一项编码分类')
        return
      }
      dialogVisible2.value = true
      title2.value = '修改编码区'
      console.log(checkedData2.value);
      form2Id.value = checkedData2.value[0].cvCdCatId
      getCatMById({ id: form2Id.value }).then(res => {
        console.log(res);
        let d = res.data.data
        for(let key in form2.value) {
          for(let key1 in d) {
            if(key === key1) {
              form2.value[key] = d[key]
            }
          }
        }
        if(form2.value.hlvCvCdCatId === '0') form2.value.hlvCvCdCatId = ''
      })
      break
    case 'delete':
      if(checkedData1.value.length === 0) {
        ElMessage.info('请选择编码区')
        return
      }
      ElMessageBox.confirm('确定删除选中的编码区吗?', '提示', {
        type: 'warning'
      }).then(() => {
        checkedData1.value.forEach(v => {
          delSphrMById({ id: v.cvCdSphrId }).then(res => {
            ElMessage.success(res.data.msg)
            getTableData1()
          })
        })
      }).catch(() => {})
      break
    case 'delete1':
      if(checkedData2.value.length !== 1) {
        ElMessage.info('请选择一项编码分类')
        return
      }
      ElMessageBox.confirm('确定删除选中的编码分类吗?', '提示', {
        type: 'warning'
      }).then(() => {
        checkedData2.value.forEach(v => {
          delCatM({ id: v.cvCdCatId }).then(res => {
            ElMessage.success(res.data.msg)
            getTableData2()
          })
        })
      }).catch(() => {})
      break
    default:
  }
}
const onClose1 = () => {
  formRef1.value.resetFields()
  form1Id.value = ''
  dialogVisible1.value = false
}
const onClose2 = () => {
  formRef2.value.resetFields()
  form2Id.value = ''
  dialogVisible2.value = false
}
const onSuccess1 = async () => {
  console.log(form1.value);
  await formRef1.value.validate()
  if(form1.value.hlvCvCdSphrId === '') form1.value.hlvCvCdSphrId = '0'
  if(form1Id.value) {
    editSphrM({ ...form1.value, cvCdSphrId: form1Id.value }).then(res => {
      ElMessage.success(res.data.msg)
      getTableData1()
      onClose1()
    })
  }else {
    addSphrM(form1.value).then(res => {
      console.log(res);
      ElMessage.success(res.data.msg)
      getTableData1()
      onClose1()
    })
  }
}
const onSuccess2 = async() => {
  console.log(form2.value);
  await formRef2.value.validate()
  if(form2.value.hlvCvCdCatId === '') form2.value.hlvCvCdCatId = '0'
  if(form2Id.value) {
    editCatM({ ...form2.value, cvCdCatId: form2Id.value }).then(res => {
      ElMessage.success(res.data.msg)
      getTableData2()
      onClose2()
    })
  }else {
    addCatM(form2.value).then(res => {
      console.log(res);
      ElMessage.success(res.data.msg)
      getTableData2()
      onClose2()
    })
  }
}
const getUpperData = (val) => {
  console.log(val);
  form1.value.hlvCvCdSphrId = val.cvCdSphrId
  form1.value.hlvCdSphrNm = val.cdSphrNm
  handleIconClick1(false)
}
const getUpperData2 = (val) => {
  console.log(val);
  form2.value.hlvCvCdCatId = val.cvCdCatId
  form2.value.hlvCvCdCatNm = val.cdCatNm
  handleIconClick2(false)
}
const handleIconClick1 = (val) => {
  searchClassObj.value.visible = val
}
const handleIconClick2 = (val) => {
  codeClassObj.value.visible = val
}

</script>
