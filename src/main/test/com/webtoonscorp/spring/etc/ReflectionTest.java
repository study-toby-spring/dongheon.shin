package com.webtoonscorp.spring.etc;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReflectionTest {

    @Test
    public void invokeTest() throws Exception {

        String name = "Spring";
        assertThat(name.length(), is(6));

        Method length = String.class.getMethod("length");
        assertThat((Integer) length.invoke(name), is(6));

        assertThat(name.charAt(0), is('S'));

        Method charAt = String.class.getMethod("charAt", int.class);
        assertThat((Character) charAt.invoke(name, 0), is('S'));
    }
}
