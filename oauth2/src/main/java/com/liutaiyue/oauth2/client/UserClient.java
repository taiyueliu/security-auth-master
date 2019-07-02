package com.liutaiyue.oauth2.client;

import com.liutaiyue.common.client.XcServiceList;
import com.liutaiyue.common.domain.ucenter.ext.XcUserExt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author 刘太月
 * @Despriction
 * @Created in 2019/7/2 10:55
 * @version: 1.0
 * <p>copyright: Copyright (c) 2018</p>
 */
@FeignClient(value = XcServiceList.SERVICE_UCENTER)
public interface UserClient {
    //根据账号查询用户信息
    @GetMapping("/user/ucenter/getuserext")
    public XcUserExt getUserext(@RequestParam("username") String username);
}
