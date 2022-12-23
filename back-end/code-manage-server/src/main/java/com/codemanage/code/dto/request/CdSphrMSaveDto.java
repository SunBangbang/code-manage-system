package com.codemanage.code.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * CVCD_编码区_SaveDto
 * </p>
 *
 * @author hyh
 * @since 2022-07-29
 */
@Getter
@Setter
@ApiModel(value = "编码区SaveDto")
public class CdSphrMSaveDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "CV编码领域ID", required = true)
    private String cvCdSphrId;

    @ApiModelProperty(value = "编码领域名称", required = true)
    private String cdSphrNm;

    @ApiModelProperty("编码领域说明内容")
    private String cdSphrExplTxt;

    @ApiModelProperty(value = "上级CV编码领域ID", required = true)
    private String hlvCvCdSphrId;

}
