package com.webtoonscorp.spring.program;

import com.webtoonscorp.spring.domain.User;
import com.webtoonscorp.spring.repository.UserDao;
import com.webtoonscorp.spring.repository.impl.NaverUserDao;

import java.sql.SQLException;

public class Program {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        UserDao dao = new NaverUserDao();

        User user = new User();

        user.setId("hello");
        user.setName("dongheon.shin");
        user.setPassword("password");

        dao.add(user);

        User found = dao.get("hello");

        System.out.println("name : " + found.getName());
        System.out.println("password : " + found.getPassword());
    }
}
