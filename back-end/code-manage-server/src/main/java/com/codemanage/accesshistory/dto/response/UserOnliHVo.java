package com.codemanage.accesshistory.dto.response;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.codemanage.accesshistory.entity.UserOnliH;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ApiModel(value = "在线历史", description = "在线历史")
public class UserOnliHVo implements Serializable {
    @ApiModelProperty("CV用户登录ID")
    private String cvLgnId;

    @ApiModelProperty("CV用户名")
    private String userNm;

    @ApiModelProperty("CV用户ID")
    private String cvUserId;

    @ApiModelProperty("在线与否")
    private String onliYn;

    @ApiModelProperty("最后登录日时")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLgnDttm;

    @ApiModelProperty("最终登出日时")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date fnlLoutDttm;
}
