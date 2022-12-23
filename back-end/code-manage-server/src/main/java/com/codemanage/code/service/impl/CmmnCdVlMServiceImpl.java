package com.codemanage.code.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.codemanage.code.entity.CmmnCdVlM;
import com.codemanage.code.mapper.CmmnCdVlMMapper;
import com.codemanage.code.service.ICmmnCdVlMService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * CVCD_共通编码值_主表 服务实现类
 * </p>
 *
 * @author hyh
 * @since 2022-08-16
 */
@Service
public class CmmnCdVlMServiceImpl extends ServiceImpl<CmmnCdVlMMapper, CmmnCdVlM> implements ICmmnCdVlMService {

    @Override
    public List<CmmnCdVlM> getByCdId(String cdStCd) {
        QueryWrapper<CmmnCdVlM> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CmmnCdVlM::getCvCdId, cdStCd);
        return this.list(queryWrapper);
    }
}
