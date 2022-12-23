package com.company;

import com.company.enums.CustomerType;

import java.util.Map;
import java.util.Objects;

public class Hotel {
    private String name;
    private Double ratesPerDay;
    private double rating;

    public Hotel() {

    }

    public Hotel(String name, Double ratesPerDay, double rating) {
        this.name = name;
        this.ratesPerDay = ratesPerDay;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRatesPerDay() {
        return ratesPerDay;
    }

    public void setRatesPerDay(Double ratesPerDay) {
        this.ratesPerDay = ratesPerDay;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


}
