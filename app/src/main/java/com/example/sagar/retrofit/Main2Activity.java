package com.example.sagar.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class Main2Activity extends AppCompatActivity {
    private final String TAG = Main2Activity.class.getSimpleName();
    private TextView textView_Demo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView_Demo = findViewById(R.id.tv_demo);


        Observable<String> animalObservable = Observable.just("Ant", "Tiger", "Lion", "Elephant", "Deer");


        Observer<String> animalObserver = getAnimalObserver();

    }


    Observer<String> getAnimalObserver() {

        return new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe()");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete()");
            }
        };
    }


    // END
}
