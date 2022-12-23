package com.codemanage.system.dto.response;

import com.codemanage.common.entity.TreeDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * <p>
 * 菜单树
 * </p>
 *
 * @author hyh
 * @since 2022-07-16
 */
@Getter
@Setter
public class AuthTreeDto extends TreeDto {

    @ApiModelProperty("CVUIID")
    private String cvUiId;

    @ApiModelProperty("权限对象说明内容")
    private String authObjExplTxt;

    @ApiModelProperty("显示序号")
    private Integer dsplSrno;

    @ApiModelProperty("菜单使用与否")
    private String menuUsgYn;

    @ApiModelProperty("CV权限对象区分编码")
    private String cvAuthObjDstsCd;

    @ApiModelProperty("是否选中")
    private boolean checked = false;
}
