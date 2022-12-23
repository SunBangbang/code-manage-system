package com.codemanage.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 初始化 控制器
 * @author hyh
 * @since  2022-06-14
 **/
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    /**
     * 最初始访问系统的请求(因为前后端打成一个jar，所以此处直接重定向到登录页面)
     * 重定向进入到登录页面
     * @return
     */
  /*  @RequestMapping("/")
    public String index()  {
        logger.info("-----------------> 初始化进入系统");
        return "redirect:index.html#/";
    }*/

}
