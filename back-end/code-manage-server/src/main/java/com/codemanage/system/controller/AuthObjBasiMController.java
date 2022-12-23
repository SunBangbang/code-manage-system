package com.codemanage.system.controller;


import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.entity.TreeDto;
import com.codemanage.common.util.TreeUtils;
import com.codemanage.system.dto.request.AuthObjSaveDto;
import com.codemanage.system.dto.response.AuthTreeDto;
import com.codemanage.system.entity.AuthObjBasiM;
import com.codemanage.system.service.IAuthObjBasiMService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * CVSO_权限对象基础_主表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-07-16
 */
@RestController
@RequestMapping("/system/authObjBasiM")
@Api(tags = "菜单管理API")
@Slf4j
public class AuthObjBasiMController {

    @Autowired
    private IAuthObjBasiMService authObjBasiMService;

    /**
     * 1. 查询菜单树
     * 2. 查询类型为Folder的菜单树
     * 3. 检查屏幕id是否唯一
     * 4. 检查菜单名称是否唯一
     * 5. 新增菜单
     * 6. 根据id查询菜单
     * 7. 修改菜单
     * 8. 删除菜单
     */

    /**
     * 1. 查询菜单树
     * @return
     */
    @GetMapping("/getAuthObjTree")
    @ApiOperation(value = "查询菜单树", notes = "查询菜单树")
    public BaseResult getAuthObjTree(@RequestParam(required = false) String name) {
        try {
            List<AuthTreeDto> list = authObjBasiMService.getAuthObjTree(name);
            List<TreeDto> tree = TreeUtils.getTree(list);
            return BaseResult.successMsg("查询成功!", tree);
        } catch (Exception e) {
            log.error("查询部门树异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }


    /**
     * 2. 查询类型为Folder的菜单树
     * @return
     */
    @GetMapping("/getAuthFolderTree")
    @ApiOperation(value = "查询类型为Folder的菜单树", notes = "查询类型为Folder的菜单树")
    public BaseResult getAuthFolderTree() {
        try {
            List<AuthTreeDto> list = authObjBasiMService.getAuthFolderTree();
            List<TreeDto> tree = TreeUtils.getTree(list);
            return BaseResult.successMsg("查询成功!", tree);
        } catch (Exception e) {
            log.error("查询部门树异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 3. 检查屏幕id是否唯一
     * @param uiId
     * @return
     */
    @GetMapping("/checkUiId/")
    @ApiOperation(value = "检查登录id是否唯一", notes = "检查登录id是否唯一")
    public BaseResult checkUiId(@RequestParam String uiId){
        try {
            boolean flag = authObjBasiMService.checkUiId(uiId);
            return BaseResult.successMsg("检验完成!", flag);
        } catch (Exception e) {
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 4. 检查菜单名称是否唯一
     * @param name
     * @return
     */
    @GetMapping("/checkAuthName/")
    @ApiOperation(value = "检查菜单名称是否唯一", notes = "检查菜单名称是否唯一")
    public BaseResult checkAuthName(@RequestParam String name){
        try {
            boolean flag = authObjBasiMService.checkAuthName(name);
            return BaseResult.successMsg("检验完成!", flag);
        } catch (Exception e) {
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 5. 添加菜单
     * @param dto
     * @return
     */
    @PostMapping("/addAuthObj")
    @ApiOperation(value = "添加菜单", notes = "添加菜单")
    public BaseResult addAuthObj(@RequestBody AuthObjSaveDto dto) {
        try {
            return authObjBasiMService.addAuthObj(dto);
        } catch (Exception e) {
            log.error("添加菜单异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 6. 根据id查询菜单
     * @param id
     * @return
     */
    @GetMapping("/getAuthObjById/")
    @ApiOperation(value = "根据id查询菜单", notes = "根据id查询菜单")
    public BaseResult getAuthObjById(@RequestParam String id){
        try {
            AuthObjBasiM obj = authObjBasiMService.getObjById(id, Constants.FNSHDTTM);
            return BaseResult.successMsg("查询完成!", obj);
        } catch (Exception e) {
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 7. 修改菜单
     * @param dto
     * @return
     */
    @PostMapping("/editAuthObj")
    @ApiOperation(value = "修改菜单", notes = "修改菜单")
    public BaseResult editAuthObj(@RequestBody AuthObjSaveDto dto) {
        try {
            return authObjBasiMService.editAuthObj(dto);
        } catch (Exception e) {
            log.error("修改菜单异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("修改异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 8. 删除菜单
     * @param id
     * @return
     */
    @GetMapping("/delAuthObj")
    @ApiOperation(value = "删除菜单", notes = "删除菜单")
    public BaseResult delAuthObj(@RequestParam String id) {
        try {
            boolean flag = authObjBasiMService.delAuthObj(id);
            return BaseResult.successMsg("删除成功!", flag);
        } catch (Exception e) {
            log.error("删除菜单异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }

}
