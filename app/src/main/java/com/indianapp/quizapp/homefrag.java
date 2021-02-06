package com.indianapp.quizapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import pl.pawelkleczkowski.customgauge.CustomGauge;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class homefrag extends Fragment {
    CustomGauge gauge;
    String token;
    Integer score;
    Integer percent;
    TextView percentage;
    List<String> categories;
    List<String> categoriesq;
    ArrayList<String> titles;
    ArrayList<Integer> count;
    String category;
    ImageView homeAvatar;
    TextView homeName;
    TextView homeUsername;
    TextView homeTotalScore;
    TextView homeRank;
    TextView homeGamesPlayed;
    Integer length;
    Integer rank;
    Integer userRank;
    ImageView button2;
    MeowBottomNavigation meo;
    ArrayList<String> quotes;
    SharedPreferences preferences;
    TextView quoteLine;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_home,container,false);
    }
    public void setDefaults(String key, String value) {
        preferences = getActivity().getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public Integer getDefInt(String key) {
        SharedPreferences preferences = getActivity().getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }
    public void setDefInt(String key, Integer value){
        preferences = getActivity().getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    @Override
    public void onStart() {
        super.onStart();
        quotes=new ArrayList<>();
        quotes.add("Be yourself\n everyone else is already taken.");
        quotes.add("A room without books is like a body without a soul.");
        quotes.add("Be the change that you wish to see in the world.");
        quotes.add("If you tell the truth, you don't have to remember anything.");
        quotes.add("If you want to know what a man's like, take a good look at how he treats his inferiors, not his equals.");
        quotes.add("To live is the rarest thing in the world. Most people exist, that is all.");
        quotes.add("Without music, life would be a mistake.");
        quotes.add("Always forgive your enemies, nothing annoys them so much.");
        quotes.add("Life isn't about getting and having, it's about giving and being.");
        quotes.add("Whatever the mind of man can conceive and believe, it can achieve.");
        quotes.add("Whatever the mind of man can conceive and believe, it can achieve.");
        quotes.add("Strive not to be a success, but rather to be of value.");
        quotes.add("The secret of success is to know something nobody else knows.");
        quotes.add("People say nothing is impossible, but I do nothing every day.");
        quotes.add("Change is not a four letter word… but often your reaction to it is!");

        score=0;
        gauge=getActivity().findViewById(R.id.gauge2);
        percentage=getActivity().findViewById(R.id.percentage);
        gauge.setValue(0);

        token = getDefaults("_id");
        button2=getActivity().findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meo=getActivity().findViewById(R.id.bottom_nav);
                meo.show(3,true);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,new catfrag()).commit();
            }
        });
        quoteLine=getActivity().findViewById(R.id.quoteLine);
        Random random1 = new Random();
        int indx = random1.nextInt(14);
        quoteLine.setText("\""+quotes.get(indx)+"\"");
        homeUsername = getActivity().findViewById(R.id.homeUsername);
        homeName = getActivity().findViewById(R.id.homeName);
        homeAvatar = getActivity().findViewById(R.id.homeAvatar);
        homeName.setText(getDefaults("name"));
        homeUsername.setText("@" + getDefaults("username"));
        if(getDefInt("avatarCode")!=0) {
            switch (getDefInt("avatarCode")) {
                case 1:
                    homeAvatar.setImageResource(R.drawable.avatar1);
                    break;
                case 2:
                    homeAvatar.setImageResource(R.drawable.avatar2);
                    break;
                case 3:
                    homeAvatar.setImageResource(R.drawable.ladka);
                    break;
                case 4:
                    homeAvatar.setImageResource(R.drawable.avatar4);
                    break;
                case 5:
                    homeAvatar.setImageResource(R.drawable.avatar5);
                    break;
                case 6:
                    homeAvatar.setImageResource(R.drawable.avatar6);
                    break;
                case 7:
                    homeAvatar.setImageResource(R.drawable.avatar7);
                    break;
                case 8:
                    homeAvatar.setImageResource(R.drawable.avatar8);
                    break;
            }
        }
        homeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage("Are you sure want to log out?").setTitle("Logout").setIcon(R.drawable.ic_baseline_login_24)
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener()                 {

                            public void onClick(DialogInterface dialog, int which) {

                                setDefaults("_id", "null");
                                setDefaults("name", "null");
                                setDefaults("username", "null");
                                setDefInt("avatarCode",0);
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                getActivity().overridePendingTransition(0, 0);
                                getActivity().finish();

                            }
                        }).setNegativeButton("Cancel",null);


                AlertDialog alert1 = alert.create();
                alert1.show();


            }
        });
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://siddhant.run-ap-south1.goorm.io/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        UserInterface client = retrofit.create(UserInterface.class);
//        Call<JsonArray> call =client.getHist(token);
//        call.enqueue(new Callback<JsonArray>() {
//            @Override
//            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
//                Log.i("user", String.valueOf(response.body()));
//                if (response.body()!=null) {
//                    categories=new ArrayList<String>();
//                    Log.i("length", String.valueOf(response.body().size()));
//                    for (int i = 0; i < response.body().size(); i++) {
//                        category=String.valueOf(response.body().get(i).getAsJsonObject().get("category"));
//                        categories.add(category.substring(1,category.length()-1));
//                        score += response.body().get(i).getAsJsonObject().get("score").getAsInt();
//                    }
//                    count=new ArrayList<>();
//                    titles=new ArrayList<>();
//                    titles.add("General Knowledge");
//                    titles.add("Books");
//                    titles.add("Film");
//                    titles.add("Music");
//                    titles.add("Theaters");
//                    titles.add("Television");
//                    titles.add("Video Games");
//                    titles.add("Board Games");
//                    titles.add("Science & Nature");
//                    titles.add("Computers");
//                    titles.add("Mathematics");
//                    titles.add("Mythology");
//                    titles.add("Sports");
//                    titles.add("Geography");
//                    titles.add("History");
//                    titles.add("Politics");
//                    titles.add("Art");
//                    titles.add("Celebrities");
//                    titles.add("Animals");
//                    titles.add("Vehicles");
//                    for(String cat:titles){
//                        Log.i("titles",cat);
//                        count.add(Collections.frequency(categories, cat));
//                    }
//                    if(count.get(getIndexOfLargest(count))!=0) {
//                        Log.i("interest", titles.get(getIndexOfLargest(count)));
//                    }else{
//                        Log.i("interest", "None");
//                    }
//                    Log.i("cat", String.valueOf(categories));
//                    Log.i("cat", String.valueOf(count));
//                    Log.i("len", String.valueOf(score));
//                    percent=(score*20);//response.body().size();
//                    Log.i("len", String.valueOf(percent));
//                    percentage.setText(String.valueOf(percent)+"%");
//                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
//                    percentage.startAnimation(animation);
//                    gauge.setValue(89);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JsonArray> call, Throwable t) {
//
//            }
//        });


        Call<JsonObject> call =client.getde(token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    Log.i("user", String.valueOf(response.body().get("name")));

                    homeRank = getActivity().findViewById(R.id.homeRank);
                    homeTotalScore = getActivity().findViewById(R.id.homeTotalScore);
                    homeGamesPlayed = getActivity().findViewById(R.id.homeGamesPlayed);

                    homeTotalScore.setText(String.valueOf(response.body().get("totalScore").toString()));
                    homeGamesPlayed.setText(String.valueOf(response.body().getAsJsonArray("games").size()));
                    getUserRank(String.valueOf(response.body().get("username").toString()), homeRank);
                    categories = new ArrayList<String>();
                    Log.i("length", String.valueOf(response.body().getAsJsonArray("games").size()));
                    for (int i = 0; i < response.body().getAsJsonArray("games").size(); i++) {
                        category = String.valueOf(response.body().getAsJsonArray("games").get(i).getAsJsonObject().get("category"));
                        categories.add(category.substring(1, category.length() - 1));
                        score += response.body().getAsJsonArray("games").get(i).getAsJsonObject().get("score").getAsInt();
                    }
                    //might change
                    if(response.body().getAsJsonArray("games").size()==0){
                        percent=0;
                    }else {
                        percent = (response.body().get("totalScore").getAsInt() * 20) / response.body().getAsJsonArray("games").size();
                    }

                    gauge.setValue(percent);
                    percentage.setText(String.valueOf(percent) + "%");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    public String getDefaults(String key) {
        SharedPreferences preferences = getActivity().getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        return preferences.getString(key, "null");
    }
    public int getIndexOfLargest( ArrayList<Integer> array )
    {
        if ( array == null || array.size() == 0 ) return -1; // null or empty

        int largest = 0;
        for ( int i = 1; i < array.size(); i++ )
        {
            if ( array.get(i) > array.get(largest) ){
            largest = i;
        }
        }
        return largest; // position of the first largest found
    }
    public void getUserRank(final String name, final TextView userRankt){
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://siddhant.run-ap-south1.goorm.io/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        UserInterface client = retrofit.create(UserInterface.class);
        Call<JsonArray> call1 =client.getLead();
        call1.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.body() != null) {
                    Log.i("leader", String.valueOf(response.body()));
                    length = response.body().size();

                    for (int i = 0; i < length; i++) {
                        rank = i + 1;
                        if (String.valueOf(response.body().get(i).getAsJsonObject().get("username")).equals(name)) {
                            userRank = rank;
                            break;
                        }
                    }
                    userRankt.setText(String.valueOf(userRank));
                    Log.i("rank", String.valueOf(userRank));
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.i("failed", String.valueOf(t.getMessage()));
            }
        });
    }
}
