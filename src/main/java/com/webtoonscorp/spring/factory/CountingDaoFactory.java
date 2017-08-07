package com.webtoonscorp.spring.factory;

import com.webtoonscorp.spring.repository.UserDao;
import com.webtoonscorp.spring.support.Connector;
import com.webtoonscorp.spring.support.impl.CountingUniversalConnector;
import com.webtoonscorp.spring.support.impl.NaverConnector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class CountingDaoFactory {

    @Bean
    public Connector connector() throws SQLException, ClassNotFoundException {

        return new CountingUniversalConnector(realConnector());
    }

    @Bean
    public Connector realConnector() throws SQLException, ClassNotFoundException {

        return new NaverConnector();
    }

    @Bean("userDao")
    public UserDao userDao() throws SQLException, ClassNotFoundException {

        return new UserDao(connector());
    }
}
