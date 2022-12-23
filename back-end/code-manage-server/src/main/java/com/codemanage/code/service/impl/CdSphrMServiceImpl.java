package com.codemanage.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.code.dto.request.CdSphrMQueryDto;
import com.codemanage.code.dto.request.CdSphrMSaveDto;
import com.codemanage.code.dto.response.CdSphrMVo;
import com.codemanage.code.entity.CdSphrM;
import com.codemanage.code.mapper.CdSphrMMapper;
import com.codemanage.code.service.ICdSphrMService;
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
 * CVCD_编码领域_主表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
@Service
public class CdSphrMServiceImpl extends ServiceImpl<CdSphrMMapper, CdSphrM> implements ICdSphrMService {

    @Autowired
    private CdSphrMMapper sphrMMapper;

    /**
     * 查询编码区列表
     * @param dto
     * @return
     */
    @Override
    public List<CdSphrMVo> getSphrMList(CdSphrMQueryDto dto) {
        return sphrMMapper.getSphrMList(dto);
    }

    @Override
    public CdSphrMVo getVoById(String id) {
        return sphrMMapper.getVoById(id);
    }

    @Override
    public CdSphrM getObjById(String id) {
        QueryWrapper<CdSphrM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CdSphrM::getCvCdSphrId, id)
                .eq(CdSphrM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.getOne(queryWrapper);
    }

    /**
     * 添加编码区
     * @param dto
     * @return
     */
    @Override
    public BaseResult addSphrM(CdSphrMSaveDto dto) {
        CdSphrM sphrM = new CdSphrM();
        this.dtoToObj(sphrM, dto);
        String id = UUID.randomUUID().toString();
        sphrM.setCvCdSphrId(id);
        sphrM.setRecBgnDttm(new Date());
        sphrM.setRecFnshDttm(Constants.FNSHDTTM);
        sphrM.setCvCdSphrDstsCd(Constants.SphrDstsCd.TYPE_ONE);
        sphrM.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        boolean flag = this.save(sphrM);
        if (flag) {
            return BaseResult.successMsg("添加成功！");
        }
        return BaseResult.failedMsg("添加失败！");
    }

    /**
     * 编辑编码区
     * @param dto
     * @return
     */
    @Override
    public BaseResult editSphrM(CdSphrMSaveDto dto) {
        Date oldFnshDttm = new Date();
        Date bgnDttm = new Date(oldFnshDttm.getTime() + 1);
        CdSphrM oldSphrM = this.getObjById(dto.getCvCdSphrId());
        this.updateFnlYn(dto.getCvCdSphrId(), oldFnshDttm);
        this.dtoToObj(oldSphrM, dto);
        oldSphrM.setRecFnshDttm(Constants.FNSHDTTM);
        oldSphrM.setRecBgnDttm(bgnDttm);
        oldSphrM.setFnlYn(Constants.FnlYn.YES);
        oldSphrM.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        boolean flag = this.save(oldSphrM);
        if (flag) {
            return BaseResult.successMsg("编辑成功！");
        }
        return BaseResult.failedMsg("编辑失败！");
    }

    /**
     * 删除编码区
     * @param id
     * @return
     */
    @Override
    public boolean delSphrM(String id) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        return this.delById(id, oldFnshDttm, newBgnDttm);
    }

    /**
     * 批量删除编码区
     * @param ids
     * @return
     */
    @Override
    public boolean delBatchSphrM(List<String> ids) {
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
        List<String> childIds = sphrMMapper.getChildIdList(id);
        delIds.add(id);
        if (CollectionUtils.isNotEmpty(childIds)) {
            delIds.addAll(childIds);
        }
        List<CdSphrM> oldList = this.getObjByIds(delIds);
        this.updateBatchFnlYn(delIds, oldFnshDttm);
        String createUserId = UserUtils.getLoginUser().getUserId();
        for (CdSphrM obj : oldList) {
            obj.setRecBgnDttm(newBgnDttm);
            obj.setRecFnshDttm(newBgnDttm);
            obj.setFnlYn(Constants.FnlYn.YES);
            obj.setCvCdSphrDstsCd(Constants.SphrDstsCd.TYPE_ONE);
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
        QueryWrapper<CdSphrM> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CdSphrM::getHlvCvCdSphrId, parentId)
                .eq(CdSphrM::getRecFnshDttm, Constants.FNSHDTTM);
        List<CdSphrM> list = baseMapper.selectList(wrapper);
        // 如果子评论不为则，则取出每条评论的评论id
        if (CollectionUtils.isNotEmpty(list)) {
            for (CdSphrM obj : list) {
                String id = obj.getCvCdSphrId();
                // 将当前查询到评论id放到要删除的id集合中
                ids.add(id);
                // 递归继续查询子评论id
                this.getIds(ids, id);
            }
        }
    }

    private CdSphrM dtoToObj (CdSphrM obj, CdSphrMSaveDto dto) {
        obj.setCdSphrNm(dto.getCdSphrNm());
        obj.setCdSphrExplTxt(dto.getCdSphrExplTxt());
        if (StringUtils.isNotEmpty(dto.getHlvCvCdSphrId())){
            obj.setHlvCvCdSphrId(dto.getHlvCvCdSphrId());
        } else {
            obj.setHlvCvCdSphrId("0");
        }
        return obj;
    }

    private boolean updateFnlYn(String sphrId, Date oldFnshDttm) {
        UpdateWrapper<CdSphrM> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(CdSphrM::getRecFnshDttm, oldFnshDttm)
                .set(CdSphrM::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().eq(CdSphrM::getCvCdSphrId, sphrId)
                .eq(CdSphrM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }


    public List<CdSphrM> getObjByIds(List<String> ids) {
        QueryWrapper<CdSphrM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(CdSphrM::getCvCdSphrId, ids)
                .eq(CdSphrM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }

    private boolean updateBatchFnlYn(List<String> ids, Date oldFnshDttm) {
        UpdateWrapper<CdSphrM> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(CdSphrM::getRecFnshDttm, oldFnshDttm)
                .set(CdSphrM::getFnlYn, Constants.FnlYn.NO);
        updateWrapper.lambda().in(CdSphrM::getCvCdSphrId, ids)
                .eq(CdSphrM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }
}
