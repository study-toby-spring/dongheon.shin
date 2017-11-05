package com.webtoonscorp.spring.annotation;

import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContentTest {

    @Test
    public void annotation() {

        Annotation annotation = Content.class.getAnnotation(ContentType.class);

        if (annotation != null) {

            ContentType meta = (ContentType) annotation;

            String type = meta.type();
            boolean valid = meta.valid();

            assertThat(type, is("application/json"));
            assertThat(valid, is(true));
        }
    }
}
