package com.codemanage.code.service;

import com.codemanage.code.entity.CmmnCdVlM;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * CVCD_共通编码值_主表 服务类
 * </p>
 *
 * @author hyh
 * @since 2022-08-16
 */
public interface ICmmnCdVlMService extends IService<CmmnCdVlM> {

    List<CmmnCdVlM> getByCdId(String cdStCd);
}
