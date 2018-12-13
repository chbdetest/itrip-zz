package cn.itrip.auth.controller;


import java.util.Calendar;
import java.util.List;

import cn.itrip.common.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itrip.auth.exception.UserLoginFailedException;
import cn.itrip.auth.service.TokenService;
import cn.itrip.auth.service.UserService;
import cn.itrip.beans.dtos.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.vo.ItripTokenVO;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 用户登录控制器
 *
 * @author hduser
 */
@Controller
@RequestMapping(value = "/api")
@Api(value = "API", basePath = "http://api.itrap.com/auth")
public class LoginController {

    @Resource
    private UserService userService;

    @Resource
    private TokenService tokenService;

    @Resource
    private ValidationToken validationToken;

    @ApiOperation(value = "用户登录", httpMethod = "POST",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "根据用户名、密码进行统一认证")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", required = true, value = "用户名", name = "name", defaultValue = "14395202@qq.com"),
            @ApiImplicitParam(paramType = "form", required = true, value = "密码", name = "password", defaultValue = "111111"),
    })
    @RequestMapping(value = "/dologin", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    Dto dologin(@RequestParam String name, @RequestParam String password, HttpServletRequest request) {
        if (!EmptyUtils.isEmpty(name) && !EmptyUtils.isEmpty(password)) {
            ItripUser user = null;
            try {
                System.out.println("MD5.getMd5(password.trim()>>>" + MD5.getMd5(password.trim(), 32));
                user = userService.login(name.trim(), MD5.getMd5(password.trim(), 32));
            } catch (UserLoginFailedException e) {
                return DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_AUTHENTICATION_FAILED);
            } catch (Exception e) {
                e.printStackTrace();
                return DtoUtil.returnFail(e.getMessage(), ErrorCode.AUTH_UNKNOWN);
            }
            if (EmptyUtils.isNotEmpty(user)) {
                String token = tokenService.generateToken(
                        request.getHeader("user-agent"), user);
                System.out.println("token>>>>>>" + token);
                tokenService.save(token, user);

                //返回ItripTokenVO

                long expTime = Calendar.getInstance().getTimeInMillis() + TokenService.SESSION_TIMEOUT * 1000;
                long genTime = Calendar.getInstance().getTimeInMillis();
                System.out.println("expTime>>>" + expTime + " " + "genTime>>>" + genTime);
                ItripTokenVO tokenVO = new ItripTokenVO(token, expTime, genTime);
                System.out.println("tokenVO>>>>" + tokenVO.getToken());
                return DtoUtil.returnDataSuccess(tokenVO);
            } else {
                return DtoUtil.returnFail("用户名密码错误", ErrorCode.AUTH_AUTHENTICATION_FAILED);
            }
        } else {
            return DtoUtil.returnFail("参数错误！检查提交的参数名称是否正确。", ErrorCode.AUTH_PARAMETER_ERROR);
        }
    }

    @ApiOperation(value = "用户注销", httpMethod = "GET",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "客户端需在header中发送token")
    @ApiImplicitParam(paramType = "header", required = true, name = "token", value = "用户认证凭据", defaultValue = "PC-yao.liu2015@bdqn.cn-8-20170516141821-d4f514")
    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = "application/json", headers = "token")
    public @ResponseBody
    Dto logout(HttpServletRequest request) {
        //验证token
        String token = request.getHeader("token");
        if (!tokenService.validate(request.getHeader("user-agent"), token))
            return DtoUtil.returnFail("token无效", ErrorCode.AUTH_TOKEN_INVALID);
        //删除token和信息
        try {
            tokenService.delete(token);
            return DtoUtil.returnSuccess("注销成功");
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("注销失败", ErrorCode.AUTH_UNKNOWN);
        }
    }

    /**
     * token置换
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/retoken", method = RequestMethod.GET, produces = "application/json", headers = "token")
    public @ResponseBody
    Dto reloadToken(HttpServletRequest request) {
        String token;
        System.out.println("retoken>>>>>>>>>>>>>>>>>>>>>>");
        try {
            token = this.tokenService.replaceToken(request.getHeader("user-agent"), request.getHeader("token"));
            ItripTokenVO vo = new ItripTokenVO(token, Calendar.getInstance().getTimeInMillis() * 2 * 60 * 60 * 1000, Calendar.getInstance().getTimeInMillis());
            return DtoUtil.returnDataSuccess(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getLocalizedMessage(), ErrorCode.AUTH_UNKNOWN);
        }
    }

    /**
     * token验证
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/validateToken", method = RequestMethod.GET, produces = "application/json", headers = "token")
    public @ResponseBody
    Dto validateToken(HttpServletRequest request) {
        String tokenString = request.getHeader("token");
        System.out.println("tokenString>>>>>>" + tokenString);
        ItripUser currentUser = validationToken.getCurrentUser(tokenString);
        if (null != currentUser) {
            System.out.println("getId" + currentUser.getId());
            System.out.println("getUserName" + currentUser.getUserName());
            System.out.println("getUserCode" + currentUser.getUserCode());
            System.out.println("getUserPassword" + currentUser.getUserPassword());
            return DtoUtil.returnSuccess("获取登录用户信息成功", currentUser);
        } else {
            return DtoUtil.returnFail("token失效，请重新登录", "100000");
        }
    }


    /**
     * ajax获取用户列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "ajax获取用户列表", httpMethod = "GET",
            protocols = "HTTP", produces = "application/json",
            response = Dto.class, notes = "客户端需在header中发送token")
    @ApiImplicitParam(paramType = "header", required = true, name = "token", value = "用户认证凭据", defaultValue = "token:PC-21ec64d6e9cae0917ea4b54bc36809d8-8-20170601100121-699dfc")
    @RequestMapping(value = "/getUserList", produces = "application/json", method = RequestMethod.GET, headers = "token")
    public @ResponseBody
    Dto getUserList(HttpServletRequest request) {
        System.out.println("getUserList>>>>>>>>>>>>>>>>>>");
        String tokenString = request.getHeader("token");
        ItripUser currentUser = validationToken.getCurrentUser(tokenString);
        List<ItripUser> list = null;
        try {
            if (EmptyUtils.isEmpty(currentUser)) {
                return DtoUtil.returnFail("token失效，请重登录", "100000");
            } else {
                list = userService.findAll();
                return DtoUtil.returnSuccess("获取成功", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("系统异常", "100513");
        }
    }


    @ApiIgnore
    @RequestMapping(value = "/getUserList.html", method = RequestMethod.GET)
    public String getUserList() {
        return "login/getUserList";
    }

    @ApiIgnore
    @RequestMapping(value = "/index.html")
    public String index() {
        return "login/index";
    }

    @ApiIgnore
    @RequestMapping(value = "/refrToken.html")
    public String refrToken() {
        return "login/refrToken";
    }

    @ApiIgnore
    @RequestMapping(value = "/validateToken.html")
    public String validateToken() {
        return "login/validateToken";
    }

    @ApiIgnore
    @RequestMapping(value = "/userlink.html")
    public String userlink() {
        return "userlink/index";
    }


    @RequestMapping("/addCookie")
    public String addCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name.trim(), value.trim());
        // 设置为30min,只要你不给cookie设置有效期,浏览器(同一会话相关的所有窗口)关闭后,cookie就消失了
        //cookie.setMaxAge(30 * 60);
        cookie.setPath("/");
        System.out.println("已添加===============");
        response.addCookie(cookie);
        return "redirect:/api/getUserList.html";
    }
}
