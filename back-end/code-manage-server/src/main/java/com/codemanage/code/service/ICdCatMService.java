package com.codemanage.code.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codemanage.code.dto.request.CdCatMQueryDto;
import com.codemanage.code.dto.request.CdCatMSaveDto;
import com.codemanage.code.dto.response.CdCatMVo;
import com.codemanage.code.entity.CdCatM;
import com.codemanage.common.entity.BaseResult;

import java.util.List;

/**
 * <p>
 * CVCD_编码分类_主表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
public interface ICdCatMService extends IService<CdCatM> {

    /**
     * 查询编码分类列表
     * @param dto
     * @return
     */
    List<CdCatMVo> getCatMList(CdCatMQueryDto dto);

    /**
     * 根据id查询Vo
     * @param id
     * @return
     */
    CdCatMVo getVoById(String id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdCatM getObjById(String id);

    /**
     * 添加编码分类
     * @param dto
     * @return
     */
    BaseResult addCatM(CdCatMSaveDto dto);

    /**
     * 编辑编码分类
     * @param dto
     * @return
     */
    BaseResult editCatM(CdCatMSaveDto dto);

    /**
     * 删除编码分类
     * @param id
     * @return
     */
    boolean delCatM(String id);

    /**
     * 批量删除编码分类
     * @param ids
     * @return
     */
    @Deprecated
    boolean delBatchCatM(List<String> ids);
}
