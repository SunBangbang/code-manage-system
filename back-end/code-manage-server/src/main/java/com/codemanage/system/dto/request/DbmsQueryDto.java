package com.codemanage.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 数据库QueryDto
 * @author hyh
 * @since 2022-07-19
 */
@Getter
@Setter
@ApiModel(value = "数据库QueryDto")
public class DbmsQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("选中节点id")
    private String id;

    @ApiModelProperty("选中节点类型")
    private String type;

    @ApiModelProperty("CV数据库管理系统ID")
    private String cvDbmsId;

    @ApiModelProperty("CV数据库模式ID")
    private String cvSchmId;

    @ApiModelProperty("CV数据库表ID")
    private String cvDbTabId;
}
