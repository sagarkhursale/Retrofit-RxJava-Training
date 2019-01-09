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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TopMoviesRxActivity extends AppCompatActivity {

    private final String TAG = TopMoviesRxActivity.class.getSimpleName();
    private static final int PAGE_TWO = 2;

    private RecyclerView recyclerView;
    private MovieListAdapter mMovieListAdapter;

    private Disposable mDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_movies_rx);

        final String API_KEY = getString(R.string.api_key);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // Reactive call to MovieApiService

        MovieApiService movieApiService = ApiClient.getRetrofitClient().create(MovieApiService.class);

        Observable<TopRated> topRatedMoviesObservable = movieApiService.getTopRatedMoviesObservable(API_KEY, PAGE_TWO);

        Observer<String> topRatedMoviesObserver = getTopRatedMoviesObserver();


        topRatedMoviesObservable
                .flatMap(new Function<TopRated, ObservableSource<Result>>() {
                    @Override
                    public ObservableSource<Result> apply(TopRated topRated) {
                        return Observable.fromIterable(topRated.getResults());
                    }
                })
                .flatMap(new Function<Result, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Result result) {
                        return Observable.just(result.getTitle());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topRatedMoviesObserver);


        // end
    }


    private Observer<String> getTopRatedMoviesObserver() {
        final List<String> resultList = new ArrayList<>();


        return new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe");
                mDisposable = d;
            }


            @Override
            public void onNext(String s) {
                Log.i(TAG, "\n" + s);
                resultList.add(s);
            }


            @Override
            public void onError(Throwable e) {
                Log.i(TAG, e.getMessage());
                Toast.makeText(TopMoviesRxActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onComplete() {
                MovieListAdapter adapter = new MovieListAdapter(resultList);
                recyclerView.setAdapter(adapter);
                Log.i(TAG, "onComplete " + resultList.size());
            }
        };
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }


    // END
}
