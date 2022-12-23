package com.codemanage.code.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * CVCD_编码属性值_主表
 * </p>
 *
 * @author hyh
 * @since 2022-08-02
 */
@Getter
@Setter
@TableName("cvcd_cd_ptyv_m")
@ApiModel(value = "CdPtyvM对象", description = "CVCD_编码属性值_主表")
public class CdPtyvM implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV编码值ID")
    @TableId("cv_cdvl_id")
    private String cvCdvlId;

    @ApiModelProperty("CV编码属性ID")
    @TableField("cv_cd_ppty_id")
    private String cvCdPptyId;

    @ApiModelProperty("CV编码ID")
    @TableField("cv_cd_id")
    private String cvCdId;

    @ApiModelProperty("记录结束日时")
    @TableField("rec_fnsh_dttm")
    private Date recFnshDttm;

    @ApiModelProperty("记录开始日时")
    @TableField("rec_bgn_dttm")
    private Date recBgnDttm;

    @ApiModelProperty("CV编码领域ID")
    @TableField("cv_cd_sphr_id")
    private String cvCdSphrId;

    @ApiModelProperty("编码属性值")
    @TableField("cd_ppty_vl")
    private String cdPptyVl;

    @ApiModelProperty("编码属性值说明内容")
    @TableField("cd_ptyv_expl_txt")
    private String cdPtyvExplTxt;

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
