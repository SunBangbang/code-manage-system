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
 * CVSO_数据库字段_主表
 * </p>
 *
 * @author hyh
 * @since 2022-07-19
 */
@Getter
@Setter
@TableName("cvso_db_fild_m")
@ApiModel(value = "DbFildM对象", description = "CVSO_数据库字段_主表")
public class DbFildM implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV数据库字段ID")
    @TableId("cv_db_fild_id")
    private String cvDbFildId;

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

    @ApiModelProperty("数据库字段名称")
    @TableField("db_fild_nm")
    private String dbFildNm;

    @ApiModelProperty("数据库字段说明内容")
    @TableField("db_fild_expl_txt")
    private String dbFildExplTxt;

    @ApiModelProperty("CV数据类型ID")
    @TableField("cv_dttp_id")
    private String cvDttpId;

    @ApiModelProperty("数据类型名称")
    @TableField("data_tp_nm")
    private String dataTpNm;

    @ApiModelProperty("数据长度")
    @TableField("data_len")
    private Integer dataLen;

    @ApiModelProperty("整数长度")
    @TableField("intm_len")
    private Integer intmLen;

    @ApiModelProperty("小数长度")
    @TableField("dcml_len")
    private Integer dcmlLen;

    @ApiModelProperty("CV排序序号")
    @TableField("cv_sort_srno")
    private Integer cvSortSrno;

    @ApiModelProperty("主键与否")
    @TableField("pk_yn")
    private String pkYn;

    @ApiModelProperty("NULL与否")
    @TableField("null_yn")
    private String nullYn;

    @ApiModelProperty("字段默认值")
    @TableField("fild_dflt_vl")
    private String fildDfltVl;

    @ApiModelProperty("外键与否")
    @TableField("frky_yn")
    private String frkyYn;

    @ApiModelProperty("范围内容")
    @TableField("scp_txt")
    private String scpTxt;

    @ApiModelProperty("业务说明内容")
    @TableField("bsns_expl_txt")
    private String bsnsExplTxt;

    @ApiModelProperty("删除与否")
    @TableField("del_yn")
    private String delYn = Constants.DelYn.YES;

    @ApiModelProperty("创建CV用户ID")
    @TableField("cret_cv_user_id")
    private String cretCvUserId;


}
