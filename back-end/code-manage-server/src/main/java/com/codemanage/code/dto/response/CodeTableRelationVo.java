package com.codemanage.code.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 编码表关系Vo
 * </p>
 *
 * @author hyh
 * @since 2022-08-17
 */
@Getter
@Setter
@ApiModel(value = "编码表关系Vo", description = "编码表关系Vo")
public class CodeTableRelationVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV编码ID")
    private String cvCdId;

    @ApiModelProperty("CV数据库管理系统ID")
    private String cvDbmsId;

    @ApiModelProperty("CV数据库模式ID")
    private String cvSchmId;

    @ApiModelProperty("CV数据库表ID")
    private String cvDbTabId;

    @ApiModelProperty("CV数据库模式名称")
    private String schmNm;

    @ApiModelProperty("数据库表名称")
    private String dbTabNm;



}
