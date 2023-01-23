package com.korvala.dependencyinjection.abstractions;

// @author Hannu Korvala
public interface ServiceInterfaceClassPair<T, V> {
    public T getServiceInterface();

    public V getServiceClass();

    public ServiceInterfaceClassPair<T, V> create(T serviceInterface, V serviceClass);
}
