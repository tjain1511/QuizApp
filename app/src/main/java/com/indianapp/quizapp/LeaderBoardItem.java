package com.indianapp.quizapp;

public class LeaderBoardItem {
    Integer mrank;
    String mtotalScore;
    String mname;
    String mtotalGamesPayed;
    public LeaderBoardItem(Integer rank,String totalScore,String name, String totalGamesPayed){
        mrank=rank;
        mtotalScore=totalScore;
        mname=name;
        mtotalGamesPayed=totalGamesPayed;
    }

    public String getMname() {
        return mname;
    }

    public Integer getMrank() {
        return mrank;
    }

    public String getMtotalGamesPayed() {
        return mtotalGamesPayed;
    }

    public String getMtotalScore() {
        return mtotalScore;
    }
}
