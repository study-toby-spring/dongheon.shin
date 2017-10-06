package com.webtoonscorp.spring.aop;

import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PointcutTest {

    @Test
    public void methodSignaturePointcut() throws SecurityException, NoSuchMethodException {

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public int com.webtoonscorp.spring.aop.Target.minus(int, int) throws java.lang.RuntimeException)");

        assertThat(pointcut.getClassFilter().matches(Target.class) && pointcut.getMethodMatcher().matches(Target.class.getMethod("plus", int.class, int.class), null), is(false));
        assertThat(pointcut.getClassFilter().matches(Target.class) && pointcut.getMethodMatcher().matches(Target.class.getMethod("minus", int.class, int.class), null), is(true));
    }

    @Test
    public void pointcut() throws Exception {

        targetClassPointcutMatches("execution(* *(..))", true, true, true, true, true, true);
    }

    public void targetClassPointcutMatches(String expression, Boolean... expected) throws Exception {

        pointcutMatches(expression, expected[0], Target.class, "hello");
        pointcutMatches(expression, expected[1], Target.class, "hello", String.class);
        pointcutMatches(expression, expected[2], Target.class, "plus", int.class, int.class);
        pointcutMatches(expression, expected[3], Target.class, "minus", int.class, int.class);
        pointcutMatches(expression, expected[4], Target.class, "method");
        pointcutMatches(expression, expected[5], Bean.class, "method");
    }

    public void pointcutMatches(String expression, boolean expected, Class<?> type, String method, Class<?>... args) throws Exception {

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

        pointcut.setExpression(expression);
        assertThat(pointcut.getClassFilter().matches(type) && pointcut.getMethodMatcher().matches(type.getMethod(method, args), null), is(expected));
    }
}