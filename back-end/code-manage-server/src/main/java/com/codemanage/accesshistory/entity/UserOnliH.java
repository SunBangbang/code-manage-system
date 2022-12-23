package com.codemanage.accesshistory.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@TableName("cvso_user_onli_h")
@ApiModel(value = "UserOnliH对象", description = "CVSO_用户在线_历史表")
public class UserOnliH implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV用户ID")
    @TableId("cv_user_id")
    private String cvUserId;

    @ApiModelProperty("在线与否")
    @TableField("onli_yn")
    private String onliYn;

    @ApiModelProperty("最后登录日时")
    @TableField("last_lgn_dttm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLgnDttm;

    @ApiModelProperty("最终登出日时")
    @TableField("fnl_lout_dttm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date fnlLoutDttm;
}
