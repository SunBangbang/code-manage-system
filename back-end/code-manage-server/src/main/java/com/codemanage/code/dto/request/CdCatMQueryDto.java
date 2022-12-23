package com.codemanage.code.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 编码分类Querydto
 * @author hyh
 * @since 2022-07-29
 */
@Getter
@Setter
@ApiModel(value = "编码分类Querydto")
public class CdCatMQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("编码分类名称")
    private String cdCatNm;
}
