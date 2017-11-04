package com.webtoonscorp.spring.service.sql.admin;

import com.webtoonscorp.spring.service.sql.event.entry.SqlEvent;
import com.webtoonscorp.spring.service.sql.event.entry.SqlEventType;
import com.webtoonscorp.spring.service.sql.event.listener.AdminEventListener;
import com.webtoonscorp.spring.service.sql.registry.UpdatableSqlRegistry;

public class SqlAdminService implements AdminEventListener {

    private UpdatableSqlRegistry registry;

    public void setSqlRegistry(UpdatableSqlRegistry registry) {

        this.registry = registry;
    }

    public void onEvent(SqlEvent event) {

        if (event.getEventType() == SqlEventType.UPDATE) {
            registry.update(event.getKey(), event.getSql());
        }
    }
}
