package com.iooiee.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;

/**
 * 使用session登录策略
 */
public class MyExpiredSessionStrategy implements SessionInformationExpiredStrategy {


    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",0);
        map.put("msg","您已经在另一台设备登录，本账号已经强制下线");
        sessionInformationExpiredEvent.getResponse().setContentType("application/json;charset=UTF-8");
        sessionInformationExpiredEvent.getResponse().getWriter().write(objectMapper.writeValueAsString(map));

    }
}
