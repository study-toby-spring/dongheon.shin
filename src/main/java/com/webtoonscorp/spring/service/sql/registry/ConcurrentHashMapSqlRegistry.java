package com.webtoonscorp.spring.service.sql.registry;

import com.webtoonscorp.spring.exception.SqlRetrievalFailureException;
import com.webtoonscorp.spring.exception.SqlUpdateFailureException;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapSqlRegistry implements UpdatableSqlRegistry {

    private Map<String, String> map = new ConcurrentHashMap<String, String>();

    public void update(String key, String sql) throws SqlUpdateFailureException {

        try {

            String previous = get(key);

            if (!StringUtils.isEmpty(previous) && !previous.equals(sql)) {
                register(key, sql);
            }
        }
        catch (SqlRetrievalFailureException e) {

            throw new SqlUpdateFailureException(key + "에 대한 SQL을 찾을 수 없어 업데이트를 수행할 수 없습니다.");
        }
    }

    public void update(Map<String, String> sqlMap) throws SqlUpdateFailureException {

        for (Map.Entry<String, String> entry : sqlMap.entrySet()) {
            update(entry.getKey(), entry.getValue());
        }
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
