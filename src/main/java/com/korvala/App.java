package com.korvala;

import java.util.HashSet;
import java.util.Set;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    public static void main(String[] args) throws Exception {
        start(createContext());
    }

    public static DependencyInjectionContext createContext() throws Exception {
        Set<Class<?>> services = new HashSet<>();
        services.add(ServiceA.class);
        services.add(ServiceB.class);
        services.add(ServiceC.class);
        return new DependencyInjectionContext(services);
    }

    public static void start(DependencyInjectionContext context) {
        ServiceA serviceA = context.getServiceInstance(ServiceA.class);

        System.out.println(serviceA.jobA());
    }
}
