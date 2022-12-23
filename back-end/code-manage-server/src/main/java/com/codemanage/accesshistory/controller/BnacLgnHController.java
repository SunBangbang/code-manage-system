package com.codemanage.accesshistory.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codemanage.accesshistory.dto.request.BnacLgnHQueryDto;
import com.codemanage.accesshistory.dto.request.SysOprtGQueryDto;
import com.codemanage.accesshistory.dto.response.BnacLgnHVo;
import com.codemanage.accesshistory.dto.response.SysOprtGVo;
import com.codemanage.accesshistory.service.IBnacLgnHService;
import com.codemanage.common.entity.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * CVSO_账号登录_历史表 前端控制器
 * </p>
 *
 * @author hyh
 * @since 2022-06-15
 */
@RestController
@RequestMapping("/accesshistory/bnacLgnH")
@Api(tags = "用户登录日志API")
@Slf4j
public class BnacLgnHController {

    @Autowired
    private IBnacLgnHService bnacLgnHService;

    @PostMapping("/getList")
    @ApiOperation(value = "查询用户登录日志")
    public BaseResult getList(@RequestBody(required = false) BnacLgnHQueryDto dto) {
        try {
            Page<BnacLgnHVo> list = bnacLgnHService.getList(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询列表异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }

}
