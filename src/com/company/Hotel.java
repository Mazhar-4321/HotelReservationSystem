package com.company;

import com.company.enums.CustomerType;

import java.util.Map;
import java.util.Objects;

public class Hotel {
    private String name;
    private Double ratesPerDay;
    private Map<CustomerType, Double> weekDayRates;
    private Map<CustomerType, Double> weekendRates;

    public Hotel() {

    }

    public Hotel(String name, Map<CustomerType, Double> weekDayRates, Map<CustomerType, Double> weekendRates) {
        this.name = name;
        this.weekDayRates = weekDayRates;
        this.weekendRates = weekendRates;
    }

    public Double getRatesPerDay() {
        return ratesPerDay;
    }

    public void setRatesPerDay(Double ratesPerDay) {
        this.ratesPerDay = ratesPerDay;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(name, hotel.name) && Objects.equals(weekDayRates, hotel.weekDayRates) && Objects.equals(weekendRates, hotel.weekendRates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weekDayRates, weekendRates);
    }
}
