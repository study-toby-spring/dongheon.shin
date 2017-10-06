package com.webtoonscorp.spring.aop.target;

public class Target implements TargetInterface {

    public void hello() {

    }

    public void hello(String message) {

    }

    public int plus(int a, int b) {
        return 0;
    }

    public int minus(int a, int b) throws RuntimeException {
        return 0;
    }

    public void method() {

    }
}
