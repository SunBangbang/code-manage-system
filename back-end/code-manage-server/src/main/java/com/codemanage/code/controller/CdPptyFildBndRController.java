package com.codemanage.code.controller;


import com.codemanage.code.dto.request.CdPptyFildBndRSaveDto;
import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.dto.request.CodeAttrColumnRQueryDto;
import com.codemanage.code.dto.response.CdPptyFildBndRVo;
import com.codemanage.code.entity.CdPptyM;
import com.codemanage.code.service.ICdPptyFildBndRService;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.system.dto.request.DbFildMQueryDto;
import com.codemanage.system.entity.DbFildM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * CVCD_编码属性字段绑定_关系表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-08-17
 */
@RestController
@RequestMapping("/code/cdPptyFildBndR")
@Slf4j
@Api(tags = "编码属性专栏关系API")
public class CdPptyFildBndRController {

    @Autowired
    private ICdPptyFildBndRService pptyFildBndRService;

    @PostMapping("/getMappingLeftData")
    @ApiOperation(value = "查询映射关系左边", notes = "查询映射关系")
    public BaseResult getMappingLeftData(@RequestBody CdPptyMQueryDto dto) {
        try {
            List<CdPptyM> list = pptyFildBndRService.getMappingLeftData(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询映射关系异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询映射关系异常,请联系管理员!", e.getMessage());
        }
    }

    @PostMapping("/getMappingRightData")
    @ApiOperation(value = "查询映射关系右边", notes = "查询映射关系")
    public BaseResult getMappingRightData(@RequestBody DbFildMQueryDto dto) {
        try {
            List<DbFildM> list = pptyFildBndRService.getMappingRightData(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询映射关系异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询映射关系异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 查询映射关系
     *
     * @param dto
     * @return
     */
    @PostMapping("/getPptyFildBndR")
    @ApiOperation(value = "查询映射关系", notes = "查询映射关系")
    public BaseResult getPptyFildBndR(@RequestBody CodeAttrColumnRQueryDto dto) {
        try {
            List<CdPptyFildBndRVo> list = pptyFildBndRService.getPptyFildBndR(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询映射关系异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询映射关系异常,请联系管理员!", e.getMessage());
        }
    }


    /**
     * 添加映射关系
     *
     * @param dto
     * @return
     */
    @PostMapping("/addPptyFildBndR")
    @ApiOperation(value = "映射", notes = "映射")
    public BaseResult addPptyFildBndR(@RequestBody CdPptyFildBndRSaveDto dto) {
        try {
            return pptyFildBndRService.addPptyFildBndR(dto);
        } catch (Exception e) {
            log.error("添加映射异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 自动映射
     *
     * @param dtoList
     * @return
     */
    @PostMapping("/addBatchPptyFildBndR")
    @ApiOperation(value = "自动映射", notes = "自动映射")
    public BaseResult addBatchPptyFildBndR(@RequestBody CdPptyFildBndRSaveDto dtoList) {
        try {
            return pptyFildBndRService.addBatchPptyFildBndR(dtoList);
        } catch (Exception e) {
            log.error("自动映射异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 删除映射
     *
     * @param dtoList
     * @return
     */
    @PostMapping("/delBatchPptyFildBndR")
    @ApiOperation(value = "删除映射", notes = "删除映射")
    public BaseResult delBatchPptyFildBndR(@RequestBody List<CdPptyFildBndRSaveDto> dtoList) {
        try {
            return pptyFildBndRService.delBatchPptyFildBndR(dtoList);
        } catch (Exception e) {
            log.error("删除映射异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }


}
