package com.codemanage.accesshistory.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * CVSO_账号登录_历史表
 * </p>
 *
 * @author hyh
 * @since 2022-06-15
 */
@Getter
@Setter
@TableName("cvso_bnac_lgn_h")
@ApiModel(value = "BnacLgnH对象", description = "CVSO_账号登录_历史表")
public class BnacLgnH implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV用户ID")
    @TableField("cv_user_id")
    private String cvUserId;

    @ApiModelProperty("登录日时")
    @TableField("lgn_dttm")
    private Date lgnDttm;

    @ApiModelProperty("CV登录结果区分编码")
    @TableField("cv_lgn_rslt_dsts_cd")
    private String cvLgnRsltDstsCd;

    @ApiModelProperty("远程主机IP")
    @TableField("rem_host_ip")
    private String remHostIp;

    @ApiModelProperty("远程主机名称")
    @TableField("rem_host_nm")
    private String remHostNm;

    @ApiModelProperty("CV登录账户ID")
    @TableField("cv_lgn_bnac_id")
    private String cvLgnBnacId;


}
