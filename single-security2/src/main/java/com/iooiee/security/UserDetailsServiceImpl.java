package com.iooiee.security;

import com.iooiee.entity.pojo.User;
import com.iooiee.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在这里给还从数据库中拿到角色和权限的信息，然后返会给security框架
 */

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //从数据库中拿到用户信息
        User user  = userService.findUserByUsername(username);

        // 存放权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Map map = new HashMap();
        map.put("username",username);
        authorities.add(new SimpleGrantedAuthority("ROLE_"+"VIP1"));
//        return new SecurityUserDetail(user);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);


    }
}
