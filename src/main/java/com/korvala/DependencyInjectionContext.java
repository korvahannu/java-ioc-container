package com.korvala;

public interface DependencyInjectionContext {
    public <T> T getService(final Class<T> serviceClass);
}
