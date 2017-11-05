package com.webtoonscorp.spring.service.sql.service;

import com.webtoonscorp.spring.configuration.SpringPracticeConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringPracticeConfiguration.class)
public class XmlSqlServiceTest {

    @Autowired
    private SqlService xmlSqlService;

    @Test
    public void readSqlMap() {

        assertThat(xmlSqlService.getSql("add"), is("insert into users (id, name, password, email, level, login, recommend) values (?, ?, ?, ?, ?, ?, ?)"));
        assertThat(xmlSqlService.getSql("get"), is("select * from users where id = ?"));
        assertThat(xmlSqlService.getSql("getAll"), is("select * from users order by id"));
        assertThat(xmlSqlService.getSql("getCount"), is("select count(*) from users"));
        assertThat(xmlSqlService.getSql("deleteAll"), is("delete from users"));
        assertThat(xmlSqlService.getSql("update"), is("update users set name = ?, password = ?, email = ?, level = ?, login = ?, recommend = ? where id = ?"));
    }
}