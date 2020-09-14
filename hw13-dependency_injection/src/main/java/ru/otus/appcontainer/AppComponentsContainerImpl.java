package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws Exception {
        processConfig(initialConfigClass);
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) { ;
        return (C) this.appComponents.stream().filter(component -> componentClass.isAssignableFrom(component.getClass())).findFirst().get();
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) this.appComponentsByName.get(componentName);
    }

    private void processConfig(Class<?> configClass) throws Exception {
        checkConfigClass(configClass);
        // You code here.
            Object obj = this.createInstance(configClass);
            List<Method> methods = this.getOrderedMethods(configClass);
            this.collectComponents(methods, obj);
    }

    private Object createInstance(Class<?> configClass) throws Exception {
        Constructor<?> constr = configClass.getDeclaredConstructor();
        constr.setAccessible(true);
        return constr.newInstance();
    }

    private List<Method> getOrderedMethods(Class<?> configClass) {
        return Arrays.stream(configClass.getMethods())
                .filter(this::isAnnotatedMethod)
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class)
                        .order()))
                .collect(Collectors.toList());

    }

    private void collectComponents(List<Method> methods, Object ConfigInstance) throws Exception {
        for(Method method : methods) {
            Class<?>[] typeParams = method.getParameterTypes();
            Object[] includedParams = this.collectParams(typeParams);
            Object component = method.invoke(ConfigInstance, includedParams);
            appComponents.add(component);
            appComponentsByName.put(method.getAnnotation(AppComponent.class).name() ,component);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private boolean isAnnotatedMethod(Method method) {
        return method.isAnnotationPresent(AppComponent.class);
    }

    private Object[] collectParams(Class<?>[] typeParams) {
        Object[] params = new Object[typeParams.length];
        int countParams = 0;
        for (Class<?> type : typeParams) {
            params[countParams] = this.getAppComponent(type);
            countParams++;
        }
        return params;
    }
}
