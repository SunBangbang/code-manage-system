package com.codemanage.system.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库表VO
 * @author hyh
 * @since 2022-07-19
 */
@Getter
@Setter
@ApiModel(value = "数据库表VO")
public class DbTabMVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("CV数据库管理系统ID")
    private String cvDbmsId;

    @ApiModelProperty("CV数据库模式ID")
    private String cvSchmId;

    @ApiModelProperty("CV数据库表ID")
    private String cvDbTabId;

    @ApiModelProperty("数据库管理系统名称")
    private String dbmsNm;

    @ApiModelProperty("数据库模式名称")
    private String schmNm;

    @ApiModelProperty("数据库表名称")
    private String dbTabNm;

    @ApiModelProperty("数据库表说明内容")
    private String dbTabExplTxt;

    @ApiModelProperty("记录开始日时")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recBgnDttm;

    @ApiModelProperty("记录结束日时")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date recFnshDttm;

    @ApiModelProperty("CV排序序号")
    private Integer cvSortSrno;


}
