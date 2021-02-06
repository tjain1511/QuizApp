package com.indianapp.quizapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    ArrayList<History> historyArrayList;
    Context mContext;
    String date;
    String time;
    HashMap<String, String> categoryLink;
    public HistoryAdapter(Context mContext, ArrayList<History> historyArrayList, HashMap<String, String> categoryLink) {
        this.historyArrayList=historyArrayList;
        this.mContext = mContext;
        this.categoryLink=categoryLink;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_history_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        Log.i("recycler","current.getMcategory()");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History current=historyArrayList.get(position);
        date=current.mdate.substring(0,10);
        time=current.mdate.substring(10);
        holder.time.setText(time);
        holder.date.setText(date);
        holder.score.setText(current.getMscore());
        holder.category.setText(current.getMcategory());
        Glide.with(mContext).load(categoryLink.get(current.getMcategory())).into(holder.cathistimg);
        Log.i("recycler",current.getMcategory());
    }


    @Override
    public int getItemCount() {
        return historyArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView score;
        TextView category;
        TextView time;
        ImageView cathistimg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cathistimg=itemView.findViewById(R.id.cathistimg);
            date=itemView.findViewById(R.id.histDate);
            category=itemView.findViewById(R.id.histCat);
            score=itemView.findViewById(R.id.histScore);
            time=itemView.findViewById(R.id.time);



        }
    }
}
