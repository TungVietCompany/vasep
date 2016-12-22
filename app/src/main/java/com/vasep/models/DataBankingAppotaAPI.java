package com.vasep.models;

import java.util.ArrayList;

/**
 * Created by Administrator on 23/12/2016.
 */

public class DataBankingAppotaAPI {
    String transaction_id;
    String developer_trans_id;
    String amount;
    String currency;
    ArrayList<BankOption> bank_options;

    public DataBankingAppotaAPI(String transaction_id, String developer_trans_id, String amount, String currency, ArrayList<BankOption> bank_options) {
        this.transaction_id = transaction_id;
        this.developer_trans_id = developer_trans_id;
        this.amount = amount;
        this.currency = currency;
        this.bank_options = bank_options;
    }

    public DataBankingAppotaAPI() {
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getDeveloper_trans_id() {
        return developer_trans_id;
    }

    public void setDeveloper_trans_id(String developer_trans_id) {
        this.developer_trans_id = developer_trans_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ArrayList<BankOption> getBank_options() {
        return bank_options;
    }

    public void setBank_options(ArrayList<BankOption> bank_options) {
        this.bank_options = bank_options;
    }
}
