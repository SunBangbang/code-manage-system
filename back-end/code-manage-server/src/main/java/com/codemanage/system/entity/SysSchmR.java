package com.codemanage.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.codemanage.common.constants.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * CVSO_系统数据库模式_关系表
 * </p>
 *
 * @author hyh
 * @since 2022-06-16
 */
@Getter
@Setter
@TableName("cvso_sys_schm_r")
@ApiModel(value = "SysSchmR对象", description = "CVSO_系统数据库模式_关系表")
public class SysSchmR implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV系统ID")
    @TableField("cv_sys_id")
    private String cvSysId;

    @ApiModelProperty("CV数据库管理系统ID")
    @TableField("cv_dbms_id")
    private String cvDbmsId;

    @ApiModelProperty("CV数据库模式ID")
    @TableField("cv_schm_id")
    private String cvSchmId;

    @ApiModelProperty("记录结束日时")
    @TableField("rec_fnsh_dttm")
    private Date recFnshDttm;

    @ApiModelProperty("记录开始日时")
    @TableField("rec_bgn_dttm")
    private Date recBgnDttm;

    @ApiModelProperty("关系说明内容")
    @TableField("rlts_expl_txt")
    private String rltsExplTxt;

    @ApiModelProperty("CV排序序号")
    @TableField("cv_sort_srno")
    private Integer cvSortSrno;

    @ApiModelProperty("最终与否")
    @TableField("fnl_yn")
    private String fnlYn = Constants.FnlYn.YES;

    @ApiModelProperty("创建CV用户ID")
    @TableField("cret_cv_user_id")
    private String cretCvUserId;


}
