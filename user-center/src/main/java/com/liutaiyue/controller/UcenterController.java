package com.liutaiyue.controller;

import com.liutaiyue.api.ucenter.UcenterControllerApi;
import com.liutaiyue.common.domain.ucenter.ext.XcUserExt;
import com.liutaiyue.service.UcenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
@RequestMapping("/ucenter")
public class UcenterController implements UcenterControllerApi{
    @Autowired
    private UcenterService ucenterService;
    @Override
    @GetMapping("/getuserext")
    public XcUserExt getUserext(@RequestParam("username")String username) {
       return ucenterService.getUserExt(username);
    }
}
