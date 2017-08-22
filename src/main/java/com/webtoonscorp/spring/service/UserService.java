package com.webtoonscorp.spring.service;

import com.webtoonscorp.spring.domain.User;
import com.webtoonscorp.spring.repository.UserDao;
import com.webtoonscorp.spring.type.Level;

import java.util.List;

public class UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void add(User user) {

        if (user.getLevel() == null)
            user.setLevel(Level.BASIC);

        userDao.add(user);
    }

    public void upgradeLevels() {

        List<User> users = userDao.getAll();

        for (User user : users) {

            boolean changed = false;
            Level level = user.getLevel();

            if (level == Level.BASIC && user.getLogin() >= 50) {

                user.setLevel(Level.SILVER);
                changed = true;
            }
            else if (level == Level.SILVER && user.getRecommend() >= 30) {

                user.setLevel(Level.GOLD);
                changed = true;
            }
            else if (level == Level.GOLD) {

                changed = false;
            }

            if (changed) {

                userDao.update(user);
            }
        }
    }
}
