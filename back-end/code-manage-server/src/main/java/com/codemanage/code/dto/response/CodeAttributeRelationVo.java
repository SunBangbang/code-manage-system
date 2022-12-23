package com.codemanage.code.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 编码属性关系Vo
 * </p>
 *
 * @author hyh
 * @since 2022-08-08
 */
@Getter
@Setter
@ApiModel(value = "编码属性关系Vo", description = "编码属性关系Vo")
public class CodeAttributeRelationVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("上级编码ID")
    private String hlvCvCdId;

    @ApiModelProperty("上级编码名称")
    private String hlvCdLgcNm;

    @ApiModelProperty("上级编码属性ID")
    private String hlvCvCdPptyId;

    @ApiModelProperty("上级编码属性名称")
    private String hlvCdPptyNm;

    @ApiModelProperty("下级编码ID")
    private String llvCvCdId;

    @ApiModelProperty("下级编码名称")
    private String llvCdLgcNm;

    @ApiModelProperty("下级编码属性ID")
    private String llvCvCdPptyId;

    @ApiModelProperty("下级编码属性名称")
    private String llvCdPptyNm;
}
