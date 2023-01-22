package com.korvala;

public interface DependencyInjectionContextBuilder {
    public DependencyInjection addService(final Class<?> serviceInterface, final Class<?> service);

    public DependencyInjectionContext build() throws Exception;
}
