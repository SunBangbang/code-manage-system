package com.codemanage.code.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * CVCD_编码属性QueryDto
 * </p>
 *
 * @author hyh
 * @since 2022-08-01
 */
@Getter
@Setter
@ApiModel(value = "编码属性QueryDto", description = "编码属性QueryDto")
public class CdPptyMQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "CV编码ID")
    private String cvCdId;

    @ApiModelProperty(value = "排序字段")
    private String sortField = "cv_sort_srno";
}
