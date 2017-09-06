package com.webtoonscorp.spring.proxy;

import org.junit.Test;

import java.lang.reflect.Proxy;

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

    @Test
    public void handlerTest() {

        HelloHandler handler = new HelloHandler();
        handler.setHello(new HelloTarget());

        Hello target = (Hello) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[] { Hello.class }, handler);

        assertThat(target.sayHello("dongheon"), is("HELLO, DONGHEON"));
        assertThat(target.sayHi("dongheon"), is("HI, DONGHEON"));
        assertThat(target.sayThankYou("dongheon"), is("THANK YOU, DONGHEON"));
    }
}