package com.codemanage.system.controller;


import com.codemanage.common.entity.BaseResult;
import com.codemanage.system.dto.request.SchmMQueryDto;
import com.codemanage.system.dto.request.SchmMSaveDto;
import com.codemanage.system.dto.response.SchmMVo;
import com.codemanage.system.service.ISchmMService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * CVSO_数据库模式_主表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
@RestController
@RequestMapping("/system/schmM")
@Api(tags = "数据库模式API")
@Slf4j
public class SchmMController {

    @Autowired
    private ISchmMService schmMService;

    /**
     * 根据dbmsId查询Schm
     * 根据条件查询可用的模式
     * 根据id查询模式
     * 校验schm名称
     * 添加schm
     * 编辑弹窗查询详情
     * 编辑schm
     *  1. 添加模式（schema）
     *  2. 编辑模式（schema）
     *  3. 删除模式（schema）
     *  4. 查询选中的模式（schema）
     *  5. 模式名称-再次确认
     */


    /**
     * 根据dbmsId查询Schm
     * @return
     */
    @GetMapping("/getSchmByDbmsId")
    @ApiOperation(value = "根据dbmsId查询Schm", notes = "根据dbmsId查询Schm")
    public BaseResult getSchmByDbmsId(@RequestParam String dbmsId) {
        try {
            List<SchmMVo> list = schmMService.getSchmByDbmsId(dbmsId);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("根据dbmsId查询Schm异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询所有Schm异常,请联系管理员!", e.getMessage());
        }
    }



    /**
     * 根据条件查询可用的模式
     * @return
     */
    @PostMapping("/getSchmByCondition")
    @ApiOperation(value = "根据条件查询可用的模式", notes = "根据条件查询可用的模式")
    public BaseResult getSchmByCondition(@RequestBody SchmMQueryDto dto) {
        try {
            List<SchmMVo> list = schmMService.getSchmByCondition(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("根据条件查询可用的模式异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("根据条件查询可用的模式异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 根据id查询模式
     * @return
     */
    @PostMapping("/getSchmById")
    @ApiOperation(value = "根据id查询模式", notes = "根据id查询模式")
    public BaseResult getSchmById(@RequestBody SchmMQueryDto dto) {
        try {
            SchmMVo vo = schmMService.getSchmById(dto);
            return BaseResult.successMsg("查询成功!", vo);
        } catch (Exception e) {
            log.error("根据id查询模式异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("根据id查询模式异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 校验schm名称
     * @param dto
     * @return
     */
    @PostMapping("/checkSchmNm")
    @ApiOperation(value = "校验schm名称", notes = "校验schm名称")
    public BaseResult checkSchmNm(@RequestBody SchmMQueryDto dto) {
        try {
            boolean flag = schmMService.checkSchmNm(dto);
            return BaseResult.successMsg("校验完成!", flag);
        } catch (Exception e) {
            log.error("校验schm名称异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("校验异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 获取排序序号
     * @return
     */
    @GetMapping("/getSortSrno")
    @ApiOperation(value = "获取排序序号", notes = "获取排序序号")
    public BaseResult getSortSrno(String dbmsId) {
        try {
            Integer sortSrno = schmMService.getSortSrno(dbmsId);
            return BaseResult.successMsg("查询完成!", sortSrno);
        } catch (Exception e) {
            log.error("获取排序序号异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("获取排序序号异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 添加schm
     * @param dto
     * @return
     */
    @PostMapping("/addSchm")
    @ApiOperation(value = "添加schm", notes = "添加schm")
    public BaseResult addSchm(@RequestBody SchmMSaveDto dto) {
        try {
            return schmMService.addSchm(dto);
        } catch (Exception e) {
            log.error("添加schm异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加schm异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 编辑弹窗查询详情
     * @return
     */
    @PostMapping("/getSchmEditById")
    @ApiOperation(value = "编辑弹窗查询详情", notes = "编辑弹窗查询详情")
    public BaseResult getSchmEditById(@RequestBody SchmMQueryDto dto) {
        try {
            SchmMVo vo = schmMService.getSchmEditById(dto);
            return BaseResult.successMsg("查询成功!", vo);
        } catch (Exception e) {
            log.error("根据id查询模式异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("根据id查询模式异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 编辑schm
     * @param dto
     * @return
     */
    @PostMapping("/editSchm")
    @ApiOperation(value = "编辑schm", notes = "编辑schm")
    public BaseResult editSchm(@RequestBody SchmMSaveDto dto) {
        try {
            return schmMService.editSchm(dto);
        } catch (Exception e) {
            log.error("编辑schm异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("编辑schm异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 删除schm
     * @param dto
     * @return
     */
    @PostMapping("/delSchm")
    @ApiOperation(value = "删除schm", notes = "删除schm")
    public BaseResult delSchm(@RequestBody SchmMQueryDto dto) {
        try {
            boolean flag = schmMService.delSchm(dto);
            if (flag) {
                return BaseResult.successMsg("删除成功!");
            }
            return BaseResult.failedMsg("删除失败!");
        } catch (Exception e) {
            log.error("删除schm异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("删除schm异常,请联系管理员!", e.getMessage());
        }
    }
}
