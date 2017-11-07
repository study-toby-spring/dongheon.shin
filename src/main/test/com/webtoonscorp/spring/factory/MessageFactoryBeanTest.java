package com.webtoonscorp.spring.factory;

import com.webtoonscorp.spring.configuration.SpringPracticeConfiguration;
import com.webtoonscorp.spring.configuration.SpringPracticeTestConfiguration;
import com.webtoonscorp.spring.domain.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = { SpringPracticeConfiguration.class, SpringPracticeTestConfiguration.class })
public class MessageFactoryBeanTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void getMessageFromFactoryBean() {

        Object message = context.getBean("message");

        assertThat(message instanceof Message, is(true));
        assertThat(((Message) message).getText(), is("dongheon"));
    }
}