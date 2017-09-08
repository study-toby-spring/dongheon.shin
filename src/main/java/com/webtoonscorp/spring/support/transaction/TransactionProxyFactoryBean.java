package com.webtoonscorp.spring.support.transaction;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

public class TransactionProxyFactoryBean implements FactoryBean<Object> {

    Object target;
    PlatformTransactionManager manager;
    String pattern;
    Class<?> serviceInterface;

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setManager(PlatformTransactionManager manager) {
        this.manager = manager;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public Object getObject() throws Exception {

        TransactionHandler handler = new TransactionHandler();

        handler.setTarget(target);
        handler.setManager(manager);
        handler.setPattern(pattern);

        return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[] { serviceInterface }, handler);
    }

    public Class<?> getObjectType() {
        return serviceInterface;
    }

    public boolean isSingleton() {
        return false;
    }
}
