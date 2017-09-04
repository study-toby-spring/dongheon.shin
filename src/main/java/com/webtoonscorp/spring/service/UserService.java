package com.webtoonscorp.spring.service;

import com.webtoonscorp.spring.domain.User;

public interface UserService {

    void add(User user);
    void upgradeLevels();
}
