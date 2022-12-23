package com.codemanage.code.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * CVCD_编码值_Dto
 * </p>
 *
 * @author hyh
 * @since 2022-06-07
 */
@Getter
@Setter
@ApiModel(value = "编码值参数")
public class CdvlMDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("当前页")
    private Integer currentPage;

    @ApiModelProperty("每页条数")
    private Integer pageSize;

    @ApiModelProperty(value = "编码值", required = true)
    private String cdVl;

}
