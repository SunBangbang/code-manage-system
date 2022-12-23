package com.codemanage.code.controller;


import com.codemanage.code.dto.request.TabCdBndRDelDto;
import com.codemanage.code.dto.request.TabCdBndRSaveDto;
import com.codemanage.code.service.ITabCdBndRService;
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
 * CVCD_编码表关系 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-08-16
 */
@RestController
@RequestMapping("/code/tabCdBndR")
@Slf4j
@Api(tags = "编码表关系API")
public class TabCdBndRController {

    @Autowired
    private ITabCdBndRService tabCdBndRService;

    /**
     * 删除编码表关系
     * @param dtoList
     * @return
     */
    @PostMapping("/delBatchTabCdBndR")
    @ApiOperation(value = "删除编码表关系", notes = "删除编码表关系")
    public BaseResult delBatchTabCdBndR(@RequestBody List<TabCdBndRDelDto> dtoList) {
        try {
            return tabCdBndRService.delBatchTabCdBndR(dtoList);
        } catch (Exception e) {
            log.error("删除编码表关系异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 添加编码表关系
     * @param dto
     * @return
     */
    @PostMapping("/addTabCdBndR")
    @ApiOperation(value = "添加编码表关系", notes = "添加编码表关系")
    public BaseResult addCdR(@RequestBody TabCdBndRSaveDto dto) {
        try {
            return tabCdBndRService.addTabCdBndR(dto);
        } catch (Exception e) {
            log.error("添加编码表关系异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }
}
