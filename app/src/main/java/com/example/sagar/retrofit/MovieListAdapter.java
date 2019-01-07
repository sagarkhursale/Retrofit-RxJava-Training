package com.example.sagar.retrofit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sagar.retrofit.apimodel.Result;

import java.util.List;


public class MovieListAdapter extends
        RecyclerView.Adapter<MovieListAdapter.MyViewHolder> {

    private List<Result> mMovieList;


    public MovieListAdapter(List<Result> mMovieList) {
        this.mMovieList = mMovieList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.tvMovieTitle.setText(mMovieList.get(position).getTitle());
    }


    @Override
    public int getItemCount() {
        return mMovieList.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMovieTitle;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMovieTitle = itemView.findViewById(R.id.tv_movie_name);
        }
    }


    // END
}
