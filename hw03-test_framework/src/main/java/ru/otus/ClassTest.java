package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.io.IOException;

public class ClassTest {

    private String firstField = "String";

    @After
    public String getFirstField() {
        return this.firstField;
    }

    @Before
    public void init(){}

    @Before
    public int getInt(){
        return 1;
    }

    @Test
    public void go(){}

    @Test
    public void fail() throws InterruptedException {
        throw new InterruptedException();
    }

    @Test
    private int next() throws IOException {
        throw new IOException();
    }

    @After
    public void stop(){}
}
