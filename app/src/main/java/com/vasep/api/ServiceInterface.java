package com.vasep.api;

import com.vasep.models.ArticleModel;
import com.vasep.models.BankingAppotaAPI;
import com.vasep.models.CategoryResult;
import com.vasep.models.Expire;
import com.vasep.models.MarketResult;
import com.vasep.models.PaymentResult;
import com.vasep.models.ProductResult;
import com.vasep.models.Result;
import com.vasep.models.TypeModel;
import com.vasep.models.AddView;
import com.vasep.models.User;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface ServiceInterface {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @GET("rest_vasep/rest/getTopArticle")
    Call<ArticleModel> getArticle(@Query("top") int top,@Query("from") int from,@Query("type") int type);

    @GET("rest_vasep/rest/insertContact_GET")
    Call<Result> insertContact(@Query("fullname") String fullname,
                               @Query("phone") String phone,@Query("address") String address,
                               @Query("note") String note
                               );

    @GET("rest_vasep/rest/getAllCategory")
    Call<CategoryResult> allCategory();

    @GET("rest_vasep/rest/getReport")
    Call<ArticleModel> getReport(@Query("article_id") int article_id,@Query("user_id") int user_id);

    @GET("rest_vasep/rest/getAllMarket")
    Call<MarketResult> getMarket();

    @GET("rest_vasep/rest/getAllProduct")
    Call<ProductResult> getProduct();

    @GET("rest_vasep/rest/searchArticle")
    Call<ArticleModel> searchArticle(@Query("categories") String categories,@Query("title") String title,@Query("type") int type,
                                     @Query("top") int top,@Query("from") int from,@Query("language_type") int language_type,@Query("market_id") int market_id,@Query("product_id") int product_id ,@Query("type_id") int type_id);

    @GET("rest_vasep/rest/getAllType")
    Call<TypeModel> getAllType();

    @GET("rest_vasep/rest/increaseViewNumber_GET")
    Call<AddView> inserView(@Query("article_id") String article_id );

    @GET("rest_vasep/rest/filterArticle")
    Call<ArticleModel> filterArticle(@Query("market_id") String market_id,@Query("product_id") String product_id,@Query("type_id") int type_id,
                                     @Query("top") int top,@Query("from") int from);

    @GET("rest_vasep/rest/insertNotification_GET")
    Call<Result> noti(@Query("firebase_id") String firebase_id,@Query("device_type") String device_type);

    @GET("rest_vasep/rest/getArticleById")
    Call<ArticleModel> getById(@Query("id") String id);

    @FormUrlEncoded
    @POST("rest_vasep/rest/login")
    Call<User> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("rest_vasep/rest/forgotPassword")
    Call<Result> forgotPass(@Field("email") String email);

    @FormUrlEncoded
    @POST("rest_vasep/rest/insertUser")
    Call<User> signUp(@Field("username") String username, @Field("password") String password,@Field("fullname") String fullname,@Field("email") String email);


    @FormUrlEncoded
    @POST("rest_vasep/rest/changePassword")
    Call<User> changePass(@Field("user_id") String user_id, @Field("password") String password,@Field("old_password") String old_password);

    @GET("rest_vasep/rest/getAllPayment")
    Call<PaymentResult> getAllPayment();

    @FormUrlEncoded
    @POST("rest_vasep/rest/insertTransaction")
    Call<Result> insertTransaction(@Field("article_id") int article_id, @Field("user_id") int user_id, @Field("payment_id") int payment_id, @Field("status") int status, @Field("error_code") int error_code,@Field("message") String message,@Field("transaction_id") String transaction_id,@Field("developer_trans_id") String developer_trans_id,@Field("amount") String amount,@Field("currency") String currency,@Field("url") String url,@Field("bank") String bank);

    @GET("rest_vasep/rest/checkUserExpire")
    Call<Expire> checkUserExpire(@Query("user_id") int user_id,@Query("report_id") int report_id);

    @FormUrlEncoded
    @POST("api_key=A180290-E5I9CK-A695AA44C9449619&lang=vi")
    Call<BankingAppotaAPI>callAPI_VI(@Field("developer_trans_id") String developer_trans_id,@Field("amount") String amount,@Field("state") String state,@Field("success_url") String success_url,@Field("error_url") String error_url);

    @FormUrlEncoded
    @POST("api_key=A180290-E5I9CK-A695AA44C9449619&lang=en")
    Call<BankingAppotaAPI> callAPI_EN(@Field("developer_trans_id") String developer_trans_id,@Field("amount") String amount,@Field("state") String state,@Field("success_url") String success_url,@Field("error_url") String error_url);

}
