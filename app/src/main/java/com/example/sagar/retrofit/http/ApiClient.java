package com.example.sagar.retrofit.http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit mRetrofit;


    public static Retrofit getRetrofitClient() {
        if (mRetrofit == null) {

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return mRetrofit;
    }

}
