package com.webtoonscorp.spring.repository;

import com.webtoonscorp.spring.domain.User;

import java.util.List;

public interface UserDao {

    void add(final User user);

    List<User> getAll();
    User get(String id);

    int getCount();
    void deleteAll();
}
