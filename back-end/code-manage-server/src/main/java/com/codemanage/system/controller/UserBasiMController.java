package com.codemanage.system.controller;


import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.system.dto.request.UserBasiMDto;
import com.codemanage.system.dto.response.UserBasiMVo;
import com.codemanage.system.service.IUserBasiMService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * CVSO_用户基础_主表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-05-31
 */
@RestController
@RequestMapping("/system/userBasiM")
@Api(tags = "用户管理API")
@Slf4j
public class UserBasiMController {

    @Autowired
    private IUserBasiMService userBasiMService;

    /**
     * 1. 查询用户列表
     * 2. 根据id查询用户
     * 3. 检查登录id是否唯一
     * 4. 添加用户
     * 5. 编辑用户
     * 6. 删除用户
     * 7. 用户绑定角色
     * 8. 初始化密码
     * 9. 解锁用户
     * 10. 检查用户id是否唯一
     */

    /**
     * 1. 查询用户列表
     * @param dto
     * @return
     */
    @PostMapping("/getUserBasiMList")
    @ApiOperation(value = "查询用户列表", notes = "查询用户列表")
    public BaseResult getUserBasiMList(@RequestBody(required = false) UserBasiMDto dto) {
        try {
            List<UserBasiMVo> list = userBasiMService.getUserBasiMList(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询用户列表异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 2. 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping("/getUserBasiMById/{id}")
    @ApiOperation(value = "根据id查询用户", notes = "根据id查询用户")
    public BaseResult getUserBasiMById(@PathVariable String id){
        try {
            UserBasiMVo obj= userBasiMService.getVoById(id);
            return BaseResult.successMsg("查询成功!", obj);
        } catch (Exception e) {
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 3. 检查员工编号是否唯一
     * @param lgnId
     * @return
     */
    @GetMapping("/checkLgnId/{lgnId}")
    @ApiOperation(value = "检查员工编号是否唯一", notes = "检查员工编号是否唯一")
    public BaseResult checkLgnId(@PathVariable String lgnId){
        try {
            boolean flag = userBasiMService.checkLgnId(lgnId);
            return BaseResult.successMsg("检验完成!", flag);
        } catch (Exception e) {
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 4. 添加用户
     * @param dto
     * @return
     */
    @PostMapping("/addUserBasiM")
    @ApiOperation(value = "添加用户", notes = "添加用户")
    public BaseResult addUserBasiM(@RequestBody UserBasiMDto dto) {
        try {
            dto.setCvUserDstsCd(Constants.UserDstsCd.USER);
            return userBasiMService.addUserBasiM(dto);
        } catch (Exception e) {
            log.error("添加用户异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 5. 编辑用户
     * @param dto
     * @return
     */
    @PostMapping("/editUserBasiM")
    @ApiOperation(value = "编辑用户", notes = "编辑用户")
    public BaseResult editUserBasiM(@RequestBody UserBasiMDto dto) {
        try {
            dto.setCvUserDstsCd(Constants.UserDstsCd.USER);
            return userBasiMService.editUserBasiM(dto);
        } catch (Exception e) {
            log.error("编辑用户异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("编辑异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 6. 删除用户
     * @param id
     * @return
     */
    @GetMapping("/delUser")
    @ApiOperation(value = "删除用户", notes = "删除用户")
    public BaseResult delUser(@RequestParam String id) {
        try {
            boolean flag = userBasiMService.delUser(id);
            if (flag) {
                return BaseResult.successMsg("删除成功!");
            }
            return BaseResult.failedMsg("删除失败!");
        } catch (Exception e) {
            log.error("删除用户异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 7. 用户绑定角色
     * @param dto
     * @return
     */
    @PostMapping("/addUserRole")
    @ApiOperation(value = "用户绑定角色", notes = "用户绑定角色")
    public BaseResult addUserRole(@RequestBody UserBasiMDto dto) {
        try {
            return userBasiMService.addUserRole(dto);
        } catch (Exception e) {
            log.error("用户绑定角色异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("用户绑定角色异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 8. 初始化密码
     * @param id
     * @return
     */
    @PostMapping("/initPassword")
    @ApiOperation(value = "初始化密码", notes = "初始化密码")
    public BaseResult initPassword(@RequestParam String id) {
        try {
            boolean flag = userBasiMService.initPassword(id);
            if (flag) {
                return BaseResult.successMsg("初始化密码成功!");
            }
            return BaseResult.failedMsg("初始化密码失败!");
        } catch (Exception e) {
            log.error("初始化密码异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("初始化密码异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 9. 解锁用户
     * @param id
     * @return
     */
    @GetMapping("/unlockUser")
    @ApiOperation(value = "解锁用户", notes = "解锁用户")
    public BaseResult unlockUser(@RequestParam String id) {
        try {
            boolean flag = userBasiMService.unlockUser(id);
            if (flag) {
                return BaseResult.successMsg("解锁用户成功!");
            }
            return BaseResult.failedMsg("解锁用户失败!");
        } catch (Exception e) {
            log.error("解锁用户异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("解锁用户异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 10. 检查用户id是否唯一
     * @param userId
     * @return
     */
    @GetMapping("/checkUserId/{userId}")
    @ApiOperation(value = "检查用户id是否唯一", notes = "检查用户id是否唯一")
    public BaseResult checkUserId(@PathVariable String userId){
        try {
            boolean flag = userBasiMService.checkUserId(userId);
            return BaseResult.successMsg("检验完成!", flag);
        } catch (Exception e) {
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }


    /**
     * 11. 查询当前用户菜单
     * @return
     */
    @GetMapping("/getUserMenu")
    @ApiOperation(value = "查询当前用户菜单", notes = "查询当前用户菜单")
    public BaseResult getUserMenu(){
        try {
            return userBasiMService.getUserMenu();
        } catch (Exception e) {
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }
}
