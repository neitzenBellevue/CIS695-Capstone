package com.example.cis695_capstone;

public class weightEntry {

    private int weight;
    private String date;
    private String image;

    public weightEntry(int weight, String date, String location) {
        this.weight = weight;
        this.date = date;
        this.image = image;
    }
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "weightEntry{" +
                "weight=" + weight +
                ", date='" + date + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
