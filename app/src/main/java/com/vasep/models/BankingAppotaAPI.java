package com.vasep.models;

/**
 * Created by Administrator on 23/12/2016.
 */

public class BankingAppotaAPI {
    int error_code;
    String message;
    DataBankingAppotaAPI data;

    public BankingAppotaAPI(int error_code, String message, DataBankingAppotaAPI data) {
        this.error_code = error_code;
        this.message = message;
        this.data = data;
    }

    public BankingAppotaAPI() {
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBankingAppotaAPI getData() {
        return data;
    }

    public void setData(DataBankingAppotaAPI data) {
        this.data = data;
    }
}
