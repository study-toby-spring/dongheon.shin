package com.webtoonscorp.spring.repository;

import com.webtoonscorp.spring.domain.User;
import com.webtoonscorp.spring.strategy.Statement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private DataSource dataSource;

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(final User user) throws SQLException {

        class AddStatement implements Statement {

            public PreparedStatement getPreparedStatement(Connection connection) throws SQLException {

                PreparedStatement preparedStatement = connection.prepareStatement("insert into users (id, name, password) values (?, ?, ?)");

                preparedStatement.setString(1, user.getId());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, user.getPassword());

                return preparedStatement;
            }
        }

        Statement statement = new AddStatement();
        jdbcContextWithStatement(statement);
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

        class DeleteAllStatement implements Statement {

            public PreparedStatement getPreparedStatement(Connection connection) throws SQLException {

                return connection.prepareStatement("delete * from users");
            }
        }

        Statement statement = new DeleteAllStatement();
        jdbcContextWithStatement(statement);
    }

    private void jdbcContextWithStatement(Statement statement) throws SQLException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = dataSource.getConnection();

            preparedStatement = statement.getPreparedStatement(connection);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw e;
        }
        finally {

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
}
