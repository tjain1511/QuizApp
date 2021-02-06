package com.indianapp.quizapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryFrag extends Fragment {
    String token;
    ArrayList<History> historyArrayList;
    Integer length;
    String category;
    String date;
    String score;
    View rootView;
    HashMap<String, String> categoryLink ;
    TextView historyload;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.activity_history,container,false);
        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        historyload=getActivity().findViewById(R.id.historyload);
        categoryLink=new HashMap<>();
        categoryLink.put("General Knowledge","https://images.unsplash.com/photo-1574087988579-da02f0b90e37?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        categoryLink.put("Books","https://images.unsplash.com/photo-1532012197267-da84d127e765?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        categoryLink.put("Film","https://images.unsplash.com/photo-1538152911114-73f1aa6d6128?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=675&q=80");
        categoryLink.put("Music","https://images.unsplash.com/photo-1483000805330-4eaf0a0d82da?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=334&q=80");
        categoryLink.put("Theaters","https://images.unsplash.com/photo-1582152492243-a32086530356?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        categoryLink.put("Television","https://images.unsplash.com/photo-1576438112307-db9c736ff392?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        categoryLink.put("Video Games","https://images.unsplash.com/photo-1585944348450-35593b4c836d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        categoryLink.put("Board Games","https://images.unsplash.com/photo-1590047698876-b50e8739b346?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        categoryLink.put("Science & Nature","https://images.unsplash.com/photo-1568226127838-28d6c3a95636?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        categoryLink.put("Computers","https://images.unsplash.com/photo-1532529867795-3c83442c1e5c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        categoryLink.put("Mathematics","https://images.unsplash.com/photo-1528082992860-d520150d6f6c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        categoryLink.put("Mythology","https://images.unsplash.com/photo-1597141797079-5a633a88cf1b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        categoryLink.put("Sports","https://images.unsplash.com/photo-1480180566821-a7d525cdfc5e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        categoryLink.put("Geography","https://images.unsplash.com/photo-1569956726918-b36bd5e659b2?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        categoryLink.put("History","https://images.unsplash.com/photo-1577083553466-e749203bc748?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        categoryLink.put("Politics","https://images.unsplash.com/photo-1534293230397-c067fc201ab8?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        categoryLink.put("Art","https://images.unsplash.com/photo-1556850003-5adb86031c6b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        categoryLink.put("Celebrities","https://www.gstatic.com/tv/thumb/persons/67369/67369_v9_bb.jpg");
        categoryLink.put("Animals","https://images.unsplash.com/photo-1488793207478-ff0892419e30?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        categoryLink.put("Vehicles","https://images.unsplash.com/photo-1522780209919-d8d8b64e0e9e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");

        token = getDefaults("_id");
        Log.i("hi",token);
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://siddhant.run-ap-south1.goorm.io/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        UserInterface client = retrofit.create(UserInterface.class);
        Call<JsonArray> call =client.getHist(token);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.body()!=null) {

                    length = response.body().size();
                    Log.i("length", String.valueOf(length));
                    historyArrayList = new ArrayList<>();
                    for (int i = 0; i < length; i++) {
                        category=String.valueOf(response.body().get(i).getAsJsonObject().get("category"));
                        date= String.valueOf(response.body().get(i).getAsJsonObject().get("date"));
                        score = String.valueOf(response.body().get(i).getAsJsonObject().get("score"));
                        historyArrayList.add(new History(category.substring(1,category.length()-1),
                                score.substring(1,score.length()-1) ,
                                date.substring(1,date.length()-1)));
                    }
                    if (length > 0) {
                        historyload.setVisibility(View.INVISIBLE);
                        String hi = new String(historyArrayList.get(0).mcategory);
                        hi.replace("\"", "");
                        Log.i("mkbhd", hi.substring(1, hi.length() - 1));
                        Collections.reverse(historyArrayList);
                        initRecylerView();
                        for (int i = 0; i < length; i++) {
                            Log.i("category", String.valueOf(historyArrayList.get(i).getMcategory()));
                            Log.i("score", String.valueOf(historyArrayList.get(i).getMscore()));
                            Log.i("date", String.valueOf(historyArrayList.get(i).getMdate()));
                        }
                    }else {
                        historyload.setText("History not found");
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.i("failed", String.valueOf(t.getMessage()));
            }
        });
    }

    public String getDefaults(String key) {
        SharedPreferences preferences = getActivity().getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }
    private void initRecylerView(){
        RecyclerView recyclerView = rootView.findViewById(R.id.histRecycler);
        HistoryAdapter adapter= new HistoryAdapter(getActivity(),historyArrayList,categoryLink);
        recyclerView.setAdapter(adapter);
        Log.i("recycler","current");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.i("recycler","current");
    }


}
