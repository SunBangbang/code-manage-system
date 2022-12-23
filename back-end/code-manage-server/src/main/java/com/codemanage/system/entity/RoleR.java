package com.codemanage.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * CVSO_角色_关系表
 * </p>
 *
 * @author hyh
 * @since 2022-07-09
 */
@Getter
@Setter
@TableName("cvso_role_r")
@ApiModel(value = "RoleR对象", description = "CVSO_角色_关系表")
public class RoleR implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("下级CV用户ID")
    @TableField("llv_cv_user_id")
    private String llvCvUserId;

    @ApiModelProperty("上级CV用户ID")
    @TableField("hlv_cv_user_id")
    private String hlvCvUserId;

    @ApiModelProperty("记录结束日时")
    @TableField("rec_fnsh_dttm")
    private Date recFnshDttm;

    @ApiModelProperty("记录开始日时")
    @TableField("rec_bgn_dttm")
    private Date recBgnDttm;

    @ApiModelProperty("上级CV用户区分编码")
    @TableField("hlv_cv_user_dsts_cd")
    private String hlvCvUserDstsCd;

    @ApiModelProperty("下级CV用户区分编码")
    @TableField("llv_cv_user_dsts_cd")
    private String llvCvUserDstsCd;

    @ApiModelProperty("最终与否")
    @TableField("fnl_yn")
    private String fnlYn = Constants.FnlYn.YES;

    @ApiModelProperty("创建CV用户ID")
    @TableField("cret_cv_user_id")
    private String cretCvUserId;


}
