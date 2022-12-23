package com.codemanage.system.controller;


import com.codemanage.common.entity.BaseResult;
import com.codemanage.system.dto.request.DbTabMQueryDto;
import com.codemanage.system.dto.request.DbTabMSaveDto;
import com.codemanage.system.dto.response.DbTabMVo;
import com.codemanage.system.service.IDbTabMService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * CVSO_数据库表_主表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
@RestController
@RequestMapping("/system/dbTabM")
@Api(tags = "数据库表API")
@Slf4j
public class DbTabMController {

    @Autowired
    private IDbTabMService tabMService;

    /**
     *  根据条件查询可用的平台
     *  根据id查询平台
     *  校验table名称
     *  获取排序序号
     *  添加table
     *  编辑弹窗查询详情
     *  编辑table
     *  删除table
     */

    /**
     * 根据条件查询可用的平台
     * @return
     */
    @PostMapping("/getTableByCondition")
    @ApiOperation(value = "根据条件查询可用的平台", notes = "根据条件查询可用的平台")
    public BaseResult getTableByCondition(@RequestBody DbTabMQueryDto dto) {
        try {
            List<DbTabMVo> list = tabMService.getTableByCondition(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("根据条件查询可用的平台异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("根据条件查询可用的平台异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 根据id查询平台
     * @return
     */
    @PostMapping("/getTableById")
    @ApiOperation(value = "根据id查询平台", notes = "根据id查询平台")
    public BaseResult getTableById(@RequestBody DbTabMQueryDto dto) {
        try {
            DbTabMVo vo = tabMService.getTableById(dto);
            return BaseResult.successMsg("查询成功!", vo);
        } catch (Exception e) {
            log.error("根据id查询平台异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("根据id查询平台异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 校验table名称
     * @param dto
     * @return
     */
    @PostMapping("/checkTabNm")
    @ApiOperation(value = "校验table名称", notes = "校验table名称")
    public BaseResult checkTabNm(@RequestBody DbTabMQueryDto dto) {
        try {
            boolean flag = tabMService.checkTabNm(dto);
            return BaseResult.successMsg("校验完成!", flag);
        } catch (Exception e) {
            log.error("校验table名称异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("校验异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 获取排序序号
     * @return
     */
    @GetMapping("/getSortSrno")
    @ApiOperation(value = "获取排序序号", notes = "获取排序序号")
    public BaseResult getSortSrno(String dbmsId, String schmId) {
        try {
            Integer sortSrno = tabMService.getSortSrno(dbmsId, schmId);
            return BaseResult.successMsg("查询完成!", sortSrno);
        } catch (Exception e) {
            log.error("获取排序序号异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("获取排序序号异常,请联系管理员!", e.getMessage());
        }
    }


    /**
     * 添加table
     * @param dto
     * @return
     */
    @PostMapping("/addTab")
    @ApiOperation(value = "添加table", notes = "添加table")
    public BaseResult addTab(@RequestBody DbTabMSaveDto dto) {
        try {
            return tabMService.addTab(dto);
        } catch (Exception e) {
            log.error("添加table异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 编辑弹窗查询详情
     * @return
     */
    @PostMapping("/getTabEditById")
    @ApiOperation(value = "编辑弹窗查询详情", notes = "编辑弹窗查询详情")
    public BaseResult getTabEditById(@RequestBody DbTabMQueryDto dto) {
        try {
            DbTabMVo vo = tabMService.getTabEditById(dto);
            return BaseResult.successMsg("查询成功!", vo);
        } catch (Exception e) {
            log.error("根据id查询模式异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("根据id查询模式异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 编辑table
     * @param dto
     * @return
     */
    @PostMapping("/editTab")
    @ApiOperation(value = "编辑table", notes = "编辑table")
    public BaseResult editTab(@RequestBody DbTabMSaveDto dto) {
        try {
            return tabMService.editTab(dto);
        } catch (Exception e) {
            log.error("编辑table异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("编辑异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 删除table
     * @param dto
     * @return
     */
    @PostMapping("/delTab")
    @ApiOperation(value = "删除table", notes = "删除table")
    public BaseResult delTab(@RequestBody DbTabMQueryDto dto) {
        try {
            boolean flag = tabMService.delTab(dto);
            if (flag) {
                return BaseResult.successMsg("删除成功!");
            }
            return BaseResult.failedMsg("删除失败!");
        } catch (Exception e) {
            log.error("删除tab异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }
}
