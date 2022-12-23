package com.codemanage.system.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 角色VO
 * @author hyh
 * @since 2022-05-31
 */
@Getter
@Setter
@ApiModel(value = "角色VO")
public class RoleVo  implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("角色CV用户ID")
    private String cvUserId;

    @ApiModelProperty(value = "角色名称")
    private String userNm;

    @ApiModelProperty(value = "角色内容说明")
    private String userExplTxt;
}
