package com.codemanage.code.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * <p>
 * 编码属性关系SaveDto
 * </p>
 *
 * @author hyh
 * @since 2022-08-09
 */
@Getter
@Setter
@ApiModel(value = "编码属性关系SaveDto", description = "编码属性关系SaveDto")
public class CdPptyRAutoSaveDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("上级CV编码ID")
    private String hlvCvCdId;

    @ApiModelProperty("下级CV编码ID")
    private String llvCvCdId;

    @ApiModelProperty("排序")
    private String codeOrder;

    @ApiModelProperty("是否等于100")
    private boolean equals100;
}
