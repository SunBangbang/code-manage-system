package com.codemanage.code.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.code.dto.request.CdvlMDelDto;
import com.codemanage.code.dto.request.CdvlMDto;
import com.codemanage.code.entity.CdvlM;

/**
 * <p>
 * CVCD_编码值_主表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
public interface ICdvlMService extends IService<CdvlM> {

    /**
     * 分页查询编码值
     * @param dto
     * @return
     */
    IPage<CdvlM> getCdvlMByPage(CdvlMDto dto);

    boolean delBatchCdvlM(CdvlMDelDto delDto);

    long countByCvCdId(String cvCdId);

    CdvlM getObjById(String cvCdvlId);

    void update(CdvlM cdvlM, String newValue);
}
