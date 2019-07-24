package com.liutaiyue.controller;

import com.github.pagehelper.PageInfo;
import com.liutaiyue.api.ucenter.UcenterControllerApi;
import com.liutaiyue.common.domain.ucenter.XcUser;
import com.liutaiyue.common.domain.ucenter.ext.XcUserExt;
import com.liutaiyue.common.model.response.CommonCode;
import com.liutaiyue.common.model.response.ResponseVO;
import com.liutaiyue.service.UcenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "UcenterController")
public class UcenterController implements UcenterControllerApi{
    @Autowired
    private UcenterService ucenterService;
    @Override
    @GetMapping("/getuserext")
    public XcUserExt getUserext(@RequestParam("username")String username) {
       return ucenterService.getUserExt(username);
    }

    @GetMapping("/getUsers")
    @ApiOperation(value = "获取用户列表", notes = "用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "int", required = true),
            @ApiImplicitParam(name = "limit", value = "条目", paramType = "int", required = true)
    })
    public ResponseVO<PageInfo<XcUser>> getUsers(int page, int limit) {
        PageInfo pageInfo = ucenterService.getUsers(page,limit);
        return ResponseVO.builder(CommonCode.SUCCESS,pageInfo);
    }
}
