package com.example.sagar.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class Main2Activity extends AppCompatActivity {

    private final String TAG = Main2Activity.class.getSimpleName();

    private TextView textView_Demo;

    private Disposable mDisposable;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private final StringBuilder builder = new StringBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView_Demo = findViewById(R.id.tv_demo);


        // 1)
        Observable<String> carObservable = Observable.just("Audi", "Mercedes", "BMW", "Honda", "Toyota");

        Observer<String> carObserver = getCarObserver();

        carObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(carObserver);


        // 2)
        Observable<String> animalObservable = getAnimalsObservable();

        DisposableObserver<String> animalObserver = getAnimalObserver();

        DisposableObserver<String> animalObserverAllCaps = getAnimalObserverAllCaps();


        mCompositeDisposable.add(
                animalObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) {
                                return s.toLowerCase().startsWith("b");
                            }
                        })
                        .subscribeWith(animalObserver));


        mCompositeDisposable.add(
                animalObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) {
                                return s.toLowerCase().startsWith("c");
                            }
                        })
                        .map(new Function<String, String>() {
                            @Override
                            public String apply(String s) {
                                return s.toUpperCase();
                            }
                        })
                        .subscribeWith(animalObserverAllCaps)

        );

        // end
    }


    private Observable<String> getAnimalsObservable() {
        return Observable.fromArray(
                "Ant", "Ape",
                "Bat", "Bee", "Bear", "ButterFly",
                "Cat", "Crab", "Cod",
                "Dog", "Dove",
                "Fox", "Frog"
        );
    }


    private DisposableObserver<String> getAnimalObserver() {
        return new DisposableObserver<String>() {

            @Override
            public void onNext(String s) {
                String str = s + "\t";
                builder.append(str);
                Log.i(TAG, s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "1. " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete()");
                builder.append("\n\n");
                textView_Demo.setText(builder.toString());
            }
        };
    }


    private DisposableObserver<String> getAnimalObserverAllCaps() {
        return new DisposableObserver<String>() {

            @Override
            public void onNext(String s) {
                String str = s + "\t";
                builder.append(str);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "2. " + e.getMessage());
            }

            @Override
            public void onComplete() {
                builder.append("\n\n");
                textView_Demo.setText(builder.toString());
            }
        };
    }


    private Observer<String> getCarObserver() {

        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(String s) {
                String str = s + "\t";
                builder.append(str);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "3. " + e.getMessage());
            }

            @Override
            public void onComplete() {
                builder.append("\n\n");
                textView_Demo.setText(builder.toString());
            }
        };

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
        mCompositeDisposable.clear();
    }

    // END
}
