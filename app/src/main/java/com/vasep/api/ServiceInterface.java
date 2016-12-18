package com.vasep.api;

import com.vasep.models.ArticleModel;
import com.vasep.models.CategoryResult;
import com.vasep.models.MarketResult;
import com.vasep.models.ProductResult;
import com.vasep.models.Result;
import com.vasep.models.TypeModel;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface ServiceInterface {
    ////////////////////////////////////////////////////////////////////////////////////////////////
    @GET("rest_vasep/rest/getTopArticle")
    Call<ArticleModel> getArticle(@Query("top") int top,@Query("from") int from,@Query("type") int type);

    @POST("rest_vasep/rest/insertContact")
    Call<Result> insertContact(@Body Object contact);

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
                                     @Query("top") int top,@Query("from") int from,@Query("language_type") String language_type,@Query("market_id") int market_id,@Query("product_id") int product_id ,@Query("type_id") int type_id);

    @GET("rest_vasep/rest/getAllType")
    Call<TypeModel> getAllType();

    @GET("rest_vasep/rest/filterArticle")
    Call<ArticleModel> filterArticle(@Query("market_id") String market_id,@Query("product_id") String product_id,@Query("type_id") int type_id,
                                     @Query("top") int top,@Query("from") int from);


}
