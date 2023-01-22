package com.korvala;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DiBuilderTests {
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenSameInterfaceIsRegisteredTwice() {
        new DependencyInjectionBuilder()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceA.class, ServiceA.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenInteraceNotImplemented() {
        new DependencyInjectionBuilder()
                .addService(IServiceB.class, ServiceA.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenSameInterfaceIsRegisteredWithDifferentClasses() {
        new DependencyInjectionBuilder()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceA.class, ServiceAA.class);
    }

    @Test
    public void addingOneServiceShouldNotThrow() {
        new DependencyInjectionBuilder()
                .addService(IServiceA.class, ServiceA.class);
    }

    @Test
    public void addingTwoServicesShouldNotThrow() {
        new DependencyInjectionBuilder()
                .addService(IServiceA.class, ServiceA.class);
    }

    @Test
    public void addingThreeServicesShouldNotThrow() {
        new DependencyInjectionBuilder()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceB.class, ServiceB.class)
                .addService(IServiceC.class, ServiceC.class);
    }

    @Test
    public void finishingBuildShouldNotThrow() throws Exception {
        DependencyInjectionBuilder
                .startBuild()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceB.class, ServiceB.class)
                .addService(IServiceC.class, ServiceC.class)
                .build();
    }

    @Test
    public void gettingServiceShouldNotThrow() throws Exception {
        var context = DependencyInjectionBuilder
                .startBuild()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceB.class, ServiceB.class)
                .addService(IServiceC.class, ServiceC.class)
                .build();

        context.getService(IServiceA.class);
    }

    @Test
    public void gettingMultipleOfSameServiceShouldNotThrow() throws Exception {
        var context = DependencyInjectionBuilder
                .startBuild()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceB.class, ServiceB.class)
                .addService(IServiceC.class, ServiceC.class)
                .build();

        context.getService(IServiceA.class);
        context.getService(IServiceA.class);
        context.getService(IServiceA.class);
    }

    @Test
    public void gettingMultipleOfDifferentServiceShouldNotThrow() throws Exception {
        var context = DependencyInjectionBuilder
                .startBuild()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceB.class, ServiceB.class)
                .addService(IServiceC.class, ServiceC.class)
                .build();

        context.getService(IServiceA.class);
        context.getService(IServiceB.class);
        context.getService(IServiceC.class);
    }

    @Test
    public void returnedServiceAreTheSameInstance() throws Exception {
        var context = DependencyInjectionBuilder
                .startBuild()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceB.class, ServiceB.class)
                .addService(IServiceC.class, ServiceC.class)
                .build();

        var first = context.getService(IServiceA.class);
        var second = context.getService(IServiceA.class);

        assertEquals(first, second);
    }
}
