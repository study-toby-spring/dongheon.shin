package com.webtoonscorp.spring.support;

import java.sql.Connection;
import java.sql.SQLException;

public interface Connector {

    Connection createConnection() throws ClassNotFoundException, SQLException;
 }
