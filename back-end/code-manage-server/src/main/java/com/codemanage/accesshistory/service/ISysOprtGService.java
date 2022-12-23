package com.codemanage.accesshistory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.accesshistory.dto.request.SysOprtGQueryDto;
import com.codemanage.accesshistory.dto.request.SysOprtGSaveDto;
import com.codemanage.accesshistory.dto.response.SysOprtGVo;
import com.codemanage.accesshistory.entity.SysOprtG;

public interface ISysOprtGService extends IService<SysOprtG> {
    Page<SysOprtGVo> getList(SysOprtGQueryDto dto);

    boolean addSysOprtG(SysOprtGSaveDto dto);
}
