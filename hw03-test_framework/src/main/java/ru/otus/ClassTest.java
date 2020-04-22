package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class ClassTest {

    private String firstField = "String";

    public ClassTest() {
        System.out.println("Object is created");
    }

    public String getFirstField() {
        return this.firstField;
    }

    @Before
    public void init(){}

    @Test
    public void go(){}

    @After
    public void stop(){}
}
