package com.iooiee.service;

import com.iooiee.entity.pojo.User;

public interface UserService {
    public User findUserByUsername(String username);
}
