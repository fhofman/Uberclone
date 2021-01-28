package com.fede.uberclone.models;

public class Driver {
    String id;

    public Driver(String id, String name, String email, String carBrand, String carPlate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.carBrand = carBrand;
        this.carPlate = carPlate;
    }

    String name;
    String email;

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    String carBrand;
    String carPlate;

    public Driver() {
    }

    public Driver(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
