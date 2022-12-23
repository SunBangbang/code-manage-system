package com.codemanage.system.controller;


import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.entity.TreeDto;
import com.codemanage.common.util.TreeUtils;
import com.codemanage.system.dto.request.DeptSaveDto;
import com.codemanage.system.dto.response.DeptTreeDto;
import com.codemanage.system.dto.response.UserBasiMVo;
import com.codemanage.system.entity.UserBasiM;
import com.codemanage.system.service.IUserBasiMService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-05-31
 */
@RestController
@RequestMapping("/system/dept")
@Api(tags = "部门管理API")
@Slf4j
public class DeptController {

    @Autowired
    private IUserBasiMService userBasiMService;

    /**
     * 1. 查询部门和用户树
     * 2. 查询部门树
     * 3. 查询部门下的用户
     * 4. 添加部门
     * 5. 编辑部门
     * 6. 删除部门
     */

    /**
     * 查询部门和用户树
     * @return
     */
    @PostMapping("/getDeptAndUserTree")
    @ApiOperation(value = "查询部门树", notes = "查询部门树")
    public BaseResult getDeptAndUserTree() {
        try {
            List<DeptTreeDto> list = userBasiMService.getDeptAndUserTree();
            List<TreeDto> tree = TreeUtils.getTree(list);
            return BaseResult.successMsg("查询成功!", tree);
        } catch (Exception e) {
            log.error("查询部门树异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 查询部门树
     * @return
     */
    @PostMapping("/getDeptTree")
    @ApiOperation(value = "查询部门树", notes = "查询部门树")
    public BaseResult getDeptTree() {
        try {
            List<DeptTreeDto> list = userBasiMService.getDeptTree();
            List<TreeDto> tree = TreeUtils.getTree(list);
            return BaseResult.successMsg("查询成功!", tree);
        } catch (Exception e) {
            log.error("查询部门树异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 查询部门下的用户
     * @param map
     * @return
     */
    @PostMapping("/getUserListByDept")
    @ApiOperation(value = "查询部门下的用户", notes = "查询部门下的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门id", paramType = "body", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userNm", value = "用户名称", paramType = "body", dataType = "String"),
            @ApiImplicitParam(name = "cvLgnId", value = "用户编号", paramType = "body", dataType = "String")
    })
    public BaseResult getUserListByDept(@RequestBody Map<String, String> map) {
        try {
            List<UserBasiMVo> voList = userBasiMService.getUserListByDept(map);
            return BaseResult.successMsg("查询成功!", voList);
        } catch (Exception e) {
            log.error("查询异常:", e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 添加部门
     * @param dto
     * @return
     */
    @PostMapping("/addDept")
    @ApiOperation(value = "添加部门", notes = "添加部门")
    public BaseResult addDept(@RequestBody DeptSaveDto dto) {
        try {
            dto.setCvUserDstsCd(Constants.UserDstsCd.DEPT);
            return userBasiMService.addDept(dto);
        } catch (Exception e) {
            log.error("添加部门异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 编辑部门
     * @param dto
     * @return
     */
    @PostMapping("/editDept")
    @ApiOperation(value = "编辑部门", notes = "编辑部门")
    public BaseResult editDept(@RequestBody DeptSaveDto dto) {
        try {
            dto.setCvUserDstsCd(Constants.UserDstsCd.DEPT);
            return userBasiMService.editDept(dto);
        } catch (Exception e) {
            log.error("编辑部门异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("编辑异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @GetMapping("/delDept")
    @ApiOperation(value = "删除部门", notes = "删除部门")
    public BaseResult delDept(@RequestParam String id) {
        try {
            boolean flag = userBasiMService.delDept(id);
            return BaseResult.successMsg("删除成功!", flag);
        } catch (Exception e) {
            log.error("删除部门异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    @GetMapping("/getDeptById")
    @ApiOperation(value = "根据id查询详情", notes = "根据id查询详情")
    public BaseResult getDeptById(@RequestParam String id) {
        try {
            UserBasiM userBasiM = userBasiMService.getObjById(id, Constants.FNSHDTTM);
            return BaseResult.successMsg("查询成功!", userBasiM);
        } catch (Exception e) {
            log.error("查询部门异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

}
