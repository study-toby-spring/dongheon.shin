package com.webtoonscorp.spring.service;

import com.webtoonscorp.spring.domain.User;
import com.webtoonscorp.spring.repository.UserDao;
import com.webtoonscorp.spring.type.Level;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

public class UserService {

    public static final int MIN_LOG_COUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    private MailSender mailSender;
    private UserDao userDao;
    private PlatformTransactionManager manager;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setTransactionManager(PlatformTransactionManager manager) {
        this.manager = manager;
    }

    public void add(User user) {

        if (user.getLevel() == null)
            user.setLevel(Level.BASIC);

        userDao.add(user);
    }

    public void upgradeLevels() throws Exception {

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
        sendUpgradeMail(user);
    }

    private void sendUpgradeMail(User user) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(user.getEmail());
        message.setFrom("admin@ksug.org");
        message.setSubject("Upgrade 안내");
        message.setText("사용자님의 등급이 " + user.getLevel().name() + " 로 업그레이드 되었습니다.");

        mailSender.send(message);
    }

    private boolean canUpgradeLevel(User user) {

        Level level = user.getLevel();

        switch (level) {

            case BASIC: return user.getLogin() >= MIN_LOG_COUNT_FOR_SILVER;
            case SILVER: return user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD;
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown level " + level);
        }
    }
}
