package com.codemanage.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.code.dto.request.CdCatMQueryDto;
import com.codemanage.code.dto.request.CdCatMSaveDto;
import com.codemanage.code.dto.response.CdCatMVo;
import com.codemanage.code.entity.CdCatM;
import com.codemanage.code.mapper.CdCatMMapper;
import com.codemanage.code.service.ICdCatMService;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.util.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * CVCD_编码分类_主表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
@Service
public class CdCatMServiceImpl extends ServiceImpl<CdCatMMapper, CdCatM> implements ICdCatMService {

    @Autowired
    private CdCatMMapper catMMapper;

    /**
     * 查询编码分类列表
     * @param dto
     * @return
     */
    @Override
    public List<CdCatMVo> getCatMList(CdCatMQueryDto dto) {
        return catMMapper.getCatMList(dto);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public CdCatMVo getVoById(String id) {
        return catMMapper.getVoById(id);
    }

    @Override
    public CdCatM getObjById(String id) {
        QueryWrapper<CdCatM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CdCatM::getCvCdCatId, id)
                .eq(CdCatM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.getOne(queryWrapper);
    }

    /**
     * 添加编码分类
     * @param dto
     * @return
     */
    @Override
    public BaseResult addCatM(CdCatMSaveDto dto) {
        CdCatM catM = new CdCatM();
        this.dtoToObj(catM, dto);
        String id = UUID.randomUUID().toString();
        catM.setCvCdCatId(id);
        catM.setRecBgnDttm(new Date());
        catM.setRecFnshDttm(Constants.FNSHDTTM);
        catM.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        boolean flag = this.save(catM);
        if (flag) {
            return BaseResult.successMsg("添加成功！");
        }
        return BaseResult.failedMsg("添加失败！");
    }

    /**
     * 编辑编码分类
     * @param dto
     * @return
     */
    @Override
    public BaseResult editCatM(CdCatMSaveDto dto) {
        Date oldFnshDttm = new Date();
        Date bgnDttm = new Date(oldFnshDttm.getTime() + 1);
        CdCatM oldObj = this.getObjById(dto.getCvCdCatId());
        this.updateFnlYn(dto.getCvCdCatId(), oldFnshDttm);
        this.dtoToObj(oldObj, dto);
        oldObj.setRecFnshDttm(Constants.FNSHDTTM);
        oldObj.setRecBgnDttm(bgnDttm);
        oldObj.setFnlYn(Constants.FnlYn.YES);
        oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        boolean flag = this.save(oldObj);
        if (flag) {
            return BaseResult.successMsg("编辑成功！");
        }
        return BaseResult.failedMsg("编辑失败！");
    }

    /**
     * 删除编码分类
     * @param id
     * @return
     */
    @Override
    public boolean delCatM(String id) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        return this.delById(id, oldFnshDttm, newBgnDttm);
    }

    /**
     * 批量删除编码分类
     * @param ids
     * @return
     */
    @Override
    public boolean delBatchCatM(List<String> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            Date oldFnshDttm = new Date();
            Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
            for (String id : ids) {
                this.delById(id, oldFnshDttm, newBgnDttm);
            }
            return true;
        }
        return false;
    }

    private boolean delById(String id, Date oldFnshDttm, Date newBgnDttm) {
        List<String> delIds = new ArrayList<>();
        List<String> childIds = catMMapper.getChildIdList(id);
        delIds.add(id);
        if (CollectionUtils.isNotEmpty(childIds)) {
            delIds.addAll(childIds);
        }
        List<CdCatM> oldList = this.getObjByIds(delIds);
        this.updateBatchFnlYn(delIds, oldFnshDttm);
        String createUserId = UserUtils.getLoginUser().getUserId();
        for (CdCatM obj : oldList) {
            obj.setRecBgnDttm(newBgnDttm);
            obj.setRecFnshDttm(newBgnDttm);
            obj.setFnlYn(Constants.FnlYn.NO);
            obj.setCretCvUserId(createUserId);
        }
        return this.saveBatch(oldList);
    }

    /**
     * 获取所有下级阶节点id
     * @param ids
     * @param parentId
     */
    @Deprecated
    private void getIds(List<String> ids, String parentId) {
        // 查询子评论信息
        QueryWrapper<CdCatM> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CdCatM::getHlvCvCdCatId, parentId);
        List<CdCatM> list = baseMapper.selectList(wrapper);
        // 如果子评论不为则，则取出每条评论的评论id
        if (CollectionUtils.isNotEmpty(list)) {
            for (CdCatM obj : list) {
                String id = obj.getCvCdCatId();
                // 将当前查询到评论id放到要删除的id集合中
                ids.add(id);
                // 递归继续查询子评论id
                this.getIds(ids, id);
            }
        }
    }


    private CdCatM dtoToObj (CdCatM obj, CdCatMSaveDto dto) {
        obj.setCdCatNm(dto.getCdCatNm());
        obj.setCdCatExplTxt(dto.getCdCatExplTxt());
        obj.setCvCdSphrId(dto.getCvCdSphrId());
        if (StringUtils.isNotEmpty(dto.getHlvCvCdCatId())){
            obj.setHlvCvCdCatId(dto.getHlvCvCdCatId());
        } else {
            obj.setHlvCvCdCatId("0");
        }
        return obj;
    }

    private boolean updateFnlYn(String catId, Date oldFnshDttm) {
        UpdateWrapper<CdCatM> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(CdCatM::getRecFnshDttm, oldFnshDttm)
                .set(CdCatM::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(CdCatM::getCvCdCatId, catId)
                .eq(CdCatM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }

    public List<CdCatM> getObjByIds(List<String> ids) {
        QueryWrapper<CdCatM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(CdCatM::getCvCdCatId, ids)
                .eq(CdCatM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }

    private boolean updateBatchFnlYn(List<String> ids, Date oldFnshDttm) {
        UpdateWrapper<CdCatM> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(CdCatM::getRecFnshDttm, oldFnshDttm)
                .set(CdCatM::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().in(CdCatM::getCvCdCatId, ids)
                .eq(CdCatM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }
}
