package com.codemanage.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.code.dto.request.CdvlRQueryDto;
import com.codemanage.code.dto.request.CdvlRSaveDto;
import com.codemanage.code.dto.response.CdvlRVo;
import com.codemanage.code.entity.CdvlR;
import com.codemanage.common.entity.BaseResult;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVCD_编码值_关系表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-08-16
 */
public interface ICdvlRService extends IService<CdvlR> {

    /**
     * 查询映射关系
     * @param dto
     * @return
     */
    List<CdvlRVo> getCdvlR(CdvlRQueryDto dto);

    /**
     * 添加映射关系
     * @param dto
     * @return
     */
    BaseResult addCdvlR(CdvlRSaveDto dto);

    /**
     * 自动映射
     * @param dtoList
     * @return
     */
    BaseResult addBatchCdvlR(CdvlRSaveDto dtoList);

    /**
     * 删除映射
     * @param dtoList
     * @return
     */
    BaseResult delBatchCdvlR(List<CdvlRSaveDto> dtoList);

    boolean delByCdvlM(String cvCdId, List<String> cvCdvlIdList, Date oldFnshDttm, Date newBgnDttm);
}
