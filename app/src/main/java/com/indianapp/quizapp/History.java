package com.indianapp.quizapp;

public class History {
    public String mcategory;
    public String mscore;
    public String mdate;
    public History(String category,String score, String date){
        mcategory=category;
        mscore=score;
        mdate=date;
    }

    public String getMcategory() {
        return mcategory;
    }

    public String getMdate() {
        return mdate;
    }

    public String getMscore() {
        return mscore;
    }
}
