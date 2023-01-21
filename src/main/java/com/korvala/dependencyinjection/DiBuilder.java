package com.korvala.dependencyinjection;

import java.util.HashSet;
import java.util.Set;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collection;

public class DiBuilder {

    public class DiContext {
        private final Set<Object> serviceInstances = new HashSet<>();

        private DiContext(final Collection<Class<?>> services) throws Exception {
            generateInstances(services);
            mapFieldsToInstances(services);
        }

        @SuppressWarnings("unchecked")
        public <T> T getService(final Class<T> serviceClass) {
            for (Object serviceInstance : this.serviceInstances) {
                if (serviceClass.isInstance(serviceInstance)) {
                    return (T) serviceInstance;
                }
            }
            return null;
        }

        private void generateInstances(final Collection<Class<?>> services) throws Exception {
            for (Class<?> serviceClass : services) {
                Constructor<?> constructor = serviceClass.getConstructor();
                constructor.setAccessible(true);
                Object serviceInstance = constructor.newInstance();
                this.serviceInstances.add(serviceInstance);
            }
        }

        private void mapFieldsToInstances(final Collection<Class<?>> services) throws Exception {
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
    }

    private Set<Class<?>> serviceInterfaces = new HashSet<>();
    private Set<Class<?>> services = new HashSet<>();

    public DiBuilder addService(final Class<?> serviceInterface, final Class<?> service) {
        if (doesInterfaceMatchWithClass(serviceInterface, service) == false) {
            throw new IllegalArgumentException();
        }

        if (serviceInterfaces.contains(serviceInterface)) {
            throw new IllegalArgumentException();
        }

        serviceInterfaces.add(serviceInterface);
        services.add(service);
        return this;
    }

    public static DiBuilder startBuild() {
        return new DiBuilder();
    }

    public DiContext build() throws Exception {
        return new DiContext(services);
    }

    private boolean doesInterfaceMatchWithClass(final Class<?> serviceInterface, final Class<?> service) {
        Class<?>[] interfaces = service.getInterfaces();
        boolean isValidInterface = false;

        for (Class<?> currentInterface : interfaces) {
            if (currentInterface.getSimpleName().equals(serviceInterface.getSimpleName())) {
                isValidInterface = true;
            }
        }

        return isValidInterface;
    }
}
