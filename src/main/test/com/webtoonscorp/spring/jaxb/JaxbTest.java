package com.webtoonscorp.spring.jaxb;

import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.InputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class JaxbTest {

    @Test
    public void readSqlMap() throws JAXBException {

        String path = Sqlmap.class.getPackage().getName();
        JAXBContext context = JAXBContext.newInstance(path);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        InputStream stream = getClass().getResourceAsStream("/sql/sqlmap.xml");
        Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(stream);

        List<SqlType> list = sqlmap.getSql();

        assertThat(list.size(), is(3));

        assertThat(list.get(0).getKey(), is("add"));
        assertThat(list.get(0).getValue(), is("insert"));

        assertThat(list.get(1).getKey(), is("get"));
        assertThat(list.get(1).getValue(), is("select"));

        assertThat(list.get(2).getKey(), is("delete"));
        assertThat(list.get(2).getValue(), is("delete"));
    }
}