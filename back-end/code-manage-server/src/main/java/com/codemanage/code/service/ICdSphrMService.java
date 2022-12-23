package com.codemanage.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.code.dto.request.CdSphrMQueryDto;
import com.codemanage.code.dto.request.CdSphrMSaveDto;
import com.codemanage.code.dto.response.CdSphrMVo;
import com.codemanage.code.entity.CdSphrM;
import com.codemanage.common.entity.BaseResult;

import java.util.List;

/**
 * <p>
 * CVCD_编码领域_主表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
public interface ICdSphrMService extends IService<CdSphrM> {

    /**
     * 查询编码区列表
     * @param dto
     * @return
     */
    List<CdSphrMVo> getSphrMList(CdSphrMQueryDto dto);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdSphrMVo getVoById(String id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdSphrM getObjById(String id);

    /**
     * 添加编码区
     * @param dto
     * @return
     */
    BaseResult addSphrM(CdSphrMSaveDto dto);

    /**
     * 编辑编码区
     * @param dto
     * @return
     */
    BaseResult editSphrM(CdSphrMSaveDto dto);

    /**
     * 删除编码区
     * @param id
     * @return
     */
    boolean delSphrM(String id);

    /**
     * 批量删除编码区
     * @param ids
     * @return
     */
    @Deprecated
    boolean delBatchSphrM(List<String> ids);

}
