package com.korvala;

import com.korvala.dependencyinjection.Inject;

public class ServiceAA implements IServiceA {

    @Inject
    private IServiceB serviceB;

    @Override
    public String jobA() {
        return "jobAA(" + this.serviceB.jobB() + ")";
    }
}
