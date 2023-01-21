package com.korvala;

public class ServiceAC implements IServiceA, IServiceC {

    @Override
    public String jobC() {
        return "jobC()";
    }

    @Override
    public String jobA() {
        return "jobC(A)()";
    }

}
