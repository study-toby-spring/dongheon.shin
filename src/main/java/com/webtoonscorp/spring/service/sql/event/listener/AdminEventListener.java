package com.webtoonscorp.spring.service.sql.event.listener;

import com.webtoonscorp.spring.service.sql.event.entry.SqlEvent;

public interface AdminEventListener {

    void onEvent(SqlEvent event);
}
