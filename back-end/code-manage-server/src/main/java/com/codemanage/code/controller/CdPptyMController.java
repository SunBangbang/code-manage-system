package com.codemanage.code.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.codemanage.code.dto.request.CdPptyMDelDto;
import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.dto.request.CdPptyMSaveDto;
import com.codemanage.code.dto.response.CdPptyMVo;
import com.codemanage.code.service.ICdPptyMService;
import com.codemanage.common.entity.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * CVCD_编码属性_主表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
@RestController
@RequestMapping("/code/cdPptyM")
@Api(tags = "编码属性API")
@Slf4j
public class CdPptyMController {

    @Autowired
    private ICdPptyMService pptyMService;

    /**
     * 1. 查询编码属性列表
     * 2. 添加编码属性
     * 3. 编辑编码属性
     * 4. 删除编码属性
     * 5. 批量删除编码属性
     */

    /**
     * 查询编码属性列表
     * @param dto
     * @return
     */
    @PostMapping("/getCdPptyMList")
    @ApiOperation(value = "查询编码属性列表", notes = "查询编码属性列表")
    public BaseResult getCdPptyMList(@RequestBody CdPptyMQueryDto dto) {
        try {
            List<CdPptyMVo> list = pptyMService.getCdPptyMList(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询编码属性异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 添加编码属性
     * @param dto
     * @return
     */
    @PostMapping("/addCdPptyM")
    @ApiOperation(value = "添加编码属性", notes = "添加编码属性")
    public BaseResult addCdPptyM(@RequestBody CdPptyMSaveDto dto) {
        try {
            return pptyMService.addCdPptyM(dto);
        } catch (Exception e) {
            log.error("添加编码属性异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 编辑编码属性
     * @param dto
     * @return
     */
    @PostMapping("/editCdPptyM")
    @ApiOperation(value = "编辑编码属性", notes = "编辑编码属性")
    public BaseResult editCdPptyM(@RequestBody CdPptyMSaveDto dto) {
        try {
            return pptyMService.editCdPptyM(dto);
        } catch (Exception e) {
            log.error("编辑编码属性异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("编辑异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 批量删除编码属性
     * @param delDto
     * @return
     */
    @PostMapping("/delBatchCdPptyM")
    @ApiOperation(value = "批量删除编码属性", notes = "批量删除编码属性")
    public BaseResult delBatchCdPptyM(@RequestBody CdPptyMDelDto delDto){
        try {
            if (CollectionUtils.isNotEmpty(delDto.getCvCdPptyIdList())) {
                boolean flag = pptyMService.delBatchCdPptyM(delDto);
                return BaseResult.successMsg("删除成功!", flag);
            } else {
                return BaseResult.failedMsg("缺少参数!", false);
            }
        } catch (Exception e) {
            log.error("批量删除编码属性异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 删除编码属性
     * @param id
     * @return
     */
    @Deprecated
    @GetMapping("/delCdPptyMById")
    @ApiOperation(value = "根据id删除编码属性", notes = "根据id删除编码属性")
    public BaseResult delCdPptyMById(@RequestParam String id){
        try {
            boolean flag = pptyMService.delCdPptyMById(id);
            return BaseResult.successMsg("删除成功!", flag);
        } catch (Exception e) {
            log.error("删除编码属性异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 批量删除编码属性
     * @param ids
     * @return
     */
    @Deprecated
    @PostMapping("/delBatchCdPptyMByIds")
    @ApiOperation(value = "批量删除编码属性", notes = "批量删除编码属性")
    public BaseResult delBatchCdPptyMByIds(@RequestBody List<String> ids){
        try {
            if (CollectionUtils.isNotEmpty(ids)) {
                boolean flag = pptyMService.delBatchCdPptyMByIds(ids);
                return BaseResult.successMsg("删除成功!", flag);
            } else {
                return BaseResult.failedMsg("缺少参数!", false);
            }
        } catch (Exception e) {
            log.error("批量删除编码属性异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }

}
