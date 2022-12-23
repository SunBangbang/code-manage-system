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
 * CVCD_编码值_主表
 * </p>
 *
 * @author hyh
 * @since 2022-08-01
 */
@Getter
@Setter
@TableName("cvcd_cdvl_m")
@ApiModel(value = "CdvlM对象", description = "CVCD_编码值_主表")
public class CdvlM implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV编码值ID")
    @TableId("cv_cdvl_id")
    private String cvCdvlId;

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

    @ApiModelProperty("编码值")
    @TableField("cd_vl")
    private String cdVl;

    @ApiModelProperty("编码值名称")
    @TableField("cdvl_nm")
    private String cdvlNm;

    @ApiModelProperty("编码值说明内容")
    @TableField("cdvl_expl_txt")
    private String cdvlExplTxt;

    @ApiModelProperty("最终与否")
    @TableField("fnl_yn")
    private String fnlYn;

    @ApiModelProperty("CV排序序号")
    @TableField("sort_srno")
    private Integer cvSortSrno;

    @ApiModelProperty("创建CV用户ID")
    @TableField("cret_cv_user_id")
    private String cretCvUserId;


}
