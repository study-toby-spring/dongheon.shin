package com.webtoonscorp.spring.service;

import com.webtoonscorp.spring.domain.User;
import com.webtoonscorp.spring.repository.UserDao;
import com.webtoonscorp.spring.type.Level;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/context/application-context.xml")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    List<User> users;

    @Before
    public void setup() {

        users = new ArrayList<User>();

        users.add(new User("1", "a", "a_pw", Level.BASIC, 49, 0));
        users.add(new User("2", "b", "b_pw", Level.BASIC, 50, 0));
        users.add(new User("3", "c", "c_pw", Level.SILVER, 60, 29));
        users.add(new User("4", "d", "d_pw", Level.SILVER, 60, 30));
        users.add(new User("5", "e", "e_pw", Level.GOLD, 100, 100));
    }

    @Test
    public void shouldNotNullWithInjection() {

        assertNotNull(userService);
    }

    @Test
    public void upgradeLevels() {

        userDao.deleteAll();

        for (User user : users)
            userDao.add(user);

        userService.upgradeLevels();
        users = userDao.getAll();

        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
    }

    private void checkLevel(User user, Level level) {

        assertThat(user.getLevel(), is(level));
    }

    @Test
    public void add() {

        userDao.deleteAll();

        User userWithLevel = users.get(0);
        User userWithoutLevel = users.get(1);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        assertEquals(userWithLevel, userDao.get(userWithLevel.getId()));
        assertEquals(userWithoutLevel, userDao.get(userWithoutLevel.getId()));
    }

    private void assertEquals(User user1, User user2) {

        assertThat(user1.getId(), is(user2.getId()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
        assertThat(user1.getLevel(), is(user2.getLevel()));
        assertThat(user1.getLogin(), is(user2.getLogin()));
        assertThat(user1.getRecommend(), is(user2.getRecommend()));
    }
}