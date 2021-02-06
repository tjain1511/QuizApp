package com.indianapp.quizapp;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    Intent intent;
    ArrayList<String> questions;
    ArrayList<String> answers;
    ArrayList<JsonArray> choices;
    ArrayList<String> tempoptions;
    ArrayList<String> tempoptions1;
    ArrayList<String> tempoptions2;
    ArrayList<String> tempoptions3;
    ArrayList<String> tempoptions4;
    ArrayList<ArrayList<String>> options ;
    public void setDefaults(String key, String value, Context context) {
       preferences = this.getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public void signup(View view){
        intent = new Intent(getApplicationContext(),signupActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
        finish();

    }
    public void login(View view){
        intent = new Intent(getApplicationContext(),Loginactivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
        finish();
    }
    public void setDefInt(String key, Integer value){
        preferences = this.getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public Integer getDefInt(String key) {
        SharedPreferences preferences = this.getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }
    public String getDefaults(String key) {
        SharedPreferences preferences = this.getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        return preferences.getString(key, "null");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        Intent intent = new Intent(getApplicationContext(), Home.class);
//        startActivity(intent);
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://opentdb.com").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        UserInterface client = retrofit.create(UserInterface.class);
        Call<JsonObject> responseBodyCall = client.getques("5","25","multiple");
        responseBodyCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("opentd", String.valueOf(response.isSuccessful()));
                Log.i("opentd", String.valueOf(response.body().get("results").getAsJsonArray().get(1).getAsJsonObject().get("question")));
                Log.i("opentd", String.valueOf(response.body().get("results").getAsJsonArray()));
                questions=new ArrayList<>();
                answers = new ArrayList<>();
                choices = new ArrayList<JsonArray>();
                options = new ArrayList<>();
                tempoptions = new ArrayList<>();
                tempoptions1 = new ArrayList<>();
                tempoptions2= new ArrayList<>();
                tempoptions3 = new ArrayList<>();
                tempoptions4= new ArrayList<>();
                for(int i =0 ; i < 5; i++) {
                    questions.add(String.valueOf(response.body().get("results").getAsJsonArray().get(i).getAsJsonObject().get("question")));
                    answers.add(String.valueOf(response.body().get("results").getAsJsonArray().get(i).getAsJsonObject().get("correct_answer")));
                    choices.add(response.body().get("results").getAsJsonArray().get(i).getAsJsonObject().get("incorrect_answers").getAsJsonArray());

                }

                options.add(tempoptions);
                options.add(tempoptions1);
                options.add(tempoptions2);
                options.add(tempoptions3);
                options.add(tempoptions4);

                for(int i =0 ; i<5;i++) {
                    Random random1 = new Random();
                    int randomNumber1 = random1.nextInt(4);
                    for(int j =0 ; j<3; j++){
                        options.get(i).add(String.valueOf(choices.get(i).get(j)));
                    }
                    options.get(i).add(randomNumber1, answers.get(i));

                    Log.i("sol", String.valueOf(options));
                    Log.i("question "+String.valueOf(i), String.valueOf(questions.get(i)));
                    Log.i("answer "+String.valueOf(i), String.valueOf(answers.get(i)));
                }
                Log.i("questions", String.valueOf(questions));
                Log.i("options", String.valueOf(options));
                Log.i("answers", String.valueOf(answers));

            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i("opentd",t.getMessage());

            }
        });
        preferences = this.getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
//        preferences.edit().putString("_id", "tush").apply();
        if(preferences.getString("_id","null").equals("null")){
            Log.i("sharedmain",preferences.getString("_id","null"));
        }else{
            if(!getDefaults("name").equals("null") && !getDefaults("username").equals("null") && getDefInt("avatarCode")!=0){
                Log.i("Avatar code","null h");
                Log.i("sharedmain", preferences.getString("_id", "null"));
                intent = new Intent(getApplicationContext(), BottomNavActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }else {
                Log.i("sharedmain", preferences.getString("_id", ""));
                intent = new Intent(getApplicationContext(), AvatarActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        }
        //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZjczNDhhZDExNzhmNjA0M2EwNWU4YjUiLCJpYXQiOjE2MDEzOTY5MzR9.U89k5kcdzfODGqBbZ9zO6HTPNJk_s3ZxIMG6E3idNIg

    }
}