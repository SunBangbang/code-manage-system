package com.codemanage.common.constants;


import com.codemanage.common.util.DateUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 常量类
 * @author hyh
 * @since  2022-05-30
 **/
public class Constants {

    /**
     * 成功 返回code
     */
    public static final int CODE_SUCCESS = 200;

    /**
     *  失败 返回code
     */
    public static final int CODE_FAILED = 500;

    /**
     * 分隔符
     */
    public static final String DELIMITER = ",";

    /**
     * 截至日期
     */
    public static final Date FNSHDTTM = DateUtils.parseDate("9999-12-31 00:00:00.000","yyyy-MM-dd HH:mm:ss.SSS");


    public static final String DEFAULT_PASSWORD_USER = "amore123456";

    public static final String DEFAULT_PASSWORD_DBMS = "123456";

    /**
     * 超级账号 用户id
     */
    public static final List<String> SUPER_ACCOUNT_USER_ID = Arrays.asList("ffd05bbb-b9e4-4d65-8ae2-4b1fda25be27", "8", "101bb429-3f9f-4297-ac4d-22fb5bfe70fd");

    /**
     * 超级账号 角色id
     */
    public static final List<String> SUPER_ACCOUNT_ROLE_ID = Arrays.asList("11", "10");

    /**
     * 最终与否
     */
    public interface FnlYn {
        /** 否 */
        String NO = "N";

        /** 是 */
        String YES = "Y";

    }

    public interface DelYn {
        /** 否 */
        String NO = "N";

        /** 是 */
        String YES = "Y";

    }

    public interface LangCd {
        /** 简体中午 */
        String SIMPLIFIED_CHINESE = "zh-CN";

        /** 英语 */
        String ENGLISH = "en";

    }
    /**
     * 用户类型编码
     */
    public interface UserDstsCd {
        /** 个人用户 */
        String USER = "0100";

        /** 部门 */
        String DEPT = "0200";

        /** 角色 */
        String ROLE = "0300";

        /** 公司 */
        String COMPANY = "0400";
    }

    /**
     * 菜单类型编码
     */
    public interface UserBnacLckStCd {
        /** 正常 */
        String UNLOCK = "1010";

        /** 锁定 */
        String LOCK = "1020";

    }

    /**
     * 菜单类型编码
     */
    public interface AuthDstsCd {
        /** 文件夹 */
        String FOLDER = "1010";

        /** 菜单 */
        String MENU = "1020";


    }

    /**
     * DBMS管理类型
     */
    public interface DbmsType {
        /** root */
        String ROOT = "root";

        /** db */
        String DB = "db";

        /** schm */
        String SCHM = "schm";

        /** tab */
        String TAB = "tab";

        /** fild */
        String FILD = "fild";


    }

    /**
     * 编码领域区分编码
     */
    public interface SphrDstsCd {
        /** 类型1 */
        String TYPE_ONE = "0010";
    }

    /**
     *公共编码id
     */
    public interface CmmnCdId {
        /** 编码状态编码 */
        String CD_ST_CD = "00001";

        /** 编码类型编码 */
        String CD_TP_CD = "00002";

        /** 数据类型 */
        String DATA_TP = "00003";

    }
}
