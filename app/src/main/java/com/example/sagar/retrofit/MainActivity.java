package com.example.sagar.retrofit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    private RecyclerView recyclerView;

    private MovieListAdapter mMovieListAdapter;
    private Call<TopRated> topRatedMovies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API_KEY = getString(R.string.api_key);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // Call to MovieApiService
        MovieApiService movieApiService = ApiClient.getRetrofitClient().create(MovieApiService.class);

        topRatedMovies = movieApiService.getTopRatedMovies(API_KEY, PAGE_ONE);

        //resultCallback();
    }


    private void resultCallback() {
        topRatedMovies.enqueue(new Callback<TopRated>() {
            @Override
            public void onResponse(@NonNull Call<TopRated> call, @NonNull Response<TopRated> response) {
                assert response.body() != null;
                List<Result> resultList = response.body().getResults();

                mMovieListAdapter = new MovieListAdapter(resultList);
                recyclerView.setAdapter(mMovieListAdapter);

                for (int i = 1; i < resultList.size(); i++)
                    Log.i(TAG, "\n" + i + ")\t" + resultList.get(i).getTitle());
            }

            @Override
            public void onFailure(@NonNull Call<TopRated> call, @NonNull Throwable t) {
                Log.i(TAG, t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.gotoNext) {

            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }


    // END
}
