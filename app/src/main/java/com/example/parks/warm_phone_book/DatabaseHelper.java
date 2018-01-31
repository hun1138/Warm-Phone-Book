package com.example.parks.warm_phone_book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Parks on 2018-02-01.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "user_db";

    private static final String CONTACT_TABLE = "tb_contact";
    private static final String KEY_ID = "id";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_DATE = "date";

    private static final String CALENDAR_TABLE = "tb_calender";
    private static final String KEY_PRIMARY = "pid";
    private static final String KEY_TIME = "time";
    private static final String KEY_PLACE = "place";
    private static final String KEY_MEMO = "memo";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String userSql = "CREATE TABLE " + CONTACT_TABLE + "("
                + KEY_ID +        " INTEGER NOT NULL,"
                + KEY_BIRTHDAY + " CHARACTER(10),"
                + KEY_DATE +      " CHARACTER(10)" + ")";

        String calenSql = "CREATE TABLE " + CALENDAR_TABLE + "("
                + KEY_PRIMARY +  " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ID +       " INTEGER NOT NULL,"
                + KEY_DATE +     " CHARACTER(10),"
                + KEY_TIME +     " CHARACTER(5),"
                + KEY_PLACE +    " varchar(15),"
                + KEY_MEMO +     " TEXT" + ")";

        db.execSQL(userSql);
        db.execSQL(calenSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion == DATABASE_VERSION){
            String dropUserSql = "drop table " + CONTACT_TABLE;
            String dropCalenSql = "drop table " + CALENDAR_TABLE;
            db.execSQL(dropUserSql);
            db.execSQL(dropCalenSql);
            onCreate(db);
        }
    }

    //>>: UserDatabase 추가 구문
    //>>: 과연 Birthday에 null값을 집어넣을수 있을까
    public void addUserDB(PersonInfo info){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, info.getId());
        values.put(KEY_BIRTHDAY, "");
        values.put(KEY_DATE, info.getCallDday());

        db.insert(CONTACT_TABLE, null, values);
        db.close();
    }

    //>> : 생일 업데이트 구문
    public void addUserBirthDay(int id, String date){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BIRTHDAY, date);

        db.update(CONTACT_TABLE, values, KEY_ID + " = ?",
                new String[] {String.valueOf(id)});
        db.close();
    }

    //>> : 생일 삭제 (값을 ""으로 준다);
    public void deleteUserBirthDay(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BIRTHDAY, "");

        db.update(CONTACT_TABLE, values, KEY_ID + " = ?",
                new String[] {String.valueOf(id)});
        db.close();
    }

    //>>: 전화한 날짜 id값으로 검색하는 구문
    public String findUserCallday(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(CONTACT_TABLE, new String[] {KEY_DATE}, KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor.getString(0);
    }

    //>>: 생일 검색하는 구문
    public String findUserBirthday(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(CONTACT_TABLE, new String[] {KEY_BIRTHDAY}, KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor.getString(0);
    }

    //>> : 일정표 추가 구문
    public void addCalenderDB(PersonInfo info, CalendarInfo calendarInfo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, info.getId());
        values.put(KEY_DATE, calendarInfo.getDate());
        values.put(KEY_TIME, calendarInfo.getTime());
        values.put(KEY_PLACE, calendarInfo.getPlace());
        values.put(KEY_MEMO, calendarInfo.getMemo());

        db.insert(CALENDAR_TABLE,null, values);
        db.close();
    }

    //>>: 일정 하나 삭제하는 구문
    //>>: 사람의 id값이 아닌 그 고유한 primaryKey로 삭제시킴.
    public void deleteCalendar(int primaryKey){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CALENDAR_TABLE, "pid=?", new String[] {String.valueOf(primaryKey)});
        Log.i("DelCalendar", "ID : " + String.valueOf(primaryKey) + " 인 사람의 일정표가 삭제되었습니다");
        db.close();
    }

    //>>: 사람 고유 아이디 값을 모든 일정 삭제하는 구문
    public void deleteAllCalendar(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CALENDAR_TABLE, "id=?", new String[] {String.valueOf(id)});
        db.close();
    }

    //>>: 일정표를 id값으로 검색하는 구문
    public ArrayList<CalendarInfo> findCalendars(int id){
        ArrayList<CalendarInfo> calendarInfos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            Cursor cursor = db.query(CALENDAR_TABLE, new String[] {KEY_ID, KEY_DATE, KEY_TIME, KEY_PLACE, KEY_MEMO}, KEY_ID + "=?",
                    new String[] {String.valueOf(id)}, null,null,null);

            while (cursor!= null && cursor.moveToNext()){
                CalendarInfo info = new CalendarInfo();
                info.setDate(cursor.getString(1));
                info.setTime(cursor.getString(2));
                info.setPlace(cursor.getString(3));
                info.setMemo(cursor.getString(4));
                calendarInfos.add(info);
            }
            cursor.close();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        db.close();
        return calendarInfos;
    }

}