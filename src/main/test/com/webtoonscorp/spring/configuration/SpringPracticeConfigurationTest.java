package com.webtoonscorp.spring.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringPracticeConfiguration.class, SpringPracticeTestConfiguration.class })
public class SpringPracticeConfigurationTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    public void checkInjectionEquality() throws Exception {

        DataSourceTransactionManager manager = (DataSourceTransactionManager) transactionManager;
        assertThat(dataSource == manager.getDataSource(), is(true));
    }
}