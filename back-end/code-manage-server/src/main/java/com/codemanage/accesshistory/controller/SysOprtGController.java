package com.codemanage.accesshistory.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codemanage.accesshistory.dto.request.SysOprtGQueryDto;
import com.codemanage.accesshistory.dto.request.SysOprtGSaveDto;
import com.codemanage.accesshistory.dto.response.SysOprtGVo;
import com.codemanage.accesshistory.service.ISysOprtGService;
import com.codemanage.common.entity.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accesshistory/sysOprtG")
@Api(tags = "用户操作日志API")
@Slf4j
public class SysOprtGController {

    @Autowired
    private ISysOprtGService sysOprtGService;

    @PostMapping("/getList")
    @ApiOperation(value = "查询用户操作日志列表")
    public BaseResult getList(@RequestBody(required = false) SysOprtGQueryDto dto) {
        try {
            Page<SysOprtGVo> list = sysOprtGService.getList(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询列表异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }


    @PostMapping("/addSysOprtG")
    @ApiOperation(value = "保存操作日志")
    public BaseResult addSysOprtG( @RequestBody SysOprtGSaveDto dto) {
        try {
            boolean flag = sysOprtGService.addSysOprtG(dto);
            return BaseResult.successMsg("保存成功!", flag);
        } catch (Exception e) {
            log.error("保存异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("保存异常,请联系管理员!", e.getMessage());
        }
    }
}
