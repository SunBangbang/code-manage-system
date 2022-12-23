package com.codemanage.accesshistory.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codemanage.accesshistory.dto.request.BnacLgnHQueryDto;
import com.codemanage.accesshistory.dto.response.BnacLgnHVo;
import com.codemanage.accesshistory.dto.response.SysOprtGVo;
import com.codemanage.accesshistory.entity.BnacLgnH;
import com.codemanage.accesshistory.mapper.BnacLgnHMapper;
import com.codemanage.accesshistory.service.IBnacLgnHService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * CVSO_账号登录_历史表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-06-15
 */
@Service
public class BnacLgnHServiceImpl extends ServiceImpl<BnacLgnHMapper, BnacLgnH> implements IBnacLgnHService {

    @Override
    public Page<BnacLgnHVo> getList(BnacLgnHQueryDto dto) {
        Page<BnacLgnHVo> page = new Page<>(dto.getCurrent(), dto.getSize());
        return getBaseMapper().getList(page, dto);
    }
}
