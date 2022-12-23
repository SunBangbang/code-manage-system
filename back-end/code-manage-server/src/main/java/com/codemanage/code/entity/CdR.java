package com.codemanage.code.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * CVCD_编码_关系表
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
@Getter
@Setter
@TableName("cvcd_cd_r")
@ApiModel(value = "CdR对象", description = "CVCD_编码_关系表")
public class CdR implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("上级CV编码ID")
    @TableField("hlv_cv_cd_id")
    private String hlvCvCdId;

    @ApiModelProperty("下级CV编码ID")
    @TableField("llv_cv_cd_id")
    private String llvCvCdId;

    @ApiModelProperty("CV编码关系编码")
    @TableField("cv_cd_rlts_cd")
    private String cvCdRltsCd;

    @ApiModelProperty("记录开始日时")
    @TableField("rec_bgn_dttm")
    private Date recBgnDttm;

    @ApiModelProperty("记录结束日时")
    @TableField("rec_fnsh_dttm")
    private Date recFnshDttm;

    @ApiModelProperty("最终与否")
    @TableField("fnl_yn")
    private String fnlYn = Constants.FnlYn.YES;

    @ApiModelProperty("创建CV用户ID")
    @TableField("cret_cv_user_id")
    private String cretCvUserId;


}
