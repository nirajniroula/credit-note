package com.wolf.nniroula.creditrecorder.model;

/**
 * Created by nniroula on 10/23/16.
 */
public class PaidModel {
    private int id;
    private String name;
    private Double paid;
    private String paid_date;

    public PaidModel(int id, String name, Double paid, String paid_date) {
        this.id = id;
        this.name = name;
        this.paid = paid;
        this.paid_date = paid_date;
    }

    public int getId() {
        return id;
    }

    public String getPaid_date() {
        return paid_date;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public void setPaid_date(String paid_date) {
        this.paid_date = paid_date;
    }

    public Double getPaid() {
        return paid;
    }

    public String getName() {
        return name;
    }
}
