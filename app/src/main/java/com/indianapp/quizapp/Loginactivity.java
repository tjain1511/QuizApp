package com.indianapp.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.HashMap;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Loginactivity extends AppCompatActivity {
    EditText lusername;
    EditText lpassword;
    private Retrofit rt;
    private UserInterface ui;
    SharedPreferences pf;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        rt = new Retrofit.Builder()
                .baseUrl("https://siddhant.run-ap-south1.goorm.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ui = rt.create(UserInterface.class);
    }
    public void signup(View view){
        Intent intent = new Intent(getApplicationContext(),signupActivity.class);
        setDefaults("username","tush",this);
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
        finish();

    }
    public void back(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        setDefaults("username","tush",this);
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right1,R.anim.righ_to_left1);
        finish();
    }
    public void setDefaults(String key, String value, Context context) {
        pf = this.getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pf.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public String getDefaults(String key, Context context) {
        SharedPreferences preferences = this.getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        return preferences.getString(key, "null");
    }
    public void log(View view){
        lusername = findViewById(R.id.lusername);
        lpassword=findViewById(R.id.lpassword);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("username", String.valueOf(lusername.getText()));
        hashMap.put("password", String.valueOf(lpassword.getText()));
//        final ImageView imageView = findViewById(R.id.imageView);
//        Call<ResponseBody> callimg = ui.getfile();
//        callimg.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.i("img get", String.valueOf(response.isSuccessful()));
//                Log.i("img get", String.valueOf(response.message()));
//                Log.i("img get body ", String.valueOf(response.body()));
//                Log.i("img get body ", String.valueOf(response.body().byteStream()));
//
//                Bitmap bitmap1 = BitmapFactory.decodeStream(response.body().byteStream());
//                imageView.setImageBitmap(bitmap1);
//                Log.i("imageScene", String.valueOf(bitmap1));
//                //intent.putExtra("Bitmap",bitmap1);
//                startActivity(intent);
//                //finish();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
        Call<ResponseBody> call =ui.executeLogin(hashMap);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("messagel", String.valueOf(response));



                if(response.code()==200) {
                    token = String.valueOf(response.headers().get("Auth-token"));
                    //Bitmap bitmap1 = BitmapFactory.decodeStream(response.body().byteStream());

                    //Toast.makeText(Loginactivity.this, "Success", Toast.LENGTH_SHORT).show();
                    setDefaults("_id",token,Loginactivity.this);
                    Log.i("shared", getDefaults("_id", Loginactivity.this));
                    if(response.code()==200){
                        Intent intent = new Intent(getApplicationContext(),AvatarCode.class);
                        startActivity(intent);
                        finish();
                    }

                    //intent.putExtra("Bitmap",bitmap);
//                    startActivity(intent);
//                   finish();

                }else if(response.code()==404){
                    Toast.makeText(Loginactivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                    setDefaults("_id","String.valueOf(response.headers().get())",Loginactivity.this);
//                    Log.i("shared", getDefaults("_id", Loginactivity.this));
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Loginactivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_to_right1,R.anim.righ_to_left1);
        finish();
    }
}