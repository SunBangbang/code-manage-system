package com.codemanage.system.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库字段VO
 * @author hyh
 * @since 2022-07-19
 */
@Getter
@Setter
@ApiModel(value = "数据库字段VO")
public class DbFildMVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV数据库管理系统ID")
    private String cvDbmsId;

    @ApiModelProperty("CV数据库模式ID")
    private String cvSchmId;

    @ApiModelProperty("CV数据库表ID")
    private String cvDbTabId;

    @ApiModelProperty("CV数据库字段ID")
    private String cvDbFildId;

    @ApiModelProperty("数据库管理系统名称")
    private String dbmsNm;

    @ApiModelProperty("数据库模式名称")
    private String schmNm;

    @ApiModelProperty("数据库表名称")
    private String dbTabNm;

    @ApiModelProperty("数据库字段名称")
    private String dbFildNm;

    @ApiModelProperty("数据库字段说明内容")
    private String dbFildExplTxt;

    @ApiModelProperty("CV数据类型ID")
    private String cvDttpId;

    @ApiModelProperty("数据类型名称")
    private String dataTpNm;

    @ApiModelProperty("整数长度")
    private Integer intmLen;

    @ApiModelProperty("小数长度")
    private Integer dcmlLen;

    @ApiModelProperty("主键与否")
    private String pkYn;

    @ApiModelProperty("NULL与否")
    private String nullYn;

    @ApiModelProperty("字段默认值")
    private String fildDfltVl;

    @ApiModelProperty("外键与否")
    private String frkyYn;

    @ApiModelProperty("范围内容")
    private String scpTxt;

    @ApiModelProperty("业务说明内容")
    private String bsnsExplTxt;

    @ApiModelProperty("删除与否")
    private String delYn;

    @ApiModelProperty("记录开始日时")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recBgnDttm;

    @ApiModelProperty("记录结束日时")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date recFnshDttm;

    @ApiModelProperty("CV排序序号")
    private Integer cvSortSrno;
}
