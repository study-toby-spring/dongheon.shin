package com.webtoonscorp.spring.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Statement {

    PreparedStatement getPreparedStatement(Connection connection) throws SQLException;
}
