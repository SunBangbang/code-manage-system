package com.codemanage.system.entity;

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
 * CVSO_数据库管理系统_主表
 * </p>
 *
 * @author hyh
 * @since 2022-07-16
 */
@Getter
@Setter
@TableName("cvso_dbms_m")
@ApiModel(value = "DbmsM对象", description = "CVSO_数据库管理系统_主表")
public class DbmsM implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV数据库管理系统ID")
    @TableId("cv_dbms_id")
    private String cvDbmsId;

    @ApiModelProperty("记录结束日时")
    @TableField("rec_fnsh_dttm")
    private Date recFnshDttm;

    @ApiModelProperty("记录开始日时")
    @TableField("rec_bgn_dttm")
    private Date recBgnDttm;

    @ApiModelProperty("数据库管理系统名称")
    @TableField("dbms_nm")
    private String dbmsNm;

    @ApiModelProperty("数据库管理系统说明内容")
    @TableField("dbms_expl_txt")
    private String dbmsExplTxt;

    @ApiModelProperty("数据库管理系统IP")
    @TableField("dbms_ip")
    private String dbmsIp;

    @ApiModelProperty("数据库管理系统端口值")
    @TableField("dbms_port_vl")
    private Integer dbmsPortVl;

    @ApiModelProperty("CV数据库管理系统账户ID")
    @TableField("cv_dbms_bnac_id")
    private String cvDbmsBnacId;

    @ApiModelProperty("用户密码密文")
    @TableField("user_pwd_encd")
    private byte[] userPwdEncd;

    @ApiModelProperty("默认连接CV数据库模式ID")
    @TableField("dflt_cnct_cv_schm_id")
    private String dfltCnctCvSchmId;

    @ApiModelProperty("最终与否")
    @TableField("fnl_yn")
    private String fnlYn = Constants.FnlYn.YES;

    @ApiModelProperty("CV排序序号")
    @TableField("cv_sort_srno")
    private Integer cvSortSrno;

    @ApiModelProperty("创建CV用户ID")
    @TableField("cret_cv_user_id")
    private String cretCvUserId;


}
