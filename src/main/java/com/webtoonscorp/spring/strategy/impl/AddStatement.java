package com.webtoonscorp.spring.strategy.impl;

import com.webtoonscorp.spring.domain.User;
import com.webtoonscorp.spring.strategy.base.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStatement implements Statement {

    private User user;

    public AddStatement(User user) {

        this.user = user;
    }

    public PreparedStatement getPreparedStatement(Connection connection) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("insert into users (id, name, password) values (?, ?, ?)");

        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());

        return preparedStatement;
    }
}
