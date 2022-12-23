import { get, post } from '@/api/http'

/*登录*/
//登录
export function login(data) {
  return post('/login', data)
}
//退出
export function logout(params) {
  return get('/logout', params)
}
//获取登录用户
export function getLoginUser(params) {
  return get('/getLoginUser', params)
}
//获取用户的菜单
export function getUserMenu(params) {
  return get('/system/userBasiM/getUserMenu', params)
}


/*用户管理*/
//查询用户列表
export function getUserBasiMList(data) {
  return post('/system/userBasiM/getUserBasiMList', data)
}
//新增用户
export function addUserBasiM(data) {
  return post('/system/userBasiM/addUserBasiM', data)
}
//删除用户
export function delUser(params) {
  return get('/system/userBasiM/delUser', params)
}
//编辑用户
export function editUserBasiM(data) {
  return post('/system/userBasiM/editUserBasiM', data)
}
//保存用户绑定角色
export function addUserRole(data) {
  return post('/system/userBasiM/addUserRole', data)
}
//解锁用户
export function unlockUser(params) {
  return get('/system/userBasiM/unlockUser', params)
}
//查询用户详情
export function getUserBasiMById(id, params) {
  return get(`/system/userBasiM/getUserBasiMById/${id}`, params)
}
//校验员工编号是否唯一
export function checkLgnId(id, params) {
  return get(`/system/userBasiM/checkLgnId/${id}`, params)
}
//校验用户ID是否唯一
export function checkUserId(id, params) {
  return get(`/system/userBasiM/checkUserId/${id}`, params)
}
//查询用户已关联的角色
export function getRoleByUserId(params) {
  return get(`/system/Role/getRoleByUserId`, params)
}
//查询所有角色
export function getAllRole(params) {
  return get(`/system/Role/getAllRole`, params)
}

/*角色管理*/
//查询角色下的用户
export function getUserPageByRole(data) {
  return post(`/system/Role/getUserPageByRole`, data)
}
//校验角色是否重复
export function checkRoleName(params) {
  return get(`/system/Role/checkRoleName`, params)
}
//删除角色
export function delRole(params) {
  return get(`/system/Role/delRole`, params)
}
//查询角色详情
export function getRoleById(params) {
  return get(`/system/Role/getRoleById`, params)
}
//编辑角色
export function editRole(data) {
  return post(`/system/Role/editRole`, data)
}
//新增角色
export function addRole(data) {
  return post(`/system/Role/addRole`, data)
}
//保存角色新增的用户
export function addRoleUserRelation(data) {
  return post(`/system/Role/addRoleUserRelation`, data)
}
//查询角色的菜单权限
export function getAuthObjTreeByRoleId(params) {
  return get(`/system/Role/getAuthObjTreeByRoleId`, params)
}
//查询角色的菜单权限
export function addRoleAuthRelation(data) {
  return post(`/system/Role/addRoleAuthRelation`, data)
}
//添加角色关联的用户
export function addBatchRoleUserRelation(data) {
  return post(`/system/Role/addBatchRoleUserRelation`, data)
}
//删除角色关联的用户
export function delBatchRoleUserRelation(data) {
  return post(`/system/Role/delBatchRoleUserRelation`, data)
}

/*部门管理*/
/*获取部门树*/
export function getDeptTree(data) {
  return post('/system/dept/getDeptTree', data)
}
/*查询部门下的用户*/
export function getUserListByDept(data) {
  return post('/system/dept/getUserListByDept', data)
}
/*添加部门*/
export function addDept(data) {
  return post('/system/dept/addDept', data)
}
/*编辑部门*/
export function editDept(data) {
  return post('/system/dept/editDept', data)
}
/*删除部门*/
export function delDept(params) {
  return get('/system/dept/delDept', params)
}
/*查询部门详情*/
export function getDeptById(params) {
  return get('/system/dept/getDeptById', params)
}


/*菜单管理*/
/*获取所有文件夹树*/
export function getAuthFolderTree(params) {
  return get('/system/authObjBasiM/getAuthFolderTree', params)
}
/*获取所有菜单树*/
export function getAuthObjTree(params) {
  return get('/system/authObjBasiM/getAuthObjTree', params)
}
/*验证屏幕id是否重复*/
export function checkUiId(params) {
  return get(`/system/authObjBasiM/checkUiId/`, params)
}
/*验证菜单名称是否重复*/
export function checkAuthName(params) {
  return get(`/system/authObjBasiM/checkAuthName/`, params)
}
/*删除菜单*/
export function delAuthObj(params) {
  return get('/system/authObjBasiM/delAuthObj', params)
}
/*查询菜单详情*/
export function getAuthObjById(params) {
  return get('/system/authObjBasiM/getAuthObjById/', params)
}
/*添加菜单*/
export function addAuthObj(data) {
  return post('/system/authObjBasiM/addAuthObj', data)
}
/*修改菜单*/
export function editAuthObj(data) {
  return post('/system/authObjBasiM/editAuthObj', data)
}

/*应用程序管理*/
/*查询列表*/
export function getSysList(data) {
  return post('/system/sysM/getSysList', data)
}
/*删除系统*/
export function delSysM(params) {
  return get('/system/sysM/delSysM', params)
}
/*校验系统名称*/
export function checkSysNm(params) {
  return get('/system/sysM/checkSysNm', params)
}
/*获取序号*/
export function getSortSrnoSys(params) {
  return get('/system/sysM/getSortSrno', params)
}
/*新增系统*/
export function addSysM(data) {
  return post('/system/sysM/addSysM', data)
}
/*编辑系统*/
export function editSysM(data) {
  return post('/system/sysM/editSysM', data)
}
/*查询详情*/
export function getSysMVoById(data) {
  return post('/system/sysM/getSysMVoById', data)
}
/*查询详情*/
export function getSysMList(data) {
  return post('/system/sysM/getSysMList', data)
}

/*DBMS管理*/
//查询可用的DBMS
export function getDbmsList(params) {
  return get('/system/dbmsM/getDbmsList', params)
}
//查询DBMS树
export function getDbmsTree(data) {
  return post('/system/dbmsM/getDbmsTree', data)
}
//添加dbms
export function addDbms(data) {
  return post('/system/dbmsM/addDbms', data)
}
//编辑dbms
export function editDbms(data) {
  return post('/system/dbmsM/editDbms', data)
}
//获取排序序号
export function getSortSrno(params) {
  return get('/system/dbmsM/getSortSrno', params)
}
//检验dbms是否重复
export function checkDdmsNm(params) {
  return get('/system/dbmsM/checkDdmsNm', params)
}
//删除dbms
export function delDbms(params) {
  return get('/system/dbmsM/delDbms', params)
}
//查dbms详情
export function getDbmsById(params) {
  return get('/system/dbmsM/getDbmsById', params)
}


//添加模式
export function addSchm(data) {
  return post('/system/schmM/addSchm', data)
}
//校验模式名称
export function checkSchmNm(data) {
  return post('/system/schmM/checkSchmNm', data)
}
//删除模式
export function delSchm(data) {
  return post('/system/schmM/delSchm', data)
}
//编辑模式
export function editSchm(data) {
  return post('/system/schmM/editSchm', data)
}
//查详情---查父级name
export function getSchmEditById(data) {
  return post('/system/schmM/getSchmEditById', data)
}
//根据id查详情
export function getSchmById(data) {
  return post('/system/schmM/getSchmById', data)
}
//根据dbmsId查询可用的模式
export function getSchmByCondition(data) {
  return post('/system/schmM/getSchmByCondition', data)
}
//根据dbmsId查询模式
export function getSchmByDbmsId(params) {
  return get('/system/schmM/getSchmByDbmsId', params)
}
//查询模式排序
export function getSortSrnoSchm(params) {
  return get('/system/schmM/getSortSrno', params)
}


//添加表
export function addTab(data) {
  return post('/system/dbTabM/addTab', data)
}
//修改表
export function editTab(data) {
  return post('/system/dbTabM/editTab', data)
}
//校验表名
export function checkTabNm(data) {
  return post('/system/dbTabM/checkTabNm', data)
}
//删除表
export function delTab(data) {
  return post('/system/dbTabM/delTab', data)
}
//查询表排序
export function getSortSrnoDb(params) {
  return get('/system/dbTabM/getSortSrno', params)
}
//根据id查表详情
export function getTableById(data) {
  return post('/system/dbTabM/getTableById', data)
}
//根据id查表详情
export function getTabEditById(data) {
  return post('/system/dbTabM/getTabEditById', data)
}
//根据条件查询可用的表
export function getTableByCondition(data) {
  return post('/system/dbTabM/getTableByCondition', data)
}


/*添加字段*/
export function addFild(data) {
  return post('/system/dbFildM/addFild', data)
}
/*校验字段名称*/
export function checkFildNm(data) {
  return post('/system/dbFildM/checkFildNm', data)
}
/*删除字段*/
export function delFild(data) {
  return post('/system/dbFildM/delFild', data)
}
/*编辑字段*/
export function editFild(data) {
  return post('/system/dbFildM/editFild', data)
}
/*获取排序序号*/
export function getSortSrnoFild(params) {
  return get('/system/dbFildM/getSortSrno', params)
}
/*根据id查字段详情*/
export function getFildById(data) {
  return post('/system/dbFildM/getFildById', data)
}
/*根据id查字段详情--可以获取所有父级的name*/
export function getFildEditById(data) {
  return post('/system/dbFildM/getFildEditById', data)
}
/*根据条件查询可用的字段*/
export function getFildByCondition(data) {
  return post('/system/dbFildM/getFildByCondition', data)
}

/*编码区管理*/
/*新增编码区*/
export function addSphrM(data) {
  return post('/code/cdSphrM/addSphrM', data)
}
/*编辑编码区*/
export function editSphrM(data) {
  return post('/code/cdSphrM/editSphrM', data)
}
/*删除编码区*/
export function delSphrMById(params) {
  return get(`/code/cdSphrM/delSphrM`, params)
}
/*查询编码区详情*/
export function getSphrMById(params) {
  return get(`/code/cdSphrM/getSphrMById`, params)
}
/*查询编码区列表*/
export function getSphrMList(data) {
  return post('/code/cdSphrM/getSphrMList', data)
}

/*编码分类*/
/*新增编码分类*/
export function addCatM(data) {
  return post('/code/cdCatM/addCatM', data)
}
/*删除编码分类*/
export function delCatM(params) {
  return get('/code/cdCatM/delCatM', params)
}
/*修改编码分类*/
export function editCatM(data) {
  return post('/code/cdCatM/editCatM', data)
}
/*根据id查编码分类详情*/
export function getCatMById(params) {
  return get('/code/cdCatM/getCatMById', params)
}
/*查询编码分类列表*/
export function getCatMList(data) {
  return post('/code/cdCatM/getCatMList', data)
}

/*日志管理*/
/*获取账号登录历史表*/
export function getBnacLgnHList(data) {
  return post(`/accesshistory/bnacLgnH/getList`, data)
}
/*获取系统操作表*/
export function getSysOprtGList(data) {
  return post(`/accesshistory/sysOprtG/getList`, data)
}
/*获取用户在线历史*/
export function getUserOnliHList(data) {
  return post(`/accesshistory/userOnliH/getList`, data)
}

/*公共编码值*/
/*查询编码状态编码*/
export function getCdStCdList(data) {
  return post(`/code/cmmnCdVlM/getCdStCdList`, data)
}
/*查询编码类型编码*/
export function getCdTpCdList(data) {
  return post(`/code/cmmnCdVlM/getCdTpCdList`, data)
}
/*查询数据类型*/
export function getDataTpList(data) {
  return post(`/code/cmmnCdVlM/getDataTpList`, data)
}

/*编码管理*/
/*查询编码属性列表*/
export function getCdPptyMList(data) {
  return post(`/code/cdM/getCdPptyMList`, data)
}
/*查询编码属性值*/
export function getCdPtyvList(data) {
  return post(`/code/cdM/getCdPtyvList`, data)
}
/*添加编码值*/
export function addCdPtyvM(data) {
  return post(`/code/cdvlM/addCdvlM`, data)
}
/*修改编码值*/
export function editCdPtyvM(data) {
  return post(`/code/cdvlM/editCdvlM`, data)
}
/*查询编码属性关系*/
export function getCodeAttributeRelation(data) {
  return post(`/code/cdM/getCodeAttributeRelation`, data)
}
/*查询编码编码关系*/
export function getCodeCodeRelation(data) {
  return post(`/code/cdM/getCodeCodeRelation`, data)
}
/*查询编码值关系*/
export function getCodeValueRelation(data) {
  return post(`/code/cdM/getCodeValueRelation`, data)
}
/*查询编码属性列关系*/
export function getCodeAttrColumnRelation(data) {
  return post(`/code/cdM/getCodeAttrColumnRelation`, data)
}
/*分页查询编码*/
export function getCdMByPage(data) {
  return post(`/code/cdM/getCdMByPage`, data)
}
/*批量删除编码*/
export function delBatchCdM(data) {
  return post(`/code/cdM/delBatchCdM`, data)
}
/*新增编码*/
export function addCdM(data) {
  return post(`/code/cdM/addCdM`, data)
}
/*查询编码表关系*/
export function getCodeTableRelation(data) {
  return post(`/code/cdM/getCodeTableRelation`, data)
}
/*编辑编码*/
export function editCdM(data) {
  return post(`/code/cdM/editCdM`, data)
}
/*查编码详情*/
export function getCdMById(params) {
  return get(`/code/cdM/getCdMById`, params)
}

/*编码属性*/
/*添加编码属性*/
export function addCdPptyM(data) {
  return post(`/code/cdPptyM/addCdPptyM`, data)
}
/*编辑编码属性*/
export function editCdPptyM(data) {
  return post(`/code/cdPptyM/editCdPptyM`, data)
}
/*查询编码属性列表*/
export function getCodeCdPptyMList(data) {
  return post(`/code/cdPptyM/getCdPptyMList`, data)
}
/*删除编码属性*/
export function delBatchCdPptyM(data) {
  return post(`/code/cdPptyM/delBatchCdPptyM`, data)
}


/*编码值关系*/
/*查询映射关系*/
export function getCdvlR(data) {
  return post(`/code/cdvlR/getCdvlR`, data)
}
/*自动映射*/
export function addBatchCdvlR(data) {
  return post(`/code/cdvlR/addBatchCdvlR`, data)
}
/*映射*/
export function addCdvlR(data) {
  return post(`/code/cdvlR/addCdvlR`, data)
}
/*删除映射*/
export function delBatchCdvlR(data) {
  return post(`/code/cdvlR/delBatchCdvlR`, data)
}
/*删除映射*/
export function delBatchCdvlM(data) {
  return post(`/code/cdvlM/delBatchCdvlM`, data)
}

/*编码数据专栏*/
/*查询映射关系*/
export function getPptyFildBndR(data) {
  return post(`/code/cdPptyFildBndR/getPptyFildBndR`, data)
}
/*自动映射*/
export function addBatchPptyFildBndR(data) {
  return post(`/code/cdPptyFildBndR/addBatchPptyFildBndR`, data)
}
/*映射*/
export function addPptyFildBndR(data) {
  return post(`/code/cdPptyFildBndR/addPptyFildBndR`, data)
}
/*删除映射*/
export function delBatchPptyFildBndR(data) {
  return post(`/code/cdPptyFildBndR/delBatchPptyFildBndR`, data)
}



/*分页查询编码值*/
export function getCdvlMByPage(data) {
  return post(`/code/cdvlM/getCdvlMByPage`, data)
}
/*删除编码编码关系*/
export function delCdR(data) {
  return post(`/code/cdR/delCdR`, data)
}
/*删除编码表关系*/
export function delBatchTabCdBndR(data) {
  return post(`/code/tabCdBndR/delBatchTabCdBndR`, data)
}

/*编码属性关系*/
export function getPptyR(data) {
  return post(`/code/cdPptyR/getPptyR`, data)
}
export function addPptyR(data) {
  return post(`/code/cdPptyR/addPptyR`, data)
}
export function addBatchPptyR(data) {
  return post(`/code/cdPptyR/addBatchPptyR`, data)
}
/*删除映射*/
export function delBatchPptyR(data) {
  return post(`/code/cdPptyR/delBatchPptyR`, data)
}


/*编码编码关系*/
export function addCdR(data) {
  return post('/code/cdR/addCdR', data);
}

export function addTabCdBndR(data) {
  return post('/code/tabCdBndR/addTabCdBndR', data);
}

export function CdPptyFildBndR_getMappingLeftData(data) {
  return post(`/code/cdPptyFildBndR/getMappingLeftData`, data);
}
export function CdPptyFildBndR_getMappingRightData(data) {
  return post(`/code/cdPptyFildBndR/getMappingRightData`, data);
}
export function CdPptyFildBndR_addPptyFildBndR(data) {
  return post(`/code/cdPptyFildBndR/addPptyFildBndR`, data);
}
export function CdPptyFildBndR_addBatchPptyFildBndR(data) {
  return post(`/code/cdPptyFildBndR/addBatchPptyFildBndR`, data);
}
export function CdPptyFildBndR_delBatchPptyFildBndR(data) {
  return post(`/code/cdPptyFildBndR/delBatchPptyFildBndR`, data);
}
