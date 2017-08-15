package com.webtoonscorp.spring.strategy.impl;

import com.webtoonscorp.spring.strategy.base.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements Statement {

    public PreparedStatement getPreparedStatement(Connection connection) throws SQLException {

        return connection.prepareStatement("delete * from users");
    }
}
