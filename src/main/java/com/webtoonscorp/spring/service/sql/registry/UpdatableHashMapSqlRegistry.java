package com.webtoonscorp.spring.service.sql.registry;

import com.webtoonscorp.spring.exception.SqlRetrievalFailureException;
import com.webtoonscorp.spring.exception.SqlUpdateFailureException;
import org.springframework.util.StringUtils;

import java.util.Map;

public class UpdatableHashMapSqlRegistry extends HashMapSqlRegistry implements UpdatableSqlRegistry {

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
}
