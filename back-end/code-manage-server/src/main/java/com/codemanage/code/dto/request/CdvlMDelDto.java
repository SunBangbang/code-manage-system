package com.codemanage.code.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 编码值DelDto
 * </p>
 *
 * @author hyh
 * @since 2022-08-16
 */
@Getter
@Setter
@ApiModel(value = "编码值DelDto", description = "编码值DelDto")
public class CdvlMDelDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "CV编码ID")
    private String cvCdId;

    @ApiModelProperty(value = "编码值id集合")
    private List<String> cvCdvlIdList;

}
