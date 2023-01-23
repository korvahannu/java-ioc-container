package com.korvala.dependencyinjection.abstractions;

// @author Hannu Korvala
public interface DependencyInjectionContextBuilder {
    public DependencyInjectionContextBuilder addService(final Class<?> serviceInterface, final Class<?> service);

    public DependencyInjectionContext build() throws Exception;
}
