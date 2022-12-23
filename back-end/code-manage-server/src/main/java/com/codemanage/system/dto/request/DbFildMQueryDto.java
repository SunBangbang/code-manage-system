package com.codemanage.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 数据表字段Querydto
 * @author hyh
 * @since 2022-07-19
 */
@Getter
@Setter
@ApiModel(value = "数据表字段Querydto")
public class DbFildMQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV数据库管理系统ID")
    private String cvDbmsId;

    @ApiModelProperty("CV数据库模式ID")
    private String cvSchmId;

    @ApiModelProperty("CV数据库表ID")
    private String cvDbTabId;

    @ApiModelProperty("CV数据库字段ID")
    private String cvDbFildId;

    @ApiModelProperty("数据库字段名称")
    private String dbFildNm;
}
