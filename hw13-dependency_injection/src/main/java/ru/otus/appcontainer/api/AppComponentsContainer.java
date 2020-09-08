package ru.otus.appcontainer.api;

public interface AppComponentsContainer {
    <C> C getAppComponent(Class<C> componentClass) throws Exception;
    <C> C getAppComponent(String componentName) throws Exception;
}
