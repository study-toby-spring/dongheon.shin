package com.webtoonscorp.spring.configuration.sql;

import com.webtoonscorp.spring.configuration.SqlServiceConfiguration;
import org.springframework.context.annotation.Import;

@Import(SqlServiceConfiguration.class)
public @interface EnableSqlService {
}
