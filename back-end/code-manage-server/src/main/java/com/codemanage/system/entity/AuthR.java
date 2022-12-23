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
 * CVSO_权限_关系表
 * </p>
 *
 * @author hyh
 * @since 2022-07-16
 */
@Getter
@Setter
@TableName("cvso_auth_r")
@ApiModel(value = "AuthR对象", description = "CVSO_权限_关系表")
public class AuthR implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("上级CV权限对象ID")
    @TableField("hlv_cv_auth_obj_id")
    private String hlvCvAuthObjId;

    @ApiModelProperty("下级CV权限对象ID")
    @TableField("llv_cv_auth_obj_id")
    private String llvCvAuthObjId;

    @ApiModelProperty("记录结束日时")
    @TableField("rec_fnsh_dttm")
    private Date recFnshDttm;

    @ApiModelProperty("记录开始日时")
    @TableField("rec_bgn_dttm")
    private Date recBgnDttm;

    @ApiModelProperty("上级CV权限对象区分编码")
    @TableField("hlv_cv_auth_obj_dsts_cd")
    private String hlvCvAuthObjDstsCd;

    @ApiModelProperty("下级CV权限对象区分编码")
    @TableField("llv_cv_auth_obj_dsts_cd")
    private String llvCvAuthObjDstsCd;

    @ApiModelProperty("最终与否")
    @TableField("fnl_yn")
    private String fnlYn = Constants.FnlYn.YES;

    @ApiModelProperty("创建CV用户ID")
    @TableField("cret_cv_user_id")
    private String cretCvUserId;


}
