package com.webtoonscorp.spring.factory;

import com.webtoonscorp.spring.repository.UserDao;
import com.webtoonscorp.spring.support.Connector;
import com.webtoonscorp.spring.support.impl.NaverConnector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

    @Bean("userDao")
    public UserDao userDao() {

        Connector connector = new NaverConnector();
        return new UserDao(connector);
    }
}
