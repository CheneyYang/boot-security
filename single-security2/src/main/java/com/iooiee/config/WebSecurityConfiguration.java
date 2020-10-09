package com.iooiee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iooiee.handler.LoginSuccessHandler;
import com.iooiee.security.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;


    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 定制请求路径的授权规则
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
             .authorizeRequests().antMatchers("/").permitAll()// 首页所有人可以访问
             .antMatchers("/login").permitAll()
             .antMatchers("/level1/**").hasRole("VIP1")
             .antMatchers("/level2/**").hasRole("VIP2")
             .antMatchers("/level3/**").hasRole("VIP3").and()

                //前后端分离相关配置
             .formLogin().loginProcessingUrl("/login") //loginProcessingUrl用于指定前后端分离的时候调用后台登录接口的名称
             .successHandler(loginSuccessHandler).and()//配置登录成功的自定义处理类

                /**
                 * 未登录时，进行json格式的提示，很喜欢这种写法，不用单独写一个又一个的类
                 */
            .httpBasic().authenticationEntryPoint((request,response,authException) -> {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("code",403);
            map.put("message","未登录");
            out.write(objectMapper.writeValueAsString(map));
            out.flush();
            out.close();
        }).and()
                .formLogin() //使用自带的登录
                .permitAll()
                //登录失败，返回json
                .failureHandler((request,response,ex) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    PrintWriter out = response.getWriter();
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("code",401);
                    if (ex instanceof UsernameNotFoundException || ex instanceof BadCredentialsException) {
                        map.put("message","用户名或密码错误");
                    } else if (ex instanceof DisabledException) {
                        map.put("message","账户被禁用");
                    } else {
                        map.put("message","登录失败!");
                    }
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                //登录成功，返回json
                .successHandler((request,response,authentication) -> {
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("code",200);
                    map.put("message","登录成功");
                    map.put("data",authentication);
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .and()
                .exceptionHandling()
                //没有权限，返回json
                .accessDeniedHandler((request,response,ex) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("code",403);
                    map.put("message", "权限不足");
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .and()
                .logout()
                //退出成功，返回json
                .logoutSuccessHandler((request,response,authentication) -> {
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("code",200);
                    map.put("message","退出成功");
                    map.put("data",authentication);
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                }).permitAll();
        //开启跨域访问
        http.cors().disable();
        //开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
        http.csrf().disable();





        //没有权限的时候，会跳转到登录的页面！
        http.formLogin();
        //开启自动配置的注销的功能
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
