package com.codemanage.common.controller;

import com.codemanage.accesshistory.entity.BnacLgnH;
import com.codemanage.accesshistory.entity.UserOnliH;
import com.codemanage.accesshistory.service.IBnacLgnHService;
import com.codemanage.accesshistory.service.IUserOnliHService;
import com.codemanage.common.entity.BaseResult;
import com.codemanage.common.entity.LoginUser;
import com.codemanage.common.util.UserUtils;
import com.codemanage.system.entity.UserBasiM;
import com.codemanage.system.service.IUserBasiMService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 登出退出 控制器
 *
 * @author hyh
 * @since 2022-06-14
 **/
@RestController
@Api(tags = "登录API")
public class LoginController {

    @Autowired
    private IBnacLgnHService bnacLgnHService;

    @Autowired
    private IUserBasiMService userBasiMService;

    @Autowired
    private IUserOnliHService userOnliHService;

    /**
     * 登录
     *
     * @param user
     * @param response
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "登录")
    public BaseResult login(@RequestBody UserBasiM user, HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = new BaseResult();
        // 获取当前用户主体
        Subject subject = SecurityUtils.getSubject();
        // 封装 UsernamePasswordToken
        UsernamePasswordToken token = new UsernamePasswordToken(user.getCvLgnId(), user.getLgnPwdEncd());
        try {
            subject.login(token);
            result.setData(subject.getSession().getId());
            result.setMsg("登录成功!");

            LoginUser loginUser = UserUtils.getLoginUser();
            BnacLgnH bnacLgnH = new BnacLgnH();
            bnacLgnH.setCvLgnBnacId(loginUser.getLoginId());
            bnacLgnH.setCvUserId(loginUser.getUserId());
            bnacLgnH.setLgnDttm(new Date());
            bnacLgnH.setRemHostIp(request.getRemoteAddr());
            bnacLgnH.setRemHostNm(request.getRemoteHost());
            bnacLgnHService.save(bnacLgnH);

            UserOnliH userOnliH = new UserOnliH();
            userOnliH.setCvUserId(loginUser.getUserId());
            userOnliH.setOnliYn("Y");
            userOnliH.setLastLgnDttm(new Date());
            userOnliH.setFnlLoutDttm(null);
            userOnliHService.saveOrUpdate(userOnliH);

        } catch (UnknownAccountException ke) {
            ke.printStackTrace();
            result.setMsg("账号不存在!");
            result.setCode(500);
        } catch (IncorrectCredentialsException ce) {
            ce.printStackTrace();
            result.setMsg("用户名与密码不匹配，请重新输入!");
            result.setCode(500);
        } catch (LockedAccountException le) {
            le.printStackTrace();
            result.setMsg("账号已被禁用，请联系管理员!");
            result.setCode(500);
        } catch (Exception ae) {
            ae.printStackTrace();
            result.setMsg("账号登录异常，请联系管理员!");
            result.setCode(500);
        }
        return result;
    }

    /**
     * 退出
     *
     * @return
     */
    @GetMapping("/logout")
    @ApiOperation(value = "退出", notes = "退出")
    public BaseResult logout() {
        LoginUser user = UserUtils.getLoginUser();
        BaseResult result = new BaseResult();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        result.setMsg("退出成功!");

        try {
            userOnliHService.logout(user.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    @GetMapping("/getLoginUser")
    @ApiOperation(value = "获取登录用户", notes = "获取登录用户")
    public BaseResult getLoginUser() {
        BaseResult result = new BaseResult();
        LoginUser user = UserUtils.getLoginUser();
        result.setData(user);
        return result;
    }
}
