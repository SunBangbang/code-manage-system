package com.codemanage.code.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * CVCD_编码属性DelDto
 * </p>
 *
 * @author hyh
 * @since 2022-08-01
 */
@Getter
@Setter
@ApiModel(value = "编码属性DelDto", description = "编码属性DelDto")
public class CdPptyMDelDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "CV编码ID")
    private String cvCdId;

    @ApiModelProperty(value = "编码属性id集合")
    private List<String> cvCdPptyIdList;
}
