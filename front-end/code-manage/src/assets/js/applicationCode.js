import { reactive } from 'vue'
// export const inputState = reactive([
//   {
//     label: 'Meta批准'
//   },{
//     label: '临时保存'
//   },{
//     label: '编码申请'
//   },{
//     label: '编码驳回'
//   },{
//     label: '编码批准'
//   }
// ])
// export const inputType = reactive([
//   {
//     label: 'CHAR'
//   },{
//     label: 'NCHAR'
//   },{
//     label: 'VARCHAR'
//   },{
//     label: 'NVARCHAR'
//   },{
//     label: 'SMALLINT'
//   },{
//     label: 'INTEGER'
//   },{
//     label: 'BIGINT'
//   },{
//     label: 'NUMERIC'
//   }
// ])
export const formList = reactive([
  // {
  //   label: '更改类型',
  //   type: 'input',
  //   value: '',
  //   prop: 'changeType'
  // },
  {
    label: '编码分类',
    type: 'input-search',
    value: '',
    prop: 'cvCdCatId',
    propDisplay: 'cdCatNm'
  },{
    label: '逻辑名称',
    type: 'input',
    value: '',
    prop: 'cdLgcNm'
  },{
    label: '物理名称',
    type: 'input',
    value: '',
    prop: 'cdPhysNm'
  },{
    label: '数据类型',
    type: 'get-select',
    value: '',
    prop: 'dataTpNm'
  },{
    label: '长度',
    type: 'input',
    value: '',
    prop: 'dataLen'
  },{
    label: '编码状态',
    type: 'input-state',
    value: '',
    prop: 'cvCdStCd'
  },{
    label: '编码类型',
    type: 'input-type',
    value: '',
    prop: 'cvCdTpCd'
  },{
    label: '是否保存编码值',
    type: 'special-select',
    value: '',
    prop: 'cdvlSaveYn'
  },{
    label: '参考值',
    type: 'input',
    value: '',
    prop: 'cvRefId'
  },{
    label: '隶属系统',
    type: 'input-search',
    value: '',
    prop: 'blngCvSysId',
    propDisplay: 'blngCvSysNm'
  },{
    label: '上层编码名',
    type: 'input-search',
    value: '',
    prop: 'hlvCvCdId',
    propDisplay: 'hlvCdLgcNm',
  },{
    label: '编码说明',
    type: 'textarea',
    value: '',
    prop: 'cdExplTxt'
  }
])

export const tableData = [
  // {
  //   changeType: '新建',
  //   codeClass: '1',
  //   luojiName: '2',
  //   wuliName: '3'
  // },{
  //   changeType: '新建',
  //   codeClass: '2',
  //   luojiName: '2',
  //   wuliName: '4'
  // }
]

export const topTableNav = [
  {
    label: '编码名称',
    prop: 'cdLgcNm'
  },{
    label: '属性序号',
    prop: 'cvSortSrno'
  },{
    label: '编码属性名称',
    prop: 'cdPptyNm'
  },{
    label: 'PK与否',
    prop: 'pkYn'
  },{
    label: 'NN与否',
    prop: 'nullYn'
  },{
    label: '数据类型',
    prop: 'dataTpNm'
  },{
    label: '长度',
    prop: 'dataLen'
  },{
    label: '历史起始日期',
    prop: 'recBgnDttm'
  },{
    label: '创建用户ID',
    prop: 'cretCvUserId'
  }
]

export const topRelationTableNav = [
  {
    label: '上下层分类',
    prop: 'hierarchy'
  },{
    label: '编码分类',
    prop: 'cdCatNm'
  },{
    label: '逻辑名称',
    prop: 'cdLgcNm'
  },{
    label: '物理名称',
    prop: 'cdPhysNm'
  },{
    label: '数据类型',
    prop: 'dataTpNm'
  },{
    label: '长度',
    prop: 'dataLen'
  },{
    label: '编码状态',
    prop: 'cvCdStCd'
  },{
    label: '编码类型',
    prop: 'cvCdTpCd'
  },{
    label: '是否保存编码值',
    prop: 'cdvlSaveYn'
  },{
    label: '所属系统',
    prop: 'blngCvSysNm'
  },{
    label: '上层编码名称',
    prop: 'hlvCdLgcNm'
  },{
    label: '说明',
    prop: 'cdExplTxt'
  }
]

export const topTableTableNav = [
  {
    label: 'DBMS',
    prop: 'cvDbmsId'
  },{
    label: '模式(Schema)',
    prop: 'cvDbmsId'
  },{
    label: '模式名称(Schema)名称',
    prop: 'schmNm'
  },{
    label: 'Entity名称',
    prop: 'dbTabNm'
  },{
    label: '表名称',
    prop: 'cvDbTabId'
  }
]

export const topTableData = [
  // {
  //   name: '测试',
  //   attrIndex: 1,
  //   codeName: '测试',
  //   isPK: 'Y',
  //   isNN: 'N',
  //   dataType: 'string',
  //   length: 5,
  //   startDate: '2020-02-02',
  //   createUser: '123'
  // },{
  //   name: '测试2',
  //   attrIndex: 2,
  //   codeName: '测试2',
  //   isPK: 'Y',
  //   isNN: 'N',
  //   dataType: 'string',
  //   length: 9,
  //   startDate: '2020-02-02',
  //   createUser: '234'
  // }
]

export const bottomTableNav = [
  {
    label: '更改类型',
    prop: 'changeType'
  },{
    label: '编码值序列号',
    prop: 'attrIndex'
  },{
    label: '编码值',
    prop: 'codeValue'
  },{
    label: '编码值名称',
    prop: 'codeName'
  },{
    label: '说明',
    prop: 'explain'
  },{
    label: '历史起始日期',
    prop: 'startDate'
  },{
    label: '创建用户ID',
    prop: 'createUser'
  }
]

export const bottomTableData = [
  // {
  //   changeType: '新建',
  //   attrIndex: 1,
  //   codeValue: 'T',
  //   codeName: '测试',
  //   explain: '未知',
  //   startDate: '2020-02-02',
  //   createUser: '123'
  // },{
  //   changeType: '新建2',
  //   attrIndex: 2,
  //   codeValue: 'T2',
  //   codeName: '测试2',
  //   explain: '未知',
  //   startDate: '2020-02-02',
  //   createUser: '234'
  // }
]

export const upperTableNav = [
  {
    label: '编码区域',
    prop: 'cdSphrNm'
  },{
    label: '编码分类',
    prop: 'cdCatNm'
  },{
    label: '编码逻辑名称',
    prop: 'cdLgcNm'
  },{
    label: '编码物理名称',
    prop: 'cdPhysNm'
  },{
    label: '编码类型编码',
    prop: 'cvCdTpCd'
  },{
    label: '数据类型',
    prop: 'dataTpNm'
  },{
    label: '长度',
    prop: 'dataLen'
  },{
    label: '参考值',
    prop: 'cvRefId'
  },{
    label: '上层编码名',
    prop: 'hlvCdLgcNm'
  },{
    label: '编码说明',
    prop: 'cdExplTxt'
  },{
    label: '编码状态编码',
    prop: 'cvCdStCd'
  },{
    label: '是否保存编码值',
    prop: 'cdvlSaveYn'
  },{
    label: '所属系统',
    prop: 'blngCvSysNm'
  },{
    label: '生成用户',
    prop: 'cretCvUserNm'
  }
]
