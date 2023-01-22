package com.korvala.dependencyinjection.abstractions;

public interface DependencyInjectionContext {
    public <T> T getService(final Class<T> serviceClass);
}
