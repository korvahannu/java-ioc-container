package com.korvala;

import com.korvala.dependencyinjection.Inject;

public class ServiceB implements IServiceB {

    @Inject
    private IServiceC serviceC;

    @Override
    public String jobB() {
        return "jobB(" + serviceC.jobC() + ")";
    }
}
