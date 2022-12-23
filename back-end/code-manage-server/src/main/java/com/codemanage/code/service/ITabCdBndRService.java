package com.codemanage.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.dto.request.TabCdBndRDelDto;
import com.codemanage.code.dto.request.TabCdBndRSaveDto;
import com.codemanage.code.dto.response.CodeTableRelationVo;
import com.codemanage.code.entity.TabCdBndR;
import com.codemanage.common.entity.BaseResult;

import java.util.List;

/**
 * <p>
 * CVCD_表编码绑定_关系表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-08-17
 */
public interface ITabCdBndRService extends IService<TabCdBndR> {

    /**
     * 查询编码表关系
     * @param dto
     * @return
     */
    List<CodeTableRelationVo> getCodeTableRelation(CdPptyMQueryDto dto);

    /**
     * 删除编码表关系
     *
     * @param dtoList
     * @return
     */
    BaseResult delBatchTabCdBndR(List<TabCdBndRDelDto> dtoList);

    BaseResult addTabCdBndR(TabCdBndRSaveDto dto);
}
