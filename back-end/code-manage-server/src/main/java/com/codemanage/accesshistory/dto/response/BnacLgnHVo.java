package com.codemanage.accesshistory.dto.response;

import com.baomidou.mybatisplus.annotation.TableField;
import com.codemanage.accesshistory.entity.BnacLgnH;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ApiModel(value = "登录历史", description = "登录历史")
public class BnacLgnHVo implements Serializable {
    @ApiModelProperty("CV用户ID")
    private String cvUserId;

    @ApiModelProperty("登录日时")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lgnDttm;

    @ApiModelProperty("CV登录结果区分编码")
    private String cvLgnRsltDstsCd;

    @ApiModelProperty("远程主机IP")
    private String remHostIp;

    @ApiModelProperty("远程主机名称")
    private String remHostNm;

    @ApiModelProperty("CV登录账户ID")
    private String cvLgnBnacId;

    @ApiModelProperty("CV用户登录ID")
    private String cvLgnId;

    @ApiModelProperty("CV用户名")
    private String userNm;
}
