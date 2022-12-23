package com.codemanage.code.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 编码值关系SaveDto
 * </p>
 *
 * @author hyh
 * @since 2022-08-16
 */
@Getter
@Setter
@ApiModel(value = "编码值关系SaveDto", description = "编码值关系SaveDto")
public class CdvlRSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("上级CV编码ID")
    private String hlvCvCdId;

    @ApiModelProperty("上级CV编码值ID")
    private String hlvCvCdvlId;

    @ApiModelProperty("下级CV编码ID")
    private String llvCvCdId;

    @ApiModelProperty("下级CV编码值ID")
    private String llvCvCdvlId;

    @ApiModelProperty("CV编码值关系区分编码")
    private String cvCdvlRltsDstsCd;

    @ApiModelProperty("关系说明内容")
    private String rltsExplTxt;

    @ApiModelProperty("排序序号")
    private String sortSrno;

    private int codeOrder;

    private String leftValue;
    private String rightValue;
}
