package com.webtoonscorp.spring.service.sql.event.entry;

public class SqlEvent {

    private SqlEventType eventType;

    public SqlEventType getEventType() {

        return eventType;
    }

    public void setEventType(SqlEventType eventType) {

        this.eventType = eventType;
    }


    private String key;

    public String getKey() {

        return key;
    }

    public void setKey(String key) {

        this.key = key;
    }


    private String sql;

    public String getSql() {

        return sql;
    }

    public void setSql(String sql) {

        this.sql = sql;
    }


    public SqlEvent(SqlEventType type) {

        setEventType(type);
    }

    public SqlEvent(SqlEventType type, String key, String sql) {

        setEventType(type);
        setKey(key);
        setSql(sql);
    }
}
