package com.korvala;

import org.junit.Test;

public class DiBuilderTests {
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenSameInterfaceIsRegisteredTwice() {
        new DependencyInjection()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceA.class, ServiceA.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenInteraceNotImplemented() {
        new DependencyInjection()
                .addService(IServiceB.class, ServiceA.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenSameInterfaceIsRegisteredWithDifferentClasses() {
        new DependencyInjection()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceA.class, ServiceAA.class);
    }

    @Test
    public void addingOneServiceShouldNotThrow() {
        new DependencyInjection()
                .addService(IServiceA.class, ServiceA.class);
    }

    @Test
    public void addingTwoServicesShouldNotThrow() {
        new DependencyInjection()
                .addService(IServiceA.class, ServiceA.class);
    }

    @Test
    public void addingThreeServicesShouldNotThrow() {
        new DependencyInjection()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceB.class, ServiceB.class)
                .addService(IServiceC.class, ServiceC.class);
    }

    @Test
    public void finishingBuildShouldNotThrow() throws Exception {
        DependencyInjection
                .startBuild()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceB.class, ServiceB.class)
                .addService(IServiceC.class, ServiceC.class)
                .build();
    }

    @Test
    public void gettingServiceShouldNotThrow() throws Exception {
        var context = DependencyInjection
                .startBuild()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceB.class, ServiceB.class)
                .addService(IServiceC.class, ServiceC.class)
                .build();

        context.getService(IServiceA.class);
    }

    @Test
    public void gettingMultipleOfSameServiceShouldNotThrow() throws Exception {
        var context = DependencyInjection
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
        var context = DependencyInjection
                .startBuild()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceB.class, ServiceB.class)
                .addService(IServiceC.class, ServiceC.class)
                .build();

        context.getService(IServiceA.class);
        context.getService(IServiceB.class);
        context.getService(IServiceC.class);
    }
}
