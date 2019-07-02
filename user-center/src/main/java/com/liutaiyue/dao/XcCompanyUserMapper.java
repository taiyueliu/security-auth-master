package com.liutaiyue.dao;

import com.liutaiyue.common.domain.ucenter.XcCompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author 刘太月
 * @Despriction
 * @Created in 2019/7/1 17:31
 * @version: 1.0
 * <p>copyright: Copyright (c) 2018</p>
 */
public interface XcCompanyUserMapper extends JpaRepository<XcCompanyUser,String> {
    XcCompanyUser findByUserId(String userId);
}
