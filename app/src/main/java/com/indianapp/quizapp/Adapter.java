package com.indianapp.quizapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<String> titles;
    LayoutInflater inflater;
    HashMap<String,String> categoryCode;
    Intent qIntent;
    List<String> url;
    Context ctx;

    public Adapter(Context ctx, List<String> titles,HashMap<String,String> categoryCode,List<String> url){
        this.titles = titles;
        this.categoryCode=categoryCode;
        this.inflater = LayoutInflater.from(ctx);
        this.ctx=ctx;
        this.url=url;
    }
    public void setCategoryCode(String code,String title,String link){
        qIntent = new Intent(ctx,QuizActivity.class);
        qIntent.putExtra("title",title);
        qIntent.putExtra("code", code);
        qIntent.putExtra("link",link);
        ctx.startActivity(qIntent);
        ((Activity)ctx).finish();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_grid_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title.setText(titles.get(position));
        //holder.gridIcon.setImageResource(images.get(position));
        Glide.with(ctx).load(url.get(position)).into(holder.gridIcon);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCategoryCode(categoryCode.get(titles.get(position)),titles.get(position),url.get(position));
            }
        });
//        holder.gridIcon.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ConstraintLayout constraintLayout;
        ImageView gridIcon;
        ConstraintLayout link;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.gridb);
            constraintLayout = itemView.findViewById(R.id.cons);
            gridIcon = itemView.findViewById(R.id.gridi);


        }
    }
}