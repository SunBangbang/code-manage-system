package com.codemanage.accesshistory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codemanage.accesshistory.dto.request.BnacLgnHQueryDto;
import com.codemanage.accesshistory.dto.request.SysOprtGQueryDto;
import com.codemanage.accesshistory.dto.response.BnacLgnHVo;
import com.codemanage.accesshistory.dto.response.SysOprtGVo;
import com.codemanage.accesshistory.entity.BnacLgnH;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * CVSO_账号登录_历史表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-06-15
 */
public interface IBnacLgnHService extends IService<BnacLgnH> {
    Page<BnacLgnHVo> getList(BnacLgnHQueryDto dto);
}
