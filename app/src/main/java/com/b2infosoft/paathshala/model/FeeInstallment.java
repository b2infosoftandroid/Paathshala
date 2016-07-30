package com.b2infosoft.paathshala.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Microsoft on 7/25/2016.
 */
public class FeeInstallment {
    private String name;
    private String type;
    private Double total;
    private Double deposit;
    private Double discount;
    private Double balance;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
