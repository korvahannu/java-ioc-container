package com.korvala;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DependencyInjectionContext {
    private final Set<Object> serviceInstances = new HashSet<>();

    public DependencyInjectionContext(Collection<Class<?>> services) throws Exception {

        for (Class<?> serviceClass : services) {
            Constructor<?> constructor = serviceClass.getConstructor();
            constructor.setAccessible(true);
            Object serviceInstance = constructor.newInstance();
            this.serviceInstances.add(serviceInstance);
        }

        for (Object serviceInstance : this.serviceInstances) {
            for (Field field : serviceInstance.getClass().getDeclaredFields()) {

                if (!field.isAnnotationPresent(Inject.class)) {
                    continue;
                }

                Class<?> fieldType = field.getType();
                field.setAccessible(true);

                for (Object matchPartner : this.serviceInstances) {
                    if (fieldType.isInstance(matchPartner)) {
                        field.set(serviceInstance, matchPartner);
                    }
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    public <T> T getServiceInstance(Class<T> serviceClass) {
        for (Object serviceInstance : this.serviceInstances) {
            if (serviceClass.isInstance(serviceInstance)) {
                return (T) serviceInstance;
            }
        }
        return null;
    }
}
