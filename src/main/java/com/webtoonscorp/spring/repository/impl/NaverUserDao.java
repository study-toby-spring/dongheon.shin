package com.webtoonscorp.spring.repository.impl;

import com.webtoonscorp.spring.repository.UserDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NaverUserDao extends UserDao {

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        System.out.println("Naver");

        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/spring", "user", "user_password");
    }
}
