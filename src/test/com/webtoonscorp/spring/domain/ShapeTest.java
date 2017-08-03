package com.webtoonscorp.spring.domain;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/context/application-context.xml")
public class ShapeTest {

    // @Autowired
    // private Shape shape;

    @Resource
    private Object shape;

    @Test
    public void shouldReturnShapeType() {

        // assertThat(shape.getClass().getSimpleName(), not("Shape"));
        // assertThat(shape.getClass().getSimpleName(), is("Rectangle"));

        assertThat(shape.getClass().getSimpleName(), is("Object"));
    }
}