package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.*;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        // You code here.
        try {
            Constructor<?> constr = configClass.getDeclaredConstructor();
            constr.setAccessible(true);
            Object obj = constr.newInstance();
            List<Method> methods = new ArrayList<>();
            for(Method method : configClass.getMethods()) {
               if(this.isAnnotatedMethod(method)) {
                   methods.add(method);
                }
            }

            methods.sort(Comparator.comparingInt(thatMethod -> thatMethod.getAnnotation(AppComponent.class).order()));

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

            System.out.println("Hello");
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        for(int i = 0; i < classesParams.length; i++) {
            for(Object obj : appComponents) {
                if(classesParams[i].isAssignableFrom(obj.getClass())) {
                    params[countParams] = obj;
                    countParams++;
                    break;
                }
            }
        }
        return params;
    }


    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        Object componentObj = null;
        Class<?>[] parameterTypes = new Class[componentClass.getTypeParameters().length];
        int i = 0;
        for(Object obj : appComponents) {
            parameterTypes[i] = obj.getClass();
            i++;
        }
        Constructor<?>[] constructors = componentClass.getConstructors();
        Parameter[] parameters = constructors[0].getParameters();
        if (parameters.length > 0) {
            Constructor<?> constr = constructors[0];
            constr.setAccessible(true);
            Object[] includedParams = this.collectParams(parameters, appComponents);
            try {
                componentObj = constr.newInstance(includedParams);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else  {
            Constructor<?> constr = constructors[0];
            constr.setAccessible(true);
            try {
                componentObj = constr.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return (C) componentObj;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return null;
    }

}
