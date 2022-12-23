package com.codemanage.system.controller;


import com.codemanage.common.entity.BaseResult;
import com.codemanage.system.dto.request.DbFildMQueryDto;
import com.codemanage.system.dto.request.DbFildMSaveDto;
import com.codemanage.system.dto.request.DbFildQueryDto;
import com.codemanage.system.dto.response.DbFildMVo;
import com.codemanage.system.service.IDbFildMService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * CVSO_数据库字段_主表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-07-19
 */
@RestController
@RequestMapping("/system/dbFildM")
@Api(tags = "数据库字段API")
@Slf4j
public class DbFildMController {

    @Autowired
    private IDbFildMService fildMService;

    /**
     *  根据条件查询可用的专栏
     *  根据id查询专栏
     *  校验fild名称
     *  获取排序序号
     *  1. 添加专栏（column）
     *  2. 编辑专栏（column）
     *  3. 删除专栏（column）
     *  4. 查询选中的专栏（column）
     *  5. 专栏-再次确认
     */

    /**
     * 根据条件查询可用的专栏
     * @return
     */
    @PostMapping("/getFildByCondition")
    @ApiOperation(value = "根据条件查询可用的专栏", notes = "根据条件查询可用的专栏")
    public BaseResult getFildByCondition(@RequestBody DbFildMQueryDto dto) {
        try {
            List<DbFildMVo> list = fildMService.getFildByCondition(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("根据条件查询可用的专栏异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("根据条件查询可用的专栏异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 根据id查询专栏
     * @return
     */
    @PostMapping("/getFildById")
    @ApiOperation(value = "根据id查询专栏", notes = "根据id查询专栏")
    public BaseResult getFildById(@RequestBody DbFildMQueryDto dto) {
        try {
            DbFildMVo vo = fildMService.getFildById(dto);
            return BaseResult.successMsg("查询成功!", vo);
        } catch (Exception e) {
            log.error("根据id查询专栏异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("根据id查询专栏异常,请联系管理员!", e.getMessage());
        }
    }


    /**
     * 校验fild名称
     * @param dto
     * @return
     */
    @PostMapping("/checkFildNm")
    @ApiOperation(value = "校验fild名称", notes = "校验fild名称")
    public BaseResult checkTabNm(@RequestBody DbFildMQueryDto dto) {
        try {
            boolean flag = fildMService.checkFildNm(dto);
            return BaseResult.successMsg("校验完成!", flag);
        } catch (Exception e) {
            log.error("校验fild名称异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("校验异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 获取排序序号
     * @return
     */
    @GetMapping("/getSortSrno")
    @ApiOperation(value = "获取排序序号", notes = "获取排序序号")
    public BaseResult getSortSrno(String dbmsId, String schmId, String tabId) {
        try {
            Integer sortSrno = fildMService.getSortSrno(dbmsId, schmId, tabId);
            return BaseResult.successMsg("查询完成!", sortSrno);
        } catch (Exception e) {
            log.error("获取排序序号异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("获取排序序号异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 添加Fild
     * @param dto
     * @return
     */
    @PostMapping("/addFild")
    @ApiOperation(value = "添加Fild", notes = "添加Fild")
    public BaseResult addFild(@RequestBody DbFildMSaveDto dto) {
        try {
            return fildMService.addFild(dto);
        } catch (Exception e) {
            log.error("添加Fild异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 编辑弹窗查询详情
     * @return
     */
    @PostMapping("/getFildEditById")
    @ApiOperation(value = "编辑弹窗查询详情", notes = "编辑弹窗查询详情")
    public BaseResult getFildEditById(@RequestBody DbFildMQueryDto dto) {
        try {
            DbFildMVo vo = fildMService.getFildEditById(dto);
            return BaseResult.successMsg("查询成功!", vo);
        } catch (Exception e) {
            log.error("根据id查询模式异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("根据id查询模式异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 编辑Fild
     * @param dto
     * @return
     */
    @PostMapping("/editFild")
    @ApiOperation(value = "编辑Fild", notes = "编辑Fild")
    public BaseResult editFild(@RequestBody DbFildMSaveDto dto) {
        try {
            return fildMService.editFild(dto);
        } catch (Exception e) {
            log.error("编辑Fild异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("编辑异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 删除Fild
     * @param dto
     * @return
     */
    @PostMapping("/delFild")
    @ApiOperation(value = "删除Fild", notes = "删除Fild")
    public BaseResult delFild(@RequestBody DbFildMQueryDto dto) {
        try {
            boolean flag = fildMService.delFild(dto);
            if (flag) {
                return BaseResult.successMsg("删除成功!");
            }
            return BaseResult.failedMsg("删除失败!");
        } catch (Exception e) {
            log.error("删除Fild异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 查询列信息-编码属性专栏关系弹窗
     * @return
     */
    @PostMapping("/getFildByCodeAttrColumn")
    @ApiOperation(value = "查询列信息-编码属性专栏关系弹窗", notes = "查询列信息-编码属性专栏关系弹窗")
    public BaseResult getFildByCodeAttrColumn(@RequestBody DbFildQueryDto dto) {
        try {
            List<DbFildMVo> list = fildMService.getFildByCodeAttrColumn(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("根据条件查询可用的专栏异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("根据条件查询可用的专栏异常,请联系管理员!", e.getMessage());
        }
    }
}
