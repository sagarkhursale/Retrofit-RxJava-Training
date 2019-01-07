package com.example.sagar.retrofit.http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit mRetrofitClient;


    public static Retrofit getRetrofitClient() {
        if (mRetrofitClient == null) {

            mRetrofitClient = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return mRetrofitClient;
    }

}
