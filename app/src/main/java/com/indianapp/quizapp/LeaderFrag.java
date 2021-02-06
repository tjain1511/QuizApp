package com.indianapp.quizapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LeaderFrag extends Fragment {
    Integer mrank;
    String mtotalScore;
    String mname;
    String mtotalGamesPayed;
    Integer length;
    ArrayList<LeaderBoardItem> leaderBoardActivityArrayList;
    View rootView;
    TextView rank;
    TextView score;
    TextView username;
    TextView lloading;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.activity_leader_board,container,false);
        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        rank=getActivity().findViewById(R.id.rankleader);
        score=getActivity().findViewById(R.id.scoreleader);
        username=getActivity().findViewById(R.id.usernameleader);
        lloading=getActivity().findViewById(R.id.loading);
        rank.setVisibility(View.INVISIBLE);
        score.setVisibility(View.INVISIBLE);
        username.setVisibility(View.INVISIBLE);
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://siddhant.run-ap-south1.goorm.io/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        UserInterface client = retrofit.create(UserInterface.class);
        Call<JsonArray> call1 =client.getLead();
        call1.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.body() != null) {
                    rank.setVisibility(View.VISIBLE);
                    score.setVisibility(View.VISIBLE);
                    username.setVisibility(View.VISIBLE);
                    lloading.setVisibility(View.INVISIBLE);
                    Log.i("leader", String.valueOf(response.body()));
                    length = response.body().size();
                    leaderBoardActivityArrayList = new ArrayList<>();
                    for (int i = 0; i < length; i++) {

                        mrank = i + 1;
                        mtotalScore = response.body().get(i).getAsJsonObject().get("totalScore").toString();
                        mname = String.valueOf(response.body().get(i).getAsJsonObject().get("username"));
                        mtotalGamesPayed = String.valueOf(response.body().get(i).getAsJsonObject().getAsJsonArray("games").size());
                        leaderBoardActivityArrayList.add(new LeaderBoardItem(mrank, mtotalScore, mname.substring(1, mname.length() - 1), mtotalGamesPayed));
                    }
                    for (int i = 0; i < length; i++) {
                        Log.i("rank", String.valueOf(leaderBoardActivityArrayList.get(i).getMrank()));
                        Log.i("name", String.valueOf(leaderBoardActivityArrayList.get(i).getMname()));
                        Log.i("Total score", String.valueOf(leaderBoardActivityArrayList.get(i).getMtotalScore()));
                        Log.i("games Played", String.valueOf(leaderBoardActivityArrayList.get(i).getMtotalGamesPayed()));
                    }
                    initRecylerView();

                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.i("failed", String.valueOf(t.getMessage()));
            }
        });
    }
    private void initRecylerView(){
        RecyclerView recyclerView = rootView.findViewById(R.id.leaderRecycler);
        LeaderBoardAdapter adapter= new LeaderBoardAdapter(getActivity(),leaderBoardActivityArrayList);
        recyclerView.setAdapter(adapter);
        Log.i("recycler","current");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.i("recycler","current");
    }
}
