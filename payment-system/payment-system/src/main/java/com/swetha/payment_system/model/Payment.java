package com.swetha.payment_system.model;

public class Payment {
    private int id;
    private double amount;
    private String status;

    public Payment() {}

    public Payment(int id, double amount) {
        this.id = id;
        this.amount = amount;
        this.status = "PENDING";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}