package com.example.sagar.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.sagar.retrofit.apimodel.Result;
import com.example.sagar.retrofit.apimodel.TopRated;
import com.example.sagar.retrofit.http.ApiClient;
import com.example.sagar.retrofit.http.MovieApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.RecyclerView.*;


public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private static String API_KEY;
    private static final int PAGE_ONE = 1;

    MovieListAdapter mMovieListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API_KEY = getString(R.string.api_key);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // Call to MovieApiService

        MovieApiService movieApiService = ApiClient.getRetrofitClient().create(MovieApiService.class);

        Call<TopRated> topRatedMovies = movieApiService.getTopRatedMovies(API_KEY, PAGE_ONE);

        topRatedMovies.enqueue(new Callback<TopRated>() {
            @Override
            public void onResponse(Call<TopRated> call, Response<TopRated> response) {
                List<Result> resultList = response.body().getResults();

                mMovieListAdapter = new MovieListAdapter(resultList);
                recyclerView.setAdapter(mMovieListAdapter);

                for (int i = 1; i < resultList.size(); i++)
                    Log.i(TAG, "\n" + i + ")\t" + resultList.get(i).getTitle());
            }

            @Override
            public void onFailure(Call<TopRated> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });


    }


    // END
}
