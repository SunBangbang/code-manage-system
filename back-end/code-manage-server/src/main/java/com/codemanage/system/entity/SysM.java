package com.codemanage.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.codemanage.common.constants.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * CVSO_系统_主表
 * </p>
 *
 * @author hyh
 * @since 2022-07-16
 */
@Getter
@Setter
@TableName("cvso_sys_m")
@ApiModel(value = "SysM对象", description = "CVSO_系统_主表")
public class SysM implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV系统ID")
    @TableId("cv_sys_id")
    private String cvSysId;

    @ApiModelProperty("记录结束日时")
    @TableField("rec_fnsh_dttm")
    private Date recFnshDttm;

    @ApiModelProperty("记录开始日时")
    @TableField("rec_bgn_dttm")
    private Date recBgnDttm;

    @ApiModelProperty("系统名称")
    @TableField("sys_nm")
    private String sysNm;

    @ApiModelProperty("系统说明内容")
    @TableField("sys_expl_txt")
    private String sysExplTxt;

    @ApiModelProperty("最终与否")
    @TableField("fnl_yn")
    private String fnlYn = Constants.FnlYn.YES;

    @ApiModelProperty("CV排序序号")
    @TableField("cv_sort_srno")
    private Integer cvSortSrno;

    @ApiModelProperty("创建CV用户ID")
    @TableField("cret_cv_user_id")
    private String cretCvUserId;


}
