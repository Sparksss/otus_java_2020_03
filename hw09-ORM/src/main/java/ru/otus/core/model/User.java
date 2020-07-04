package ru.otus.core.model;

import ru.otus.annotations.Id;

/**
 * @author sergey
 * created on 03.02.19.
 */
public class User {

    @Id
    private long id;
    private String name;
    private int age;

    public User(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age = " + age;

    }
}
