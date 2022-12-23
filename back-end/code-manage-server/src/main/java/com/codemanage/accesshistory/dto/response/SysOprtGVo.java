package com.codemanage.accesshistory.dto.response;

import com.baomidou.mybatisplus.annotation.TableField;
import com.codemanage.accesshistory.entity.SysOprtG;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ApiModel(value = "操作记录", description = "操作记录")
public class SysOprtGVo implements Serializable {
    @ApiModelProperty("CV操作日志序号")
    private String cvOprtLogSrno;

    @ApiModelProperty("CV用户ID")
    private String cvUserId;

    @ApiModelProperty("记录时间日时")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recTimeDttm;

    @ApiModelProperty("系统IP")
    private String sysIp;

    @ApiModelProperty("日志程序名称")
    private String logPgmNm;

    @ApiModelProperty("操作结果说明内容")
    private String oprtRsltExplTxt;

    @ApiModelProperty("操作参数名称")
    private String oprtParmNm;

    @ApiModelProperty("操作简单说明内容")
    private String oprtSmptExplTxt;

    @ApiModelProperty("操作详细说明内容")
    private String oprtDtlExplTxt;

    @ApiModelProperty("CV日志记录编码")
    private String cvLogRecCd;

    @ApiModelProperty("CV收集类型编码")
    private String cvGatrTpCd;

    @ApiModelProperty("CV用户登录ID")
    private String cvLgnId;

    @ApiModelProperty("CV用户名")
    private String userNm;
}
