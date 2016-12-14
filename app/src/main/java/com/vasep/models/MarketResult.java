package com.vasep.models;

import java.util.List;

/**
 * Created by thuyetpham94 on 15/12/2016.
 */

public class MarketResult {
    private int code;
    private List<Market> markets;

    public MarketResult(int code, List<Market> markets) {
        this.code = code;
        this.markets = markets;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Market> getMarkets() {
        return markets;
    }

    public void setMarkets(List<Market> markets) {
        this.markets = markets;
    }
}
