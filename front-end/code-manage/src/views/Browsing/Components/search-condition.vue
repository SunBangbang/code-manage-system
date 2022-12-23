<template>
  <div class="coding-con">
    <div class="coding-search">
      <el-form :model="form" label-width="120px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="编码区:">
              <el-select v-model="form.cdSphrNm" clearable>
                <el-option v-for="item in sphrList"
                           :label="item.cdSphrNm"
                           :value="item.cdSphrNm"
                           :key="item.cvCdSphrId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="查询:">
              <el-select v-model="isSearchValue" @change="changeSearchValue" clearable>
                <el-option v-for="item in searchType"
                           :label="item.label"
                           :value="item.value"
                           :key="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-input v-model="searchTypeValue" placeholder="请输入" v-if="isSearchValue" @blur="blurSearchValue"></el-input>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-button type="primary" @click="reset">重置搜索条件</el-button>
            <el-button type="primary" @click="changeHighSearch">高级搜索条件 折叠</el-button>
            <el-button type="primary" @click="search">搜索</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <transition name="draw">
      <div class="conding-highSearch" v-show="highSearchShow">
        <el-form :model="highForm" label-width="120px">
          <el-row>
            <el-col :span="8">
              <el-form-item label="编码分类:">
                <el-select v-model="highForm.cdCatNm" clearable>
                  <el-option v-for="item in catList"
                             :label="item.cdCatNm"
                             :value="item.cdCatNm"
                             :key="item.cvCdCatId"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="隶属系统:">
                <el-select v-model="highForm.blngCvSysId" clearable>
                  <el-option v-for="item in sysList"
                             :label="item.sysNm"
                             :value="item.cvSysId"
                             :key="item.cvSysId"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="编码类型编码:">
                <el-select v-model="highForm.cvCdTpCd" clearable>
                  <el-option v-for="item in cdTpCdList"
                             :label="item.cdvlNm"
                             :value="item.cdvlNm"
                             :key="item.cdvlNm"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="是否保存编码值:">
                <el-select v-model="highForm.cdvlSaveYn" clearable>
                  <el-option value="Y">是</el-option>
                  <el-option value="N">否</el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="数据类型:">
                <el-select v-model="highForm.dataTpNm" clearable>
                  <el-option v-for="item in dataTpList"
                             :label="item.cdvlNm"
                             :value="item.cdvlNm"
                             :key="item.cdvlNm"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="编码状态编码:">
<!--                <el-select v-model="highForm.cvCdStCd">-->
<!--                  <el-option v-for="item in codeState"-->
<!--                             :label="item.label"-->
<!--                             :value="item.label"-->
<!--                             :key="item.label"-->
<!--                  ></el-option>-->
<!--                </el-select>-->
                <el-select v-model="highForm.cvCdStCd" clearable>
                  <el-option v-for="item in cdStCdList"
                             :label="item.cdvlNm"
                             :value="item.cdvlNm"
                             :key="item.cdvlNm"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <!--            <el-row>-->
          <!--              <el-col :span="8">-->
          <!--                <el-form-item label="更改类型编码:">-->
          <!--                  <el-select v-model="highForm.cvCdTpCd">-->
          <!--                    <el-option v-for="item in changeTypeCode"-->
          <!--                               :label="item.label"-->
          <!--                               :value="item.label"-->
          <!--                               :key="item.label"-->
          <!--                    ></el-option>-->
          <!--                  </el-select>-->
          <!--                </el-form-item>-->
          <!--              </el-col>-->
          <!--            </el-row>-->
        </el-form>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { reactive, toRefs, watch, onMounted } from 'vue'
import { getCatMList, getSphrMList, getSysMList, getCdStCdList, getCdTpCdList, getDataTpList } from '@/api'
const data = reactive({
  codeValue: '',
  form: {
    cdSphrNm: '', //编码区
    cdLgcNm: '',  //编码逻辑名称
    cdPhysNm: '',  //编码物理名称
    cdExplTxt: '',  //编码说明
    cvCdId: '',  //编码ID
  },
  highForm: {
    cdCatNm: '', //编码分类
    blngCvSysId: '', //所属系统
    cvCdTpCd: '', //编码类型编码
    cdvlSaveYn: '', //是否保存编码值
    dataTpNm: '', //数据类型
    cvCdStCd: '', //编码状态编码
  },
  searchType: [
    {
      label: '编码逻辑名称',
      value: 'cdLgcNm'
    },{
      label: '编码物理名称',
      value: 'cdPhysNm'
    },{
      label: '编码说明',
      value: 'cdExplTxt'
    },{
      label: '编码ID',
      value: 'cvCdId'
    }
  ],
  codeState: [
    {
      label: '元核准入'
    },{
      label: '临时储存'
    },{
      label: '申请编码'
    },{
      label: '批准编码'
    }
  ],
  sphrList: [],
  catList: [],
  sysList: [],
  cdStCdList: [],
  cdTpCdList: [],
  dataTpList: [],
  highSearchShow: true,
  isSearchValue: '',
  searchTypeValue: '',
})
const { codeValue, form, isSearchValue, searchTypeValue, sysList, cdStCdList, cdTpCdList, dataTpList,
  highForm, catList, sphrList, searchType, codeState, highSearchShow } = toRefs(data)

const props = defineProps({
  count: Number
})
watch(() => props.count, val => {
  reset()
})
const emits = defineEmits(['changeShow', 'searchTable', 'transSphrList'])
const reset = () => {
  for(let key in form.value) {
    form.value[key] = ''
  }
  for(let key in highForm.value) {
    highForm.value[key] = ''
  }
  search()
}
const changeHighSearch = () => {
  highSearchShow.value = !highSearchShow.value
  emits('changeShow', highSearchShow.value)
}
const search = () => {
  emits('searchTable', form.value, highForm.value)
}

const blurSearchValue = () => {
  form.value[isSearchValue.value] = searchTypeValue.value
  console.log(form.value);
  let arr = searchType.value.filter(v => v.value !== isSearchValue.value)
  arr.forEach(v => {
    form.value[v.value] = ''
  })
}

const changeSearchValue = (val) => {
  searchTypeValue.value = ''
  console.log(val);
  if(!val) {
    searchType.value.forEach(v => {
      form.value[v.value] = ''
    })
  }
}

const getSphrLists = () => getSphrMList().then(res => {
  sphrList.value = res.data.data
  emits('transSphrList', sphrList.value)
})
const getCatLists = () => getCatMList().then(res => {
  catList.value = res.data.data
})
const getSysLists = () => getSysMList().then(res => {
  sysList.value = res.data.data
})
const getCdStCdLists = () => getCdStCdList().then(res => {
  cdStCdList.value = res.data.data
})
const getCdTpCdLists = () => getCdTpCdList().then(res => {
  cdTpCdList.value = res.data.data
})
const getDataTpLists = () => getDataTpList().then(res => {
  dataTpList.value = res.data.data
})
onMounted(() => {
  getSphrLists()
  getCatLists()
  getSysLists()
  getCdStCdLists()
  getCdTpCdLists()
  getDataTpLists()
})
</script>
