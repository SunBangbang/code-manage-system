package com.codemanage.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 部门dto
 * @author hyh
 * @since 2022-05-31
 */
@Getter
@Setter
@ApiModel(value = "部门参数")
public class DeptSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("部门ID")
    private String cvUserId;

    @ApiModelProperty(value = "部门名称",required = true)
    private String userNm;

    @ApiModelProperty("部门说明内容")
    private String userExplTxt;

    @ApiModelProperty(value = "CV用户区分编码")
    private String cvUserDstsCd;

    @ApiModelProperty(value = "上级部门ID",required = true)
    private String hlvCvUserId;


}
