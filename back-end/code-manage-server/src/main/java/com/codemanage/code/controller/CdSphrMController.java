package com.codemanage.code.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.codemanage.code.dto.request.CdSphrMQueryDto;
import com.codemanage.code.dto.request.CdSphrMSaveDto;
import com.codemanage.code.dto.response.CdSphrMVo;
import com.codemanage.code.service.ICdSphrMService;
import com.codemanage.common.entity.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * CVCD_编码领域_主表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/code/cdSphrM")
@Slf4j
@Api(tags = "编码区API")
public class CdSphrMController {

    @Autowired
    private ICdSphrMService sphrMService;

    /**
     * 1. 查询编码区列表
     * 2. 根据id查询编码区
     * 3. 添加编码区
     * 4. 编辑编码区
     * 5. 删除编码区
     */

    /**
     * 查询编码区列表
     * @param dto
     * @return
     */
    @PostMapping("/getSphrMList")
    @ApiOperation(value = "查询编码区列表", notes = "查询编码区列表")
    public BaseResult getSphrMList(@RequestBody(required = false) CdSphrMQueryDto dto) {
        try {
            List<CdSphrMVo> list = sphrMService.getSphrMList(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询编码区列表异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 根据id查询编码区
     * @param id
     * @return
     */
    @GetMapping("/getSphrMById")
    @ApiOperation(value = "根据id查询编码区", notes = "根据id查询编码区")
    public BaseResult getSphrMById(@RequestParam String id){
        try {
            CdSphrMVo obj= sphrMService.getVoById(id);
            return BaseResult.successMsg("查询成功!", obj);
        } catch (Exception e) {
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 添加编码区
     * @param dto
     * @return
     */
    @PostMapping("/addSphrM")
    @ApiOperation(value = "添加编码区", notes = "添加编码区")
    public BaseResult addSphrM(@RequestBody CdSphrMSaveDto dto) {
        try {
            return sphrMService.addSphrM(dto);
        } catch (Exception e) {
            log.error("添加编码区异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 编辑编码区
     * @param dto
     * @return
     */
    @PostMapping("/editSphrM")
    @ApiOperation(value = "编辑编码区", notes = "编辑编码区")
    public BaseResult editSphrM(@RequestBody CdSphrMSaveDto dto) {
        try {
            return sphrMService.editSphrM(dto);
        } catch (Exception e) {
            log.error("编辑编码区异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("编辑异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 删除编码区
     * @param id
     * @return
     */
    @GetMapping("/delSphrM")
    @ApiOperation(value = "根据id删除编码区", notes = "根据id删除编码区")
    public BaseResult delSphrM(@RequestParam String id){
        try {
            boolean flag = sphrMService.delSphrM(id);
            return BaseResult.successMsg("删除成功!", flag);
        } catch (Exception e) {
            log.error("删除编码区异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 批量删除编码区
     * @param ids
     * @return
     */
    @Deprecated
    @PostMapping("/delBatchSphrM")
    @ApiOperation(value = "批量删除编码区", notes = "批量删除编码区")
    public BaseResult delBatchSphrM(@RequestBody List<String> ids){
        try {
            if (CollectionUtils.isNotEmpty(ids)) {
                boolean flag = sphrMService.delBatchSphrM(ids);
                return BaseResult.successMsg("删除成功!", flag);
            } else {
                return BaseResult.failedMsg("缺少参数!");
            }
        } catch (Exception e) {
            log.error("批量删除编码区异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }

}
