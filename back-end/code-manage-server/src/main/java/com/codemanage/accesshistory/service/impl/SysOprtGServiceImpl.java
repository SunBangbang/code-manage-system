package com.codemanage.accesshistory.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.accesshistory.dto.request.SysOprtGQueryDto;
import com.codemanage.accesshistory.dto.request.SysOprtGSaveDto;
import com.codemanage.accesshistory.dto.response.SysOprtGVo;
import com.codemanage.accesshistory.entity.SysOprtG;
import com.codemanage.accesshistory.mapper.SysOprtGMapper;
import com.codemanage.accesshistory.service.ISysOprtGService;
import com.codemanage.common.util.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class SysOprtGServiceImpl extends ServiceImpl<SysOprtGMapper, SysOprtG> implements ISysOprtGService {
    @Override
    public Page<SysOprtGVo> getList(SysOprtGQueryDto dto) {
        Page<SysOprtGVo> page = new Page<>(dto.getCurrent(), dto.getSize());
        return getBaseMapper().getList(page, dto);
    }

    @Override
    public boolean addSysOprtG(SysOprtGSaveDto dto) {
        SysOprtG obj = new SysOprtG();
        BeanUtils.copyProperties(dto, obj);
        String logSrno = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
        obj.setCvOprtLogSrno(logSrno);
        obj.setCvUserId(UserUtils.getLoginUser().getUserId());
        obj.setRecTimeDttm(new Date());
        return this.save(obj);
    }
}
