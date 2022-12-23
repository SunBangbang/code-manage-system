package com.codemanage.accesshistory.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codemanage.accesshistory.dto.request.UserOnliHQueryDto;
import com.codemanage.accesshistory.dto.response.UserOnliHVo;
import com.codemanage.accesshistory.service.IUserOnliHService;
import com.codemanage.accesshistory.service.impl.UserOnliHServiceImpl;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.system.dto.response.UserBasiMVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accesshistory/userOnliH")
@Api(tags = "用户在线历史API")
@Slf4j
public class UserOnliHController {

    @Autowired
    private IUserOnliHService userOnliHService;

    @PostMapping("/getList")
    @ApiOperation(value = "查询用户在线日志列表")
    public BaseResult getList(@RequestBody(required = false)UserOnliHQueryDto dto) {
        try {
            Page<UserOnliHVo> list = userOnliHService.getList(dto);
            return BaseResult.successMsg("查询成功!", list);
        } catch (Exception e) {
            log.error("查询列表异常: " +e.getMessage(), e);
            return BaseResult.failedMsg("查询异常,请联系管理员!", e.getMessage());
        }
    }
}
