package com.korvala;

public class ServiceB implements IServiceB {

    @Inject
    private IServiceC serviceC;

    @Override
    public String jobB() {
        return "jobB(" + serviceC.jobC() + ")";
    }
}
