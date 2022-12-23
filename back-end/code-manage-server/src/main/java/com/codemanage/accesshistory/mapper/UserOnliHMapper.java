package com.codemanage.accesshistory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codemanage.accesshistory.dto.request.UserOnliHQueryDto;
import com.codemanage.accesshistory.dto.response.UserOnliHVo;
import com.codemanage.accesshistory.entity.UserOnliH;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserOnliHMapper extends BaseMapper<UserOnliH> {

    Page<UserOnliHVo> getList(Page<UserOnliHVo> page, UserOnliHQueryDto dto);
}
