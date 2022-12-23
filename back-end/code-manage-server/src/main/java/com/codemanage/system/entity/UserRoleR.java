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
 * CVSO_用户角色_关系表
 * </p>
 *
 * @author hyh
 * @since 2022-07-18
 */
@Getter
@Setter
@TableName("cvso_user_role_r")
@ApiModel(value = "UserRoleR对象", description = "CVSO_用户角色_关系表")
public class UserRoleR implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV用户ID")
    @TableField("cv_user_id")
    private String cvUserId;

    @ApiModelProperty("CV权限对象ID")
    @TableField("cv_auth_obj_id")
    private String cvAuthObjId;

    @ApiModelProperty("记录结束日时")
    @TableField("rec_fnsh_dttm")
    private Date recFnshDttm;

    @ApiModelProperty("记录开始日时")
    @TableField("rec_bgn_dttm")
    private Date recBgnDttm;

    @ApiModelProperty("CV用户区分编码")
    @TableField("cv_user_dsts_cd")
    private String cvUserDstsCd;

    @ApiModelProperty("CV权限对象区分编码")
    @TableField("cv_auth_obj_dsts_cd")
    private String cvAuthObjDstsCd;

    @ApiModelProperty("最终与否")
    @TableField("fnl_yn")
    private String fnlYn = Constants.FnlYn.YES;

    @ApiModelProperty("创建CV用户ID")
    @TableField("cret_cv_user_id")
    private String cretCvUserId;


}
