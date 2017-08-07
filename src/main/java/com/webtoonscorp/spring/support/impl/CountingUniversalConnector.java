package com.webtoonscorp.spring.support.impl;

import com.webtoonscorp.spring.support.Connector;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class CountingUniversalConnector implements Connector {

    private int counter = 0;
    public int getCounter() { return this.counter; }

    private Connector connector;

    public CountingUniversalConnector(Connector connector) {
        this.connector = connector;
    }

    public Connection createConnection() throws ClassNotFoundException, SQLException {

        System.out.println("Counting !!");
        this.counter++;

        return connector.createConnection();
    }
}
