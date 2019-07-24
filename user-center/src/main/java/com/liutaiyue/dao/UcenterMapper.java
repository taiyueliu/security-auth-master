package com.liutaiyue.dao;

import com.liutaiyue.common.domain.ucenter.XcUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author 刘太月
 * @Despriction
 * @Created in 2019/7/1 17:25
 * @version: 1.0
 * <p>copyright: Copyright (c) 2018</p>
 */
public interface UcenterMapper extends JpaRepository<XcUser,String>{
    XcUser findXcUserByUsername(String username);
}
