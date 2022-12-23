package com.codemanage.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.codemanage.common.constants.Constants;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.util.UserUtils;
import com.codemanage.system.dto.request.SysMQueryDto;
import com.codemanage.system.dto.request.SysMSaveDto;
import com.codemanage.system.dto.request.SysSchmRSaveDto;
import com.codemanage.system.dto.response.SysMVo;
import com.codemanage.system.entity.SysM;
import com.codemanage.system.mapper.SysMMapper;
import com.codemanage.system.service.ISysMService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codemanage.system.service.ISysSchmRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * CVSO_系统_主表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
@Service
public class SysMServiceImpl extends ServiceImpl<SysMMapper, SysM> implements ISysMService {

    @Autowired
    private SysMMapper sysMMapper;

    @Autowired
    @Lazy
    private ISysSchmRService sysSchmRService;

    /**
     * 查询系统列表
     * @param dto
     * @return
     */
    @Override
    public List<SysMVo> getSysList(SysMQueryDto dto) {
        return sysMMapper.getSysList(dto);
    }

    /**
     * 校验系统名称
     * @param sysNm
     * @return
     */
    @Override
    public boolean checkSysNm(String sysNm) {
        int count = sysMMapper.checkSysNm(sysNm);
        if (count < 1) {
            return true;
        }
        return false;
    }

    @Override
    public Integer getSortSrno() {
        return  sysMMapper.getSortSrno();
    }

    /**
     * 添加系统
     * @param dto
     * @return
     */
    @Override
    public BaseResult addSysM(SysMSaveDto dto) {
        List<SysSchmRSaveDto> schmList = dto.getSchmList();
        if (CollectionUtils.isNotEmpty(schmList)) {
            Date newBgnDttm = new Date();
            // 获取系统id
            String sysId= sysMMapper.getSysId();
            SysM obj = new SysM();
            obj.setCvSysId(sysId);
            obj.setRecFnshDttm(Constants.FNSHDTTM);
            obj.setRecBgnDttm(newBgnDttm);
            obj.setSysNm(dto.getSysNm());
            obj.setSysExplTxt(dto.getSysExplTxt());
            if (dto.getCvSortSrno() != null) {
                obj.setCvSortSrno(dto.getCvSortSrno());
            } else {
                obj.setCvSortSrno(this.getSortSrno());
            }
            obj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            boolean flag = this.save(obj);
            if (flag) {
                for (SysSchmRSaveDto saveDto : schmList) {
                    sysSchmRService.addObj(sysId, saveDto.getCvDbmsId(), saveDto.getCvSchmId(), newBgnDttm);
                }
                return BaseResult.successMsg("添加完成!");
            }
            return BaseResult.failedMsg("添加异常!");
        }
        return BaseResult.failedMsg("请选择模式!");
    }

    /**
     * 根据id查询
     * @param dto
     * @return
     */
    @Override
    public SysMVo getSysMVoById(SysMQueryDto dto) {
        return sysMMapper.getSysMVoById(dto);
    }

    /**
     * 编辑系统
     * @param dto
     * @return
     */
    @Override
    public BaseResult editSysM(SysMSaveDto dto) {
        List<SysSchmRSaveDto> schmList = dto.getSchmList();
        if (CollectionUtils.isNotEmpty(schmList)) {
            Date oldFnshDttm = new Date();
            Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);

            SysM oldObj = this.getObjById(dto.getCvSysId());
            this.updateFnlYn(dto.getCvSysId(), oldFnshDttm);
            oldObj.setRecFnshDttm(Constants.FNSHDTTM);
            oldObj.setRecBgnDttm(newBgnDttm);
            oldObj.setSysNm(dto.getSysNm());
            oldObj.setSysExplTxt(dto.getSysExplTxt());
            oldObj.setFnlYn(Constants.FnlYn.YES);
            oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
            boolean flag = this.save(oldObj);
            if (flag) {
                sysSchmRService.updateSysSchmR(dto.getCvSysId(), schmList, oldFnshDttm, newBgnDttm);
                return BaseResult.successMsg("更新完成!");
            }
            return BaseResult.failedMsg("更新异常!");
        }
        return BaseResult.failedMsg("请选择模式!");
    }

    /**
     * 删除系统
     * @param sysId
     * @return
     */
    @Override
    public boolean delSysM(String sysId) {
        Date oldFnshDttm = new Date();
        Date newBgnDttm = new Date(oldFnshDttm.getTime() + 1);
        SysM oldObj = this.getObjById(sysId);
        this.updateFnlYn(sysId, oldFnshDttm);
        oldObj.setRecFnshDttm(newBgnDttm);
        oldObj.setRecBgnDttm(newBgnDttm);
        oldObj.setFnlYn(Constants.FnlYn.YES);
        oldObj.setCretCvUserId(UserUtils.getLoginUser().getUserId());
        boolean flag = this.save(oldObj);
        if (flag) {
           return sysSchmRService.delSysSchmR(sysId, oldFnshDttm, newBgnDttm);
        }
        return false;
    }

    @Override
    public List<SysM> getSysMList() {
        QueryWrapper<SysM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.list(queryWrapper);
    }


    private SysM getObjById(String sysId) {
        QueryWrapper<SysM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysM::getCvSysId, sysId)
                .eq(SysM::getRecFnshDttm, Constants.FNSHDTTM);
        List<SysM> sysMList = this.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(sysMList)) {
            return sysMList.get(0);
        }
        return null;
    }

    private boolean updateFnlYn(String sysId, Date oldFnshDttm) {
        UpdateWrapper<SysM> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(SysM::getRecFnshDttm, oldFnshDttm)
                .set(SysM::getFnlYn, Constants.FnlYn.NO)
                .eq(SysM::getCvSysId, sysId)
                .eq(SysM::getRecFnshDttm, Constants.FNSHDTTM);
        return this.update(updateWrapper);
    }
}
