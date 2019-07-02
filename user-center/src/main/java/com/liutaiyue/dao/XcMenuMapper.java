package com.liutaiyue.dao;

import com.liutaiyue.common.domain.ucenter.XcMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author 刘太月
 * @Despriction
 * @Created in 2019/7/2 11:07
 * @version: 1.0
 * <p>copyright: Copyright (c) 2018</p>
 */
@Repository
public interface XcMenuMapper {
    //根据用户id查询用户的权限
    public List<XcMenu> selectPermissionByUserId(String userid);
}
