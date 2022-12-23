package com.codemanage.system.dto.response;

import com.codemanage.common.entity.TreeDto;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 部门树
 * </p>
 *
 * @author hyh
 * @since 2022-05-31
 */
@Getter
@Setter
public class DeptTreeDto extends TreeDto {
    private String code;
}
