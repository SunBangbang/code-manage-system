package com.codemanage.system.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 系统SaveDto
 * @author hyh
 * @since 2022-07-19
 */
@Getter
@Setter
@ApiModel(value = "系统SaveDto")
public class SysMSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV系统ID")
    private String cvSysId;

    @ApiModelProperty("系统名称")
    private String sysNm;

    @ApiModelProperty("CV排序序号")
    private Integer cvSortSrno;

    @ApiModelProperty("系统说明内容")
    private String sysExplTxt;

    @ApiModelProperty("数据库模式集合")
    private List<SysSchmRSaveDto> schmList;

}
