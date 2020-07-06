package ru.otus.jdbc.mapper;

public class NoSuchFieldIdException extends Exception {
    public NoSuchFieldIdException(Exception ex) {
        super(ex);
    }
}
