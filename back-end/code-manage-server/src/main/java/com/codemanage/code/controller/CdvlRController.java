package com.codemanage.code.controller;


import com.codemanage.code.dto.request.CdvlRQueryDto;
import com.codemanage.code.dto.request.CdvlRSaveDto;
import com.codemanage.code.dto.response.CdvlRVo;
import com.codemanage.code.service.ICdvlRService;
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
 * CVCD_编码值_关系表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-08-16
 */
@RestController
@RequestMapping("/code/cdvlR")
@Slf4j
@Api(tags = "编码值关系API")
public class CdvlRController {

    @Autowired
    private ICdvlRService cdvlRService;

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
    @PostMapping("/getCdvlR")
    @ApiOperation(value = "查询映射关系", notes = "查询映射关系")
    public BaseResult getCdvlR(@RequestBody CdvlRQueryDto dto) {
        try {
            List<CdvlRVo> list = cdvlRService.getCdvlR(dto);
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
    @PostMapping("/addCdvlR")
    @ApiOperation(value = "映射", notes = "映射")
    public BaseResult addCdvlR(@RequestBody CdvlRSaveDto dto) {
        try {
            return cdvlRService.addCdvlR(dto);
        } catch (Exception e) {
            log.error("添加映射异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 自动映射
     * @param dtoList
     * @return
     */
    @PostMapping("/addBatchCdvlR")
    @ApiOperation(value = "自动映射", notes = "自动映射")
    public BaseResult addBatchCdvlR(@RequestBody CdvlRSaveDto dtoList) {
        try {
            return cdvlRService.addBatchCdvlR(dtoList);
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
    @PostMapping("/delBatchCdvlR")
    @ApiOperation(value = "删除映射", notes = "删除映射")
    public BaseResult delBatchCdvlR(@RequestBody List<CdvlRSaveDto> dtoList) {
        try {
            return cdvlRService.delBatchCdvlR(dtoList);
        } catch (Exception e) {
            log.error("删除映射异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }
}
