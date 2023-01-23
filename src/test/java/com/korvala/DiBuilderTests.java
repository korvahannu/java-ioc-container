package com.korvala;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.korvala.dependencyinjection.DependencyInjectionBuilder;

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

        assertNotNull(context.getService(IServiceA.class));
    }

    @Test
    public void gettingMultipleOfSameServiceShouldNotThrow() throws Exception {
        var context = DependencyInjectionBuilder
                .startBuild()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceB.class, ServiceB.class)
                .addService(IServiceC.class, ServiceC.class)
                .build();

        assertNotNull(context.getService(IServiceA.class));
        assertNotNull(context.getService(IServiceB.class));
        assertNotNull(context.getService(IServiceC.class));

        assertTrue(context.getService(IServiceA.class) instanceof IServiceA);
        assertTrue(context.getService(IServiceB.class) instanceof IServiceB);
        assertTrue(context.getService(IServiceC.class) instanceof IServiceC);
    }

    @Test
    public void gettingMultipleOfDifferentServiceShouldNotThrow() throws Exception {
        var context = DependencyInjectionBuilder
                .startBuild()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceB.class, ServiceB.class)
                .addService(IServiceC.class, ServiceC.class)
                .build();

        assertNotNull(context.getService(IServiceA.class));
        assertNotNull(context.getService(IServiceB.class));
        assertNotNull(context.getService(IServiceC.class));
        assertTrue(context.getService(IServiceA.class) instanceof IServiceA);
        assertTrue(context.getService(IServiceB.class) instanceof IServiceB);
        assertTrue(context.getService(IServiceC.class) instanceof IServiceC);
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

        assertNotNull(first);
        assertNotNull(second);
        assertEquals(first, second);
    }

    @Test
    public void nonExistingServiceShouldReturnNull() throws Exception {
        var context = DependencyInjectionBuilder
                .startBuild()
                .addService(IServiceA.class, ServiceA.class)
                .build();

        var target = context.getService(IServiceB.class);

        assertEquals(null, target);
    }

    @Test
    public void itemsNotMarkedAsInjectedShouldNotBeInContext() throws Exception {
        var context = DependencyInjectionBuilder
                .startBuild()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceB.class, ServiceB.class)
                .addService(IServiceC.class, ServiceC.class)
                .addService(IServiceF.class, ServiceF.class)
                .build();

        var target = context.getService(IServiceA.class);

        assertEquals(null, target.getServiceF());
    }

    @Test
    public void correctTypeIsReturned() throws Exception {
        var context = DependencyInjectionBuilder
                .startBuild()
                .addService(IServiceA.class, ServiceAC.class) // implements IServiceA and IServiceC
                .addService(IServiceC.class, ServiceC.class)
                .build();

        var target = context.getService(IServiceC.class);
        var target2 = context.getService(IServiceA.class);

        assertTrue("Fetching IServiceC should return correct class assigned to it", target instanceof IServiceC);
        assertTrue("Fetching IServiceA should return correct class assigned to it", target2 instanceof IServiceA);
    }

    @Test
    public void correctTypeIsReturned2() throws Exception {
        var context = DependencyInjectionBuilder
                .startBuild()
                .addService(IServiceC.class, ServiceC.class)
                .addService(IServiceA.class, ServiceAC.class) // implements IServiceA and IServiceC
                .build();

        var target = context.getService(IServiceC.class);
        var target2 = context.getService(IServiceA.class);

        assertTrue("Fetching IServiceC should return correct class assigned to it", target instanceof IServiceC);
        assertTrue("Fetching IServiceA should return correct class assigned to it", target2 instanceof IServiceA);
    }

    @Test
    public void finishingEmptyBuildShouldNotThrow() throws Exception {
        DependencyInjectionBuilder
                .startBuild()
                .build();
    }
}
