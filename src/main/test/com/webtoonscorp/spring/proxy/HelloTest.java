package com.webtoonscorp.spring.proxy;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class HelloTest {

    @Test
    public void targetTest() {

        Hello hello = new HelloTarget();

        assertThat(hello.sayHello("dongheon"), is("Hello, dongheon"));
        assertThat(hello.sayHi("dongheon"), is("Hi, dongheon"));
        assertThat(hello.sayThankYou("dongheon"), is("Thank you, dongheon"));
    }

    @Test
    public void proxyTest() {

        HelloProxy proxy = new HelloProxy();
        proxy.setHello(new HelloTarget());

        assertThat(proxy.sayHello("dongheon"), is("Hello, dongheon"));
        assertThat(proxy.sayHi("dongheon"), is("Hi, dongheon"));
        assertThat(proxy.sayThankYou("dongheon"), is("Thank you, dongheon"));
    }
}