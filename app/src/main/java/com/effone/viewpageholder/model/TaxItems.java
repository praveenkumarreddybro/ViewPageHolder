package com.effone.viewpageholder.model;

/**
 * Created by sumanth.peddinti on 5/11/2017.
 */

public class TaxItems {
    private String name;
    private double  value;

    public TaxItems(String name, double price) {
        this.name=name;
        this.value=price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
