package com.codemanage.code.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * CVCD_共通编码值_主表
 * </p>
 *
 * @author hyh
 * @since 2022-08-16
 */
@Getter
@Setter
@TableName("cvcd_cmmn_cd_vl_m")
@ApiModel(value = "CmmnCdVlM对象", description = "CVCD_共通编码值_主表")
public class CmmnCdVlM implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV编码ID")
    @TableId("cv_cd_id")
    private String cvCdId;

    @ApiModelProperty("CV编码值ID")
    @TableField("cv_cdvl_id")
    private String cvCdvlId;

    @ApiModelProperty("编码值名称")
    @TableField("cdvl_nm")
    private String cdvlNm;

    @ApiModelProperty("编码值说明内容")
    @TableField("cdvl_expl_txt")
    private String cdvlExplTxt;


}
