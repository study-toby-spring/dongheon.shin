package com.webtoonscorp.spring.aop;

public interface TargetInterface {

    void hello();
    void hello(String message);

    int plus(int a, int b);
    int minus(int a, int b) throws RuntimeException;

    void method();
}
