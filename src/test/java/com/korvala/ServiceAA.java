package com.korvala;

public class ServiceAA implements IServiceA {

    @Inject
    private IServiceB serviceB;

    @Override
    public String jobA() {
        return "jobAA(" + this.serviceB.jobB() + ")";
    }

    @Override
    public IServiceF getServiceF() {
        return null;
    }
}
