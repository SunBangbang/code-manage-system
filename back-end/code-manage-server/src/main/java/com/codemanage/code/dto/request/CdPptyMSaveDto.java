package com.codemanage.code.dto.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 编码属性SaveDto
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
@Getter
@Setter
@ApiModel(value = "编码属性SaveDto", description = "编码属性SaveDto")
public class CdPptyMSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "CV编码属性ID")
    private String cvCdPptyId;

    @ApiModelProperty(value = "CV编码ID", required = true)
    private String cvCdId;

    @ApiModelProperty(value = "编码属性名称", required = true)
    private String cdPptyNm;

    @ApiModelProperty(value = "主键与否", required = true)
    @TableField("pk_yn")
    private String pkYn;

    @ApiModelProperty(value = "数据类型名称", required = true)
    private String dataTpNm;

    @ApiModelProperty("数据长度")
    private Integer dataLen;

    @ApiModelProperty(value = "CV编码领域ID", required = true)
    private String cvCdSphrId;

    @ApiModelProperty(value = "NULL与否", required = true)
    private String nullYn;

    @ApiModelProperty("CV排序序号")
    private Integer cvSortSrno;
}
