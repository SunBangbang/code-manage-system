package com.codemanage.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.entity.TreeDto;
import com.codemanage.common.util.TreeUtils;
import com.codemanage.system.dto.request.RoleQueryDto;
import com.codemanage.system.dto.request.RoleSaveDto;
import com.codemanage.system.dto.response.AuthTreeDto;
import com.codemanage.system.dto.response.RoleVo;
import com.codemanage.system.dto.response.UserBasiMVo;
import com.codemanage.system.service.IAuthObjBasiMService;
import com.codemanage.system.service.IUserBasiMService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色管理 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-05-31
 */
@RestController
@RequestMapping("/system/Role")
@Api(tags = "角色管理API")
@Slf4j
public class RoleController {

    @Autowired
    private IUserBasiMService userBasiMService;

    @Autowired
    private IAuthObjBasiMService authObjBasiMService;

    /**
     * 1. 查询当前部门下级的所有用户信息
     * 2. 查询所有角色信息
     * 3. 校验角色名称是否重复
     * 4. 新增角色
     * 5. 编辑角色
     * 6. 删除角色
     * 7. 分页查询角色下的用户
     * 8. 新增角色和用户关系
     * 9. 查询用户已绑定角色
     * 10. 根据id查询角色
     * 11. 批量添加角色和用户关系
     * 12. 批量删除角色和用户关系
     * 13. 查询角色的菜单树
     * 14. 添加角色和菜单关系
     */


    /**
     * 1. 查询当前部门下级的所有用户信息
     * @param deptId
     * @return
     */
    @GetMapping("/getSubordinateUserByDeptId")
    @ApiOperation(value = "查询当前部门下级的所有用户信息", notes = "查询当前部门下级的所有用户信息")
    public BaseResult getSubordinateUserByDeptId(@RequestParam String deptId) {
        try {
            List<UserBasiMVo> list = userBasiMService.getSubordinateUserByDeptId(deptId);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询当前部门下级的所有用户信息异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 2. 查询所有角色信息
     * @return
     */
    @GetMapping("/getAllRole")
    @ApiOperation(value = "查询所有角色信息", notes = "查询所有角色信息")
    public BaseResult getAllRole() {
        try {
            List<RoleVo> list = userBasiMService.getAllRole();
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询所有角色信息异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 3. 校验角色名称是否重复
     * @param roleName
     * @return
     */
    @GetMapping("/checkRoleName")
    @ApiOperation(value = "校验角色名称是否重复", notes = "校验角色名称是否重复")
    public BaseResult checkRoleName(@RequestParam String roleName) {
        try {
            boolean flag = userBasiMService.checkRoleName(roleName);
            return BaseResult.successMsg("校验完成!", flag);
        } catch (Exception e) {
            log.error("校验角色名称是否重复异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("校验异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 4. 新增角色
     * @param dto
     * @return
     */
    @PostMapping("/addRole")
    @ApiOperation(value = "添加角色", notes = "添加角色")
    public BaseResult addRole(@RequestBody RoleSaveDto dto) {
        try {
            dto.setCvUserDstsCd(Constants.UserDstsCd.ROLE);
            return userBasiMService.addRole(dto);
        } catch (Exception e) {
            log.error("添加角色异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 5. 编辑角色
     * @param dto
     * @return
     */
    @PostMapping("/editRole")
    @ApiOperation(value = "编辑角色", notes = "编辑角色")
    public BaseResult editRole(@RequestBody RoleSaveDto dto) {
        try {
            dto.setCvUserDstsCd(Constants.UserDstsCd.ROLE);
            return userBasiMService.editRole(dto);
        } catch (Exception e) {
            log.error("编辑角色异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("编辑异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 6. 删除角色
     * @param id
     * @return
     */
    @GetMapping("/delRole")
    @ApiOperation(value = "删除角色", notes = "删除角色")
    public BaseResult delRole(@RequestParam String id) {
        try {
            boolean flag = userBasiMService.delRole(id);
            return BaseResult.successMsg("删除成功!", flag);
        } catch (Exception e) {
            log.error("删除角色异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 7. 分页查询角色下的用户
     * @param dto
     * @return
     */
    @PostMapping("/getUserPageByRole")
    @ApiOperation(value = "查询角色下的用户", notes = "查询角色下的用户")
    public BaseResult getUserPageByRole(@RequestBody RoleQueryDto dto) {
        try {
            Page<UserBasiMVo> page = userBasiMService.getUserPageByRole(dto);
            return BaseResult.successMsg("查询成功!", page);
        } catch (Exception e) {
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 8. 新增角色和用户关系
     * @param roleId
     * @param userId
     * @return
     */
    @Deprecated
    @GetMapping("/addRoleUserRelation")
    @ApiOperation(value = "新增角色和用户关系", notes = "新增角色和用户关系")
    public BaseResult addRoleUserRelation(@RequestParam String roleId, @RequestParam String userId) {
        try {
            boolean flag = userBasiMService.addRoleUserRelation(roleId, userId);
            return BaseResult.successMsg("新增角色和用户关系成功!", flag);
        } catch (Exception e) {
            return BaseResult.failedMsg("新增异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 9. 查询用户已绑定角色
     * @param userId
     * @return
     */
    @GetMapping("/getRoleByUserId")
    @ApiOperation(value = "查询用户已绑定角色", notes = "查询用户已绑定角色")
    public BaseResult getRoleByUserId(@RequestParam String userId) {
        try {
            List<RoleVo> list = userBasiMService.getRoleByUserId(userId);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 10. 根据id查询角色
     * @return
     */
    @GetMapping("/getRoleById")
    @ApiOperation(value = "根据id查询角色", notes = "根据id查询角色")
    public BaseResult getRoleById(@RequestParam String id) {
        try {
            RoleVo roleVo = userBasiMService.getRoleById(id);
            return BaseResult.successMsg("查询成功!", roleVo);
        } catch (Exception e) {
            log.error("查询角色信息异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 11. 批量添加角色和用户关系
     * @param dto
     * @return
     */
    @PostMapping("/addBatchRoleUserRelation")
    @ApiOperation(value = "添加角色和用户关系", notes = "添加角色和用户关系")
    public BaseResult addBatchRoleUserRelation(@RequestBody RoleSaveDto dto) {
        try {
            boolean flag = userBasiMService.addBatchRoleUserRelation(dto);
            if (flag) {
                return BaseResult.successMsg("添加成功!");
            }
            return BaseResult.failedMsg("添加失败!");
        } catch (Exception e) {
            log.error("添加角色和用户关系异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加角色和用户关系异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 12. 批量删除角色和用户关系
     * @param dto
     * @return
     */
    @PostMapping("/delBatchRoleUserRelation")
    @ApiOperation(value = "删除角色和用户关系", notes = "删除角色和用户关系")
    public BaseResult delBatchRoleUserRelation(@RequestBody RoleSaveDto dto) {
        try {
            boolean flag = userBasiMService.delBatchRoleUserRelation(dto);
            if (flag) {
                return BaseResult.successMsg("删除成功!");
            }
            return BaseResult.failedMsg("删除失败!");
        } catch (Exception e) {
            log.error("删除角色和用户关系异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除角色和用户关系异常,请联系管理员!", e.getMessage());
        }
    }


    /**
     * 13. 查询角色的菜单树
     * @return
     */
    @GetMapping("/getAuthObjTreeByRoleId")
    @ApiOperation(value = "查询角色的菜单树", notes = "查询角色的菜单树")
    public BaseResult getAuthObjTreeByRoleId(@RequestParam String id) {
        try {
            List<AuthTreeDto> list = authObjBasiMService.getAuthObjTreeByRoleId(id);
            List<TreeDto> tree = TreeUtils.getTree(list);
            return BaseResult.successMsg("查询成功!", tree);
        } catch (Exception e) {
            log.error("查询角色的菜单树异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 14. 添加角色和菜单关系
     * @param dto
     * @return
     */
    @PostMapping("/addRoleAuthRelation")
    @ApiOperation(value = "添加角色和菜单关系", notes = "添加角色和菜单关系")
    public BaseResult addRoleAuthRelation(@RequestBody RoleSaveDto dto) {
        try {
            return authObjBasiMService.addRoleAuthRelation(dto.getCvUserId(), dto.getAuthIdList());
        } catch (Exception e) {
            log.error("添加角色和菜单关系异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加角色和菜单关系异常,请联系管理员!", e.getMessage());
        }
    }

}
