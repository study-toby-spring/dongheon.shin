package com.webtoonscorp.spring.repository;

import com.webtoonscorp.spring.domain.User;

import java.sql.*;

public class UserDao {

    public void add(User user) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring", "user", "user_password");

        PreparedStatement preparedStatement = connection.prepareStatement("insert into users (id, name, password) values (?, ?, ?)");

        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring", "user", "user_password");

        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id = ?");
        preparedStatement.setString(1, id);

        ResultSet result = preparedStatement.executeQuery();
        result.next();


        User user = new User();

        user.setId(result.getString("id"));
        user.setName(result.getString("name"));
        user.setPassword(result.getString("password"));

        result.close();
        preparedStatement.close();
        connection.close();

        return user;
    }
}
