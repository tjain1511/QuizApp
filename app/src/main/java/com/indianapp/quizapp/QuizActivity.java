package com.indianapp.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anton46.stepsview.StepsView;
import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Parameter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import okhttp3.ResponseBody;
import pl.pawelkleczkowski.customgauge.CustomGauge;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizActivity extends AppCompatActivity {

CustomGauge gauge;
TextView seconds;
TextView scoreText;
TextView s_sign;
CountDownTimer countDownTimer;
    ArrayList<String> questions;
    ArrayList<String> answers;
    ArrayList<JsonArray> choices;
    ArrayList<String> tempoptions;
    ArrayList<String> tempoptions1;
    ArrayList<String> tempoptions2;
    ArrayList<String> tempoptions3;
    ArrayList<String> tempoptions4;
    ArrayList<ArrayList<String>> options ;
    Button op1;
    Button op2;
    Button op3;
    Button op4;
    TextView questiontext;
    TextView score;
    ProgressBar progressBar;
    int increment = 0;
    String title;
    Boolean forTimer;
    Handler handler1;
    String questionsString;
    String answerString;
    String optionsString;
    TextView textView;
    ConstraintLayout circ;
    int counter=60;
    TextView counttime;
    ConstraintLayout link;
    int currentstate=0;
    StepsView stepsView;
    String[] labels={"","","","",""};
    WindowManager windowManager2;
    WindowManager.LayoutParams params;
    String auth_token;
    TextView category;

    public String getDefaults(String key) {
        SharedPreferences preferences = this.getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public void checkinAndIncreasing(final Button opt){
        Animation animFadeIn1= AnimationUtils.loadAnimation(this,R.anim.fade_out);
        final Animation animFadeIn= AnimationUtils.loadAnimation(this,R.anim.in);
        animFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                op1.setClickable(true);
                op2.setClickable(true);
                op3.setClickable(true);
                op4.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animFadeIn1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                op1.setClickable(false);
                op2.setClickable(false);
                op3.setClickable(false);
                op4.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(increment<=4){
                    score=findViewById(R.id.score);
                    if(opt.getText().toString().equals(answers.get(increment))){

                        score.setText(String.valueOf(Integer.parseInt(score.getText().toString())+1));
                        Log.i("score", String.valueOf(Integer.parseInt(score.getText().toString())+1));
                    }else{
                        score.setText(String.valueOf(Integer.parseInt(score.getText().toString())));

                    }
                    increment = increment+1;
                    stepsView=findViewById(R.id.spb);
                    if(increment<5) {
                        stepsView.setCompletedPosition(increment).drawView();
                    }
                    if(increment<=4){
                        questiontext.startAnimation(animFadeIn);
                        op1.startAnimation(animFadeIn);
                        op2.startAnimation(animFadeIn);
                        op3.startAnimation(animFadeIn);
                        op4.startAnimation(animFadeIn);
                        questiontext.setText(String.valueOf(questions.get(increment)));
                        op1.setText(options.get(increment).get(0));
                        op2.setText(String.valueOf(Html.fromHtml(options.get(increment).get(1))));
                        op3.setText(String.valueOf(Html.fromHtml(options.get(increment).get(2))));
                        op4.setText(String.valueOf(Html.fromHtml(options.get(increment).get(3))));
                    }else{
                        handler1.removeCallbacksAndMessages(null);
                        countDownTimer.cancel();
                        auth_token=getDefaults("_id");
                        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://siddhant.run-ap-south1.goorm.io/").addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit = builder.build();
                        HashMap<String,String> map = new HashMap<>();
//        map.put("username","tjain");
                        map.put("score",String.valueOf(score.getText()));
                        map.put("category",title);
                        final UserInterface client = retrofit.create(UserInterface.class);
                        Call<ResponseBody> call =client.sendscor(auth_token,map);//,map
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Log.i("aftequiz", String.valueOf(response.isSuccessful()));
                                Log.i("aftequiz",String.valueOf(response.code()));
                                Log.i("aftequiz",String.valueOf(response.body()));
                                Log.i("aftequiz",String.valueOf(response.errorBody()));
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        final Dialog dialog = new Dialog(QuizActivity.this);
                                        dialog.setContentView(R.layout.afterq);
                                        dialog.setTitle("Title...");
                                        final TextView scorepo = dialog.findViewById(R.id.scorepop);
                                        scorepo.setText(String.valueOf(score.getText()));
                                        category=dialog.findViewById(R.id.catdia);
                                        category.setText(title);

                                        final Button home = (Button) dialog.findViewById(R.id.pophome);

                                        home.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(QuizActivity.this,BottomNavActivity.class);
                                                startActivity(intent);
                                                overridePendingTransition(0,0);
                                                finish();

                                            }
                                        });
                                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                            @Override
                                            public void onDismiss(DialogInterface dialog) {
                                                home.performClick();
                                            }
                                        });

                                        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                            @Override
                                            public void onCancel(DialogInterface dialog) {

                                               home.performClick();                                        }
                                        });
                                        dialog.show();
                                    }
                                },100);


                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.i("fail", t.getMessage());
                            }
                        });

//                        Intent intent = new Intent(getApplicationContext(),YouScoredActivity.class);
//                        intent.putExtra("score",String.valueOf(score.getText()));
//                        intent.putExtra("title",title);
//                        startActivity(intent);
//                        overridePendingTransition(0,0);
//                        finish();
//                        final Dialog dialog = new Dialog(getApplicationContext());
//                        dialog.setContentView(R.layout.afterq);
//                        dialog.setTitle("Title...");
//                        final TextView scorepo = dialog.findViewById(R.id.scorepop);
//                        scorepo.setText(String.valueOf(score.getText()));
//
//                        Button home = (Button) dialog.findViewById(R.id.pophome);
//
//                       home.setOnClickListener(new View.OnClickListener() {
//                           @Override
//                           public void onClick(View v) {
//
//                           }
//                       });
//                        dialog.show();


                    }


                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        opt.startAnimation(animFadeIn1);



    }
    public void setForTimer(Boolean forTimer1){
        if(forTimer1) {

        }else{

        }
    }
    Bitmap drawable_from_url(String url) throws java.net.MalformedURLException, java.io.IOException {

        HttpURLConnection connection = (HttpURLConnection)new URL(url) .openConnection();
        connection.setRequestProperty("User-agent","Mozilla/4.0");

        connection.connect();
        InputStream input = connection.getInputStream();

        return BitmapFactory.decodeStream(input);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_quiz);
        s_sign=findViewById(R.id.s_sign);
        gauge=findViewById(R.id.gauge);
        stepsView=findViewById(R.id.spb);
        stepsView.setLabels(labels).setBarColorIndicator(Color.parseColor("#E6E5E6"))
                .setProgressColorIndicator(Color.parseColor("#38A4EC"))
                .setLabelColorIndicator(R.color.orange)
                .setCompletedPosition(0)
                .drawView();

        circ=findViewById(R.id.circ);
        textView = findViewById(R.id.textView);
        circ.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        link=findViewById(R.id.link);
//        try {
//            Drawable mDrawable = new BitmapDrawable(getResources(), drawable_from_url(getIntent().getStringExtra("link"))); link.setBackground(mDrawable);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        questiontext=findViewById(R.id.question);
        progressBar=findViewById(R.id.progressBar2);
        score = findViewById(R.id.score);
        op1=findViewById(R.id.op1);
        op2=findViewById(R.id.op2);
        op3=findViewById(R.id.op3);
        op4=findViewById(R.id.op4);
        op1.setVisibility(View.INVISIBLE);
        op2.setVisibility(View.INVISIBLE);
        op3.setVisibility(View.INVISIBLE);
        op4.setVisibility(View.INVISIBLE);
        questiontext.setVisibility(View.INVISIBLE);
        title = getIntent().getStringExtra("title");
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://opentdb.com").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        UserInterface client = retrofit.create(UserInterface.class);
        Call<JsonObject> responseBodyCall = client.getques("5",getIntent().getStringExtra("code"),"multiple");

        responseBodyCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, final Response<JsonObject> response) {
                Log.i("opentd", String.valueOf(response.isSuccessful()));
                Log.i("opentd", String.valueOf(response.body().get("results").getAsJsonArray().get(1).getAsJsonObject().get("question").toString()));
                Log.i("opentd", String.valueOf(response.body().get("results").getAsJsonArray()));
                circ=findViewById(R.id.circ);
                textView = findViewById(R.id.textView);
                progressBar.setVisibility(View.INVISIBLE);
                final Animation animFadeIn1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                final Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                textView.startAnimation(animFadeIn);
               // circ.startAnimation(animFadeIn);
                textView.setText("3");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.startAnimation(animFadeIn);
                        circ.startAnimation(animFadeIn);
                        textView.setText("3");
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                textView.startAnimation(animFadeIn);
                                circ.startAnimation(animFadeIn);
                                textView.setText("2");
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        textView.startAnimation(animFadeIn);
                                        circ.startAnimation(animFadeIn);
                                        textView.setText("1");
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                textView.startAnimation(animFadeIn);
                                                circ.startAnimation(animFadeIn);
                                                textView.setText("GO");
                                                circ.setVisibility(View.INVISIBLE);
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        op1.setVisibility(View.VISIBLE);
                                                        op2.setVisibility(View.VISIBLE);
                                                        op3.setVisibility(View.VISIBLE);
                                                        op4.setVisibility(View.VISIBLE);
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        questiontext.setVisibility(View.VISIBLE);
                                                        questions = new ArrayList<>();
                                                        answers = new ArrayList<>();
                                                        choices = new ArrayList<JsonArray>();
                                                        options = new ArrayList<>();
                                                        tempoptions = new ArrayList<>();
                                                        tempoptions1 = new ArrayList<>();
                                                        tempoptions2 = new ArrayList<>();
                                                        tempoptions3 = new ArrayList<>();
                                                        tempoptions4 = new ArrayList<>();
                                                        for (int i = 0; i < 5; i++) {
                                                            questionsString = String.valueOf(Html.fromHtml(response.body().get("results").getAsJsonArray().get(i).getAsJsonObject().get("question").toString()));
                                                            answerString = String.valueOf(Html.fromHtml(response.body().get("results").getAsJsonArray().get(i).getAsJsonObject().get("correct_answer").toString()));
                                                            questions.add(questionsString.substring(1, questionsString.length() - 1));
                                                            answers.add(answerString.substring(1, answerString.length() - 1));
                                                            choices.add(response.body().get("results").getAsJsonArray().get(i).getAsJsonObject().get("incorrect_answers").getAsJsonArray());

                                                        }

                                                        options.add(tempoptions);
                                                        options.add(tempoptions1);
                                                        options.add(tempoptions2);
                                                        options.add(tempoptions3);
                                                        options.add(tempoptions4);

                                                        for (int i = 0; i < 5; i++) {
                                                            Random random1 = new Random();
                                                            int randomNumber1 = random1.nextInt(4);
                                                            for (int j = 0; j < 3; j++) {
                                                                optionsString = String.valueOf(Html.fromHtml(String.valueOf(choices.get(i).get(j))));
                                                                options.get(i).add(optionsString.substring(1, optionsString.length() - 1));
                                                            }
                                                            options.get(i).add(randomNumber1, answers.get(i));
                                                            Log.i("sol", options.toString());
                                                            Log.i("question " + String.valueOf(i), questions.get(i).toString());
                                                            Log.i("answer " + String.valueOf(i), answers.get(i).toString());
                                                        }
                                                        Log.i("questions", String.valueOf(questions));
                                                        Log.i("options", String.valueOf(options));
                                                        Log.i("answers", String.valueOf(answers));
                                                        questiontext.setText(String.valueOf(questions.get(increment)));
                                                        op1.setText(String.valueOf(options.get(increment).get(0).toString()));
                                                        op2.setText(String.valueOf(Html.fromHtml(options.get(increment).get(1))));
                                                        op3.setText(String.valueOf(Html.fromHtml(options.get(increment).get(2))));
                                                        op4.setText(String.valueOf(Html.fromHtml(options.get(increment).get(3))));
                                                        op1.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                Log.i("number", String.valueOf(increment));
                                                                checkinAndIncreasing(op1);
                                                            }
                                                        });
                                                        op2.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                Log.i("number", String.valueOf(increment));
                                                                checkinAndIncreasing(op2);
                                                            }
                                                        });
                                                        op3.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                Log.i("number", String.valueOf(increment));
                                                                checkinAndIncreasing(op3);
                                                            }
                                                        });
                                                        op4.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                Log.i("number", String.valueOf(increment));
                                                                checkinAndIncreasing(op4);
                                                            }
                                                        });
                                                        counttime = findViewById(R.id.counttime);
                                                        counttime.setVisibility(View.VISIBLE);
                                                        stepsView = findViewById(R.id.spb);
                                                        stepsView.setVisibility(View.VISIBLE);
                                                        scoreText = findViewById(R.id.textView2);
                                                        s_sign.setVisibility(View.VISIBLE);
                                                        scoreText.setVisibility(View.VISIBLE);
                                                        score.setVisibility(View.VISIBLE);
                                                        gauge.setVisibility(View.VISIBLE);
                                                        seconds = findViewById(R.id.seconds);
                                                        seconds.setVisibility(View.VISIBLE);
                                                        countDownTimer =new CountDownTimer(60000, 1000) {
                                                            @Override
                                                            public void onTick(long millisUntilFinished) {
                                                                gauge.setValue(counter);
                                                                counttime.setText(String.valueOf(counter));
                                                                counter--;
                                                            }

                                                            @Override
                                                            public void onFinish() {
                                                                counttime.setText("0");
                                                            }
                                                        }.start();

                                                        handler1 = new Handler();
                                                        handler1.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                auth_token=getDefaults("_id");
                                                                Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://siddhant.run-ap-south1.goorm.io/").addConverterFactory(GsonConverterFactory.create());
                                                                Retrofit retrofit = builder.build();
                                                                HashMap<String,String> map = new HashMap<>();
//        map.put("username","tjain");
                                                                map.put("score",String.valueOf(score.getText()));
                                                                map.put("category",title);
                                                                final UserInterface client = retrofit.create(UserInterface.class);
                                                                Call<ResponseBody> call =client.sendscor(auth_token,map);//,map
                                                                call.enqueue(new Callback<ResponseBody>() {
                                                                    @Override
                                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                        Log.i("aftequiz", String.valueOf(response.isSuccessful()));
                                                                        Log.i("aftequiz",String.valueOf(response.code()));
                                                                        Log.i("aftequiz",String.valueOf(response.body()));
                                                                        Log.i("aftequiz",String.valueOf(response.errorBody()));
                                                                        new Handler().postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                final Dialog dialog = new Dialog(QuizActivity.this);
                                                                                dialog.setContentView(R.layout.afterq);
                                                                                dialog.setTitle("Title...");
                                                                                final TextView scorepo = dialog.findViewById(R.id.scorepop);
                                                                                scorepo.setText(String.valueOf(score.getText()));

                                                                                final Button home = (Button) dialog.findViewById(R.id.pophome);

                                                                                home.setOnClickListener(new View.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(View v) {
                                                                                        Intent intent = new Intent(QuizActivity.this,BottomNavActivity.class);
                                                                                        startActivity(intent);
                                                                                        overridePendingTransition(0,0);
                                                                                        finish();

                                                                                    }
                                                                                });
                                                                                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                                                    @Override
                                                                                    public void onDismiss(DialogInterface dialog) {
                                                                                        home.performClick();
//                                                                                        Intent intent = new Intent(QuizActivity.this,BottomNavActivity.class);
//                                                                                        startActivity(intent);
//                                                                                        overridePendingTransition(0,0);
//                                                                                        QuizActivity.this.finish();
                                                                                    }
                                                                                });

                                                                                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                                                    @Override
                                                                                    public void onCancel(DialogInterface dialog) {
                                                                                        home.performClick();
//                                                                                        Intent intent = new Intent(QuizActivity.this,BottomNavActivity.class);
//                                                                                        startActivity(intent);
//                                                                                        overridePendingTransition(0,0);
//                                                                                        QuizActivity.this.finish();
                                                                                    }
                                                                                });
                                                                                dialog.show();
                                                                            }
                                                                        },100);


                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                                        Log.i("fail", t.getMessage());
                                                                    }
                                                                });
                                                            }
                                                        }, 60000);


                                                    }
                                                }, 1000);
                                            }

                                        }, 1000);
                                    }
                                }, 1000);
                            }
                        }, 1000);
                    }
                },1000);

            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i("opentd",t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}