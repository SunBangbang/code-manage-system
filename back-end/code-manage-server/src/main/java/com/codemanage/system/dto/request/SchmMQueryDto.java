package com.codemanage.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 数据库模式Querydto
 * @author hyh
 * @since 2022-07-19
 */
@Getter
@Setter
@ApiModel(value = "数据库模式Querydto")
public class SchmMQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV数据库管理系统ID")
    private String cvDbmsId;

    @ApiModelProperty("CV数据库模式ID")
    private String cvSchmId;

    @ApiModelProperty("CV数据库模式名称")
    private String schmNm;
}
