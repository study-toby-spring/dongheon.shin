package com.webtoonscorp.spring.program;

import com.webtoonscorp.spring.domain.User;
import com.webtoonscorp.spring.factory.DaoFactory;
import com.webtoonscorp.spring.repository.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class Program {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = applicationContext.getBean("userDao", UserDao.class);

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
