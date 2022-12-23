package com.codemanage.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 数据库SaveDto
 * @author hyh
 * @since 2022-07-19
 */
@Getter
@Setter
@ApiModel(value = "数据库SaveDto")
public class DbmsSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV数据库管理系统ID")
    private String cvDbmsId;

    @ApiModelProperty("数据库管理系统名称")
    private String dbmsNm;

    @ApiModelProperty("数据库管理系统IP")
    private String dbmsIp;

    @ApiModelProperty("数据库管理系统端口值")
    private Integer dbmsPortVl;

    @ApiModelProperty("CV数据库管理系统账户ID")
    private String cvDbmsBnacId;

    @ApiModelProperty("用户密码密文")
    private String userPwdEncd;

    @ApiModelProperty("CV排序序号")
    private Integer cvSortSrno;

    @ApiModelProperty("数据库管理系统说明内容")
    private String dbmsExplTxt;

    @ApiModelProperty("开始时间")
    private String recBgnDttm;


}
