package com.codemanage.common.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 当前登录用户
 * @author hyh
 * @since 2022-06-06
 */
@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String loginId;

    private String userName;

    private String companyId;

    private String cmpyGmNm;
}
