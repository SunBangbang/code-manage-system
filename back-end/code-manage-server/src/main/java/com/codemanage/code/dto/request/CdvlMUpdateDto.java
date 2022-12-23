package com.codemanage.code.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ApiModel(value = "编码值SaveDto", description = "编码值保存")
public class CdvlMUpdateDto implements Serializable {

    @ApiModelProperty("当前CV编码值ID")
    private String cvCdvlId;

    @ApiModelProperty("编码属性ID-编码属性值对应表")
    private Map<String, String> values = new HashMap<>();
}
