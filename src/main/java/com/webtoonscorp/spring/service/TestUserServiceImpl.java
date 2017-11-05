package com.webtoonscorp.spring.service;

import com.webtoonscorp.spring.domain.User;

import java.util.List;

public class TestUserServiceImpl extends UserServiceImpl {

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
