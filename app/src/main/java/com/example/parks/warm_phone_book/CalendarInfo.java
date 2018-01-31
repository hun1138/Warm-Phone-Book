package com.example.parks.warm_phone_book;

/**
 * Created by Parks on 2018-02-01.
 */

public class CalendarInfo {
    private String date;
    private String time;
    private String place;
    private String memo;

    CalendarInfo(String date, String time, String place, String memo){
        this.date = date;
        this.time = time;
        this.place = place;
        this.memo = memo;
    }

    CalendarInfo(){
        this.date = null;
        this.time = null;
        this.place = null;
        this.memo = null;
    }

    public void setDate(String date){
        this.date =date;
    }
    public String getDate(){
        return this.date;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return this.time;
    }
    public void setPlace(String place){
        this.place = place;
    }
    public String getPlace(){
        return this.place;
    }
    public void setMemo(String memo){
        this.memo = memo;
    }
    public String getMemo(){
        return this.memo;
    }
}
