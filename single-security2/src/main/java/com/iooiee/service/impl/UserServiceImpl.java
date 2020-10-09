package com.iooiee.service.impl;

import com.iooiee.entity.pojo.User;
import com.iooiee.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User findUserByUsername(String username) {
        return new User(1L,"小明","xiaoming","xm123456");
    }
}
