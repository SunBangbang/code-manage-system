package com.codemanage.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.codemanage.common.constants.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * CVSO_数据库模式_主表
 * </p>
 *
 * @author hyh
 * @since 2022-07-16
 */
@Getter
@Setter
@TableName("cvso_schm_m")
@ApiModel(value = "SchmM对象", description = "CVSO_数据库模式_主表")
public class SchmM implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV数据库模式ID")
    @TableId("cv_schm_id")
    private String cvSchmId;

    @ApiModelProperty("CV数据库管理系统ID")
    @TableField("cv_dbms_id")
    private String cvDbmsId;

    @ApiModelProperty("记录结束日时")
    @TableField("rec_fnsh_dttm")
    private Date recFnshDttm;

    @ApiModelProperty("记录开始日时")
    @TableField("rec_bgn_dttm")
    private Date recBgnDttm;

    @ApiModelProperty("数据库模式名称")
    @TableField("schm_nm")
    private String schmNm;

    @ApiModelProperty("数据库模式说明内容")
    @TableField("schm_expl_txt")
    private String schmExplTxt;

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
