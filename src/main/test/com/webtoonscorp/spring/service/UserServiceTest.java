package com.webtoonscorp.spring.service;

import com.webtoonscorp.spring.domain.User;
import com.webtoonscorp.spring.repository.TestUserDao;
import com.webtoonscorp.spring.repository.UserDao;
import com.webtoonscorp.spring.support.mail.TestMailSender;
import com.webtoonscorp.spring.type.Level;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(locations = "classpath:/context/application-context.xml")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserService testUserService;

    @Autowired
    private UserDao userDao;

    List<User> users;

    static class TestUserServiceImpl extends UserServiceImpl {

        @Override
        protected void upgradeLevel(User user) {

            if (user.getId().equals("2")) {
                throw new TestUserServiceException();
            }

            user.upgradeLevel();
        }

        @Override
        public List<User> getAll() {

            for (User user : super.getAll()) {
                super.update(user);
            }

            return null;
        }
    }

    @Before
    public void setup() {

        users = new ArrayList<User>();

        users.add(new User("1", "a", "a_pw", "niah_raszagal@live.com", Level.BASIC, 49, 0));
        users.add(new User("2", "b", "b_pw", "leucosian@gmail.com", Level.BASIC, 50, 0));
        users.add(new User("3", "c", "c_pw", "niah_lawliet@naver.com", Level.SILVER, 60, 29));
        users.add(new User("4", "d", "d_pw", "account@domain.com", Level.SILVER, 60, 30));
        users.add(new User("5", "e", "e_pw", "user@domain.com", Level.GOLD, 100, 100));
    }

    @Test(expected = TransientDataAccessResourceException.class)
    public void readOnlyTransactionAttribute() {
        testUserService.getAll();
    }

    @Test
    public void shouldNotNullWithInjection() {

        assertNotNull(userService);
    }

    @Test
    public void mockUpgradeLevels() {

        UserDao userDao = mock(UserDao.class);
        when(userDao.getAll()).thenReturn(users);

        MailSender mailSender = mock(MailSender.class);

        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(userDao);
        userService.setMailSender(mailSender);

        userService.upgradeLevels();

        verify(userDao, times(2)).update(any(User.class));
        verify(userDao, times(2)).update(any(User.class));

        verify(userDao).update(users.get(1));
        assertThat(users.get(1).getLevel(), is(Level.SILVER));

        verify(userDao).update(users.get(3));
        assertThat(users.get(3).getLevel(), is(Level.GOLD));


        ArgumentCaptor<SimpleMailMessage> message = ArgumentCaptor.forClass(SimpleMailMessage.class);

        verify(mailSender, times(2)).send(message.capture());

        List<SimpleMailMessage> messages = message.getAllValues();

        assertThat(messages.get(0).getTo()[0], is(users.get(1).getEmail()));
        assertThat(messages.get(1).getTo()[0], is(users.get(3).getEmail()));
    }

    @Test
    public void upgradeLevels() throws Exception {

        TestMailSender mailSender = new TestMailSender();
        TestUserDao userDao = new TestUserDao(this.users);

        UserServiceImpl userService = new UserServiceImpl();

        userService.setUserDao(userDao);
        userService.setMailSender(mailSender);

        userService.upgradeLevels();

        List<User> updated = userDao.getUpdated();

        assertThat(updated.size(), is(2));

        checkUserAndLevel(users.get(0), "a", Level.BASIC);
        checkUserAndLevel(users.get(1), "b", Level.SILVER);

        List<String> requests = mailSender.getRequests();

        assertThat(requests.size(), is(2));
        assertThat(requests.get(0), is(users.get(1).getEmail()));
        assertThat(requests.get(1), is(users.get(3).getEmail()));
    }

    private void checkUserAndLevel(User user, String name, Level level) {

        assertThat(user.getName(), is(name));
        assertThat(user.getLevel(), is(level));
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {

        Level fromDao = userDao.get(user.getId()).getLevel();

        if (upgraded) {
            assertThat(fromDao, is(user.getLevel().next()));
        }
        else {
            assertThat(fromDao, is(user.getLevel()));
        }
    }

    @Test
    public void upgradeAllOrNothing() throws Exception {

        userDao.deleteAll();

        for (User user : users)
            userDao.add(user);

        try {

            testUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        }
        catch (TestUserServiceException e) {

        }

        checkLevelUpgraded(users.get(1), false);
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

    @Test
    public void advisorAutoProxyCreator() {

        assertThat(testUserService instanceof Proxy, is(true));
    }
}