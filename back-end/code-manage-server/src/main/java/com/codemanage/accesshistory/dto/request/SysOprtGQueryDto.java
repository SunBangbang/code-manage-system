package com.codemanage.accesshistory.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ApiModel(value = "用户在线参数")
public class SysOprtGQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    private String cvLgnId;

    @ApiModelProperty(value = "用户名")
    private String userNm;

    @ApiModelProperty(value = "开始时间")
    private Date beginTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty("每页条数")
    private Integer size = 50;

    @ApiModelProperty("当前页")
    private Integer current = 1;
}
