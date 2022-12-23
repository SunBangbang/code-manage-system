package com.codemanage.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 权限对象SaveDto
 * @author hyh
 * @since 2022-07-19
 */
@Getter
@Setter
@ApiModel(value = "权限对象SaveDto")
public class AuthObjSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV权限对象ID")
    private String cvAuthObjId;

    @ApiModelProperty("权限对象名称")
    private String authObjNm;

    @ApiModelProperty("CVUIID")
    private String cvUiId;

    @ApiModelProperty("权限对象说明内容")
    private String authObjExplTxt;

    @ApiModelProperty("CV权限对象区分编码")
    private String cvAuthObjDstsCd;

    @ApiModelProperty("上级CV权限对象ID")
    private String hlvCvAuthObjId;

    @ApiModelProperty("显示序号")
    private Integer dsplSrno;

    @ApiModelProperty("菜单使用与否")
    private String menuUsgYn;

}
