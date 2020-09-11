package ru.otus.appcontainer;

import org.reflections.Reflections;
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
                Parameter[] parameters = method.getParameters();
                if (parameters.length > 0) {
                    Object[] includedParams = this.collectParams(parameters, appComponents);
                    appComponents.add(method.invoke(obj, includedParams));
                    appComponentsByName.put(method.getAnnotation(AppComponent.class).name() ,method.invoke(obj, includedParams));
                } else {
                    appComponents.add(method.invoke(obj));
                    appComponentsByName.put(method.getAnnotation(AppComponent.class).name() ,method.invoke(obj));
                }

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

    private Object[] collectParams(Parameter[] parameters, List<Object> appComponents) {
        Object[] params = new Object[parameters.length];
        int countParams = 0;
        Parameter parameter = parameters[0];
        Executable executable = parameter.getDeclaringExecutable();
        Class<?>[] classesParams = executable.getParameterTypes();
        for (Class<?> classesParam : classesParams) {
            for (Object obj : appComponents) {
                if (classesParam.isAssignableFrom(obj.getClass())) {
                    params[countParams] = obj;
                    countParams++;
                    break;
                }
            }
        }
        return params;
    }


    @Override
    public <C> C getAppComponent(Class<C> componentClass) throws Exception {
        Object componentObj = null;
        Class<C> clazz = componentClass;
        if(componentClass.isInterface()) {
            clazz = (Class<C>) this.findImplementedClass(componentClass);
        }
        Constructor<?>[] constructors = clazz.getConstructors();
        Parameter[] parameters = constructors[0].getParameters();
        if (parameters.length > 0) {
            Constructor<?> constr = constructors[0];
            constr.setAccessible(true);
            Object[] includedParams = this.collectParams(parameters, appComponents);
            componentObj = constr.newInstance(includedParams);
        } else  {
            Constructor<?> constr = constructors[0];
            constr.setAccessible(true);
            componentObj = constr.newInstance();
        }
        return (C) componentObj;
    }

    @Override
    public <C> C getAppComponent(String componentName) throws Exception {
        return (C) getAppComponent(Class.forName(componentName));
    }


    private Class<?> findImplementedClass(Class<?> clazz) {
        Reflections reflections = new Reflections("ru.otus");
        Set<Class<? extends Object>> allClasses =
                reflections.getSubTypesOf((Class<Object>) clazz);
        Iterator<Class<? extends Object>> it = allClasses.iterator();
        return it.next();
    }

}
