package com.codemanage.system.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * CVSO_用户基础_Vo
 * </p>
 *
 * @author hyh
 * @since 2022-05-31
 */
@Getter
@Setter
@ApiModel(value = "用户VO")
public class UserBasiMVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV用户ID")
    private String cvUserId;

    @ApiModelProperty(value = "用户名称")
    private String userNm;

    @ApiModelProperty("CV登录ID")
    private String cvLgnId;

    @ApiModelProperty("部门名称")
    private String dept;

    @ApiModelProperty("邮箱URL")
    private String mlbxUrl;

    @ApiModelProperty("公司电话号码")
    private String cmpyTlphNo;

    @ApiModelProperty("账户到期日期")
    private String bnacExprDt;

    @ApiModelProperty("最终与否")
    private String fnlYn;

    @ApiModelProperty("菜单权限")
    private String userrole;

    @ApiModelProperty(value = "上级CV用户ID")
    private String hlvCvUserId;

    @ApiModelProperty("部门Id")
    private String deptId;

    @ApiModelProperty("登录密码密文")
    private String lgnPwdEncd;

    @ApiModelProperty("CV账户锁定状态编码")
    private String cvBnacLckStCd;

}
