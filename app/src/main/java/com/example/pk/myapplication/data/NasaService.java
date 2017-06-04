package com.example.pk.myapplication.data;

import com.example.pk.myapplication.model.NasaArticle;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pk on 03.06.2017.
 */

public interface NasaService {
    @GET("planetary/apod")
    Call<NasaArticle> loadArticle(@Query("api_key") String key,
                                  @Query("date") String date);
}
