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
 * CVSO_权限对象基础_主表
 * </p>
 *
 * @author hyh
 * @since 2022-07-16
 */
@Getter
@Setter
@TableName("cvso_auth_obj_basi_m")
@ApiModel(value = "AuthObjBasiM对象", description = "CVSO_权限对象基础_主表")
public class AuthObjBasiM implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV权限对象ID")
    @TableId("cv_auth_obj_id")
    private String cvAuthObjId;

    @ApiModelProperty("记录结束日时")
    @TableField("rec_fnsh_dttm")
    private Date recFnshDttm;

    @ApiModelProperty("记录开始日时")
    @TableField("rec_bgn_dttm")
    private Date recBgnDttm;

    @ApiModelProperty("权限对象名称")
    @TableField("auth_obj_nm")
    private String authObjNm;

    @ApiModelProperty("CVUIID")
    @TableField("cv_ui_id")
    private String cvUiId;

    @ApiModelProperty("权限对象说明内容")
    @TableField("auth_obj_expl_txt")
    private String authObjExplTxt;

    @ApiModelProperty("CV权限对象区分编码")
    @TableField("cv_auth_obj_dsts_cd")
    private String cvAuthObjDstsCd;

    @ApiModelProperty("上级CV权限对象ID")
    @TableField("hlv_cv_auth_obj_id")
    private String hlvCvAuthObjId;

    @ApiModelProperty("最终与否")
    @TableField("fnl_yn")
    private String fnlYn = Constants.FnlYn.YES;

    @ApiModelProperty("创建CV用户ID")
    @TableField("cret_cv_user_id")
    private String cretCvUserId;

    @ApiModelProperty("显示序号")
    @TableField("dspl_srno")
    private Integer dsplSrno;

    @ApiModelProperty("菜单使用与否")
    @TableField("menu_usg_yn")
    private String menuUsgYn;


}
