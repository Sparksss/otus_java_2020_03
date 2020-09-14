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
            Constructor<?> constr = configClass.getDeclaredConstructor();
            constr.setAccessible(true);
            Object obj = constr.newInstance();
            List<Method> methods = Arrays.stream(configClass.getMethods())
                    .filter(this::isAnnotatedMethod)
                    .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class)
                            .order()))
                    .collect(Collectors.toList());

            for(Method method : methods) {
                Class<?>[] typeParams = method.getParameterTypes();
                Object[] includedParams = this.collectParams(typeParams, appComponents);
                Object component = method.invoke(obj, includedParams);
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

    private Object[] collectParams(Class<?>[] typeParams, List<Object> appComponents) {
        Object[] params = new Object[typeParams.length];
        int countParams = 0;
        for (Class<?> type : typeParams) {
            for (Object obj : appComponents) {
                if (type.isAssignableFrom(obj.getClass())) {
                    params[countParams] = obj;
                    countParams++;
                    break;
                }
            }
        }
        return params;
    }
}
