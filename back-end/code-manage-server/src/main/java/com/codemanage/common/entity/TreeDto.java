package com.codemanage.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 通用树
 * @author hyh
 * @since 2022-05-31
 */
@Getter
@Setter
public class TreeDto{
    private String id;
    private String name;
    private boolean open = true;
    private List<? extends TreeDto> children;
    private String parentId;

}
