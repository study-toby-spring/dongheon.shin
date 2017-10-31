package com.webtoonscorp.spring.service.sql.reader;

import com.webtoonscorp.spring.jaxb.SqlType;
import com.webtoonscorp.spring.jaxb.Sqlmap;
import com.webtoonscorp.spring.service.sql.registry.SqlRegistry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class JaxbXmlSqlReader implements SqlReader {

    private static final String DEFAULT_SQLMAP_FILEPATH = "/sql/sqlmap.xml";

    private String sqlMapFile = DEFAULT_SQLMAP_FILEPATH;

    public String getSqlMapFile() {

        return this.sqlMapFile;
    }

    public void setSqlMapFile(String sqlMapFile) {

        this.sqlMapFile = sqlMapFile;
    }

    public void load(SqlRegistry registry) {

        try {

            String path = Sqlmap.class.getPackage().getName();
            JAXBContext context = JAXBContext.newInstance(path);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            InputStream stream = getClass().getResourceAsStream(getSqlMapFile());
            Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(stream);

            for (SqlType type : sqlmap.getSql()) {
                registry.register(type.getKey(), type.getValue());
            }
        }
        catch (JAXBException e) {

            throw new RuntimeException();
        }
    }
}
