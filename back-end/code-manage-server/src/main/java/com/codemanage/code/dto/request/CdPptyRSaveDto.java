package com.codemanage.code.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * <p>
 * 编码属性关系SaveDto
 * </p>
 *
 * @author hyh
 * @since 2022-08-09
 */
@Getter
@Setter
@ApiModel(value = "编码属性关系SaveDto", description = "编码属性关系SaveDto")
public class CdPptyRSaveDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("上级CV编码属性ID")
    private String hlvCvCdPptyId;

    @ApiModelProperty("上级CV编码ID")
    private String hlvCvCdId;

    @ApiModelProperty("下级CV编码属性ID")
    private String llvCvCdPptyId;

    @ApiModelProperty("下级CV编码ID")
    private String llvCvCdId;

    @ApiModelProperty("CV属性关系区分编码")
    private String cvPptyRltsDstsCd;

    @ApiModelProperty("关系说明内容")
    private String rltsExplTxt;

    @ApiModelProperty("排序序号")
    private String sortSrno;

    private String leftValue;
    private String rightValue;
}
