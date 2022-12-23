package com.codemanage.code.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.codemanage.code.dto.request.CdCatMQueryDto;
import com.codemanage.code.dto.request.CdCatMSaveDto;
import com.codemanage.code.dto.response.CdCatMVo;
import com.codemanage.code.service.ICdCatMService;
import com.codemanage.common.entity.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * CVCD_编码分类_主表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/code/cdCatM")
@Api(tags = "编码分类API")
@Slf4j
public class CdCatMController {

    @Autowired
    private ICdCatMService catMService;

    /**
     * 1. 查询编码分类列表
     * 2. 根据id查询编码分类
     * 3. 添加编码分类
     * 4. 编辑编码分类
     * 5. 删除编码分类
     * 6. 批量删除编码分类
     */

    /**
     * 查询编码分类列表
     * @param dto
     * @return
     */
    @PostMapping("/getCatMList")
    @ApiOperation(value = "查询编码分类列表", notes = "查询编码分类列表")
    public BaseResult getCatMList(@RequestBody(required = false) CdCatMQueryDto dto) {
        try {
            List<CdCatMVo> list = catMService.getCatMList(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询编码分类列表异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 根据id查询编码分类
     * @param id
     * @return
     */
    @GetMapping("/getCatMById")
    @ApiOperation(value = "根据id查询编码分类", notes = "根据id查询编码分类")
    public BaseResult getCatMById(@RequestParam String id){
        try {
            CdCatMVo vo= catMService.getVoById(id);
            return BaseResult.successMsg("查询成功!", vo);
        } catch (Exception e) {
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 添加编码分类
     * @param dto
     * @return
     */
    @PostMapping("/addCatM")
    @ApiOperation(value = "添加编码分类", notes = "添加编码分类")
    public BaseResult addCatM(@RequestBody CdCatMSaveDto dto) {
        try {
            return catMService.addCatM(dto);
        } catch (Exception e) {
            log.error("添加编码分类异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 编辑编码分类
     * @param dto
     * @return
     */
    @PostMapping("/editCatM")
    @ApiOperation(value = "编辑编码分类", notes = "编辑编码分类")
    public BaseResult editCatM(@RequestBody CdCatMSaveDto dto) {
        try {
            return catMService.editCatM(dto);
        } catch (Exception e) {
            log.error("编辑编码分类异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("编辑异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 删除编码分类
     * @param id
     * @return
     */
    @GetMapping("/delCatM")
    @ApiOperation(value = "根据id删除编码分类", notes = "根据id删除编码分类")
    public BaseResult delCatM(@RequestParam String id){
        try {
            boolean flag = catMService.delCatM(id);
            return BaseResult.successMsg("删除成功!", flag);
        } catch (Exception e) {
            log.error("删除编码分类异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 批量删除编码分类
     * @param ids
     * @return
     */
    @Deprecated
    @PostMapping("/delBatchCatM")
    @ApiOperation(value = "批量删除编码分类", notes = "批量删除编码分类")
    public BaseResult delBatchCatM(@RequestBody List<String> ids){
        try {
            if (CollectionUtils.isNotEmpty(ids)) {
                boolean flag = catMService.delBatchCatM(ids);
                return BaseResult.successMsg("删除成功!", flag);
            } else {
                return BaseResult.failedMsg("缺少参数!", false);
            }
        } catch (Exception e) {
            log.error("批量删除编码分类异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }

}
