package com.codemanage.accesshistory.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@ApiModel(value = "用户在线参数")
public class SysOprtGSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("操作简单说明内容")
    private String oprtSmptExplTxt;

    @ApiModelProperty("操作详细说明内容")
    private String oprtDtlExplTxt;


}
