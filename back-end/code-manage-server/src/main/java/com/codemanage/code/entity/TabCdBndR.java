package com.codemanage.code.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * CVCD_表编码绑定_关系表
 * </p>
 *
 * @author hyh
 * @since 2022-08-17
 */
@Getter
@Setter
@TableName("cvcd_tab_cd_bnd_r")
@ApiModel(value = "TabCdBndR对象", description = "CVCD_表编码绑定_关系表")
public class TabCdBndR implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV编码ID")
    @TableId("cv_cd_id")
    private String cvCdId;

    @ApiModelProperty("CV数据库管理系统ID")
    @TableField("cv_dbms_id")
    private String cvDbmsId;

    @ApiModelProperty("CV数据库模式ID")
    @TableField("cv_schm_id")
    private String cvSchmId;

    @ApiModelProperty("CV数据库表ID")
    @TableField("cv_db_tab_id")
    private String cvDbTabId;

    @ApiModelProperty("记录结束日时")
    @TableField("rec_fnsh_dttm")
    private Date recFnshDttm;

    @ApiModelProperty("记录开始日时")
    @TableField("rec_bgn_dttm")
    private Date recBgnDttm;

    @ApiModelProperty("关系说明内容")
    @TableField("rlts_expl_txt")
    private String rltsExplTxt;

    @ApiModelProperty("最终与否")
    @TableField("fnl_yn")
    private String fnlYn;

    @ApiModelProperty("排序序号")
    @TableField("sort_srno")
    private String sortSrno;

    @ApiModelProperty("创建CV用户ID")
    @TableField("cret_cv_user_id")
    private String cretCvUserId;


}
