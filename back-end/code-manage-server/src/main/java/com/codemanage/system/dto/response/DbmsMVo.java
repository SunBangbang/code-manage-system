package com.codemanage.system.dto.response;

import com.codemanage.common.util.PasswordUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.primitives.Bytes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库管理VO
 * @author hyh
 * @since 2022-07-19
 */
@Getter
@Setter
@ApiModel(value = "数据库管理VO")
public class DbmsMVo implements Serializable {
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

    @ApiModelProperty("默认连接CV数据库模式ID")
    private String dfltCnctCvSchmId;

    @ApiModelProperty("记录开始日时")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recBgnDttm;

    @ApiModelProperty("记录结束日时")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date recFnshDttm;

    @ApiModelProperty("数据库管理系统说明内容")
    private String dbmsExplTxt;

    @ApiModelProperty("CV排序序号")
    private Integer cvSortSrno;

    @ApiModelProperty("用户密码密文")
    private byte[] userPwdEncdBytes;

    public byte[] getUserPwdEncdBytes() {
        return userPwdEncdBytes;
    }

    public void setUserPwdEncdBytes(byte[] userPwdEncdBytes) {
        this.userPwdEncdBytes = userPwdEncdBytes;
        if (userPwdEncdBytes != null) {
            this.userPwdEncd = PasswordUtils.byte2Hex(userPwdEncdBytes);
        }
    }
}
