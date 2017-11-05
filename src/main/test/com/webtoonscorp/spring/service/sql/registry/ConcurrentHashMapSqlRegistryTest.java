package com.webtoonscorp.spring.service.sql.registry;

public class ConcurrentHashMapSqlRegistryTest extends AbstractUpdatableSqlRegistryTest {

    public UpdatableSqlRegistry createRegistry() {

        ConcurrentHashMapSqlRegistry registry = new ConcurrentHashMapSqlRegistry();

        for (int i = 0; i < 3; i++) {
            registry.register(getKeys()[i], getSqls()[i]);
        }

        return registry;
    }
}
