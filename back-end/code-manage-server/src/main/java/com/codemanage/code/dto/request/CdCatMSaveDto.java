package com.codemanage.code.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * CVCD_编码分类SaveDto
 * </p>
 *
 * @author hyh
 * @since 2022-06-06
 */
@Data
@ApiModel(value = "编码分类SaveDto")
public class CdCatMSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "CV编码分类ID", required = true)
    private String cvCdCatId;

    @ApiModelProperty(value = "编码分类名称", required = true)
    private String cdCatNm;

    @ApiModelProperty("编码分类说明内容")
    private String cdCatExplTxt;

    @ApiModelProperty(value = "CV编码领域ID", required = true)
    private String cvCdSphrId;

    @ApiModelProperty(value = "上级CV编码分类ID", required = true)
    private String hlvCvCdCatId;

}
