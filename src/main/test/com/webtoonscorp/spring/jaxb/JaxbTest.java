package com.webtoonscorp.spring.jaxb;

import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JaxbTest {

    @Test
    public void readSqlMap() throws JAXBException, IOException {

        String path = Sqlmap.class.getPackage().getName();
        JAXBContext context = JAXBContext.newInstance(path);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        InputStream stream = getClass().getResourceAsStream("/sql/sqlmap.xml");
        Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(stream);

        List<SqlType> list = sqlmap.getSql();

        assertThat(list.size(), is(6));

        assertThat(list.get(0).getKey(), is("add"));
        assertThat(list.get(0).getValue(), is("insert into users (id, name, password, email, level, login, recommend) values (?, ?, ?, ?, ?, ?, ?)"));

        assertThat(list.get(1).getKey(), is("get"));
        assertThat(list.get(1).getValue(), is("select * from users where id = ?"));

        assertThat(list.get(2).getKey(), is("getAll"));
        assertThat(list.get(2).getValue(), is("select * from users order by id"));

        assertThat(list.get(3).getKey(), is("getCount"));
        assertThat(list.get(3).getValue(), is("select count(*) from users"));

        assertThat(list.get(4).getKey(), is("deleteAll"));
        assertThat(list.get(4).getValue(), is("delete from users"));

        assertThat(list.get(5).getKey(), is("update"));
        assertThat(list.get(5).getValue(), is("update users set name = ?, password = ?, email = ?, level = ?, login = ?, recommend = ? where id = ?"));
    }
}