package com.iooiee.security;

import com.iooiee.entity.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 自定义User 从数据库中拿到用户的信息
 */
public class SecurityUserDetail extends User implements UserDetails {

    public SecurityUserDetail(User user) {
        if(user!=null) {
            this.setId(user.getId());
            this.setNickName(user.getNickName());
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //理想型返回 admin 权限，可自已处理这块
        return AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
    }


    /**
     * 账户是否没有过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 是否不上锁
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否启用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
