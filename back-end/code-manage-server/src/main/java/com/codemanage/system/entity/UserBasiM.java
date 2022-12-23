package com.codemanage.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.codemanage.common.constants.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * CVSO_用户基础_主表
 * </p>
 *
 * @author hyh
 * @since 2022-07-09
 */
@Getter
@Setter
@TableName("cvso_user_basi_m")
@ApiModel(value = "UserBasiM对象", description = "CVSO_用户基础_主表")
public class UserBasiM implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("CV用户ID")
    @TableId("cv_user_id")
    private String cvUserId;

    @ApiModelProperty("记录结束日时")
    @TableField("rec_fnsh_dttm")
    private Date recFnshDttm;

    @ApiModelProperty("记录开始日时")
    @TableField("rec_bgn_dttm")
    private Date recBgnDttm;

    @ApiModelProperty("用户名称")
    @TableField("user_nm")
    private String userNm;

    @ApiModelProperty("用户说明内容")
    @TableField("user_expl_txt")
    private String userExplTxt;

    @ApiModelProperty("CV用户区分编码")
    @TableField("cv_user_dsts_cd")
    private String cvUserDstsCd;

    @ApiModelProperty("上级CV用户ID")
    @TableField("hlv_cv_user_id")
    private String hlvCvUserId = "0";

    @ApiModelProperty("CV语言编码")
    @TableField("cv_lang_cd")
    private String cvLangCd = Constants.LangCd.SIMPLIFIED_CHINESE;

    @ApiModelProperty("账户到期日期")
    @TableField("bnac_expr_dt")
    private String bnacExprDt;

    @ApiModelProperty("CV账户锁定状态编码")
    @TableField("cv_bnac_lck_st_cd")
    private String cvBnacLckStCd;

    @ApiModelProperty("最终与否")
    @TableField("fnl_yn")
    private String fnlYn = Constants.FnlYn.YES;

    @ApiModelProperty("创建CV用户ID")
    @TableField("cret_cv_user_id")
    private String cretCvUserId;

    @ApiModelProperty("CV登录ID")
    @TableField("cv_lgn_id")
    private String cvLgnId;

    @ApiModelProperty("登录密码密文")
    @TableField("lgn_pwd_encd")
    private String lgnPwdEncd;

    @ApiModelProperty("手机号码")
    @TableField("mbph_no")
    private String mbphNo;

    @ApiModelProperty("电话号码")
    @TableField("tlph_no")
    private String tlphNo;

    @ApiModelProperty("公司电话号码")
    @TableField("cmpy_tlph_no")
    private String cmpyTlphNo;

    @ApiModelProperty("邮箱URL")
    @TableField("mlbx_url")
    private String mlbxUrl;

    @ApiModelProperty("CV所有公司ID")
    @TableField("cv_own_cmpy_id")
    private String cvOwnCmpyId;

    @ApiModelProperty("公司总经理名称")
    @TableField("cmpy_gm_nm")
    private String cmpyGmNm;


    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        list1.add("5");

        List<String> list2 = new ArrayList<>();
        list2.add("2");
        list2.add("3");
        list2.add("6");
        list2.add("7");
//
//        // 交集
//        List<String> intersection = list1.stream().filter(item -> list2.contains(item)).collect(Collectors.toList());
//        System.out.println("---交集 intersection---");
//        intersection.parallelStream().forEach(System.out::println);

        // 差集 (list1 - list2)
        List<String> reduce1 = list1.stream().filter(item -> !list2.contains(item)).collect(Collectors.toList());
        System.out.println("---差集 reduce1 (list1 - list2)---");
        reduce1.parallelStream().forEach(System.out::println);

        // 差集 (list2 - list1)
        List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(Collectors.toList());
        System.out.println("---差集 reduce2 (list2 - list1)---");
        reduce2.parallelStream().forEach(System.out::println);

//        // 并集
//        List<String> listAll = list1.parallelStream().collect(Collectors.toList());
//        List<String> listAll2 = list2.parallelStream().collect(Collectors.toList());
//        listAll.addAll(listAll2);
//        System.out.println("---并集 listAll---");
//        listAll.parallelStream().forEachOrdered(System.out::println);
//
//        // 去重并集
//        List<String> listAllDistinct = listAll.stream().distinct().collect(Collectors.toList());
//        System.out.println("---得到去重并集 listAllDistinct---");
//        listAllDistinct.parallelStream().forEachOrdered(System.out::println);

    }
}
