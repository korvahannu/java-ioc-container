package com.korvala.dependencyinjection.abstractions;

// @author Hannu Korvala
public interface DependencyInjectionContext {
    public <T> T getService(final Class<T> serviceClass);
}
