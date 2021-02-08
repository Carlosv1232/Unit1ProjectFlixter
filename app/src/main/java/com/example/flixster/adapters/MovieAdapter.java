package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.Models.Movie;
import com.example.flixster.R;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    //the base RecyclerView.Adapter is a class that needs methods in order for them to work
    //we need, onCreateViewHolder, onBindViewHolder, getItemCount to make it work.
    //it will also need key pieces of data

    Context context; //we could inflate a view to use
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) { //constructor to populate the data
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //we will inflate a layout (item_movie), and return inside of a ViewHolder
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        Log.d("MovieAdapter", "onCreateViewHolder");

        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //involves populating data into the item through holder
        //we have a 'position' in data, and we want to put that data into the ViewHolder
        //get the movie at the position
        Movie movie = movies.get(position);
        //bind the movie data into the ViewHolder
        holder.bind(movie);

        Log.d("MovieAdapter", "onBindViewHolder" + position);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) { //we should always first define the viewholder before defining the adapter

            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            container = itemView.findViewById(R.id.container);

        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            //IF(Phone is Landscape)
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            //then imageUrl = backdrop image
                imageUrl = movie.getBackdropPath();
                Log.d("Image", imageUrl + " backdrop");

            } else {
            //ELSE imageUel = poster image
                imageUrl = movie.getPosterPath();
                Log.d("Image", imageUrl + " poster Path");
            }

            //ELSE imageUel = poster image

            //progressBar.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(imageUrl)
                    .into(ivPoster);

            //we want to register the click listener on the whole row
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //action taken when we click, to a new activity
                    Intent i = new Intent(context, DetailActivity.class);
                    //i.putExtra("tvTitle", movie.getTitle());
                    //i.putExtra("tvOverview", movie.getOverview());
                    //i.putExtra("ivPoster", ivPoster);

                    i.putExtra("movie", Parcels.wrap(movie));
                    //we have to wrap the object, and unwrap on the other side
                    context.startActivity(i);

                }
            });





        }
    }
}
