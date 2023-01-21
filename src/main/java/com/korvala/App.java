package com.korvala;

import com.korvala.dependencyinjection.DiBuilder;

public final class App {
    public static void main(String[] args) throws Exception {
        var context = DiBuilder
                .startBuild()
                .addService(IServiceA.class, ServiceA.class)
                .addService(IServiceB.class, ServiceB.class)
                .addService(IServiceC.class, ServiceAC.class)
                .build();

        System.out.println(context.getService(ServiceA.class).jobA());
    }
}
