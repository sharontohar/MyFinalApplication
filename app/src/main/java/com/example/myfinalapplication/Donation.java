package com.example.myfinalapplication;

public class Donation {
    private String name;
    private double count;
    private String location;
    private String area;
//    private CheckBox allergenicIngredients;

    public Donation(String name, double count, String location, String area) {
        this.name = name;
        this.count = count;
        this.location = location;
        this.area=area;
//        this.allergenicIngredients = allergenicIngredients;
    }

    public Donation() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
//
//    public CheckBox getAllergenicIngredients() {
//        return allergenicIngredients;
//    }
//
//    public void setAllergenicIngredients(CheckBox allergenicIngredients) {
//        this.allergenicIngredients = allergenicIngredients;
//    }
}
