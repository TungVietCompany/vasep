package com.vasep.api;

import com.vasep.models.ArticleModel;
import com.vasep.models.Result;

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
}
