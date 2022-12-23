package com.codemanage.accesshistory.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("cvso_sys_oprt_g")
@ApiModel(value = "SysOprtG对象", description = "CVSO_系统操作_日志表")
public class SysOprtG implements Serializable {

    @ApiModelProperty("CV操作日志序号")
    @TableField("cv_oprt_log_srno")
    private String cvOprtLogSrno;

    @ApiModelProperty("CV用户ID")
    @TableField("cv_user_id")
    private String cvUserId;

    @ApiModelProperty("记录时间日时")
    @TableField("rec_time_dttm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recTimeDttm;

    @ApiModelProperty("系统IP")
    @TableField("sys_ip")
    private String sysIp;

    @ApiModelProperty("日志程序名称")
    @TableField("log_pgm_nm")
    private String logPgmNm;

    @ApiModelProperty("操作结果说明内容")
    @TableField("oprt_rslt_expl_txt")
    private String oprtRsltExplTxt;

    @ApiModelProperty("操作参数名称")
    @TableField("oprt_parm_nm")
    private String oprtParmNm;

    @ApiModelProperty("操作简单说明内容")
    @TableField("oprt_smpt_expl_txt")
    private String oprtSmptExplTxt;

    @ApiModelProperty("操作详细说明内容")
    @TableField("oprt_dtl_expl_txt")
    private String oprtDtlExplTxt;

    @ApiModelProperty("CV日志记录编码")
    @TableField("cv_log_rec_cd")
    private String cvLogRecCd;

    @ApiModelProperty("CV收集类型编码")
    @TableField("cv_gatr_tp_cd")
    private String cvGatrTpCd;
}
