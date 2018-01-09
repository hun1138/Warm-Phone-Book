package com.example.parks.warm_phone_book;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Parks on 2018-01-03.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "warmphone.db";
    private static final String TABLE_CONTACTS = "contacts";

    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_ANNIVERSARY = "anniversary";
    private static final String KEY_CALENDAR = "calendar";
    private static final String KEY_MEMO = "memo";

    private static final int DB_MODE = Context.MODE_PRIVATE;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_DATE + " TEXT," + KEY_ANNIVERSARY + " TEXT," +  KEY_CALENDAR + " TEXT," + KEY_MEMO + " TEXT" + ");";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public void addContact(PersonInfo personInfo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, personInfo.getCallDday());
        values.put(KEY_ANNIVERSARY, personInfo.getAnniversary());
        values.put(KEY_CALENDAR, personInfo.getCalendar());
        values.put(KEY_MEMO, personInfo.getMemo());
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    public PersonInfo getPerson(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] {KEY_ID, KEY_DATE, KEY_ANNIVERSARY, KEY_CALENDAR, KEY_MEMO}, KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        PersonInfo personInfo = new PersonInfo();
        personInfo.setId(Integer.parseInt(cursor.getString(0)));
        personInfo.setCallDday(cursor.getString(3));
        return personInfo;
    }

    public ArrayList<PersonInfo> getAllContacts(){
        ArrayList<PersonInfo> personInfoList = new ArrayList<PersonInfo>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                PersonInfo personInfo = new PersonInfo();
                personInfo.setId(Integer.parseInt(cursor.getString(0)));
                personInfo.setCallDday(cursor.getString(3));
                personInfoList.add(personInfo);
            }while(cursor.moveToNext());
        }
        return personInfoList;
    }

    public int getPersonCount(){
        String countQery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQery, null);
        cursor.close();
        return cursor.getCount();
    }

    public int updatePersonInfo(PersonInfo personInfo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MEMO, personInfo.getMemo());
        values.put(KEY_CALENDAR, personInfo.getCalendar());
        values.put(KEY_ANNIVERSARY, personInfo.getAnniversary());
        values.put(KEY_DATE, personInfo.getCallDday());
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?", new String[] {String.valueOf(personInfo.getId())});
    }

    public void deletePersonInfo(PersonInfo personInfo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[] {String.valueOf(personInfo.getId())});
        db.close();
    }

    public void DebugPersonDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db.isOpen()){
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);
            while(c!=null && c.moveToNext()){
                Log.i("PersonInfo", "ID : "+ c.getString(0) + ", NAME : " + c.getString(1) +
                        ", PHONENUMBER : " + c.getString(2) + ", DATE : " + c.getString(3));
            }
            c.close();
        }
        else{
            Log.i("DataBase", "closd Database");
        }
    }
}
