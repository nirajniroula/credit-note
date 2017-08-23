package com.wolf.nniroula.creditrecorder.model;

/**
 * Created by nniroula on 5/18/16.
 */
public class RecordModel {
    int id;
    String name;
    Double weight;
    String created_at;
    String item;
    String unit;
    Double price;


    public RecordModel(int id, String name, Double weight, String item, String unit, Double price, String created_at) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.created_at = created_at;
        this.item = item;
        this.unit = unit;
        this.price = price;

    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }


    public Double getWeight() {
        return weight;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getItem() {
        return item;
    }

    public String getUnit() {
        return unit;
    }

    public Double getPrice() {
        return price;
    }

}
