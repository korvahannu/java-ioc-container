package com.korvala.dependencyinjection.abstractions;

import com.korvala.DependencyInjectionBuilder;

public interface DependencyInjectionContextBuilder {
    public DependencyInjectionBuilder addService(final Class<?> serviceInterface, final Class<?> service);

    public DependencyInjectionContext build() throws Exception;
}
