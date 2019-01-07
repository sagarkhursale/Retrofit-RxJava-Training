package com.example.sagar.retrofit.http;

import com.example.sagar.retrofit.apimodel.TopRated;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MovieApiService {

    @GET("movie/top_rated?{api_key}&language=en-US")
    Call<TopRated> getTopRatedMovies(@Path("api_key") String api_key, @Query("page") int page);

}
