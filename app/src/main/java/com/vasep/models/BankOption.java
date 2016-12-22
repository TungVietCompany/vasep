package com.vasep.models;

/**
 * Created by Administrator on 23/12/2016.
 */

public class BankOption {
    String url;
    String bank;

    public BankOption(String url, String bank) {
        this.url = url;
        this.bank = bank;
    }

    public BankOption() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
