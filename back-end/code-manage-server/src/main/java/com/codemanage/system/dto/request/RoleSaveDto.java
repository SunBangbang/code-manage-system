package com.codemanage.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 角色dto
 * @author hyh
 * @since 2022-05-31
 */
@Getter
@Setter
@ApiModel(value = "角色SaveDto")
public class RoleSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色ID")
    private String cvUserId;

    @ApiModelProperty(value = "角色名称",required = true)
    private String userNm;

    @ApiModelProperty("角色说明内容")
    private String userExplTxt;

    @ApiModelProperty(value = "CV用户区分编码")
    private String cvUserDstsCd;

    @ApiModelProperty(value = "用户id集合")
    private List<String> userIdList;

    @ApiModelProperty(value = "菜单id集合")
    private List<String> authIdList;
}
