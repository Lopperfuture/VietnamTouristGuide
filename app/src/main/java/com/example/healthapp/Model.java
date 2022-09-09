package com.example.healthapp;

public class Model {
    private String cityName;
    private String name;
    private String description;
    private String image;


    public Model() {
    }

    public Model(String healthName, String description, String image) {
        this.cityName = cityName;
        this.description = description;
        this.image = image;
        this.name = name;

    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
