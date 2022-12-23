package com.codemanage.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 角色dto
 * @author hyh
 * @since 2022-05-31
 */
@Getter
@Setter
@ApiModel(value = "角色QueryDto")
public class RoleQueryDto {

    @ApiModelProperty("每页条数")
    private Integer size = 50;

    @ApiModelProperty("当前页")
    private Integer current = 1;

    @ApiModelProperty("角色ID")
    private String roleId;
}
