package com.codemanage.code.entity;

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
 * CVCD_编码_主表
 * </p>
 *
 * @author hyh
 * @since 2022-07-31
 */
@Getter
@Setter
@TableName("cvcd_cd_m")
@ApiModel(value = "CdM对象", description = "CVCD_编码_主表")
public class CdM implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV编码ID")
    @TableId("cv_cd_id")
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

    @ApiModelProperty("CV编码分类ID")
    @TableField("cv_cd_cat_id")
    private String cvCdCatId;

    @ApiModelProperty("编码逻辑名称")
    @TableField("cd_lgc_nm")
    private String cdLgcNm;

    @ApiModelProperty("编码物理名称")
    @TableField("cd_phys_nm")
    private String cdPhysNm;

    @ApiModelProperty("编码值保存与否")
    @TableField("cdvl_save_yn")
    private String cdvlSaveYn;

    @ApiModelProperty("CV编码类型编码")
    @TableField("cv_cd_tp_cd")
    private String cvCdTpCd;

    @ApiModelProperty("所属CV系统ID")
    @TableField("blng_cv_sys_id")
    private String blngCvSysId;

    @ApiModelProperty("CV参考ID")
    @TableField("cv_ref_id")
    private String cvRefId;

    @ApiModelProperty("数据类型名称")
    @TableField("data_tp_nm")
    private String dataTpNm;

    @ApiModelProperty("数据长度")
    @TableField("data_len")
    private Integer dataLen;

    @ApiModelProperty("编码说明内容")
    @TableField("cd_expl_txt")
    private String cdExplTxt;

    @ApiModelProperty("最终与否")
    @TableField("fnl_yn")
    private String fnlYn = Constants.FnlYn.YES;

    @ApiModelProperty("创建CV用户ID")
    @TableField("cret_cv_user_id")
    private String cretCvUserId;

    @ApiModelProperty("CV编码状态编码")
    @TableField("cv_cd_st_cd")
    private String cvCdStCd;

    @ApiModelProperty("上层CV编码ID")
    @TableField("hlv_cv_cd_id")
    private String hlvCvCdId;
}
