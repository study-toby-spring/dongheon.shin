package com.webtoonscorp.spring.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class HelloAdvice implements MethodInterceptor {

    public Object invoke(MethodInvocation invocation) throws Throwable {

        String ret = (String) invocation.proceed();
        return ret.toUpperCase();
    }
}