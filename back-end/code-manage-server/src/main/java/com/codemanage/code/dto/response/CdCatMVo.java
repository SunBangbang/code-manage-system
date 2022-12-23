package com.codemanage.code.dto.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * CVCD_编码分类_Vo
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
@Getter
@Setter
@ApiModel(value = "编码分类Vo", description = "CVCD_编码分类_Vo")
public class CdCatMVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV编码分类ID")
    private String cvCdCatId;

    @ApiModelProperty("编码分类名称")
    private String cdCatNm;

    @ApiModelProperty("编码分类说明内容")
    private String cdCatExplTxt;

    @ApiModelProperty("上级CV编码分类ID")
    private String hlvCvCdCatId;

    @ApiModelProperty("上级CV编码分类名称")
    private String hlvCvCdCatNm;

    @ApiModelProperty("CV编码区ID")
    private String cvCdSphrId;

    @ApiModelProperty("编码区名称")
    private String cdSphrNm;

}
