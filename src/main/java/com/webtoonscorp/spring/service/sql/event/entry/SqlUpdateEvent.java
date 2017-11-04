package com.webtoonscorp.spring.service.sql.event.entry;

public class SqlUpdateEvent extends SqlEvent {

    public SqlUpdateEvent() {
        super(SqlEventType.UPDATE);
    }
}
