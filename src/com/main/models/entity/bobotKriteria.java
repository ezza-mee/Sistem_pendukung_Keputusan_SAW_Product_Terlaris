package com.main.models.entity;

public class bobotKriteria {
    private String label;
    private int value;

    public bobotKriteria(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label + " [" + value + "]";
    }

}
