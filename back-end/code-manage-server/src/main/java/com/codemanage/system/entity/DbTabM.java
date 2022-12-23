package com.codemanage.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codemanage.common.constants.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * CVSO_数据库表_主表
 * </p>
 *
 * @author hyh
 * @since 2022-07-16
 */
@Getter
@Setter
@TableName("cvso_db_tab_m")
@ApiModel(value = "DbTabM对象", description = "CVSO_数据库表_主表")
public class DbTabM implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV数据库表ID")
    @TableId("cv_db_tab_id")
    private String cvDbTabId;

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

    @ApiModelProperty("数据库表名称")
    @TableField("db_tab_nm")
    private String dbTabNm;

    @ApiModelProperty("数据库表说明内容")
    @TableField("db_tab_expl_txt")
    private String dbTabExplTxt;

    @ApiModelProperty("最终与否")
    @TableField("fnl_yn")
    private String fnlYn = Constants.FnlYn.YES;

    @ApiModelProperty("CV排序序号")
    @TableField("cv_sort_srno")
    private Integer cvSortSrno;

    @ApiModelProperty("创建CV用户ID")
    @TableField("cret_cv_user_id")
    private String cretCvUserId;


}
