package com.korvala.dependencyinjection;

public class ServiceInterfacePair<T, V> {
    private final T serviceInterface;
    private final V serviceClass;

    public ServiceInterfacePair(T serviceInterface, V serviceClass) {
        this.serviceInterface = serviceInterface;
        this.serviceClass = serviceClass;
    }

    public T getServiceInterface() {
        return serviceInterface;
    }

    public V getServiceClass() {
        return serviceClass;
    }
}
