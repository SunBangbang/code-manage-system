package com.codemanage.code.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * CVCD_编码属性_Vo
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
@Getter
@Setter
public class CdPptyMVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV编码ID")
    private String cvCdId;

    @ApiModelProperty("编码逻辑名称")
    private String cdLgcNm;

    @ApiModelProperty("CV编码属性ID")
    private String cvCdPptyId;

    @ApiModelProperty("编码属性名称")
    private String cdPptyNm;

    @ApiModelProperty("主键与否")
    private String pkYn;

    @ApiModelProperty("NULL与否")
    private String nullYn;

    @ApiModelProperty("数据类型名称")
    private String dataTpNm;

    @ApiModelProperty("数据长度")
    private Integer dataLen;

    @ApiModelProperty("记录开始日时")
    private String recBgnDttm;

    @ApiModelProperty("创建CV用户ID")
    private String cretCvUserId;

    @ApiModelProperty("创建CV用户")
    private String cretCvUserNm;

    @ApiModelProperty("CV排序序号")
    private Integer cvSortSrno;

    @ApiModelProperty("CV属性区分编码")
    private String cvPptyDstsCd;

    @ApiModelProperty("CV编码领域ID")
    private String cvCdSphrId;

}
