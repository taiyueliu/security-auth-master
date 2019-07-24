package com.liutaiyue.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liutaiyue.common.domain.ucenter.XcCompanyUser;
import com.liutaiyue.common.domain.ucenter.XcMenu;
import com.liutaiyue.common.domain.ucenter.XcRole;
import com.liutaiyue.common.domain.ucenter.XcUser;
import com.liutaiyue.common.domain.ucenter.ext.XcUserExt;
import com.liutaiyue.dao.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 刘太月
 * @Despriction
 * @Created in 2019/7/1 17:22
 * @version: 1.0
 * <p>copyright: Copyright (c) 2018</p>
 */
@Service
public class UcenterService {
    @Autowired
    private UcenterMapper ucenterMapper;
    @Autowired
    private XcCompanyUserMapper xcCompanyUserMapper;
    @Autowired
    private XcMenuMapper xcMenuMapper;
    @Autowired
    private XcRoleMapper xcRoleMapper;
    @Autowired
    private XcUserMapper xcUserMapper;

    public XcUser findXcUserByUsername(String username) {
        return ucenterMapper.findXcUserByUsername(username);
    }

    //根据账号查询用户的信息，返回用户扩展信息
    public XcUserExt getUserExt(String username){
        XcUser xcUser = this.findXcUserByUsername(username);
        if(xcUser == null){
            return null;
        }
        XcUserExt xcUserExt = new XcUserExt();
        BeanUtils.copyProperties(xcUser,xcUserExt);
        String userId = xcUserExt.getId();
        //查询用户所属公司
        XcCompanyUser xcCompanyUser = xcCompanyUserMapper.findByUserId(userId);
        if(xcCompanyUser!=null){
            String companyId = xcCompanyUser.getCompanyId();
            xcUserExt.setCompanyId(companyId);
        }
        //查询权限
        List<XcMenu> xcMenus = xcMenuMapper.selectPermissionByUserId(userId);
        xcUserExt.setPermissions(xcMenus);

        List<XcRole> xcRole =  xcRoleMapper.selectRoleByUserId(userId);
        xcUserExt.setRoles(xcRole);

        return xcUserExt;
    }

    public PageInfo getUsers(int page,int limit) {
        PageHelper.startPage(page, limit);
        List<XcUser> xcUsers = xcUserMapper.getUsers();
        PageInfo pageInfo = new PageInfo(xcUsers);
        return pageInfo;
    }
}
