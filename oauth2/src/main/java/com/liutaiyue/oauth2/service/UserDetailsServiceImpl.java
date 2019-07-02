package com.liutaiyue.oauth2.service;

import com.liutaiyue.common.domain.ucenter.XcMenu;
import com.liutaiyue.common.domain.ucenter.XcRole;
import com.liutaiyue.common.domain.ucenter.ext.XcUserExt;
import com.liutaiyue.oauth2.client.UserClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private UserClient userClient;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用httpbasic认证，httpbasic中存储了client_id和client_secret，开始认证client_id和client_secret
        if(authentication==null){
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if(clientDetails!=null){
                //密码
                String clientSecret = clientDetails.getClientSecret();
                return new User(username,clientSecret,AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        XcUserExt userext = userClient.getUserext(username);
        if(userext == null){
            return null;
        }
        String password = userext.getPassword();

        List<XcRole> roles = userext.getRoles();
        if(roles == null){
            roles = new ArrayList<>();
        }
        List<String> user_roles = new ArrayList<>();
        roles.forEach(item -> user_roles.add("ROLE_"+item.getRoleCode()));

        List<XcMenu> permissions = userext.getPermissions();
        if(permissions == null){
            permissions = new ArrayList<>();
        }
        List<String> user_permissions = new ArrayList<>();
        permissions.forEach(item-> user_permissions.add(item.getCode()));
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String user_role:user_roles) {//将角色传输到在后面进行全权限验证时会使用GrantedAuthority
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user_role);
            //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
            grantedAuthorities.add(grantedAuthority);
        }

        for (String user_permission:user_permissions) {//将权限传输到在后面进行全权限验证时会使用GrantedAuthority
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user_permission);
            //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
            grantedAuthorities.add(grantedAuthority);
        }

        UserJwt userDetails = new UserJwt(username,password,grantedAuthorities);

        userDetails.setId(userext.getId());
        userDetails.setUtype(userext.getUtype());//用户类型
        userDetails.setCompanyId(userext.getCompanyId());//所属企业
        userDetails.setName(userext.getName());//用户名称
        userDetails.setUserpic(userext.getUserpic());//用户头像
        return userDetails;
    }
}
