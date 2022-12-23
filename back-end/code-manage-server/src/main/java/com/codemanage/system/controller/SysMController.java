package com.codemanage.system.controller;


import com.codemanage.common.entity.BaseResult;
import com.codemanage.system.dto.request.SysMQueryDto;
import com.codemanage.system.dto.request.SysMSaveDto;
import com.codemanage.system.dto.response.SysMVo;
import com.codemanage.system.entity.SysM;
import com.codemanage.system.service.ISysMService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * CVSO_系统_主表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
@RestController
@RequestMapping("/system/sysM")
@Api(tags = "应用程序API")
@Slf4j
public class SysMController {

    @Autowired
    private ISysMService sysMService;

    /**
     *  查询系统列表
     *  校验系统名称
     *  添加系统
     *  根据id查询
     *  编辑系统
     *  删除系统
     */

    /**
     * 查询系统列表
     * @return
     */
    @PostMapping("/getSysList")
    @ApiOperation(value = "查询系统列表", notes = "查询系统列表")
    public BaseResult getSysList(@RequestBody SysMQueryDto dto) {
        try {
            List<SysMVo> list = sysMService.getSysList(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询系统列表异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 校验系统名称
     * @return
     */
    @GetMapping("/checkSysNm")
    @ApiOperation(value = "校验系统名称", notes = "校验系统名称")
    public BaseResult checkSysNm(@RequestParam String sysNm) {
        try {
            boolean flag = sysMService.checkSysNm(sysNm);
            return BaseResult.successMsg("校验完成!", flag);
        } catch (Exception e) {
            log.error("校验系统名称异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("校验系统名称异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 获取排序序号
     * @return
     */
    @GetMapping("/getSortSrno")
    @ApiOperation(value = "获取排序序号", notes = "获取排序序号")
    public BaseResult getSortSrno() {
        try {
            Integer sortSrno = sysMService.getSortSrno();
            return BaseResult.successMsg("查询完成!", sortSrno);
        } catch (Exception e) {
            log.error("获取排序序号异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("获取排序序号异常,请联系管理员!", e.getMessage());
        }
    }


    /**
     * 添加系统
     * @param dto
     * @return
     */
    @PostMapping("/addSysM")
    @ApiOperation(value = "添加系统", notes = "添加系统")
    public BaseResult addSysM(@RequestBody SysMSaveDto dto) {
        try {
            return sysMService.addSysM(dto);
        } catch (Exception e) {
            log.error("添加系统异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 根据id查询
     * @param dto
     * @return
     */
    @PostMapping("/getSysMVoById")
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    public BaseResult getSysMVoById(@RequestBody SysMQueryDto dto) {
        try {
            SysMVo vo = sysMService.getSysMVoById(dto);
            return BaseResult.successMsg("查询成功!", vo);
        } catch (Exception e) {
            log.error("根据id查询异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("根据id查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 编辑系统
     * @param dto
     * @return
     */
    @PostMapping("/editSysM")
    @ApiOperation(value = "编辑系统", notes = "编辑系统")
    public BaseResult editSysM(@RequestBody SysMSaveDto dto) {
        try {
            return sysMService.editSysM(dto);
        } catch (Exception e) {
            log.error("编辑系统异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("编辑系统异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 删除系统
     * @param sysId
     * @return
     */
    @GetMapping("/delSysM")
    @ApiOperation(value = "删除系统", notes = "删除系统")
    public BaseResult delSysM(@RequestParam String sysId) {
        try {
            boolean flag = sysMService.delSysM(sysId);
            return BaseResult.successMsg("删除系统完成!", flag);
        } catch (Exception e) {
            log.error("删除系统名称异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("删除系统异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 查询所属系统名称
     * @return
     */
    @PostMapping("/getSysMList")
    @ApiOperation(value = "查询所属系统名称", notes = "查询所属系统名称")
    public BaseResult getSysMList() {
        try {
            List<SysM> list = sysMService.getSysMList();
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询所属系统名称异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }


}
