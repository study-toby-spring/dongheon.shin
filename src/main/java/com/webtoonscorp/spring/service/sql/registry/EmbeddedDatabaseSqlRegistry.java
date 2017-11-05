package com.webtoonscorp.spring.service.sql.registry;

import com.webtoonscorp.spring.exception.SqlRetrievalFailureException;
import com.webtoonscorp.spring.exception.SqlUpdateFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Map;

public class EmbeddedDatabaseSqlRegistry implements UpdatableSqlRegistry {

    private JdbcTemplate jdbcTemplate;
    private TransactionTemplate transactionTemplate;

    public void setDataSource(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
    }


    public void update(String key, String sql) throws SqlUpdateFailureException {

        int affected  = jdbcTemplate.update("UPDATE SQLMAP SET SQL_ = ? WHERE KEY_ = ?", sql, key);

        if (affected == 0) {
            throw new SqlUpdateFailureException(key + "에 대한 SQL을 찾을 수 없어 업데이트를 수행할 수 없습니다.");
        }
    }

    public void update(final Map<String, String> sqlMap) throws SqlUpdateFailureException {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

                for (Map.Entry<String, String> entry : sqlMap.entrySet()) {
                    update(entry.getKey(), entry.getValue());
                }
            }
        });
    }

    public void register(String key, String sql) {

        jdbcTemplate.update("INSERT INTO SQLMAP(KEY_, SQL_) VALUES (?, ?)", key, sql);
    }

    public String get(String key) throws SqlRetrievalFailureException {

        try {

            return jdbcTemplate.queryForObject("SELECT SQL_ FROM SQLMAP WHERE KEY_ = ?", String.class, key);
        }
        catch (EmptyResultDataAccessException e) {

            throw new SqlRetrievalFailureException(key + "에 대한 SQL을 찾을 수 없습니다");
        }
    }
}
