package com.vasep.api;

import com.vasep.models.BankingAppotaAPI;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 23/12/2016.
 */

public interface ServiceInterfaceAppota {

    @FormUrlEncoded
    @POST("ibanking?api_key=A180290-E5I9CK-A695AA44C9449619&lang=vi")
    Call<BankingAppotaAPI> callAPI_VI(@Field("developer_trans_id") String developer_trans_id, @Field("amount") String amount, @Field("state") String state, @Field("success_url") String success_url, @Field("error_url") String error_url);

    @FormUrlEncoded
    @POST("ibanking?api_key=A180290-E5I9CK-A695AA44C9449619&lang=en")
    Call<BankingAppotaAPI> callAPI_EN(@Field("developer_trans_id") String developer_trans_id,@Field("amount") String amount,@Field("state") String state,@Field("success_url") String success_url,@Field("error_url") String error_url);

    @FormUrlEncoded
    @POST("pay_visa?api_key=A180290-E5I9CK-A695AA44C9449619&lang=vi")
    Call<BankingAppotaAPI> visaCallAPI_VI(@Field("developer_trans_id") String developer_trans_id, @Field("amount") String amount, @Field("state") String state, @Field("success_url") String success_url, @Field("error_url") String error_url);

    @FormUrlEncoded
    @POST("pay_visa?api_key=A180290-E5I9CK-A695AA44C9449619&lang=en")
    Call<BankingAppotaAPI> visacallAPI_EN(@Field("developer_trans_id") String developer_trans_id,@Field("amount") String amount,@Field("state") String state,@Field("success_url") String success_url,@Field("error_url") String error_url);

}
