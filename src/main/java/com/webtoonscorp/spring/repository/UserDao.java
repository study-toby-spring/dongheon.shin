package com.webtoonscorp.spring.repository;

import com.webtoonscorp.spring.domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> mapper = new RowMapper<User>() {

        public User mapRow(ResultSet result, int row) throws SQLException {

            User user = new User();

            user.setId(result.getString("id"));
            user.setName(result.getString("name"));
            user.setPassword(result.getString("password"));

            return user;
        }
    };

    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) throws SQLException {

        jdbcTemplate.update("insert into users (id, name, password) values (?, ?, ?)", user.getId(), user.getName(), user.getPassword());
    }

    public List<User> getAll() {

        return jdbcTemplate.query("select * from users order by id", mapper);
    }

    public User get(String id) throws SQLException {

        return jdbcTemplate.queryForObject("select * from users where id = ?", new Object[] { id }, mapper);
    }

    public int getCount() throws SQLException {

        return jdbcTemplate.query(
                new PreparedStatementCreator() {

                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        return connection.prepareStatement("select count(*) from users");
                    }
                },
                new ResultSetExtractor<Integer>() {

                    public Integer extractData(ResultSet result) throws SQLException, DataAccessException {

                        result.next();
                        return result.getInt(1);
                    }
                });
    }

    public void deleteAll() throws SQLException {

        jdbcTemplate.update("delete from users");
    }
}
