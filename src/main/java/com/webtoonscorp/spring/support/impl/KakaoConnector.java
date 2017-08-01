package com.webtoonscorp.spring.support.impl;

import com.webtoonscorp.spring.support.Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KakaoConnector implements Connector {

    public Connection createConnection() throws ClassNotFoundException, SQLException {

        System.out.println("Kakao");

        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/spring", "user", "user_password");
    }
}
