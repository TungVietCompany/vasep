package com.vasep.models;

import java.util.ArrayList;

/**
 * Created by Administrator on 22/12/2016.
 */

public class PaymentResult {
    int code;
    ArrayList<Payment> payments;


    public PaymentResult() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public void setPayments(ArrayList<Payment> payments) {
        this.payments = payments;
    }
}
