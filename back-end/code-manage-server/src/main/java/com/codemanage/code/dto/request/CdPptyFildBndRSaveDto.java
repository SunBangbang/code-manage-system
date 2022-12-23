package com.codemanage.code.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 编码属性专栏关系SaveDto
 * </p>
 *
 * @author hyh
 * @since 2022-08-17
 */
@Getter
@Setter
@ApiModel(value = "编码属性专栏关系SaveDto", description = "编码属性专栏关系SaveDto")
public class CdPptyFildBndRSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "CV编码ID")
    private String cvCdId;

    @ApiModelProperty(value = "CV编码属性ID")
    private String cvCdPptyId;

    @ApiModelProperty("CV数据库管理系统ID")
    private String cvDbmsId;

    @ApiModelProperty("CV数据库模式ID")
    private String cvSchmId;

    @ApiModelProperty("CV数据库表ID")
    private String cvDbTabId;

    @ApiModelProperty("CV数据库字段ID")
    private String cvDbFildId;

    @ApiModelProperty("CV排序序号")
    private Integer cvSortSrno;

    private int codeOrder;

    private String leftValue;
    private String rightValue;
}
