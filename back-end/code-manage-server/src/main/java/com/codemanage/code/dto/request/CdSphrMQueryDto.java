package com.codemanage.code.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 编码区Querydto
 * @author hyh
 * @since 2022-07-29
 */
@Getter
@Setter
@ApiModel(value = "编码区Querydto")
public class CdSphrMQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编码领域名称")
    private String cdSphrNm;
}
