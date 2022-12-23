package com.codemanage.code.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * CVCD_编码_Vo
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
@Getter
@Setter
@ApiModel(value = "编码VO", description = "CVCD_编码_Vo")
public class CdMVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV编码ID")
    private String cvCdId;

    @ApiModelProperty("CV编码领域ID")
    private String cvCdSphrId;

    @ApiModelProperty("编码领域名称")
    private String cdSphrNm;

    @ApiModelProperty("CV编码分类ID")
    private String cvCdCatId;

    @ApiModelProperty("编码分类名称")
    private String cdCatNm;

    @ApiModelProperty("编码逻辑名称")
    private String cdLgcNm;

    @ApiModelProperty("编码物理名称")
    private String cdPhysNm;

    @ApiModelProperty("CV编码类型编码ID")
    private String cvCdTpCdId;

    @ApiModelProperty("CV编码类型编码")
    private String cvCdTpCd;

    @ApiModelProperty("数据类型名称ID")
    private String dataTpNmId;

    @ApiModelProperty("数据类型名称")
    private String dataTpNm;

    @ApiModelProperty("数据长度")
    private Integer dataLen;

    @ApiModelProperty("上层CV编码ID")
    private String hlvCvCdId;

    @ApiModelProperty("上层编码名称")
    private String hlvCdLgcNm;

    @ApiModelProperty("编码说明内容")
    private String cdExplTxt;

    @ApiModelProperty("CV编码状态编码ID")
    private String cvCdStCdId;

    @ApiModelProperty("CV编码状态编码")
    private String cvCdStCd;

    @ApiModelProperty("编码值保存与否")
    private String cdvlSaveYn;

    @ApiModelProperty("编码参考值")
    private String cvRefId;

    @ApiModelProperty("所属CV系统ID")
    private String blngCvSysId;

    @ApiModelProperty("所属CV系统名称")
    private String blngCvSysNm;

    @ApiModelProperty("创建CV用户ID")
    private String cretCvUserId;

    @ApiModelProperty("创建CV用户ID")
    private String cretCvUserNm;

}
