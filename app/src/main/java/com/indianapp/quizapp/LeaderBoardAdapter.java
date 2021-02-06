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

import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {
    ArrayList<LeaderBoardItem> leaderBoardItems;
    Context mContext;
    public LeaderBoardAdapter(Context mContext,ArrayList<LeaderBoardItem> leaderBoardItems){
        this.mContext=mContext;
        this.leaderBoardItems=leaderBoardItems;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_leader_layout,parent,false);
        LeaderBoardAdapter.ViewHolder holder = new LeaderBoardAdapter.ViewHolder(view);
        Log.i("recycler","current.getMcategory()");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaderBoardItem current=leaderBoardItems.get(position);
        holder.rank.setText(String.valueOf(current.getMrank()));
        if(current.getMrank()==1){
            holder.trophy.setImageResource(R.drawable.trophy1);
        }else if(current.getMrank()==2){
            holder.trophy.setImageResource(R.drawable.trophy2);
        }else if(current.getMrank()==3){
            holder.trophy.setImageResource(R.drawable.trophy3);
        }else {
            holder.trophy.setImageResource(0);
        }
        holder.name.setText(current.getMname());
        holder.games.setText(current.getMtotalGamesPayed());
        holder.score.setText(current.getMtotalScore());
    }

    @Override
    public int getItemCount() {
        Log.i("leaderBoardItems", String.valueOf(leaderBoardItems.size()));
        return leaderBoardItems.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rank;
        TextView score;
        TextView name;
        TextView games;
        ImageView trophy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trophy=itemView.findViewById(R.id.trophy);
            rank=itemView.findViewById(R.id.leadRank);
            score=itemView.findViewById(R.id.leadScore);
            name=itemView.findViewById(R.id.leadName);
            games=itemView.findViewById(R.id.leadGame);
        }
    }
}
