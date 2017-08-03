package com.webtoonscorp.spring.factory;

import com.webtoonscorp.spring.repository.UserDao;
import com.webtoonscorp.spring.support.Connector;
import com.webtoonscorp.spring.support.impl.NaverConnector;

public class DaoFactory {

    public UserDao userDao() {

        Connector connector = new NaverConnector();
        return new UserDao(connector);
    }
}
