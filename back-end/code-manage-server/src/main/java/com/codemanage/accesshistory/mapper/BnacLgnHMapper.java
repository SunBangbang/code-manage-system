package com.codemanage.accesshistory.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codemanage.accesshistory.dto.request.BnacLgnHQueryDto;
import com.codemanage.accesshistory.dto.request.SysOprtGQueryDto;
import com.codemanage.accesshistory.dto.response.BnacLgnHVo;
import com.codemanage.accesshistory.dto.response.SysOprtGVo;
import com.codemanage.accesshistory.entity.BnacLgnH;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * CVSO_账号登录_历史表 Mapper 接口
 * </p>
 *
 * @author hyh
 * @since 2022-06-15
 */
@Mapper
public interface BnacLgnHMapper extends BaseMapper<BnacLgnH> {
    Page<BnacLgnHVo> getList(Page<BnacLgnHVo> page, BnacLgnHQueryDto dto);
}
