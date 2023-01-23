package com.korvala;

import java.util.List;

import com.korvala.dependencyinjection.ServiceInterfacePair;
import com.korvala.dependencyinjection.abstractions.DependencyInjectionContext;
import com.korvala.dependencyinjection.abstractions.DependencyInjectionContextBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * DiBuilder: Builder pattern: builds a context for dependency injection
 * 
 * @author Hannu Korvala
 */
public class DependencyInjectionBuilder implements DependencyInjectionContextBuilder {

    /**
     * DiContext: Context that you can use to get services
     * 
     * @author Hannu Korvala
     */
    public class Context implements DependencyInjectionContext {
        private List<ServiceInterfacePair<Class<?>, Object>> serviceContainer = new ArrayList<>();

        /**
         * Constructor. DiContext can only be built from the DiBuilder
         * 
         * @param services List of services defined by the you
         * @throws Exception
         */
        private Context(final List<ServiceInterfacePair<Class<?>, Class<?>>> services) throws Exception {
            generateInstances(services);
            mapFieldsToInstances();
        }

        /**
         * Use to get an instance for a service
         * 
         * @param serviceClass class type of what service you wish to receive
         * @return instance of the service you want
         */
        @SuppressWarnings("unchecked")
        public <T> T getService(final Class<T> serviceInterface) {
            for (int i = 0; i < this.serviceContainer.size(); i++) {
                var targetInterface = serviceContainer.get(i).getServiceInterface();
                if (targetInterface.equals(serviceInterface)) {
                    return (T) serviceContainer.get(i).getServiceClass();
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
        private void generateInstances(final List<ServiceInterfacePair<Class<?>, Class<?>>> services) throws Exception {
            for (int i = 0; i < services.size(); i++) {
                Class<?> serviceClass = services.get(i).getServiceClass();
                Constructor<?> constructor = serviceClass.getConstructor();
                constructor.setAccessible(true);
                Object serviceInstance = constructor.newInstance();
                this.serviceContainer.add(new ServiceInterfacePair<Class<?>, Object>(
                        services.get(i).getServiceInterface(), serviceInstance));
            }
        }

        /**
         * Maps the serviceInstances' fields
         * Essentially this method loops through are instances generated in
         * generateInstances -method
         * and then iterates over each of their fields and assigns them with other
         * instances generated in generateInstances -method
         * 
         * @param services Services as a collection of Class<?>
         * @throws Exception
         */
        private void mapFieldsToInstances()
                throws Exception {
            for (int i = 0; i < this.serviceContainer.size(); i++) {
                Object serviceInstance = this.serviceContainer.get(i).getServiceClass();
                for (Field field : serviceInstance.getClass().getDeclaredFields()) {

                    /**
                     * Only map fields that are marked with @Inject -attribute
                     */
                    if (!field.isAnnotationPresent(Inject.class)) {
                        continue;
                    }

                    Class<?> fieldType = field.getType();
                    field.setAccessible(true);

                    /**
                     * Here we iterate through serviceInstances again to find the correct matching
                     * field instance
                     */
                    for (int j = 0; j < this.serviceContainer.size(); j++) {
                        Object matchPartner = this.serviceContainer.get(j).getServiceClass();
                        if (fieldType.isInstance(matchPartner)) {
                            field.set(serviceInstance, matchPartner);
                            continue;
                        }
                    }
                }
            }
        }
    }

    private List<ServiceInterfacePair<Class<?>, Class<?>>> serviceRegstrationContainer = new ArrayList<>();

    /**
     * Add a service to dependency injection
     * 
     * @param serviceInterface Interface the service implements
     * @param service          Implementation of said interface
     * @return For fluent building, returns an instance of itself
     *         (DependencyInjectionBuilder)
     * @throws IllegalArgumentException
     */
    public DependencyInjectionBuilder addService(final Class<?> serviceInterface, final Class<?> service)
            throws IllegalArgumentException {
        if (doesInterfaceMatchWithClass(serviceInterface, service) == false) {
            throw new IllegalArgumentException("Service does not implement designated interface");
        }

        if (hasServiceBeenRegistered(serviceInterface)) {
            throw new IllegalArgumentException("Service with interface has already been added");
        }

        this.serviceRegstrationContainer.add(new ServiceInterfacePair<Class<?>, Class<?>>(serviceInterface, service));
        return this;
    }

    public static DependencyInjectionBuilder startBuild() {
        return new DependencyInjectionBuilder();
    }

    public Context build() throws Exception {
        return new Context(this.serviceRegstrationContainer);
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

    private boolean hasServiceBeenRegistered(final Class<?> serviceInterface) {
        for (int i = 0; i < this.serviceRegstrationContainer.size(); i++) {
            if (serviceInterface.equals(this.serviceRegstrationContainer.get(i).getServiceInterface())) {
                return true;
            }
        }

        return false;
    }
}
