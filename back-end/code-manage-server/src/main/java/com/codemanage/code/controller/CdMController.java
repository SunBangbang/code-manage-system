package com.codemanage.code.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.codemanage.code.dto.request.*;
import com.codemanage.code.dto.response.*;
import com.codemanage.code.service.ICdMService;
import com.codemanage.common.entity.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * CVCD_编码_主表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/code/cdM")
@Api(tags = "编码API")
@Slf4j
public class CdMController {

    @Autowired
    private ICdMService cdMService;


    /**
     * 1. 分页查询编码
     * 2. 根据id查询编码
     * 3. 添加编码
     * 4. 编辑编码
     * 5，批量删除编码
     */

    /**
     * 分页查询编码
     *
     * @param dto
     * @return
     */
    @PostMapping("/getCdMByPage")
    @ApiOperation(value = "分页查询编码", notes = "分页查询编码")
    public BaseResult getCdMByPage(@RequestBody CdMQueryDto dto) {
        try {
            if (dto.getCurrentPage() != null && dto.getPageSize() != null) {
                IPage<CdMVo> page = cdMService.getCdMByPage(dto);
                return BaseResult.successMsg("查询成功!", page);
            } else {
                return BaseResult.failedMsg("缺少分页参数！");
            }
        } catch (Exception e) {
            log.error("分页查询编码异常：" + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 根据id查询编码
     *
     * @param id
     * @return
     */
    @GetMapping("/getCdMById")
    @ApiOperation(value = "根据id查询编码", notes = "根据id查询编码")
    public BaseResult getCdMById(@RequestParam String id) {
        try {
            CdMVo obj = cdMService.getCdMById(id);
            return BaseResult.successMsg("查询成功!", obj);
        } catch (Exception e) {
            log.error("根据id查询编码异常：" + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }


    /**
     * 添加编码
     *
     * @param dto
     * @return
     */
    @PostMapping("/addCdM")
    @ApiOperation(value = "添加编码", notes = "添加编码")
    public BaseResult addCdM(@RequestBody CdMSaveDto dto) {
        try {
            return cdMService.addCdM(dto);
        } catch (Exception e) {
            log.error("添加编码异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 编辑编码
     *
     * @param dto
     * @return
     */
    @PostMapping("/editCdM")
    @ApiOperation(value = "编辑编码", notes = "编辑编码")
    public BaseResult editCdM(@RequestBody CdMSaveDto dto) {
        try {
            return cdMService.editCdM(dto);
        } catch (Exception e) {
            log.error("编辑编码异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("编辑异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 批量删除编码值
     *
     * @param ids
     * @return
     */
    @PostMapping("/delBatchCdM")
    @ApiOperation(value = "批量删除编码", notes = "批量删除编码")
    public BaseResult delBatchCdM(@RequestBody List<String> ids) {
        try {
            if (CollectionUtils.isNotEmpty(ids)) {
                boolean flag = cdMService.delBatchCdM(ids);
                return BaseResult.successMsg("删除成功!", flag);
            } else {
                return BaseResult.failedMsg("缺少参数!", false);
            }
        } catch (Exception e) {
            log.error("批量删除编码异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }


    /**
     * 批量删除编码
     *
     * @param ids
     * @return
     */
    @Deprecated
    @PostMapping("/delBatchCdMByIds")
    @ApiOperation(value = "批量删除编码", notes = "批量删除编码")
    public BaseResult delBatchCdMByIds(@RequestBody List<String> ids) {
        try {
            if (CollectionUtils.isNotEmpty(ids)) {
                boolean flag = cdMService.removeBatchByIds(ids);
                return BaseResult.successMsg("删除成功!", flag);
            } else {
                return BaseResult.failedMsg("缺少参数!", false);
            }
        } catch (Exception e) {
            log.error("批量删除编码异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 查询编码属性列表
     *
     * @param dto
     * @return
     */
    @PostMapping("/getCdPptyMList")
    @ApiOperation(value = "查询编码属性列表", notes = "查询编码属性列表")
    public BaseResult getCdPptyMList(@RequestBody CdPptyMQueryDto dto) {
        try {
            List<CdPptyMVo> list = cdMService.getCdPptyMList(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询编码属性异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 查询编码属性值
     *
     * @param dto
     * @return
     */
    @Deprecated
    @PostMapping("/getPtyvList")
    @ApiOperation(value = "查询编码属性值", notes = "查询编码属性值")
    public BaseResult getPtyvList(@RequestBody CdPptyMQueryDto dto) {
        try {
            List<Map<String, String>> list = cdMService.getPtyvList(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询编码属性异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 查询编码属性值
     *
     * @param dto
     * @return
     */
    @PostMapping("/getCdPtyvList")
    @ApiOperation(value = "查询编码属性值", notes = "查询编码属性值")
    public BaseResult getCdPtyvList(@RequestBody CdPptyMQueryDto dto) {
        try {
            Map<String, Object> map = cdMService.getCdPtyvList(dto);
            return BaseResult.successMsg("查询成功!", map);
        } catch (Exception e) {
            log.error("查询编码属性值异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }


    /**
     * 查询编码编码关系
     *
     * @param dto
     * @return
     */
    @PostMapping("/getCodeCodeRelation")
    @ApiOperation(value = "查询编码编码关系", notes = "查询编码编码关系")
    public BaseResult getCodeCodeRelation(@RequestBody CdPptyMQueryDto dto) {
        try {
            List<CodeCodeRelationVo> list = cdMService.getCodeCodeRelation(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询编码编码关系异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 查询编码属性关系
     *
     * @param dto
     * @return
     */
    @PostMapping("/getCodeAttributeRelation")
    @ApiOperation(value = "查询编码属性关系", notes = "查询编码属性关系")
    public BaseResult getCodeAttributeRelation(@RequestBody CdPptyMQueryDto dto) {
        try {
            List<CodeAttributeRelationVo> list = cdMService.getCodeAttributeRelation(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询编码属性关系异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 查询编码值关系
     *
     * @param dto
     * @return
     */
    @PostMapping("/getCodeValueRelation")
    @ApiOperation(value = "查询编码值关系", notes = "查询编码值关系")
    public BaseResult getCodeValueRelation(@RequestBody CdPptyRQueryDto dto) {
        try {
            Map<String, Object> map = cdMService.getCodeValueRelation(dto);
            return BaseResult.successMsg("查询成功!", map);
        } catch (Exception e) {
            log.error("查询编码值关系异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 查询编码表关系
     *
     * @param dto
     * @return
     */
    @PostMapping("/getCodeTableRelation")
    @ApiOperation(value = "查询编码表关系", notes = "查询编码表关系")
    public BaseResult getCodeTableRelation(@RequestBody CdPptyMQueryDto dto) {
        try {
            List<CodeTableRelationVo> list = cdMService.getCodeTableRelation(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询编码表关系异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 查询编码属性列关系
     *
     * @param dto
     * @return
     */
    @PostMapping("/getCodeAttrColumnRelation")
    @ApiOperation(value = "查询编码属性列关系", notes = "查询编码属性列关系")
    public BaseResult getCodeAttrColumnRelation(@RequestBody CodeAttrColumnRQueryDto dto) {
        try {
            List<CodeAttrColumnRelationVo> list = cdMService.getCodeAttrColumnRelation(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询编码属性列关系异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }
}
