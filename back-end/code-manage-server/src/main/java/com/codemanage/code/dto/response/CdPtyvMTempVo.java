package com.codemanage.code.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * CVCD_编码属性值TempVo
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
@Getter
@Setter
public class CdPtyvMTempVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编码属性值名称
     */
    private String cdPptyNm;

    /**
     * 编码值id
     */
    private String cvCdvlId;

    /**
     * 编码属性值
     */
    private String cdPptyVl;
}
