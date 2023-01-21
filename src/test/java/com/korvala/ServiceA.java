package com.korvala;

import com.korvala.dependencyinjection.Inject;

public class ServiceA implements IServiceA {

    @Inject
    private IServiceB serviceB;

    @Override
    public String jobA() {
        return "jobA(" + this.serviceB.jobB() + ")";
    }
}
