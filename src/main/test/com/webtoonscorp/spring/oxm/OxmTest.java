package com.webtoonscorp.spring.oxm;

import com.webtoonscorp.spring.jaxb.SqlType;
import com.webtoonscorp.spring.jaxb.Sqlmap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/context/application-context.xml")
public class OxmTest {

    @Autowired
    private Unmarshaller unmarshaller;

    @Test
    public void readSqlMap() throws XmlMappingException, IOException {

        InputStream stream = getClass().getResourceAsStream("/sql/sqlmap.xml");
        Source source = new StreamSource(stream);

        Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(source);

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
