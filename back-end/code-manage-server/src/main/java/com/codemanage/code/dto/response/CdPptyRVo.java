package com.codemanage.code.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * CVCD_编码属性关系_Vo
 * </p>
 *
 * @author hyh
 * @since 2022-08-16
 */
@Getter
@Setter
public class CdPptyRVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("上级CV编码属性ID")
    private String hlvCvCdPptyId;

    @ApiModelProperty("上级CV编码ID")
    private String hlvCvCdId;

    @ApiModelProperty("上级CV编码属性名称")
    private String hlvCdPptyNm;

    @ApiModelProperty("下级CV编码属性ID")
    private String llvCvCdPptyId;

    @ApiModelProperty("下级CV编码ID")
    private String llvCvCdId;

    @ApiModelProperty("下级CV编码属性名称")
    private String llvCdPptyNm;

    @ApiModelProperty("关系说明内容")
    private String rltsExplTxt;

    @ApiModelProperty("排序序号")
    private String sortSrno;

}
