package com.codemanage.code.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 编码QueryDto
 * </p>
 *
 * @author hyh
 * @since 2022-07-31
 */
@Getter
@Setter
@ApiModel(value = "编码QueryDto", description = "编码QueryDto")
public class CdMQueryDto  implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("当前页")
    private Integer currentPage;

    @ApiModelProperty("每页条数")
    private Integer pageSize;

    @ApiModelProperty(value = "CV编码领域名称")
    private String cdSphrNm;

    @ApiModelProperty(value = "CV编码ID")
    private String cvCdId;

    @ApiModelProperty(value = "编码逻辑名称")
    private String cdLgcNm;

    @ApiModelProperty(value = "编码物理名称")
    private String cdPhysNm;

    @ApiModelProperty("编码说明内容")
    private String cdExplTxt;

    @ApiModelProperty(value = "CV编码分类名称")
    private String cdCatNm;

    @ApiModelProperty(value = "编码值保存与否")
    private String cdvlSaveYn;

    @ApiModelProperty(value = "所属CV系统ID")
    private String blngCvSysId;

    @ApiModelProperty(value = "数据类型名称")
    private String dataTpNm;

    @ApiModelProperty(value = "CV编码类型编码")
    private String cvCdTpCd;

    @ApiModelProperty("CV编码状态编码")
    private String cvCdStCd;

}
