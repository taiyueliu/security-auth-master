package com.liutaiyue.api.auth;

import com.liutaiyue.common.domain.ucenter.request.LoginRequest;
import com.liutaiyue.common.domain.ucenter.response.LoginResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author 刘太月
 * @Despriction 用户中心
 * @Created in 2019/7/1 10:09
 * @version: 1.0
 * <p>copyright: Copyright (c) 2018</p>
 */
@Api(value="用户认证",description="用户认证接口")
public interface AuthControllerApi {
    @ApiOperation("登录")
   LoginResult login(LoginRequest loginRequest);
    @ApiOperation("退出")
   LoginResult logout();

}
