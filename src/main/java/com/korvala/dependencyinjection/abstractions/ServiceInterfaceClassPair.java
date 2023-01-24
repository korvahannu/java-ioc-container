package com.korvala.dependencyinjection.abstractions;

// @author Hannu Korvala
public interface ServiceInterfaceClassPair<T, V> {
    public T serviceInterface();

    public V serviceClass();
}
