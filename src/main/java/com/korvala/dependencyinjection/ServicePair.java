package com.korvala.dependencyinjection;

import com.korvala.dependencyinjection.abstractions.ServiceInterfaceClassPair;

/**
 * Class that represents a service pair where T is the interface of class and V
 * is the class implementation of an interface
 * 
 * @author Hannu Korvala
 */
public class ServicePair<T, V> implements ServiceInterfaceClassPair<T, V> {
    private final T serviceInterface;
    private final V serviceClass;

    public ServicePair(T serviceInterface, V serviceClass) {
        this.serviceInterface = serviceInterface;
        this.serviceClass = serviceClass;
    }

    public T getServiceInterface() {
        return serviceInterface;
    }

    public V getServiceClass() {
        return serviceClass;
    }

    @Override
    public ServiceInterfaceClassPair<T, V> create(T serviceInterface, V serviceClass) {
        return new ServicePair<T, V>(serviceInterface, serviceClass);
    }
}
