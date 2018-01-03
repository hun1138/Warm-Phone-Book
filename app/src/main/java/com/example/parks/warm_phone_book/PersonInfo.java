package com.example.parks.warm_phone_book;

/**
 * Created by Parks on 2018-01-02.
 */

public class PersonInfo {
    private int id;
    private String name;
    private String phoneNumber;
    private String address;
    private String email;
    private String anniversary;
    private String calendar;
    private String memo;
    private String callDday;

    PersonInfo(int id, String name, String phoneNumber, String address, String email, String anniversary, String calendar, String memo, String callDday){
        this.id =id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.anniversary = anniversary;
        this.calendar = calendar;
        this.memo = memo;
        this.callDday = callDday;
    }

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
    public String getCalendar() {return this.calendar;}
    public void setCalendar(String calendar) {this.calendar = calendar;}
    public String getMemo() {return this.memo;}
    public void setMemo(String memo) {this.memo = memo;}
    public String getCallDday() {return this.callDday;}
    public void setCallDday(String callDday) {this.callDday = callDday;}
}
