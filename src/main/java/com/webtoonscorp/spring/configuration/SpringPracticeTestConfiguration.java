package com.webtoonscorp.spring.configuration;

import com.webtoonscorp.spring.factory.MessageFactoryBean;
import com.webtoonscorp.spring.service.TestUserServiceImpl;
import com.webtoonscorp.spring.service.UserService;
import com.webtoonscorp.spring.support.mail.TestMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

@Configuration
public class SpringPracticeTestConfiguration {

    @Bean
    public UserService testUserService() {
        return new TestUserServiceImpl();
    }

    @Bean
    public MailSender mailSender() {
        return new TestMailSender();
    }

    @Bean
    public MessageFactoryBean message() {

        MessageFactoryBean bean = new MessageFactoryBean();
        bean.setText("dongheon");

        return bean;
    }
}
