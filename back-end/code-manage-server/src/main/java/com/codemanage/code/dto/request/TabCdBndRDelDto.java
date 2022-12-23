package com.codemanage.code.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 编码表关系DelDto
 * </p>
 *
 * @author hyh
 * @since 2022-08-16
 */
@Getter
@Setter
@ApiModel(value = "编码表关系DelDto", description = "编码表关系DelDto")
public class TabCdBndRDelDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV编码ID")
    private String cvCdId;

    @ApiModelProperty("CV数据库管理系统ID")
    private String cvDbmsId;

    @ApiModelProperty("CV数据库模式ID")
    private String cvSchmId;

    @ApiModelProperty("CV数据库表ID")
    private String cvDbTabId;
}
