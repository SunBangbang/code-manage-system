package com.codemanage.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.entity.CdPtyvM;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * CVCD_编码属性值_主表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-08-02
 */
public interface ICdPtyvMService extends IService<CdPtyvM> {

    /**
     * 查询编码属性值
     * @param dto
     * @return
     */
    @Deprecated
    List<Map<String, String>> getPtyvList(CdPptyMQueryDto dto);

    List<Map<String, String>> getDataListMap(int count, CdPptyMQueryDto dto);

    List<CdPtyvM> getListByCdvlM(String cvCdId, String cvCdvlId);

    void update(CdPtyvM item, String newValue);
}
