package com.company;

import java.util.Map;
import java.util.Objects;

public class Hotel {
    private String name;
    private Map<CustomerType, Double> weekDayRates;
    private Map<CustomerType, Double> weekendRates;
    private double rating;
    public Hotel(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<CustomerType, Double> getWeekDayRates() {
        return weekDayRates;
    }

    public void setWeekDayRates(Map<CustomerType, Double> weekDayRates) {
        this.weekDayRates = weekDayRates;
    }

    public Map<CustomerType, Double> getWeekendRates() {
        return weekendRates;
    }

    public void setWeekendRates(Map<CustomerType, Double> weekendRates) {
        this.weekendRates = weekendRates;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Double.compare(hotel.rating, rating) == 0 && name.equals(hotel.name) && weekDayRates.equals(hotel.weekDayRates) && weekendRates.equals(hotel.weekendRates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weekDayRates, weekendRates, rating);
    }
}
