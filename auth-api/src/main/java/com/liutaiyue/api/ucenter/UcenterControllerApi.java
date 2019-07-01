package com.liutaiyue.api.ucenter;

import com.liutaiyue.common.domain.ucenter.ext.XcUserExt;
import io.swagger.annotations.Api;

/**
 * @Author 刘太月
 * @Despriction
 * @Created in 2019/7/1 17:15
 * @version: 1.0
 * <p>copyright: Copyright (c) 2018</p>
 */
@Api(value = "用户中心",description = "用户中心管理")
public interface UcenterControllerApi {
    XcUserExt getUserext(String username);
}
