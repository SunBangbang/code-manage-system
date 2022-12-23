package com.codemanage.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 系统QueryDto
 * @author hyh
 * @since 2022-07-19
 */
@Getter
@Setter
@ApiModel(value = "系统QueryDto")
public class SysMQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("系统名称")
    private String sysNm;

    @ApiModelProperty("最终与否")
    private String fnlYn;

    @ApiModelProperty("CV系统ID")
    private String cvSysId;

    @ApiModelProperty("CV数据库管理系统ID")
    private String cvDbmsId;

    @ApiModelProperty("CV数据库模式ID")
    private String cvSchmId;
}
