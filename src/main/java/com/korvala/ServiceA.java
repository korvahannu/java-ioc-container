package com.korvala;

public class ServiceA implements IServiceA {

    @Inject
    private ServiceB serviceB;

    @Override
    public String jobA() {
        return "jobA(" + this.serviceB.jobB() + ")";
    }
}
