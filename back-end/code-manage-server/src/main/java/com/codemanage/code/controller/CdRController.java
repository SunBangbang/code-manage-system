package com.codemanage.code.controller;


import com.codemanage.code.dto.request.CdRSaveDto;
import com.codemanage.code.service.ICdRService;
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
 * CVCD_编码_关系表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
@RestController
@RequestMapping("/code/cdR")
@Slf4j
@Api(tags = "编码关系API")
public class CdRController {

    @Autowired
    private ICdRService cdRService;

    /**
     * 添加编码编码关系
     * @param dto
     * @return
     */
    @PostMapping("/addCdR")
    @ApiOperation(value = "添加编码编码关系", notes = "添加编码编码关系")
    public BaseResult addCdR(@RequestBody CdRSaveDto dto) {
        try {
            return cdRService.addCdR(dto);
        } catch (Exception e) {
            log.error("添加编码关系异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }


    /**
     * 删除编码编码关系
     * @param dtoList
     * @return
     */
    @PostMapping("/delCdR")
    @ApiOperation(value = "删除编码编码关系", notes = "删除编码编码关系")
    public BaseResult delCdR(@RequestBody List<CdRSaveDto> dtoList) {
        try {
            return cdRService.delCdR(dtoList);
        } catch (Exception e) {
            log.error("添加编码关系异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }


}
