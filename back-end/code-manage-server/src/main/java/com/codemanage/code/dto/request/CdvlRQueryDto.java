package com.codemanage.code.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 编码值关系QueryDto
 * </p>
 *
 * @author hyh
 * @since 2022-08-16
 */
@Getter
@Setter
@ApiModel(value = "编码值关系QueryDto", description = "编码值关系QueryDto")
public class CdvlRQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("上级CV编码ID")
    private String hlvCvCdId;

    @ApiModelProperty("下级CV编码ID")
    private String llvCvCdId;

}
