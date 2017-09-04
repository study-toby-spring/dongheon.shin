package com.webtoonscorp.spring.service;

import com.webtoonscorp.spring.domain.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class UserServiceTx implements UserService {

    private UserService userService;

    public void setUserService(UserService userService) {

        this.userService = userService;
    }

    private PlatformTransactionManager manager;

    public void setTransactionManager(PlatformTransactionManager manager) {
        this.manager = manager;
    }

    public void add(User user) {

        userService.add(user);
    }

    public void upgradeLevels() {

        TransactionStatus status = manager.getTransaction(new DefaultTransactionDefinition());

        try {

            userService.upgradeLevels();
            manager.commit(status);
        }
        catch (RuntimeException e) {

            manager.rollback(status);
            throw e;
        }
    }
}
