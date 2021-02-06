package com.indianapp.quizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class signupActivity extends AppCompatActivity {
    SharedPreferences preferences;
    EditText name;
    EditText username;
    EditText password;
    private Retrofit retrofit;
    private UserInterface userInterface;
    Button sign;
    String token;
    EditText name1;


        public void setDefaults(String key, String value, Context context) {
        preferences = this.getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getDefaults(String key, Context context) {
        SharedPreferences preferences = this.getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        return preferences.getString(key, "null");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setDefaults("usename","sid",this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Log.i("sd","ds");
        Log.i("hi",getDefaults("username",this));
       // Log.i("question",getIntent().getStringExtra("question"));
//        retrofit = new Retrofit.Builder()
//                .baseUrl("https://siddhant.run-ap-south1.goorm.io/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        userInterface = retrofit.create(UserInterface.class);
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://siddhant.run-ap-south1.goorm.io/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        final UserInterface client = retrofit.create(UserInterface.class);
        sign = findViewById(R.id.sign);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = findViewById(R.id.name);
                username = findViewById(R.id.username);
                password=findViewById(R.id.password);
                name1=findViewById(R.id.name1);
                HashMap<String,String> map = new HashMap<>();
//                  map.put("upload","image");
                map.put("name",String.valueOf(name1.getText()));
                map.put("email", String.valueOf(name.getText()));
                map.put("username", String.valueOf(username.getText()));
                map.put("password", String.valueOf(password.getText()));
//
                Call<ResponseBody> call =client.executeSignup(map);//,map
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        token=String.valueOf(response.headers().get("auth-token"));
                        Log.i("message", String.valueOf(response.message()));
                        Log.i("message", String.valueOf(response.isSuccessful()));
                        Log.i("message", String.valueOf(response.code()));
                        Log.i("shared",token);
                        setDefaults("_id",token,signupActivity.this);
                        Log.i("shared", getDefaults("_id", signupActivity.this));
                        if(response.code() == 200){
                           // Toast.makeText(signupActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),AvatarActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(signupActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("messafge",t.getMessage());
                        Log.i("messafge","t.getMessage()");
                    }
                });

//
            }
        });



    }
//    public void upload(View view){
//        getPhoto();
//    }
//    public void getPhoto(){
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent,1);
//    }
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Uri selectImage = data.getData();
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
//            Bitmap bitmap = null;
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectImage);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
//            byte[] byteArray = stream.toByteArray();
//            File originalfile = new File(FIleUtils.getPath(this, selectImage));
//            RequestBody filePart = RequestBody.create(MediaType.parse(getContentResolver().getType(selectImage)), byteArray);
//            Log.i("path",originalfile.getName());
//            final MultipartBody.Part file = MultipartBody.Part.createFormData("photo",originalfile.getName(), filePart);
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
//
//        }
//    }
@Override
public void onBackPressed() {
    super.onBackPressed();
    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
    startActivity(intent);
    overridePendingTransition(R.anim.left_to_right1,R.anim.righ_to_left1);
    finish();
}
public void logs(View view){
    Intent intent = new Intent(getApplicationContext(),Loginactivity.class);
    startActivity(intent);
    overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
    finish();
}
public void back(View view){
    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
    startActivity(intent);
    overridePendingTransition(R.anim.left_to_right1,R.anim.righ_to_left1);
    finish();
}
}