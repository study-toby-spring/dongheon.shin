package com.webtoonscorp.spring.support.transaction;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TransactionHandler implements InvocationHandler {

    private Object target;
    private PlatformTransactionManager manager;
    private String pattern;

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setManager(PlatformTransactionManager manager) {
        this.manager = manager;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getName().startsWith(pattern)) {
            return invokeInTransaction(method, args);
        }
        else {
            return method.invoke(target, args);
        }
    }

    private Object invokeInTransaction(Method method, Object[] args) throws Throwable {

        TransactionStatus status = manager.getTransaction(new DefaultTransactionDefinition());

        try {

            Object ret = method.invoke(target, args);
            manager.commit(status);

            return ret;
        }
        catch (InvocationTargetException e) {

            manager.rollback(status);
            throw e.getTargetException();
        }
    }
}
