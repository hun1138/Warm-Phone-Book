package com.example.parks.warm_phone_book;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Parks on 2018-01-02.
 */

public class PersonInfo implements Serializable{
    private int id;
    private String name;
    private String phoneNumber;
    private String address;
    private String email;
    private String anniversary; //기념일
    private ArrayList<CalendarInfo> calendarInfos;
    //private ArrayList<String> calendar; //일정표
    private String memo;
    private String callDday;
    private String recentCallDay;

    PersonInfo(int id, String name, String phoneNumber, String address, String email, String anniversary, ArrayList<CalendarInfo> calendarInfos, String memo, String callDday, String recentCallDay){
        this.id =id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.anniversary = anniversary;
        this.calendarInfos = calendarInfos;
        this.memo = memo;
        this.callDday = callDday;
        this.recentCallDay = recentCallDay;
    }

    PersonInfo() {}

    public int getId() {return this.id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}
    public String getPhoneNumber() {return this.phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public String getAddress() {return this.address;}
    public void setAddress(String address) {this.address = address;}
    public String getEmail() {return this.email;}
    public void setEmail(String email) {this.email = email;}
    public String getAnniversary() {return this.anniversary;}
    public void setAnniversary(String anniversary) {this.anniversary = anniversary;}
    //public ArrayList<String> getCalendar() {return this.calendar;}
    //public void setCalendar(ArrayList<String> calendar) {this.calendar = calendar;}
    public String getMemo() {return this.memo;}
    public void setMemo(String memo) {this.memo = memo;}
    public String getCallDday() {return this.callDday;}
    public void setCallDday(String callDday) {this.callDday = callDday;}
    public String getRecentCallDay() {return this.recentCallDay;}
    public void setRecentCallDay(String RecentCallDay){this.recentCallDay = RecentCallDay;}
    public void setCalendarInfos(ArrayList<CalendarInfo> calendarInfos){this.calendarInfos = calendarInfos;}
    public ArrayList<CalendarInfo> getCalendarInfos(){return this.calendarInfos;}
}
