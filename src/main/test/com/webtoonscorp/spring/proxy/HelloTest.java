package com.webtoonscorp.spring.proxy;

import org.junit.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

    @Test
    public void proxyFactoryBean() {

        ProxyFactoryBean bean = new ProxyFactoryBean();

        bean.setTarget(new HelloTarget());
        bean.addAdvice(new HelloAdvice());

        Hello proxy = (Hello) bean.getObject();

        assertThat(proxy.sayHello("dongheon"), is("HELLO, DONGHEON"));
        assertThat(proxy.sayHi("dongheon"), is("HI, DONGHEON"));
        assertThat(proxy.sayThankYou("dongheon"), is("THANK YOU, DONGHEON"));
    }

    @Test
    public void pointcutAdvisor() {

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        ProxyFactoryBean bean = new ProxyFactoryBean();

        bean.setTarget(new HelloTarget());
        bean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new HelloAdvice()));

        Hello proxy = (Hello) bean.getObject();

        assertThat(proxy.sayHello("dongheon"), is("HELLO, DONGHEON"));
        assertThat(proxy.sayHi("dongheon"), is("HI, DONGHEON"));
        assertThat(proxy.sayThankYou("dongheon"), is("Thank you, dongheon"));
    }

    @Test
    public void classNamePointcutAdvisor() {

        NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut() {

            @Override
            public ClassFilter getClassFilter() {

                return new ClassFilter() {

                    public boolean matches(Class<?> type) {

                        String name = type.getSimpleName();
                        return name.startsWith("HelloT") || name.startsWith("HelloD");
                    }
                };
            }
        };

        classMethodPointcut.setMappedName("sayH*");

        class HelloWorld extends HelloTarget { }
        class HelloDongheon extends HelloTarget { }

        checkAdvised(new HelloTarget(), classMethodPointcut, true);
        checkAdvised(new HelloWorld(), classMethodPointcut, false);
        checkAdvised(new HelloDongheon(), classMethodPointcut, true);
    }

    private void checkAdvised(Object target, Pointcut pointcut, boolean advised) {

        ProxyFactoryBean bean = new ProxyFactoryBean();

        bean.setTarget(target);
        bean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new HelloAdvice()));

        Hello proxy = (Hello) bean.getObject();

        if (advised) {

            assertThat(proxy.sayHello("dongheon"), is("HELLO, DONGHEON"));
            assertThat(proxy.sayHi("dongheon"), is("HI, DONGHEON"));
            assertThat(proxy.sayThankYou("dongheon"), is("Thank you, dongheon"));
        }
        else {

            assertThat(proxy.sayHello("dongheon"), is("Hello, dongheon"));
            assertThat(proxy.sayHi("dongheon"), is("Hi, dongheon"));
            assertThat(proxy.sayThankYou("dongheon"), is("Thank you, dongheon"));
        }
    }
}