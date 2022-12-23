package com.codemanage.code.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * CVCD_编码值关系_Vo
 * </p>
 *
 * @author hyh
 * @since 2022-08-16
 */
@Getter
@Setter
public class CdvlRVo implements Serializable {

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

    @ApiModelProperty("上级编码值")
    private String hlvCdVl;

    @ApiModelProperty("上级编码值名称")
    private String hlvCdvlNm;

    @ApiModelProperty("下级编码值")
    private String llvCdVl;

    @ApiModelProperty("下级编码值名称")
    private String llvCdvlNm;
}
