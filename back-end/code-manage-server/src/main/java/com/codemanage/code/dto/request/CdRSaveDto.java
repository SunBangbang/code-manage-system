package com.codemanage.code.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * <p>
 * 编码关系SaveDto
 * </p>
 *
 * @author hyh
 * @since 2022-07-31
 */
@Getter
@Setter
@ApiModel(value = "编码关系SaveDto", description = "编码关系SaveDto")
public class CdRSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("上级CV编码ID")
    private String hlvCvCdId;

    @ApiModelProperty("下级CV编码ID")
    private String llvCvCdId;

    @ApiModelProperty("CV编码关系编码")
    private String cvCdRltsCd;
}
