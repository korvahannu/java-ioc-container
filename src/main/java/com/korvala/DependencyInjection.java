package com.korvala;

import java.util.HashSet;
import java.util.Set;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * DiBuilder: Builder pattern: builds a context for dependency injection
 * 
 * @author Hannu Korvala
 */
public class DependencyInjection implements DependencyInjectionContextBuilder {

    /**
     * DiContext: Context that you can use to get services
     * 
     * @author Hannu Korvala
     */
    public class Context implements DependencyInjectionContext {
        private final Set<Object> serviceInstances = new HashSet<>();

        /**
         * Constructor. DiContext can only be built from the DiBuilder
         * 
         * @param services List of services defined by the you
         * @throws Exception
         */
        private Context(final Collection<Class<?>> services) throws Exception {
            generateInstances(services);
            mapFieldsToInstances(services);
        }

        /**
         * Use to get an instance for a service
         * 
         * @param serviceClass class type of what service you wish to receive
         * @return instance of the service you want
         */
        @SuppressWarnings("unchecked")
        public <T> T getService(final Class<T> serviceClass) {
            for (Object serviceInstance : this.serviceInstances) {
                if (serviceClass.isInstance(serviceInstance)) {
                    return (T) serviceInstance;
                }
            }
            return null;
        }

        /**
         * Generates class instances from a collection of Class<?> types
         * 
         * @param services Collection<Class<?>>
         * @throws Exception
         */
        private void generateInstances(final Collection<Class<?>> services) throws Exception {
            for (Class<?> serviceClass : services) {
                Constructor<?> constructor = serviceClass.getConstructor();
                constructor.setAccessible(true);
                Object serviceInstance = constructor.newInstance();
                this.serviceInstances.add(serviceInstance);
            }
        }

        /**
         * Maps the serviceInstances' fields
         * 
         * @param services Services as a collection of Class<?>
         * @throws Exception
         */
        private void mapFieldsToInstances(final Collection<Class<?>> services) throws Exception {
            for (Object serviceInstance : this.serviceInstances) {
                for (Field field : serviceInstance.getClass().getDeclaredFields()) {

                    /**
                     * Only map fields that are marked with @Inject -attribute
                     */
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

    /**
     * Add a service to dependency injection
     * 
     * @param serviceInterface Interface the service implements
     * @param service          Implementation of said interface
     * @return
     */
    public DependencyInjection addService(final Class<?> serviceInterface, final Class<?> service) {
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

    public static DependencyInjection startBuild() {
        return new DependencyInjection();
    }

    public Context build() throws Exception {
        return new Context(services);
    }

    /**
     * Helper class that checks if a class implements an interface
     * 
     * @param serviceInterface interface that should be implemented
     * @param service          implementing class that should implement interface
     * @return
     */
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
