package com.korvala;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.korvala.dependencyinjection.DiBuilder;

public class DiBuilderTests {
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenSameInterfaceIsRegisteredTwice() {
        new DiBuilder()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceA.class, ServiceA.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenInteraceNotImplemented() {
        new DiBuilder()
                .addService(IServiceB.class, ServiceA.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenSameInterfaceIsRegisteredWithDifferentClasses() {
        new DiBuilder()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceA.class, ServiceAA.class);
    }

    @Test
    public void aa() {
        new DiBuilder()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceC.class, ServiceAC.class);

        assertTrue("asd", true);
    }
}
