package com.indianapp.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AvatarCode extends AppCompatActivity {
    SharedPreferences preferences;
    String auth_token;
    String name;
    String username;
    Integer avatarCode;
    public void setDefaults(String key, String value) {
        preferences = this.getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public void setDefInt(String key, Integer value){
        preferences = this.getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public String getDefaults(String key) {
        SharedPreferences preferences = this.getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        return preferences.getString(key, "null");
    }
    public Integer getDefInt(String key) {
        SharedPreferences preferences = this.getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_code);
        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(!getDefaults("name").equals("null") && !getDefaults("username").equals("null") && getDefInt("avatarCode")!=0){
            Intent intent = new Intent(getApplicationContext(),BottomNavActivity.class);
            startActivity(intent);
            overridePendingTransition(0,0);
            finish();
        }else {
            getdet();
        }
    }
    public void getdet(){
        auth_token=getDefaults("_id");
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://siddhant.run-ap-south1.goorm.io/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Log.i("avatar",auth_token);
        final UserInterface client = retrofit.create(UserInterface.class);
        Call<JsonObject> callde =client.getde(auth_token);//map
        callde.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body()!=null){
                    name=String.valueOf(response.body().get("name").toString().substring(1, 2)) + String.valueOf(response.body().get("name").toString()).substring(2, String.valueOf(response.body().get("name")).length() - 1);
                    username=String.valueOf(response.body().get("username")).substring(1, String.valueOf(response.body().get("username")).length() - 1);
                    avatarCode=Integer.parseInt(String.valueOf(response.body().get("avatarCode")));
                    setDefaults("name",name);
                    setDefaults("username",username);
                    setDefInt("avatarCode",avatarCode);
                    Log.i("name",getDefaults("name"));
                    Log.i("username",getDefaults("username"));
                    Log.i("avatar", String.valueOf(getDefInt("avatarCode")));
                    if(!getDefaults("name").equals("null") && !getDefaults("username").equals("null") && getDefInt("avatarCode")!=0){
                        Intent intent = new Intent(getApplicationContext(),BottomNavActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}