package com.liutaiyue.oauth2.controller;

import com.liutaiyue.api.auth.AuthControllerApi;
import com.liutaiyue.common.domain.ucenter.ext.AuthToken;
import com.liutaiyue.common.domain.ucenter.request.LoginRequest;
import com.liutaiyue.common.domain.ucenter.response.AuthCode;
import com.liutaiyue.common.domain.ucenter.response.LoginResult;
import com.liutaiyue.common.exception.ExceptionCast;
import com.liutaiyue.common.model.response.CommonCode;
import com.liutaiyue.common.utils.CookieUtil;
import com.liutaiyue.oauth2.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author 刘太月
 * @Despriction 用户管理
 * @Created in 2019/7/1 14:13
 * @version: 1.0
 * <p>copyright: Copyright (c) 2018</p>
 */
@RestController
public class AuthController implements AuthControllerApi{
    @Value("${auth.clientId}")
    private String clientId;
    @Value("${auth.clientSecret}")
    private String clientSecret;
    @Value("${auth.cookieDomain}")
    private String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    private Integer cookieMaxAge;
    @Value("${auth.tokenValiditySeconds}")
    private Integer tokenValiditySeconds;
    @Autowired
    private AuthService  authService;
    @Override
    @PostMapping("/userlogin")
    public LoginResult login(LoginRequest loginRequest) {
        if(loginRequest ==null || StringUtils.isEmpty(loginRequest.getUsername())){
            ExceptionCast.cast(AuthCode.AUTH_USERNAME_NONE);
        }
        if(StringUtils.isEmpty(loginRequest.getPassword())){
            ExceptionCast.cast(AuthCode.AUTH_PASSWORD_NONE);
        }
        //获取令牌
        AuthToken authToken = authService.login(clientId,clientSecret,loginRequest.getUsername(),loginRequest.getPassword());
        String access_token = authToken.getAccess_token();
        //将短令牌存入cookies
        this.saveCookie(access_token);

        return new LoginResult(CommonCode.SUCCESS,access_token);
    }

    /**
     * 存入cookie
     * @param token
     */
    private void saveCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //添加cookie 认证令牌，最后一个参数设置为false，表示允许浏览器获取
        CookieUtil.addCookie(response,cookieDomain,"/","uid",token,cookieMaxAge,false);
    }

    @Override
    public LoginResult logout() {
        return null;
    }
}
