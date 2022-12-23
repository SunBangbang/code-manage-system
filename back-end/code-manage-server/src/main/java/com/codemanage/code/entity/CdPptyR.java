package com.codemanage.code.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * CVCD_编码属性_关系表
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
@Getter
@Setter
@TableName("cvcd_cd_ppty_r")
@ApiModel(value = "CdPptyR对象", description = "CVCD_编码属性_关系表")
public class CdPptyR implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("上级CV编码属性ID")
    @TableField("hlv_cv_cd_ppty_id")
    private String hlvCvCdPptyId;

    @ApiModelProperty("上级CV编码ID")
    @TableField("hlv_cv_cd_id")
    private String hlvCvCdId;

    @ApiModelProperty("下级CV编码属性ID")
    @TableField("llv_cv_cd_ppty_id")
    private String llvCvCdPptyId;

    @ApiModelProperty("下级CV编码ID")
    @TableField("llv_cv_cd_id")
    private String llvCvCdId;

    @ApiModelProperty("记录开始日时")
    @TableField("rec_bgn_dttm")
    private Date recBgnDttm;

    @ApiModelProperty("记录结束日时")
    @TableField("rec_fnsh_dttm")
    private Date recFnshDttm;

    @ApiModelProperty("CV属性关系区分编码")
    @TableField("cv_ppty_rlts_dsts_cd")
    private String cvPptyRltsDstsCd;

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
