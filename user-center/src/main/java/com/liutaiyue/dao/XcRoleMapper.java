package com.liutaiyue.dao;

import com.liutaiyue.common.domain.ucenter.XcRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author 刘太月
 * @Despriction
 * @Created in 2019/7/2 16:05
 * @version: 1.0
 * <p>copyright: Copyright (c) 2018</p>
 */
@Repository
public interface XcRoleMapper {
    List<XcRole> selectRoleByUserId(String userId);
}
