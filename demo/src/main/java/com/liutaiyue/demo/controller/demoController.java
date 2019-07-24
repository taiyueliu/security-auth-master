package com.liutaiyue.demo.controller;

import com.liutaiyue.common.domain.ucenter.XcUser;
import com.liutaiyue.common.model.response.CommonCode;
import com.liutaiyue.common.model.response.ResponseVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/demo")
@RestController
@ResponseBody
public class demoController {
    @GetMapping("/user/{index}")
    @PreAuthorize("hasRole('USER') or hasAnyAuthority('xc_teachmanager_course_del','course_find_list')")
    public String getIndex(@PathVariable("index") String index){
        return "my name is "+index;
    }


    @GetMapping("/head/{index}")
    public ResponseVO<XcUser> getHead(@PathVariable("index") String index){

        XcUser xcUser = new XcUser();
        xcUser.setId("23456");
        xcUser.setName("嘻嘻");

        return new ResponseVO(CommonCode.SUCCESS,xcUser);
    }
}
