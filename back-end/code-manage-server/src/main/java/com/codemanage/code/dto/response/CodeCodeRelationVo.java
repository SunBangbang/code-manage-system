package com.codemanage.code.dto.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 编码编码关系Vo
 * </p>
 *
 * @author hyh
 * @since 2022-08-08
 */
@Getter
@Setter
@ApiModel(value = "编码编码关系Vo", description = "编码编码关系Vo")
public class CodeCodeRelationVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV编码ID")
    private String cvCdId;

    @ApiModelProperty("上级编码ID")
    private String hlvCvCdId;

    @ApiModelProperty("下级编码ID")
    private String llvCvCdId;

    @ApiModelProperty("上线层分类")
    private String hierarchy;

    @ApiModelProperty("CV编码分类ID")
    private String cvCdCatId;

    @ApiModelProperty("编码分类名称")
    private String cdCatNm;

    @ApiModelProperty("编码逻辑名称")
    private String cdLgcNm;

    @ApiModelProperty("编码物理名称")
    private String cdPhysNm;

    @ApiModelProperty("数据类型名称")
    private String dataTpNm;

    @ApiModelProperty("数据长度")
    private Integer dataLen;

    @ApiModelProperty("CV编码状态编码")
    private String cvCdStCd;

    @ApiModelProperty("CV编码类型编码")
    private String cvCdTpCd;

    @ApiModelProperty("编码值保存与否")
    private String cdvlSaveYn;

    @ApiModelProperty("CV参考ID")
    private String cvRefId;

    @ApiModelProperty("所属CV系统ID")
    private String blngCvSysId;

    @ApiModelProperty("所属CV系统名称")
    private String blngCvSysNm;

    @ApiModelProperty("上级编码逻辑名称")
    private String hlvCdLgcNm;

    @ApiModelProperty("编码说明内容")
    private String cdExplTxt;
}
