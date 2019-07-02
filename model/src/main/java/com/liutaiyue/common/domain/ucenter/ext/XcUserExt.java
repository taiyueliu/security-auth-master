package com.liutaiyue.common.domain.ucenter.ext;

import com.liutaiyue.common.domain.ucenter.XcMenu;
import com.liutaiyue.common.domain.ucenter.XcRole;
import com.liutaiyue.common.domain.ucenter.XcUser;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by admin on 2018/3/20.
 */
@Data
@ToString
public class XcUserExt extends XcUser {

    //权限信息
    private List<XcMenu> permissions;

    //企业信息
    private String companyId;

    //角色
    private List<XcRole> roles;
}
