package com.codemanage.accesshistory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codemanage.accesshistory.dto.request.SysOprtGQueryDto;
import com.codemanage.accesshistory.dto.response.SysOprtGVo;
import com.codemanage.accesshistory.entity.SysOprtG;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysOprtGMapper extends BaseMapper<SysOprtG> {
    Page<SysOprtGVo> getList(Page<SysOprtGVo> page, SysOprtGQueryDto dto);
}
