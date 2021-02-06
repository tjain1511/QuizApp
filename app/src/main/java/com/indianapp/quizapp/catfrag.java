package com.indianapp.quizapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class catfrag extends Fragment {
    RecyclerView dataList;
    List<String> titles;
    List<String> url;
    List<Integer> images;
    HashMap<String, String> categoryCode ;
    com.indianapp.quizapp.Adapter adapter;
    SharedPreferences preferences;
    View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.activity_grid_view,container,false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        dataList = rootView.findViewById(R.id.dataList);

        titles = new ArrayList<>();
        images = new ArrayList<>();
        url=new ArrayList<>();
        categoryCode=new HashMap<>();
        categoryCode.put("General Knowledge","9");
        categoryCode.put("Books","10");
        categoryCode.put("Film","11");
        categoryCode.put("Music","12");
        categoryCode.put("Theaters","13");
        categoryCode.put("Television","14");
        categoryCode.put("Video Games","15");
        categoryCode.put("Board Games","16");
        categoryCode.put("Science & Nature","17");
        categoryCode.put("Computers","18");
        categoryCode.put("Mathematics","19");
        categoryCode.put("Mythology","20");
        categoryCode.put("Sports","21");
        categoryCode.put("Geography","22");
        categoryCode.put("History","23");
        categoryCode.put("Politics","24");
        categoryCode.put("Art","25");
        categoryCode.put("Celebrities","26");
        categoryCode.put("Animals","27");
        categoryCode.put("Vehicles","28");

        titles.add("General Knowledge");
        titles.add("Books");
        titles.add("Film");
        titles.add("Music");
        titles.add("Theaters");
        titles.add("Television");
        titles.add("Video Games");
        titles.add("Board Games");
        titles.add("Science & Nature");
        titles.add("Computers");
        titles.add("Mathematics");
        titles.add("Mythology");
        titles.add("Sports");
        titles.add("Geography");
        titles.add("History");
        titles.add("Politics");
        titles.add("Art");
        titles.add("Celebrities");
        titles.add("Animals");
        titles.add("Vehicles");


        url.add("https://images.unsplash.com/photo-1574087988579-da02f0b90e37?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        url.add("https://images.unsplash.com/photo-1532012197267-da84d127e765?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        url.add("https://images.unsplash.com/photo-1538152911114-73f1aa6d6128?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=675&q=80");
        url.add("https://images.unsplash.com/photo-1483000805330-4eaf0a0d82da?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=334&q=80");
        url.add("https://images.unsplash.com/photo-1582152492243-a32086530356?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        url.add("https://images.unsplash.com/photo-1576438112307-db9c736ff392?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        url.add("https://images.unsplash.com/photo-1585944348450-35593b4c836d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        url.add("https://images.unsplash.com/photo-1590047698876-b50e8739b346?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        url.add("https://images.unsplash.com/photo-1568226127838-28d6c3a95636?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        url.add("https://images.unsplash.com/photo-1532529867795-3c83442c1e5c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        url.add("https://images.unsplash.com/photo-1528082992860-d520150d6f6c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        url.add("https://images.unsplash.com/photo-1597141797079-5a633a88cf1b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        url.add("https://images.unsplash.com/photo-1480180566821-a7d525cdfc5e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        url.add("https://images.unsplash.com/photo-1569956726918-b36bd5e659b2?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        url.add("https://images.unsplash.com/photo-1577083553466-e749203bc748?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        url.add("https://images.unsplash.com/photo-1534293230397-c067fc201ab8?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        url.add("https://images.unsplash.com/photo-1556850003-5adb86031c6b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        url.add("https://www.gstatic.com/tv/thumb/persons/67369/67369_v9_bb.jpg");
        url.add("https://images.unsplash.com/photo-1488793207478-ff0892419e30?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        url.add("https://images.unsplash.com/photo-1522780209919-d8d8b64e0e9e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
        adapter = new Adapter(getActivity(),titles,categoryCode,url);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);
    }
    public void setDefaults(String key, String value, Context context) {
        preferences = getActivity().getSharedPreferences("com.indianapp.quizapp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    /*Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.logout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logOut_menu) {
            setDefaults("_id","",GridView.this);
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (item.getItemId() == R.id.history_menu) {

            Intent intent = new Intent(getApplicationContext(),HistoryActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (item.getItemId() == R.id.leaderBoard_menu) {

            Intent intent = new Intent(getApplicationContext(),LeaderBoardActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }*/
}