package com.codemanage.common.util;

import com.alibaba.fastjson.JSONObject;
import com.codemanage.common.entity.LoginUser;
import com.codemanage.system.entity.UserBasiM;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录用户工具类
 * @author hyh
 * @since 2022-06-06
 **/
public class UserUtils {

    private static final Logger logger = LoggerFactory.getLogger(UserUtils.class);

    public static LoginUser getLoginUser() {
        LoginUser user = null;
        try {
            user = new LoginUser();
//            user.setUserId("f472195d2a2088580a2ab4e74365eb6e");
//            user.setLoginId("admin");
//            user.setUserName("admin");
//            user.setCompanyId("1");
//            user.setCmpyGmNm("11");
            Object obj = SecurityUtils.getSubject().getPrincipal();
            if (null != obj) {
                UserBasiM userBasiM = (UserBasiM) obj;
                user.setUserId(userBasiM.getCvUserId());
                user.setUserName(userBasiM.getUserNm());
                user.setLoginId(userBasiM.getCvLgnId());
                user.setCompanyId(userBasiM.getCvOwnCmpyId());
                user.setCmpyGmNm(userBasiM.getCmpyGmNm());
            } else {
                logger.error("获取登录用户为空!");
            }
        } catch (Exception e) {
            logger.error("获取登录用户失败,异常信息:" + e.getMessage(), e);
        }
        return user;
    }

    public static void writerMsg(Integer code, String msg, HttpServletResponse response) {
        // 解决返回数据跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        // 返回提示信息到前端
        try {
            PrintWriter writer = response.getWriter();
            Map<String, Object> map= new HashMap<>();
            map.put("code", code);
            map.put("msg", msg);
            writer.write(JSONObject.toJSONString(map));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("返回response信息失败,异常信息:", e);
        }
    }
}
