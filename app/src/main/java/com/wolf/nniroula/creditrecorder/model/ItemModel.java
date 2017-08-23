package com.wolf.nniroula.creditrecorder.model;

/**
 * Created by nniroula on 10/27/16.
 */
public class ItemModel {
    private int id;
    private String item_name;
    private Double item_price;
    private String item_unit;

    public ItemModel(int id, String name, String unit, Double price) {
        this.id = id;
        this.item_name = name;
        this.item_price = price;
        this.item_unit = unit;
    }

    public ItemModel(int id, String name, Double price) {
        this.id = id;
        this.item_name = name;
        this.item_price = price;
    }

    public ItemModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getItem_price() {
        return item_price;
    }

    public void setItem_price(Double item_price) {
        this.item_price = item_price;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_unit() {
        return item_unit;
    }

    public void setItem_unit(String item_unit) {
        this.item_unit = item_unit;
    }
}
