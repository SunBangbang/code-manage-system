package com.codemanage.code.entity;

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
 * CVCD_编码属性_主表
 * </p>
 *
 * @author hyh
 * @since 2022-08-01
 */
@Getter
@Setter
@TableName("cvcd_cd_ppty_m")
@ApiModel(value = "CdPptyM对象", description = "CVCD_编码属性_主表")
public class CdPptyM implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV编码属性ID")
    @TableId("cv_cd_ppty_id")
    private String cvCdPptyId;

    @ApiModelProperty("CV编码ID")
    @TableField("cv_cd_id")
    private String cvCdId;

    @ApiModelProperty("记录结束日时")
    @TableField("rec_fnsh_dttm")
    private Date recFnshDttm;

    @ApiModelProperty("记录开始日时")
    @TableField("rec_bgn_dttm")
    private Date recBgnDttm;

    @ApiModelProperty("CV编码领域ID")
    @TableField("cv_cd_sphr_id")
    private String cvCdSphrId;

    @ApiModelProperty("编码属性名称")
    @TableField("cd_ppty_nm")
    private String cdPptyNm;

    @ApiModelProperty("主键与否")
    @TableField("pk_yn")
    private String pkYn;

    @ApiModelProperty("NULL与否")
    @TableField("null_yn")
    private String nullYn;

    @ApiModelProperty("数据类型名称")
    @TableField("data_tp_nm")
    private String dataTpNm;

    @ApiModelProperty("数据长度")
    @TableField("data_len")
    private Integer dataLen;

    @ApiModelProperty("最终与否")
    @TableField("fnl_yn")
    private String fnlYn = Constants.FnlYn.YES;

    @ApiModelProperty("创建CV用户ID")
    @TableField("cret_cv_user_id")
    private String cretCvUserId;

    @ApiModelProperty("CV排序序号")
    @TableField("cv_sort_srno")
    private Integer cvSortSrno;

    @ApiModelProperty("CV属性区分编码")
    @TableField("cv_ppty_dsts_cd")
    private String cvPptyDstsCd;


}
