package com.webtoonscorp.spring.strategy.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Statement {

    PreparedStatement getPreparedStatement(Connection connection) throws SQLException;
}
