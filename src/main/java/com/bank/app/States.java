package com.bank.app;

public class States {

    private String code;
    private String name;

    public States() {}

    public States(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
