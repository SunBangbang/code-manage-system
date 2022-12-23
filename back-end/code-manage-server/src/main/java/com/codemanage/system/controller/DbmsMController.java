package com.codemanage.system.controller;


import com.codemanage.common.entity.BaseResult;
import com.codemanage.system.dto.request.DbmsQueryDto;
import com.codemanage.system.dto.request.DbmsSaveDto;
import com.codemanage.system.dto.response.DbmsMVo;
import com.codemanage.system.dto.response.DbmsTreeDto;
import com.codemanage.system.service.IDbmsMService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * CVSO_数据库管理系统_主表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
@RestController
@RequestMapping("/system/dbmsM")
@Api(tags = "数据库API")
@Slf4j
public class DbmsMController {

    @Autowired
    private IDbmsMService dbmsMService;

    /**
     * 查询所有DBMS
     * 查询可用的DBMS
     * 根据id查询DBMS
     * 查询DBMS树结构
     * 校验DBMS名称
     * 获取排序序号
     * 添加DBMS
     * 编辑DBMS
     * 删除DBMS
     *
     *  7. IP-再次确认
     *  8. 用户账号-再次确认
     */

    /**
     * 查询所有DBMS
     * @return
     */
    @GetMapping("/getAllDbms")
    @ApiOperation(value = "查询所有DBMS", notes = "查询所有DBMS")
    public BaseResult getAllDbms() {
        try {
            List<DbmsMVo> list = dbmsMService.getAllDbms();
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询所有DBMS异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询所有DBMS异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 查询可用的DBMS
     * @return
     */
    @GetMapping("/getDbmsList")
    @ApiOperation(value = "查询可用的DBMS", notes = "查询可用的DBMS")
    public BaseResult getDbmsList() {
        try {
            List<DbmsMVo> list = dbmsMService.getDbmsList();
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询可用的DBMS异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询可用的DBMS异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 根据id查询DBMS
     * @param dbmsId
     * @param recBgnDttm
     * @return
     */
    @GetMapping("/getDbmsById")
    @ApiOperation(value = "根据id查询DBMS", notes = "根据id查询DBMS")
    public BaseResult getDbmsById(@RequestParam String dbmsId,  @RequestParam String recBgnDttm) {
        try {
            DbmsMVo vo = dbmsMService.getDbmsById(dbmsId, recBgnDttm);
            return BaseResult.successMsg("查询成功!", vo);
        } catch (Exception e) {
            log.error("根据id查询DBMS异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("根据id查询DBMS异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 查询DBMS树结构
     * @param dto
     * @return
     */
    @PostMapping("/getDbmsTree")
    @ApiOperation(value = "查询DBMS树", notes = "查询DBMS树")
    public BaseResult getDbmsTree(@RequestBody DbmsQueryDto dto) {
        try {
            List<DbmsTreeDto> list = dbmsMService.getDbmsTree(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询DBMS树异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询DBMS树异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 校验DBMS名称
     * @param dbmsNm
     * @return
     */
    @GetMapping("/checkDdmsNm")
    @ApiOperation(value = "校验DBMS名称", notes = "校验DBMS名称")
    public BaseResult checkDdmsNm(@RequestParam String dbmsNm) {
        try {
            boolean flag = dbmsMService.checkDdmsNm(dbmsNm);
            return BaseResult.successMsg("校验完成!", flag);
        } catch (Exception e) {
            log.error("校验DBMS名称异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("校验异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 获取排序序号
     * @return
     */
    @GetMapping("/getSortSrno")
    @ApiOperation(value = "获取排序序号", notes = "获取排序序号")
    public BaseResult getSortSrno() {
        try {
            Integer sortSrno = dbmsMService.getSortSrno();
            return BaseResult.successMsg("查询完成!", sortSrno);
        } catch (Exception e) {
            log.error("获取排序序号异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("获取排序序号异常,请联系管理员!", e.getMessage());
        }
    }


    /**
     * 添加DBMS
     * @param dto
     * @return
     */
    @PostMapping("/addDbms")
    @ApiOperation(value = "添加DBMS", notes = "添加DBMS")
    public BaseResult addDbms(@RequestBody DbmsSaveDto dto) {
        try {
            return dbmsMService.addDbms(dto);
        } catch (Exception e) {
            log.error("添加DBMS异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("添加异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 编辑DBMS
     * @param dto
     * @return
     */
    @PostMapping("/editDbms")
    @ApiOperation(value = "编辑DBMS", notes = "编辑DBMS")
    public BaseResult editDbms(@RequestBody DbmsSaveDto dto) {
        try {
            return dbmsMService.editDbms(dto);
        } catch (Exception e) {
            log.error("编辑DBMS异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("编辑异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 删除DBMS
     * @return
     */
    @GetMapping("/delDbms")
    @ApiOperation(value = "删除DBMS", notes = "删除DBMS")
    public BaseResult delDbms(@RequestParam String dbmsId, @RequestParam String recBgnDttm) {
        try {
            boolean flag = dbmsMService.delDbms(dbmsId, recBgnDttm);
            if (flag) {
                return BaseResult.successMsg("删除成功!");
            }
            return BaseResult.failedMsg("删除失败!");
        } catch (Exception e) {
            log.error("根据id查询DBMS异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("根据id查询DBMS异常,请联系管理员!", e.getMessage());
        }
    }
}
