package com.webtoonscorp.spring.template;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CalcSumTest {

    Calculator calculator;
    String path;

    @Before
    public void setup() {

        calculator = new Calculator();
        path = getClass().getResource("numbers.txt").getPath();
    }

    @Test
    public void sumOfNumbers() throws IOException {

        int sum = calculator.calcSum(path);
        assertThat(sum, is(10));
    }

    @Test
    public void mulOfNumbers() throws IOException {

        int sum = calculator.calcMul(path);
        assertThat(sum, is(24));
    }

    @Test
    public void concatenate() throws IOException {

        String str = calculator.concatenate(path);
        assertThat(str, is("1234"));
    }
}