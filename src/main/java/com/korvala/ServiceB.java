package com.korvala;

public class ServiceB implements IServiceB {

    @Inject
    private ServiceC serviceC;

    @Override
    public String jobB() {
        return "jobB(" + serviceC.jobC() + ")";
    }
}
