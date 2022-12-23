package com.codemanage.system.dto.request;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.codemanage.common.constants.Constants;
import com.codemanage.system.entity.UserBasiM;
import com.google.common.base.Converter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * CVSO_用户基础_Dto
 * </p>
 *
 * @author hyh
 * @since 2022-05-31
 */
@Getter
@Setter
@ApiModel(value = "用户参数")
public class UserBasiMDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV用户ID")
    private String cvUserId;

    @ApiModelProperty(value = "用户名称",required = true)
    private String userNm;

    @ApiModelProperty("用户说明内容")
    private String userExplTxt;

    @ApiModelProperty(value = "CV用户区分编码")
    private String cvUserDstsCd;

    @ApiModelProperty(value = "上级CV用户ID",required = true)
    private String hlvCvUserId;

    @ApiModelProperty(value = "CV语言编码")
    private String cvLangCd;

    @ApiModelProperty(value = "账户到期日期")
    private String bnacExprDt;

    @ApiModelProperty("CV登录ID")
    private String cvLgnId;

    @ApiModelProperty("登录密码密文")
    private String lgnPwdEncd;

    @ApiModelProperty("邮箱URL")
    private String mlbxUrl;

    @ApiModelProperty("CV所有公司ID")
    private String cvOwnCmpyId;

    @ApiModelProperty("部门名称")
    private String dept;

    @ApiModelProperty("角色集合")
    private List<String> roleList;

    @ApiModelProperty("最终与否")
    private String fnlYn;

    @ApiModelProperty("是否到期")
    private String isExpired;

    public UserBasiM convertToUserBasiM(){
        UserBasiMDto.UserBasiMDtoConvert dtoConvert = new UserBasiMDto.UserBasiMDtoConvert();
        UserBasiM obj = dtoConvert.convert(this);
        return obj;
    }

    public UserBasiMDto convertFor(UserBasiM obj){
        UserBasiMDto.UserBasiMDtoConvert dtoConvert = new UserBasiMDto.UserBasiMDtoConvert();
        UserBasiMDto dto = dtoConvert.reverse().convert(obj);
        return dto;
    }

    private static class UserBasiMDtoConvert extends Converter<UserBasiMDto, UserBasiM> {
        @Override
        protected UserBasiM doForward(UserBasiMDto dto) {
            UserBasiM obj = new UserBasiM();
            if (StringUtils.isEmpty(dto.getLgnPwdEncd())) {
                dto.setLgnPwdEncd(Constants.DEFAULT_PASSWORD_USER);
            }
            if (StringUtils.isEmpty(dto.getHlvCvUserId())) {
                dto.setHlvCvUserId("0");
            }
            BeanUtils.copyProperties(dto, obj);
            return obj;
        }

        @Override
        protected UserBasiMDto doBackward(UserBasiM obj) {
            UserBasiMDto dto = new UserBasiMDto();
            BeanUtils.copyProperties(obj, dto);
            return dto;
        }
    }

}
