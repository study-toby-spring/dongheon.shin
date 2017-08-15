package com.webtoonscorp.spring.interceptor;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class HelloInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String query = request.getQueryString();

        if (StringUtils.isEmpty(query))
            return false;

        Map<String, String> map = new HashMap<String, String>();

        for (String param : query.split("&")) {

            String[] token = param.split("=");
            map.put(token[0], token[1]);
        }

        return map.containsKey("hi");
    }
}
