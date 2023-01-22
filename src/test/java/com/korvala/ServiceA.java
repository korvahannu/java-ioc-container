package com.korvala;

public class ServiceA implements IServiceA {

    @Inject
    private IServiceB serviceB;

    private IServiceF serviceF;

    @Override
    public String jobA() {
        return "jobA(" + this.serviceB.jobB() + ")";
    }

    @Override
    public IServiceF getServiceF() {
        return serviceF;
    }
}
