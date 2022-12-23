package com.codemanage.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.code.dto.request.CdPptyMQueryDto;
import com.codemanage.code.dto.request.TabCdBndRDelDto;
import com.codemanage.code.dto.request.TabCdBndRSaveDto;
import com.codemanage.code.dto.response.CodeTableRelationVo;
import com.codemanage.code.entity.TabCdBndR;
import com.codemanage.code.mapper.TabCdBndRMapper;
import com.codemanage.code.service.ITabCdBndRService;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.util.UserUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVCD_表编码绑定_关系表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-08-17
 */
@Service
public class TabCdBndRServiceImpl extends ServiceImpl<TabCdBndRMapper, TabCdBndR> implements ITabCdBndRService {

    @Override
    public List<CodeTableRelationVo> getCodeTableRelation(CdPptyMQueryDto dto) {
        return baseMapper.getCodeTableRelation(dto);
    }

    @Override
    public BaseResult delBatchTabCdBndR(List<TabCdBndRDelDto> dtoList) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        for (TabCdBndRDelDto dto : dtoList) {
            this.delTabCdBndR(dto, oldFnshDttm, newBgnDttm);
        }
        return BaseResult.successMsg("删除完成!");
    }

    @Override
    public BaseResult addTabCdBndR(TabCdBndRSaveDto dto) {
        TabCdBndR obj = new TabCdBndR();
        BeanUtils.copyProperties(dto, obj);
        obj.setFnlYn(Constants.FnlYn.YES);
        obj.setRecBgnDttm(new Date());
        obj.setRecFnshDttm(Constants.FNSHDTTM);
        obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        this.save(obj);
        return BaseResult.successMsg("添加成功");
    }

    private boolean delTabCdBndR(TabCdBndRDelDto dto, Date oldFnshDttm, Date newBgnDttm) {
        TabCdBndR oldObj = this.getObjById(dto);
        boolean flag = this.updateFnlYn(dto, oldFnshDttm);
        if (flag) {
            oldObj.setRecFnshDttm(newBgnDttm);
            oldObj.setRecBgnDttm(newBgnDttm);
            oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            return this.save(oldObj);
        }
        return false;
    }

    private TabCdBndR getObjById(TabCdBndRDelDto dto) {
        QueryWrapper<TabCdBndR> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TabCdBndR::getCvCdId, dto.getCvCdId())
                .eq(TabCdBndR::getCvDbmsId, dto.getCvDbmsId())
                .eq(TabCdBndR::getCvSchmId, dto.getCvSchmId())
                .eq(TabCdBndR::getCvDbTabId, dto.getCvDbTabId())
                .eq(TabCdBndR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.getOne(queryWrapper);

    }

    private boolean updateFnlYn(TabCdBndRDelDto dto, Date oldFnshDttm) {
        UpdateWrapper<TabCdBndR> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(TabCdBndR::getFnlYn, Constants.FnlYn.NO)
                        .set(TabCdBndR::getRecFnshDttm, oldFnshDttm);
        updateWrapper.lambda().eq(TabCdBndR::getCvCdId, dto.getCvCdId())
                .eq(TabCdBndR::getCvDbmsId, dto.getCvDbmsId())
                .eq(TabCdBndR::getCvSchmId, dto.getCvSchmId())
                .eq(TabCdBndR::getCvDbTabId, dto.getCvDbTabId())
                .eq(TabCdBndR::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }
}
