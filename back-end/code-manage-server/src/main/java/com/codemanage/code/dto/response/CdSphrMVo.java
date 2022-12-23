package com.codemanage.code.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * CVCD_编码领域_Vo
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
@Getter
@Setter
@ApiModel(value = "编码区Vo", description = "CVCD_编码区_Vo")
public class CdSphrMVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV编码领域ID")
    private String cvCdSphrId;

    @ApiModelProperty("编码领域名称")
    private String cdSphrNm;

    @ApiModelProperty("编码领域说明内容")
    private String cdSphrExplTxt;

    @ApiModelProperty("上级CV编码领域ID")
    private String hlvCvCdSphrId;

    @ApiModelProperty("上级CV编码领域名称")
    private String hlvCvCdSphrNm;
}
