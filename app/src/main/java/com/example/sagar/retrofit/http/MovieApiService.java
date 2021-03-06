package com.example.sagar.retrofit.http;


import com.example.sagar.retrofit.apimodel.TopRated;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MovieApiService {

    @GET("movie/top_rated?language=en-US")
    Call<TopRated> getTopRatedMovies(@Query("api_key") String api_key, @Query("page") int page);


    @GET("movie/top_rated?language=en-US")
    Observable<TopRated> getTopRatedMoviesObservable(@Query("api_key") String api_key, @Query("page") int page);

}
