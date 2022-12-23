package com.codemanage.code.controller;


import com.codemanage.code.entity.CmmnCdVlM;
import com.codemanage.code.service.ICmmnCdVlMService;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * CVCD_共通编码值_主表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-08-16
 */
@RestController
@RequestMapping("/code/cmmnCdVlM")
@Slf4j
@Api(tags = "公共编码值API")
public class CmmnCdVlMController {

    @Autowired
    private ICmmnCdVlMService cmmnCdVlMService;


    /**
     * 查询编码状态编码
     * @return
     */
    @PostMapping("/getCdStCdList")
    @ApiOperation(value = "查询编码状态编码", notes = "查询编码状态编码")
    public BaseResult getSphrMList() {
        try {
            List<CmmnCdVlM> list = cmmnCdVlMService.getByCdId(Constants.CmmnCdId.CD_ST_CD);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询编码状态编码异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 查询编码类型编码
     * @return
     */
    @PostMapping("/getCdTpCdList")
    @ApiOperation(value = "查询编码类型编码", notes = "查询编码类型编码")
    public BaseResult getCdTpCdList() {
        try {
            List<CmmnCdVlM> list = cmmnCdVlMService.getByCdId(Constants.CmmnCdId.CD_TP_CD);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询编码类型编码异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

    /**
     * 查询数据类型
     * @return
     */
    @PostMapping("/getDataTpList")
    @ApiOperation(value = "查询数据类型", notes = "查询数据类型")
    public BaseResult getDataTpList() {
        try {
            List<CmmnCdVlM> list = cmmnCdVlMService.getByCdId(Constants.CmmnCdId.DATA_TP);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询数据类型异常: " + e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }
}
