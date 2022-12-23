package com.codemanage.code.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codemanage.common.constants.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * CVCD_编码领域_主表
 * </p>
 *
 * @author hyh
 * @since 2022-06-02
 */
@Getter
@Setter
@ToString
@TableName("cvcd_cd_sphr_m")
@ApiModel(value = "CdSphrM对象", description = "CVCD_编码领域_主表")
public class CdSphrM implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV编码领域ID")
    @TableId("cv_cd_sphr_id")
    private String cvCdSphrId;

    @ApiModelProperty("记录结束日时")
    @TableField("rec_fnsh_dttm")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date recFnshDttm;

    @ApiModelProperty("记录开始日时")
    @TableField("rec_bgn_dttm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recBgnDttm;

    @ApiModelProperty("编码领域名称")
    @TableField("cd_sphr_nm")
    private String cdSphrNm;

    @ApiModelProperty("CV编码领域区分编码")
    @TableField("cv_cd_sphr_dsts_cd")
    private String cvCdSphrDstsCd;

    @ApiModelProperty("编码领域说明内容")
    @TableField("cd_sphr_expl_txt")
    private String cdSphrExplTxt;

    @ApiModelProperty("最终与否")
    @TableField("fnl_yn")
    private String fnlYn = Constants.FnlYn.YES;

    @ApiModelProperty("上级CV编码领域ID")
    @TableField("hlv_cv_cd_sphr_id")
    private String hlvCvCdSphrId;

    @ApiModelProperty("创建CV用户ID")
    @TableField("cret_cv_user_id")
    private String cretCvUserId;


}
