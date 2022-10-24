package com.aegisep;

public enum Constants {
    READ_WRITE_DELETE("READ_WRITE_DELETE"),
    READ_WRITE("READ_WRITE"),
    READ("READ");


    private final String label;

    Constants(String label) {
        this.label = label;
    }
}
