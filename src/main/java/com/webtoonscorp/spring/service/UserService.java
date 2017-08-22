package com.webtoonscorp.spring.service;

import com.webtoonscorp.spring.domain.User;
import com.webtoonscorp.spring.repository.UserDao;
import com.webtoonscorp.spring.type.Level;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.util.List;

public class UserService {

    public static final int MIN_LOG_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECCOMEND_FOR_GOLD = 30;

    private UserDao userDao;
    private DataSource dataSource;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) {

        if (user.getLevel() == null)
            user.setLevel(Level.BASIC);

        userDao.add(user);
    }

    public void upgradeLevels() throws Exception {

        PlatformTransactionManager manager = new DataSourceTransactionManager(dataSource);
        TransactionStatus status = manager.getTransaction(new DefaultTransactionDefinition());

        try {

            List<User> users = userDao.getAll();

            for (User user : users) {

                if (canUpgradeLevel(user))
                    upgradeLevel(user);
            }

            manager.commit(status);
        }
        catch (Exception e) {

            manager.rollback(status);
            throw e;
        }
    }

    protected void upgradeLevel(User user) {

        user.upgradeLevel();
        userDao.update(user);
    }

    private boolean canUpgradeLevel(User user) {

        Level level = user.getLevel();

        switch (level) {

            case BASIC: return user.getLogin() >= MIN_LOG_COUNT_FOR_SILVER;
            case SILVER: return user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD;
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown level " + level);
        }
    }
}