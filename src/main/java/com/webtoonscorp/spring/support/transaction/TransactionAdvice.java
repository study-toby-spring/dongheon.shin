package com.webtoonscorp.spring.support.transaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor {

    PlatformTransactionManager manager;

    public void setManager(PlatformTransactionManager manager) {
        this.manager = manager;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {

        TransactionStatus status = manager.getTransaction(new DefaultTransactionDefinition());

        try {

            Object ret = invocation.proceed();
            manager.commit(status);

            return ret;
        }
        catch (RuntimeException e) {

            manager.rollback(status);
            throw e;
        }
    }
}
