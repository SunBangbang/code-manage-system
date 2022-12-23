package com.codemanage.code.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * <p>
 * 编码SaveDto
 * </p>
 *
 * @author hyh
 * @since 2022-07-31
 */
@Getter
@Setter
@ApiModel(value = "编码SaveDto", description = "编码SaveDto")
public class CdMSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "CV编码ID", required = true)
    private String cvCdId;

    @ApiModelProperty(value = "编码逻辑名称", required = true)
    private String cdLgcNm;

    @ApiModelProperty(value = "CV编码类型编码", required = true)
    private String cvCdTpCd;

    @ApiModelProperty(value = "编码值保存与否", required = true)
    private String cdvlSaveYn;

    @ApiModelProperty(value = "所属CV系统ID", required = true)
    private String blngCvSysId;

    @ApiModelProperty(value = "CV编码领域ID", required = true)
    private String cvCdSphrId;

    @ApiModelProperty(value = "CV编码分类ID", required = true)
    private String cvCdCatId;

    @ApiModelProperty(value = "CV参考ID", required = true)
    private String cvRefId;

    @ApiModelProperty(value = "编码物理名称", required = true)
    private String cdPhysNm;

    @ApiModelProperty(value = "数据类型名称", required = true)
    private String dataTpNm;

    @ApiModelProperty(value = "数据长度", required = true)
    private Integer dataLen;

    @ApiModelProperty("编码说明内容")
    private String cdExplTxt;

    @ApiModelProperty("CV编码状态编码")
    private String cvCdStCd;

    @ApiModelProperty("上级编码ID")
    private String hlvCvCdId;

}

