package com.webtoonscorp.spring.service.sql;

import com.webtoonscorp.spring.exception.SqlRetrievalFailureException;
import com.webtoonscorp.spring.jaxb.SqlType;
import com.webtoonscorp.spring.jaxb.Sqlmap;
import com.webtoonscorp.spring.service.sql.reader.SqlReader;
import com.webtoonscorp.spring.service.sql.registry.SqlRegistry;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XmlSqlService implements SqlService, SqlRegistry, SqlReader {

    private String sqlMapFile;

    public String getSqlMapFile() {

        return this.sqlMapFile;
    }

    public void setSqlMapFile(String sqlMapFile) {

        this.sqlMapFile = sqlMapFile;
    }

    private Map<String, String> map = new HashMap<String, String>();


    private SqlRegistry registry;

    public void setSqlRegistry(SqlRegistry registry) {

        this.registry = registry;
    }

    private SqlReader reader;

    public void setSqlReader(SqlReader reader) {

        this.reader = reader;
    }


    @PostConstruct
    public void initialize() {

        reader.load(registry);
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

    public String getSql(String key) throws SqlRetrievalFailureException {

        String sql = registry.get(key);

        if (sql == null) {
            throw new SqlRetrievalFailureException(key + "에 대한 SQL을 찾을 수 없습니다");
        }

        return sql;
    }


    public void register(String key, String sql) {

        map.put(key, sql);
    }

    public String get(String key) throws SqlRetrievalFailureException {

        String sql = map.get(key);

        if (sql == null) {
            throw new SqlRetrievalFailureException(key + "에 대한 SQL을 찾을 수 없습니다");
        }

        return sql;
    }
}