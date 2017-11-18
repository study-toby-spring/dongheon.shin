package com.webtoonscorp.spring.configuration;

import com.mysql.jdbc.Driver;
import com.webtoonscorp.spring.factory.MessageFactoryBean;
import com.webtoonscorp.spring.service.TestUserServiceImpl;
import com.webtoonscorp.spring.service.UserService;
import com.webtoonscorp.spring.service.sql.service.OxmSqlService;
import com.webtoonscorp.spring.service.sql.service.SqlService;
import com.webtoonscorp.spring.support.mail.TestMailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.webtoonscorp.spring")
@PropertySource("classpath:/property/database.properties")
@Import({ SqlServiceConfiguration.class })
public class SpringPracticeConfiguration {

    @Value("${db.driverClass}")
    private Class<? extends Driver> driverClass;

    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Configuration
    @Profile("test")
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

    @Configuration
    @Profile("production")
    public class SpringPracticeProductionConfiguration {

        @Bean
        public MailSender mailSender() {

            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("localhost");

            return mailSender;
        }
    }


    @Bean
    public DataSource dataSource() {

        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());

        return transactionManager;
    }

    @Bean
    public SqlService sqlService() {
        return new OxmSqlService();
    }
}
