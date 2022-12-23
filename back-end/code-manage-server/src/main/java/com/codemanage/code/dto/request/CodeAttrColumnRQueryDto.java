package com.codemanage.code.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 编码属性列关系QueryDto
 * </p>
 *
 * @author hyh
 * @since 2022-08-17
 */
@Getter
@Setter
@ApiModel(value = "编码属性列关系QueryDto", description = "编码属性列关系QueryDto")
public class CodeAttrColumnRQueryDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "CV编码ID")
    private String cvCdId;

    @ApiModelProperty("CV数据库管理系统ID")
    private String cvDbmsId;

    @ApiModelProperty("CV数据库模式ID")
    private String cvSchmId;

    @ApiModelProperty("CV数据库表ID")
    private String cvDbTabId;
}
