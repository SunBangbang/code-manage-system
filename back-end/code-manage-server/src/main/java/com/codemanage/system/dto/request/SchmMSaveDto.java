package com.codemanage.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 数据库模式SaveDto
 * @author hyh
 * @since 2022-07-19
 */
@Getter
@Setter
@ApiModel(value = "数据库模式SaveDto")
public class SchmMSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV数据库管理系统ID")
    private String cvDbmsId;

    @ApiModelProperty("CV数据库模式ID")
    private String cvSchmId;

    @ApiModelProperty("CV数据库模式名称")
    private String schmNm;

    @ApiModelProperty("CV排序序号")
    private Integer cvSortSrno;

    @ApiModelProperty("数据库模式说明内容")
    private String schmExplTxt;
}
