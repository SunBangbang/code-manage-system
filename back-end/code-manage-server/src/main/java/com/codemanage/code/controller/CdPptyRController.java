package com.codemanage.code.controller;


import com.codemanage.code.dto.request.CdPptyRAutoSaveDto;
import com.codemanage.code.dto.request.CdPptyRQueryDto;
import com.codemanage.code.dto.request.CdPptyRSaveDto;
import com.codemanage.code.dto.response.CdPptyRVo;
import com.codemanage.code.service.ICdPptyRService;
import com.codemanage.common.entity.BaseResult;
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
 * CVCD_编码属性_关系表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
@RestController
@RequestMapping("/code/cdPptyR")
@Slf4j
@Api(tags = "编码属性关系API")
public class CdPptyRController {

    @Autowired
    private ICdPptyRService pptyRService;

    /**
     * 查询映射关系
     * 添加映射关系
     * 自动映射
     * 删除映射
     */

    /**
     * 查询映射关系
     * @param dto
     * @return
     */
    @PostMapping("/getPptyR")
    @ApiOperation(value = "查询映射关系", notes = "查询映射关系")
    public BaseResult getPptyR(@RequestBody CdPptyRQueryDto dto) {
        try {
            List<CdPptyRVo> list = pptyRService.getPptyR(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询映射关系异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询映射关系异常,请联系管理员!", e.getMessage());
        }
    }


    /**
     * 添加映射关系
     * @param dto
     * @return
     */
    @PostMapping("/addPptyR")
    @ApiOperation(value = "映射", notes = "映射")
    public BaseResult addPptyR(@RequestBody CdPptyRSaveDto dto) {
        try {
            return pptyRService.addPptyR(dto);
        } catch (Exception e) {
            log.error("添加映射异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 自动映射
     * @param dto
     * @return
     */
    @PostMapping("/addBatchPptyR")
    @ApiOperation(value = "自动映射", notes = "自动映射")
    public BaseResult addBatchPptyR(@RequestBody CdPptyRAutoSaveDto dto) {
        try {
            return pptyRService.addBatchPptyR(dto);
        } catch (Exception e) {
            log.error("自动映射异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 删除映射
     * @param dtoList
     * @return
     */
    @PostMapping("/delBatchPptyR")
    @ApiOperation(value = "删除映射", notes = "删除映射")
    public BaseResult delBatchPptyR(@RequestBody List<CdPptyRSaveDto> dtoList) {
        try {
            return pptyRService.delBatchPptyR(dtoList);
        } catch (Exception e) {
            log.error("删除映射异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }



}
