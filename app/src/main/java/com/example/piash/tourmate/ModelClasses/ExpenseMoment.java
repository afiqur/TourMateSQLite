package com.example.piash.tourmate.ModelClasses;

/**
 * Created by PIASH on 10-May-17.
 */

public class ExpenseMoment{

    private int id;
    private String name;
    private double amount;
    private int eventIdForeign;
    //private int eventIduserIdForeign;

    public ExpenseMoment(int id, String name, double amount, int eventIdForeign) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.eventIdForeign = eventIdForeign;
    }

    public ExpenseMoment(String name, double amount, int eventIdForeign) {
        this.name = name;
        this.amount = amount;
        this.eventIdForeign = eventIdForeign;
    }

    public ExpenseMoment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getEventIdForeign() {
        return eventIdForeign;
    }

    public void setEventIdForeign(int eventIdForeign) {
        this.eventIdForeign = eventIdForeign;
    }

    @Override
    public String toString() {
        return "ExpenseMoment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount='" + amount + '\'' +
                ", eventIdForeign=" + eventIdForeign +
                '}';
    }
}
