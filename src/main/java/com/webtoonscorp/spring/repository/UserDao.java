package com.webtoonscorp.spring.repository;

import com.webtoonscorp.spring.context.JdbcContext;
import com.webtoonscorp.spring.domain.User;
import com.webtoonscorp.spring.strategy.Statement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private DataSource dataSource;
    private JdbcContext jdbcContext;

    public void setDataSource(DataSource dataSource) {

        jdbcContext = new JdbcContext();
        jdbcContext.setDataSource(dataSource);

        this.dataSource = dataSource;
    }

    public void add(final User user) throws SQLException {

        jdbcContext.workWithStatement(new Statement() {

            public PreparedStatement getPreparedStatement(Connection connection) throws SQLException {

                PreparedStatement preparedStatement = connection.prepareStatement("insert into users (id, name, password) values (?, ?, ?)");

                preparedStatement.setString(1, user.getId());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, user.getPassword());

                return preparedStatement;
            }
        });
    }

    public User get(String id) throws SQLException {

        Connection connection = dataSource.getConnection();

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

    public int getCount() throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        try {

            preparedStatement = connection.prepareStatement("select count(*) from users");
            result = preparedStatement.executeQuery();

            result.next();
            return result.getInt(1);
        }
        catch (SQLException e) {
            throw e;
        }
        finally {

            if (result != null) {

                try {

                    result.close();
                }
                catch (SQLException e) { }
            }

            if (preparedStatement != null) {

                try {

                    preparedStatement.close();
                }
                catch (SQLException e) { }
            }

            if (connection != null) {

                try {

                    connection.close();
                }
                catch (SQLException e) { }
            }
        }
    }

    public void deleteAll() throws SQLException {

        jdbcContext.workWithStatement(new Statement() {

            public PreparedStatement getPreparedStatement(Connection connection) throws SQLException {

                return connection.prepareStatement("delete * from users");
            }
        });
    }
}
