package com.indianapp.quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

import java.util.ArrayList;

public class BottomNavActivity extends AppCompatActivity  {
    SharedPreferences preferences;
    MeowBottomNavigation meo;
    RelativeLayout lin_id;
    PathModel outline;
    ArrayList<MeowBottomNavigation.Model> modelArrayList;
    Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        meo=findViewById(R.id.bottom_nav);
        meo.show(1,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,new homefrag()).commit();
        meo.add(new MeowBottomNavigation.Model(1,R.drawable.home));
        meo.add(new MeowBottomNavigation.Model(3,R.drawable.category));
        meo.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_leaderboard_24));

        meo.add(new MeowBottomNavigation.Model(4,R.drawable.history));
        modelArrayList=new ArrayList<>();
        modelArrayList.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_leaderboard_24));
        modelArrayList.add(new MeowBottomNavigation.Model(4,R.drawable.history));
        selectedFragment=null;
        Log.i("info", String.valueOf(selectedFragment));
        meo.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
               case 1:
                   selectedFragment = new homefrag();

                   break;
               case 3:
                   selectedFragment = new catfrag();
                   break;
               case 2:
                   selectedFragment = new LeaderFrag();
                   break;
               case 4:
                   selectedFragment = new HistoryFrag();
                   break;
//               case R.id.logout:
//                   setDefaults("_id", "null", BottomNavActivity.this);
//                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                   startActivity(intent);
//                   overridePendingTransition(0, 0);
//                   finish();
           }
           getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,selectedFragment).commit();
            }
        });

        meo.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

            }
        });

        meo.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                // your codes
            }
        });
//        bottomNavigationView = findViewById(R.id.bottom_nav);
//        fab=findViewById(R.id.fab);
//        fab1=findViewById(R.id.fab1);
//        fab2=findViewById(R.id.fab2);
//        lin_id=findViewById(R.id.lin_id);
//        bottomNavigationView.inflateMenu(R.menu.menu);
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }
//    private void animateBottomIcon(int itemIndex, boolean isChecked,int id) {
//        final View view = menuView.getChildAt(itemIndex).findViewById(id);
//        ObjectAnimator translateUpAnimator = ObjectAnimator.ofFloat(view, "translationY",
//                0,
//                (float) (-(bottomNavigationView.getHeight() / 2))).setDuration(500);
//        if(!isChecked) {
//            translateUpAnimator.start();
//        }
//        if(itemIndex != -1) {
//            final View currentView = menuView.getChildAt(itemIndex).findViewById(id);
//            ObjectAnimator translateDownAnimator = ObjectAnimator.ofFloat(currentView, "translationY",
//                    0,
//                    (float) (-(bottomNavigationView.getHeight() / 2))).setDuration(500);
//            if (!isChecked) {
//                translateDownAnimator.reverse();
//            }
//        }
//    }
//
//   private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
//       @Override
//       public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//           switch (item.getItemId()) {
//               case R.id.home:
//                   selectedFragment = new homefrag();
//
//                   break;
//
//
//               case R.id.category:
//                   selectedFragment = new catfrag();
//                   break;
//               case R.id.leaderboard:
//                   selectedFragment = new LeaderFrag();
//                   break;
//               case R.id.history:
//                   selectedFragment = new HistoryFrag();
//                   break;
//               case R.id.logout:
//                   setDefaults("_id", "", BottomNavActivity.this);
//                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                   startActivity(intent);
//                   overridePendingTransition(0, 0);
//                   finish();
//                   return true;
//
//           }
//           animateBottomIcon(3,item.isChecked(),item.getItemId());
//           getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,selectedFragment).commit();
//           return true;
//       }
//   };
    public void setDefaults(String key, String value, Context context) {
        preferences = this.getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
//
    @Override
    public void onBackPressed() {

        if(selectedFragment==null){
            finishAffinity();
        }else{
            Intent intent = new Intent(getApplicationContext(),BottomNavActivity.class);
            startActivity(intent);
            overridePendingTransition(0,0);
            finish();
        }
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId())
//        {
//                case R.id.leaderboard:
//                    draw(6);
//                    lin_id.setX(bottomNavigationView.mFirstCurveControlPoint1.x);
//                    fab.setVisibility(View.VISIBLE);
//                    fab1.setVisibility(View.GONE);
//                    fab2.setVisibility(View.GONE);
//                    drawAnimation(fab);
//                    break;
//                case R.id.home:
//                    draw(2);
//                    lin_id.setX(bottomNavigationView.mFirstCurveControlPoint1.x);
//                    fab1.setVisibility(View.VISIBLE);
//                    fab.setVisibility(View.GONE);
//                    fab2.setVisibility(View.GONE);
//                    drawAnimation(fab1);
//                   break;
//                case R.id.history:
//                    draw();
//                    lin_id.setX(bottomNavigationView.mFirstCurveControlPoint1.x);
//                    fab2.setVisibility(View.VISIBLE);
//                    fab1.setVisibility(View.GONE);
//                    fab.setVisibility(View.GONE);
//                    drawAnimation(fab2);
//                   break;
//        }
//        return true;
//    }
//
//    private void draw() {
//        bottomNavigationView.mFirstCurveStartPoint.set((bottomNavigationView.mNavigationBarWidth* 10/12)-(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)-(bottomNavigationView.CURVE_CIRCLE_RADIUS/3),0);
//        bottomNavigationView.mFirstCurveEndPoint.set((bottomNavigationView.mNavigationBarWidth* 10/12),(bottomNavigationView.CURVE_CIRCLE_RADIUS)+(bottomNavigationView.CURVE_CIRCLE_RADIUS/4));
//        bottomNavigationView.mSecondCurveStartPoint = bottomNavigationView.mFirstCurveEndPoint;
//        bottomNavigationView.mSecondCurveEndPoint.set((bottomNavigationView.mNavigationBarWidth* 10/12)+(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)+(bottomNavigationView.CURVE_CIRCLE_RADIUS/3),0);
//        bottomNavigationView.mFirstCurveControlPoint1.set((bottomNavigationView.mFirstCurveStartPoint.x)+bottomNavigationView.CURVE_CIRCLE_RADIUS+(bottomNavigationView.CURVE_CIRCLE_RADIUS/4),bottomNavigationView.mFirstCurveStartPoint.y);
//        bottomNavigationView.mFirstCurveControlPoint2.set(bottomNavigationView.mFirstCurveEndPoint.x-(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)+bottomNavigationView.CURVE_CIRCLE_RADIUS,bottomNavigationView.mFirstCurveEndPoint.y);
//
//        bottomNavigationView.mSecondCurveControlPoint1.set((bottomNavigationView.mSecondCurveStartPoint.x)+(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)-bottomNavigationView.CURVE_CIRCLE_RADIUS,bottomNavigationView.mSecondCurveEndPoint.y);
//        bottomNavigationView.mSecondCurveControlPoint2.set(bottomNavigationView.mSecondCurveEndPoint.x-bottomNavigationView.CURVE_CIRCLE_RADIUS+(bottomNavigationView.CURVE_CIRCLE_RADIUS/4),bottomNavigationView.mFirstCurveStartPoint.y);
//
//
//    }
//
//    private void drawAnimation(final VectorMasterView fab) {
//        outline=fab.getPathModelByName("outline");
//        outline.setStrokeColor(Color.WHITE);
//        outline.setTrimPathEnd(0.0f);
//        final ValueAnimator valueAnimator =ValueAnimator.ofFloat(0.0f,1.0f);
//        valueAnimator.setDuration(1000);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                outline.setTrimPathEnd((Float) valueAnimator.getAnimatedValue());
//                fab.update();
//            }
//        });
//    }
//
//    private void draw(int i) {
//        bottomNavigationView.mFirstCurveStartPoint.set((bottomNavigationView.mNavigationBarWidth/i)-(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)-(bottomNavigationView.CURVE_CIRCLE_RADIUS/3),0);
//        bottomNavigationView.mFirstCurveEndPoint.set((bottomNavigationView.mNavigationBarWidth/i),(bottomNavigationView.CURVE_CIRCLE_RADIUS)+(bottomNavigationView.CURVE_CIRCLE_RADIUS/4));
//        bottomNavigationView.mSecondCurveStartPoint = bottomNavigationView.mFirstCurveEndPoint;
//        bottomNavigationView.mSecondCurveEndPoint.set((bottomNavigationView.mNavigationBarWidth/i)+(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)+(bottomNavigationView.CURVE_CIRCLE_RADIUS/3),0);
//        bottomNavigationView.mFirstCurveControlPoint1.set((bottomNavigationView.mFirstCurveStartPoint.x)+bottomNavigationView.CURVE_CIRCLE_RADIUS+(bottomNavigationView.CURVE_CIRCLE_RADIUS/4),bottomNavigationView.mFirstCurveStartPoint.y);
//        bottomNavigationView.mFirstCurveControlPoint2.set(bottomNavigationView.mFirstCurveEndPoint.x-(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)+bottomNavigationView.CURVE_CIRCLE_RADIUS,bottomNavigationView.mFirstCurveEndPoint.y);
//
//        bottomNavigationView.mSecondCurveControlPoint1.set((bottomNavigationView.mSecondCurveStartPoint.x)+(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)-bottomNavigationView.CURVE_CIRCLE_RADIUS,bottomNavigationView.mSecondCurveEndPoint.y);
//        bottomNavigationView.mSecondCurveControlPoint2.set(bottomNavigationView.mSecondCurveEndPoint.x-bottomNavigationView.CURVE_CIRCLE_RADIUS+(bottomNavigationView.CURVE_CIRCLE_RADIUS/4),bottomNavigationView.mFirstCurveStartPoint.y);
//
//
//    }
}
