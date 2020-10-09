package com.iooiee.config;

import com.iooiee.security.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * 定制请求路径的授权规则
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
             .authorizeRequests().antMatchers("/").permitAll()// 首页所有人可以访问
             .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3").and()
                .httpBasic().and();
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //前后端分离采用JWT 不需要session
        //没有权限的时候，会跳转到登录的页面！
        http.formLogin();

        //开启自动配置的注销的功能
        //路径是 /logout
        http.logout();

        // .logoutSuccessUrl("/"); 注销成功来到首页
        http.logout().logoutSuccessUrl("/hello/index");

        //记住密码功能，加入此方法，会在form页面多一个记住密码勾选框
        http.rememberMe();

    }


    /**
     * 定义认证规则,密码加密方式为BCrypt
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //在这里完成获得数据库中的用户信息,密码是加密过后的密码
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
    }


}
