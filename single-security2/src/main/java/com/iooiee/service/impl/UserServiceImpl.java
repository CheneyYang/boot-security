package com.iooiee.service.impl;

import com.iooiee.entity.pojo.User;
import com.iooiee.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User findUserByUsername(String username) {
        //模拟在数据库中拿到用户的信息，密码（123456）为BCrypt加密过后的密码
        return new User(1L,"系统管理员","admin","$2a$10$dXkwrj7wPXDff1ET7I0wkOrnRavZk4GpWpEgv94yDMovbjdqCIKjq");
//        return new User(1L,"系统管理员","admin","{noop}"+"123456");
    }
}
