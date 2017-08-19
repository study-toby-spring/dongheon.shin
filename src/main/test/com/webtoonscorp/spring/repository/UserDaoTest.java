package com.webtoonscorp.spring.repository;

import com.webtoonscorp.spring.domain.User;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/context/application-context.xml")
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource dataSource;

    private List<User> users;

    @Before
    public void setup() {

        users = new ArrayList<User>();

        users.add(create("1", "dongheon.shin", "123"));
        users.add(create("2", "dongheon", "456"));
        users.add(create("3", "xdxo", "789"));
    }

    private User create(String id, String name, String password) {

        User user = new User();

        user.setId(id);
        user.setName(name);
        user.setPassword(password);

        return user;
    }

    @Test
    public void duplicatedUserScenario() {

        User user = users.get(0);

        try {

            userDao.add(user);
            userDao.add(user);
        }
        catch (DuplicateKeyException e) {

            SQLException exception = (SQLException) e.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(dataSource);

            assertThat(set.translate(null, null, exception) instanceof DuplicateKeyException, is(true));
        }
    }

    @Test
    public void getAll() throws Exception {

        List<User> fromDao;

        userDao.deleteAll();

        fromDao = userDao.getAll();

        assertThat(fromDao.size(), is(0));

        for (User user : users) {
            userDao.add(user);
        }

        fromDao = userDao.getAll();
        assertThat(fromDao.size(), is(3));

        for (int i = 0; i < fromDao.size(); i++)
            assertEquals(users.get(i), fromDao.get(i));
    }

    private void assertEquals(User user1, User user2) {

        assertThat(user1.getId(), is(user2.getId()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
    }
}